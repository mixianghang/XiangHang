package com.run.crawler.weibo.util.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.AccountOperator;
import com.run.crawler.weibo.friendship.create.SinaFriendships;
import com.run.crawler.weibo.friendship.create.TwitterFriendShipCreator;
import com.run.crawler.weibo.frontier.Frontier;
import com.run.crawler.weibo.util.Task;

public class AccountCache extends TimerTask implements AccountCacheInterface {

	BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(500);
	ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L,
			TimeUnit.MINUTES, workQueue);

	private Frontier frontier;

	public void registerFrontier(Frontier frontier) {
		this.frontier = frontier;
	}

	/**
	 * 保存AccountID与重点人账户
	 */
	private Map<Long, Account> id2AccountCache = new ConcurrentHashMap<Long, Account>();

	/**
	 * 当前这一次读取重点人账户
	 */
	private List<Account> currentAccounts = new LinkedList<Account>();

	/**
	 * 当前所有读取重点人账户,用AccountID与文件名区分不同重点人账户文件
	 */
	private Queue<Account> allAccounts = new ConcurrentLinkedQueue<Account>();

	/**
	 * 当前关注、取消关注成功重点人账户
	 */
	private List<Account> successAccounts = new LinkedList<Account>();

	/**
	 * 当前关注、取消关注失败重点人账户
	 */
	private Queue<Account> failedQueue = new ConcurrentLinkedQueue<Account>();

	/**
	 * 保存重点人账户与AccountID
	 */
	private Map<String, List<Long>> account2IdCache = new ConcurrentHashMap<String, List<Long>>();
	private final Logger log = Logger.getLogger(AccountCache.class);
	private static final AccountCache accountCache = new AccountCache();

	/**
	 * 私有化构造方法
	 */

	private AccountCache() {
		try {
			List<Account> accounts = getAll();
			init(accounts);
			Timer timer = new Timer();
			long period = VariableCache.AccountCache.reloadInterval;
			timer.schedule(this, 0, period);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static AccountCache getInstance() {
		return accountCache;
	}

	/**
	 * 正在初始化账户信息
	 * 
	 * @param accounts
	 */
	public void init(List<Account> accounts) {
		if (accounts != null && accounts.size() > 0) {
			log.info("正在初始化账户信息..................................");
			for (Account account : accounts) {
				id2AccountCache.put(account.getAccountId(), account);
			}
			updateAccount2IdCache();
		}
	}

	/**
	 * 初始化账户
	 */
	public void reLoadNewFiles() {
		log.info("正在读取重点人账户文件..................................");
		File folder = new File(VariableCache.AccountCache.accountFolder);
		int count = 0;

		File[] files = folder.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				String f1Name = f1.getName().replaceAll("\\d*_", "")
						.replaceAll(".properties", "");
				String f2Name = f2.getName().replaceAll("\\d*_", "")
						.replaceAll(".properties", "");
				return f1Name.compareTo(f2Name);
			}
		});
		for (File file : files) {
			try {
				/**
				 * 文件是否已经被读取成功
				 */
				if (readPropertiesFile(file)) {
					if (++count > 30)
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				continue;
			}
		}
		updateAccount2IdCache();

		/**
		 * 1.关注与取消关注,产生操作成功与失败的重点人账户, 成功的保存在successAccounts中,失败的保存在failedQueue中
		 */
		processFriends();
		/**
		 * 2.清除当前一次读取重点人账户,这些账户已经处理
		 */
		currentAccounts.clear();

		/**
		 * 3.添加操作成功的重点人任务,同时清除当前处理成功 的重点人账户
		 */
		synchronized (successAccounts) {
			notifyFrontier();
			successAccounts.clear();
		}
		/**
		 * 4.重复执行失败的操作failedQueue
		 */
		synchronized (failedQueue) {
			processFaildFriends();
		}
	}

	/**
	 * 更新Account2IdCache缓存 缓存中保存昵称的小写形式
	 */
	private void updateAccount2IdCache() {
		account2IdCache.clear();
		for (Map.Entry<Long, Account> entry : id2AccountCache.entrySet()) {
			Account account = entry.getValue();
			String key = account.getTask() + ":"
					+ account.getNickName().toLowerCase().trim();
			if (account2IdCache.containsKey(key)) {
				account2IdCache.get(key).add(account.getAccountId());
			} else {
				List<Long> lists = new ArrayList<Long>();
				lists.add(account.getAccountId());
				account2IdCache.put(key, lists);
			}
		}
	}

	/**
	 * 将properties文件内容添加至Cache中
	 * 
	 * @param file
	 */
	private boolean readPropertiesFile(File file) {
		if (file.getName().endsWith(".properties")) {
			Properties properties = new Properties();
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				properties.load(fileInputStream);
				fileInputStream.close();
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			Long accountId = Long
					.parseLong(properties.getProperty("accountId"));
			String accountStr = properties.getProperty("account");
			int category = Integer.parseInt(properties.getProperty("category"));
			int optType = Integer.parseInt(properties.getProperty("optType"));
			Account account = new Account();
			account.setNickName(accountStr);
			account.setAccountId(accountId);
			account.setTask(Task.getTask(category));
			account.setFile(file);

			/**
			 * 重点人账户文件已经被读取
			 */
			if (allAccounts.contains(account)) {
				log.info("文件：" + account.getFile().getName()
						+ " 已经被读取,目前还尚未处理成功!");
				return false;
			}
			switch (optType) {
			case 1:
				account.setOperator(AccountOperator.Add);
				id2AccountCache.put(account.getAccountId(), account);
				break;
			case 2:
				account.setOperator(AccountOperator.Add);
				Account oldAccount = id2AccountCache
						.get(account.getAccountId());
				if (oldAccount != null) {
					oldAccount.setOperator(AccountOperator.Delete);
					currentAccounts.add(oldAccount);
				}
				id2AccountCache.put(account.getAccountId(), account);
				break;
			case 3:
				account.setOperator(AccountOperator.Delete);
				id2AccountCache.remove(account.getAccountId());
				break;
			default:
				break;
			}
			allAccounts.add(account);
			currentAccounts.add(account);
			log.info("AccountId=" + account.getAccountId() + " NickName="
					+ account.getNickName() + " 重点人读取成功!");
			return true;
		}
		return false;
	}

	private void createFriend(final Account account) {
		/**
		 * 如果是新浪用线程池启动，避免造成阻塞
		 */
		switch (account.getTask()) {
		case Sina:
			threadPoolExecutor.execute(new Create(account));
			break;
		default:
			new Create(account).run();
		}
	}

	private void destoryFriend(final Account account) {
		List<Long> ids = getAccountIdList(account.getTask(),
				account.getNickName());
		/**
		 * ids.size()>0表示:此同名重点人,被系统中多个管理员添加, 这时只能将Redis中的数据删除,不能执行取消关注操作
		 */
		if (ids != null && ids.size() > 0) {
			log.info("重点人:" + account.getNickName() + " 被多个管理员添加！");
			afterProcess(true, account);
			return;
		}
		/**
		 * 如果是新浪用线程池启动，避免造成阻塞
		 */
		switch (account.getTask()) {
		case Sina:
			threadPoolExecutor.execute(new Destory(account));
			break;
		default:
			new Destory(account).run();
		}
	}

	/**
	 * 通知Frontier更改任务
	 */
	private void notifyFrontier() {
		if (successAccounts != null && successAccounts.size() > 0) {
			frontier.updateWeiboPage(successAccounts);
		}
	}

	/**
	 * 更新好友
	 */
	private void processFriends() {
		for (Account account : currentAccounts) {
			processFriend(account);
		}
	}

	/**
	 * 更新关注失败、取消关注失败好友
	 */
	private void processFaildFriends() {
		while (failedQueue.size() > 0) {
			Account account = failedQueue.poll();
			if (null == account) {
				return;
			}
			if (account.getFailedNo() < VariableCache.AccountCache.maxFailedNo) {
				log.info("失败后, " + account.getNickName() + " 第"
						+ (account.getFailedNo() + 1) + " 次被处理!");
				processFriend(account);
			} else {
				moveFile(account);
				log.info(account.getNickName() + ": 三次处理均失败!");
			}
		}
		notifyFrontier();
	}

	/**
	 * 更新好友
	 */
	private void processFriend(Account account) {
		try {
			log.info(account.getTask() + ": 重点人处理频率="
					+ account.getTask().getFriendShipProcessInterval());
			Thread.sleep(account.getTask().getFriendShipProcessInterval());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switch (account.getOperator()) {
		case Add:
			createFriend(account);
			break;
		case Delete:
			destoryFriend(account);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据任务与昵称得到重点人AccountId
	 * 
	 * @param task
	 * @param screenName
	 * @return
	 */
	public List<Long> getAccountIdList(Task task, String screenName) {
		String key = task + ":" + screenName.toLowerCase().trim();
		if (account2IdCache.containsKey(key)) {
			return account2IdCache.get(key);
		}
		return null;
	}

	/**
	 * 根据重点人AccountId得到重点人昵称
	 * 
	 * @param accountId
	 * @return
	 */
	public String getScreenName(long accountId) {
		if (id2AccountCache.containsKey(accountId)) {
			return id2AccountCache.get(accountId).getNickName();
		}
		return "";
	}

	private class Create implements Runnable {
		private Account account;

		Create(final Account account) {
			this.account = account;
		}

		@Override
		public void run() {

			/**
			 * 如果Redis中有这个帐号,则不再重复添加
			 */

			if (AccountRedisCache.exit(account)) {
				log.info("accountRedisCache中存在 " + account.getNickName()
						+ " 这个人");
				return;
			}
			boolean processOk = false;
			switch (account.getTask()) {
			case Twitter:
				processOk = TwitterFriendShipCreator.createFriendShip(account
						.getNickName());
				break;
			case Sina:
				processOk = SinaFriendships.create(account.getNickName());
				break;
			default:
				processOk = true;
				break;
			}
			afterProcess(processOk, account);
		}
	}

	private class Destory implements Runnable {
		private Account account;

		Destory(final Account account) {
			this.account = account;
		}

		@Override
		public void run() {
			boolean processOk = false;
			switch (account.getTask()) {
			case Twitter:
				processOk = TwitterFriendShipCreator.destroyFriendShip(account
						.getNickName());
				break;
			case Sina:
				processOk = SinaFriendships.destory(account.getNickName());
				break;
			default:
				processOk = true;
				break;
			}
			afterProcess(processOk, account);
		}
	}

	@Override
	public void run() {
		try {
			this.reLoadNewFiles();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public List<Account> getAll() {
		return AccountRedisCache.getAll();
	}

	/**
	 * 关注、取消关注成功或者三次失败,则移动文件 TODO
	 * 
	 * @param account
	 */
	private void moveFile(Account account) {
		try {
			File file = account.getFile();
			if (file != null) {
				String newPath = VariableCache.AccountCache.bakAccountFolder;
				File newFile = new File(newPath + file.getName());
				if (newFile.exists())
					newFile.delete();
				/**
				 * 已读取文件转移至备份目录下
				 */
				file.renameTo(newFile);
			}
			allAccounts.remove(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 当前重点人是否包含微博账户Id TODO
	 * 
	 * @param account
	 * @return
	 */
	public boolean containsUserid(Account account) {
		Account preAccount = id2AccountCache.get(account.getAccountId());
		if (preAccount.getUserId() != null
				&& !"".equals(preAccount.getUserId())) {
			return true;
		}
		preAccount.setUserId(account.getUserId());
		return false;
	}

	/**
	 * 重点人账户读取后期处理 TODO
	 * 
	 * @param processOk
	 * @param account
	 */
	public void afterProcess(boolean processOk, Account account) {
		/**
		 * 处理成功,则移动文件
		 */
		if (processOk) {
			moveFile(account);
			synchronized (successAccounts) {
				successAccounts.add(account);
			}
			switch (account.getOperator()) {
			case Add:
				AccountRedisCache.add(account);
				break;
			case Delete:
				AccountRedisCache.del(account);
				break;
			default:
				break;
			}
		} else {
			account.setFailedNo(account.getFailedNo() + 1);
			failedQueue.add(account);
		}
	}
}
