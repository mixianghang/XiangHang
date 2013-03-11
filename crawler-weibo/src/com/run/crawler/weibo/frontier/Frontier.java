package com.run.crawler.weibo.frontier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.framework.Controller;
import com.run.crawler.weibo.rules.Rule;
import com.run.crawler.weibo.rules.impl.RuleChain;
import com.run.crawler.weibo.util.Attribute;
import com.run.crawler.weibo.util.GenWeiboPageFromAccount;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.TimerUtil;
import com.run.crawler.weibo.util.cache.VariableCache;

public class Frontier {

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * startTime - waitTimeԽС(��������ʱ��Խ��)���ȼ�Խ�� ��startTime��ȣ�priorityԽ�����ȼ�Խ��
	 */
	private final PriorityBlockingQueue<WeiboPage> readyList = new PriorityBlockingQueue<WeiboPage>(
			1, new Comparator<WeiboPage>() {
				public int compare(WeiboPage obj1, WeiboPage obj2) {
					if (obj1.getPriority() == obj2.getPriority()) {
						return obj2.then() - obj1.then();
					}
					return obj1.getPriority() - obj2.getPriority();
				}
			});

	private final List<WeiboPage> disableList = new ArrayList<WeiboPage>();
	private final List<WeiboPage> sonList = new ArrayList<WeiboPage>();

	private final RuleChain ruleChain = new RuleChain();

