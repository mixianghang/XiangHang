package com.run.crawler.weibo.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeOutThreadPoolExecutor extends ThreadPoolExecutor {


	public TimeOutThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	/**
	 * �̳߳� ִ���߳�ǰ����Ӧ��鹤��
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		try {
			t.join(5*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(t.isAlive()){
			t.interrupt();
		}
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		System.out.println("afterExecute ִ�����........");
		super.afterExecute(r, t);
	}
}
