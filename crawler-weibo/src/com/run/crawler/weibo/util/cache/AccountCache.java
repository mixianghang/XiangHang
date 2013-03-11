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
	 * ����AccountID���ص����˻�
	 */
	private Map<Long, Account> id2AccountCache = new ConcurrentHashMap<Long, Account>();

	/**
	 * ��ǰ��һ�ζ�ȡ�ص����˻�
	 */
	private List<Account> currentAccounts = new LinkedList<Account>();

	/**
	 * ��ǰ���ж�ȡ�ص����˻�,��AccountID���ļ������ֲ�ͬ�ص����˻��ļ�
	 */
	private Queue<Account> allAccounts = new ConcurrentLinkedQueue<Account>();

	/**
	 * ��ǰ��ע��ȡ����ע�ɹ��ص����˻�
	 */
	private List<Account> successAccounts = new LinkedList<Account>();

	/**
	 * ��ǰ��ע��ȡ����עʧ���ص����˻�
	 */
	private Queue<Account> failedQueue = new ConcurrentLinkedQueue<Account>();

	/**
	 * �����ص����˻���AccountID
	 */
	private Map<String, List<Long>> account2IdCache = new ConcurrentHashMap<String, List<Long>>();
	private final Logger log = Logger.getLogger(AccountCache.class);
	private static final AccountCache accountCache = new AccountCache();

	/**
	 * ˽�л����췽��
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
	 * ���ڳ�ʼ���˻���Ϣ
	 * 
	 * @param accounts
	 */
	public void init(List<Account> accounts) {
		if (accounts != null && accounts.size() > 0) {
			log.info("���ڳ�ʼ���˻���Ϣ..................................");
			for (Account account : accounts) {
				id2AccountCache.put(account.getAccountId(), account);
			}
			updateAccount2IdCache();
		}
	}

	/**
	 * ��ʼ���˻�
	 */
	public void reLoadNewFiles() {
		log.info("���ڶ�ȡ�ص����˻��ļ�..................................");
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
				 * �ļ��Ƿ��Ѿ�����ȡ�ɹ�
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
		 * 1.��ע��ȡ����ע,���������ɹ���ʧ�ܵ��ص����˻�, �ɹ��ı�����successAccounts��,ʧ�ܵı�����failedQueue��
		 */
		processFriends();
		/**
		 * 2.�����ǰһ�ζ�ȡ�ص����˻�,��Щ�˻��Ѿ�����
		 */
		currentAccounts.clear();

		/**
		 * 3.��Ӳ����ɹ����ص�������,ͬʱ�����ǰ����ɹ� ���ص����˻�
		 */
		synchronized (successAccounts) {
			notifyFrontier();
			successAccounts.clear();
		}
		/**
		 * 4.�ظ�ִ��ʧ�ܵĲ���failedQueue
		 */
		synchronized (failedQueue) {
			processFaildFriends();
		}
	}

	/**
	 * ����Account2IdCache���� �����б����ǳƵ�Сд��ʽ
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
	 * ��properties�ļ����������Cache��
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
			 * �ص����˻��ļ��Ѿ�����ȡ
			 */
			if (allAccounts.contains(account)) {
				log.info("�ļ���" + account.getFile().getName()
						+ " �Ѿ�����ȡ,Ŀǰ����δ����ɹ�!");
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
					+ account.getNickName() + " �ص��˶�ȡ�ɹ�!");
			return true;
		}
		return false;
	}

	private void createFriend(final Account account) {
		/**
		 * ������������̳߳������������������
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
		 * ids.size()>0��ʾ:��ͬ���ص���,��ϵͳ�ж������Ա���, ��ʱֻ�ܽ�Redis�е�����ɾ��,����ִ��ȡ����ע����
		 */
		if (ids != null && ids.size() > 0) {
			log.info("�ص���:" + account.getNickName() + " ���������Ա��ӣ�");
			afterProcess(true, account);
			return;
		}
		/**
		 * ������������̳߳������������������
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
	 * ֪ͨFrontier��������
	 */
	private void notifyFrontier() {
		if (successAccounts != null && successAccounts.size() > 0) {
			frontier.updateWeiboPage(successAccounts);
		}
	}

	/**
	 * ���º���
	 */
	private void processFriends() {
		for (Account account : currentAccounts) {
			processFriend(account);
		}
	}

	/**
	 * ���¹�עʧ�ܡ�ȡ����עʧ�ܺ���
	 */
	private void processFaildFriends() {
		while (failedQueue.size() > 0) {
			Account account = failedQueue.poll();
			if (null == account) {
				return;
			}
			if (account.getFailedNo() < VariableCache.AccountCache.maxFailedNo) {
				log.info("ʧ�ܺ�, " + account.getNickName() + " ��"
						+ (account.getFailedNo() + 1) + " �α�����!");
				processFriend(account);
			} else {
				moveFile(account);
				log.info(account.getNickName() + ": ���δ����ʧ��!");
			}
		}
		notifyFrontier();
	}

	/**
	 * ���º���
	 */
	private void processFriend(Account account) {
		try {
			log.info(account.getTask() + ": �ص��˴���Ƶ��="
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
	 * �����������ǳƵõ��ص���AccountId
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
	 * �����ص���AccountId�õ��ص����ǳ�
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
			 * ���Redis��������ʺ�,�����ظ����
			 */

			if (AccountRedisCache.exit(account)) {
				log.info("accountRedisCache�д��� " + account.getNickName()
						+ " �����");
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
	 * ��ע��ȡ����ע�ɹ���������ʧ��,���ƶ��ļ� TODO
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
				 * �Ѷ�ȡ�ļ�ת��������Ŀ¼��
				 */
				file.renameTo(newFile);
			}
			allAccounts.remove(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ǰ�ص����Ƿ����΢���˻�Id TODO
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
	 * �ص����˻���ȡ���ڴ��� TODO
	 * 
	 * @param processOk
	 * @param account
	 */
	public void afterProcess(boolean processOk, Account account) {
		/**
		 * ����ɹ�,���ƶ��ļ�
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
