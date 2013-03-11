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
	 * 线程池 执行线程前做相应检查工作
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
		System.out.println("afterExecute 执行完毕........");
		super.afterExecute(r, t);
	}
}