	private final long minRateControl;
	{
		minRateControl = Task.getMinRateControl() <= 0 ? 1000 : Task
				.getMinRateControl();
		final int checkTime = VariableCache.Frontier.checkTime;
		Timer timer = new Timer("frontier");
		/**
		 * ����queue������ĵȴ�ʱ��
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				synchronized (readyList) {
					if (readyList.size() == 0)
						return;
					for (WeiboPage weiboPage : readyList) {
						weiboPage.plusWaitTime(checkTime);
					}
				}
			}

		}, checkTime * TimerUtil._1s, checkTime * TimerUtil._1s);
		/**
		 * ���readyList�����������¼��ؽ�Queue
		 */
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				synchronized (readyList) {
					if (readyList.size() == 0)
						return;
					Iterator<WeiboPage> iterator = readyList.iterator();
					while (iterator.hasNext()) {
						if (update(iterator.next()))
							iterator.remove();
					}
					readyList.addAll(sonList);
					sonList.clear();
				}
			}
		}, minRateControl, minRateControl);

	}

	Controller controller;

	public Frontier(Controller controller) {
		this.controller = controller;
		/**
		 * frontier��ʼ��
		 */
		init();

		/**
		 * ��ʼ��TelnetListener
		 */
		TelnetListener telnetListener = new TelnetListener(this);
		Thread t = new Thread(telnetListener);
		t.start();
	}

	/**
	 * startTime - waitTimeԽС(��������ʱ��Խ��)���ȼ�Խ�� ��startTime��ȣ�priorityԽ�����ȼ�Խ��
	 */
	private final PriorityBlockingQueue<WeiboPage> queue = new PriorityBlockingQueue<WeiboPage>(
			1, new Comparator<WeiboPage>() {
				public int compare(WeiboPage obj1, WeiboPage obj2) {
					if (obj1.getPriority() == obj2.getPriority()) {
						return obj2.then() - obj1.then();
					}
					return obj1.getPriority() - obj2.getPriority();
				}
			});

	public void addRule(Rule rule) {
		ruleChain.add(rule);
	}

	/**
	 * �ַ�����
	 * 
	 * @return
	 */
	public WeiboPage next() {
		WeiboPage weiboPage = null;
		try {
			weiboPage = queue.take();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
		add2ReadyList(weiboPage);
		log.info("next into:" + weiboPage.getAttribute());
		return weiboPage;
	}

	/**
	 * ������ͣ����
	 * 
	 * @param minute
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void pause(long minute) {
		controller.pause(minute);
	}

	/**
	 * ���ַ���������ӽ�readyList��
	 * 
	 * @param weiboPage
	 */
	private void add2ReadyList(WeiboPage weiboPage) {
		/**
		 * ���̷߳�����Ҫͬ��
		 */
		synchronized (readyList) {
			readyList.add(weiboPage);
		}
	}

	/**
	 * ��������״̬
	 * 
	 * @param weiboPage
	 */
	private void add2Queue(WeiboPage weiboPage) {
		weiboPage.setProcessed(false);
		queue.add(weiboPage);
	}

	private void add2disableList(WeiboPage weiboPage) {
		log.error("add2disableList : " + weiboPage);
		disableList.add(weiboPage);
	}

	private void add2SonList(WeiboPage weiboPage) {
		sonList.add(weiboPage);
	}

	/**
	 * ��readyList�����¼�������ʱ���м�� ����״̬����������ص�queue��disableList
	 * 
	 * @param weiboPage
	 */
	private boolean update(WeiboPage weiboPage) {

		/**
		 * �����weiboPageδ�����꣬��������
		 */
		if (!weiboPage.isProcessed())
			return false;

		/**
		 * �����Twitter���ѡ���˿��������
		 */
		if (weiboPage.getSonWeiboPages() != null
				&& weiboPage.getSonWeiboPages().length > 0) {
			for (WeiboPage page : weiboPage.getSonWeiboPages()) {
				add2SonList(page);
			}
			weiboPage.setSonWeiboPages();
		}

		/**
		 * �������������Ѿ�����ɹ���ֱ��ɾ��
		 */
		if (weiboPage.getAttribute() == Attribute.Son) {
			if (weiboPage.getErrorNum() == 0)
				return true;
		}

		/**
		 * ����ȴ�ʱ��û�дﵽ,������
		 */
		if (weiboPage.then() > 0)
			return false;

		/**
		 * ���û�����������Ҫ��,������
		 */
		if (!ruleChain.rule(weiboPage))
			return false;

		/**
		 * ֻ��������Ž�������ʧЧ����,��������Task��ʧ
		 */
		if (weiboPage.isDisAbled() && weiboPage.getAttribute() == Attribute.Son) {
			add2disableList(weiboPage);
		} else {
			add2Queue(weiboPage);
		}

		return true;
	}

	/**
	 * ������������
	 */
	private void init() {
		String fileName = VariableCache.Frontier.TaskFileName;
		List<WeiboPage> list = new FrontierTaskInit(this)
				.getTaskFromFile(fileName);
		this.readyList.addAll(list);
	}

	/**
	 * ��������ӿ�
	 * 
	 * @param list
	 */
	public void updateWeiboPage(List<Account> list) {
		for (Account account : list) {
			switch (account.getOperator()) {
			case Add:
				add(account);
				break;
			case Update:
				update(account);
				break;
			case Delete:
				del(account);
				break;
			}
		}
	}

	/**
	 * �鿴readyList�е���Ϣ
	 * 
	 * @return
	 */
	public List<WeiboPage> lookReadyList() {
		List<WeiboPage> list = new ArrayList<WeiboPage>();
		for (WeiboPage weiboPage : readyList) {
			list.add((WeiboPage) weiboPage.clone());
		}
		return list;
	}

	/**
	 * �鿴disableList�е���Ϣ
	 * 
	 * @return
	 */
	public List<WeiboPage> lookDisableList() {
		List<WeiboPage> list = new ArrayList<WeiboPage>();
		for (WeiboPage weiboPage : disableList) {
			list.add((WeiboPage) weiboPage.clone());
		}
		return list;
	}

	private void add(Account account) {
		synchronized (readyList) {
			readyList.addAll(GenWeiboPageFromAccount.ofAllExceptWeibo(account));
		}
	}

	private void del(Account account) {
		long accountId = account.getAccountId();
		WeiboPage weiboPage;
		synchronized (readyList) {
			Iterator<WeiboPage> iterator = readyList.iterator();
			while (iterator.hasNext()) {
				weiboPage = iterator.next();
				if (weiboPage.getAccountId() == accountId) {
					synchronized (weiboPage) {
						iterator.remove();
					}
				}
			}
		}
	}

	private void update(Account account) {
		del(account);
		add(account);
	}
}
