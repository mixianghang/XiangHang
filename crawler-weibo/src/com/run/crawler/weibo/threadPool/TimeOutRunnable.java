package com.run.crawler.weibo.threadPool;

public class TimeOutRunnable implements Runnable {

	public int i = 0;

	public TimeOutRunnable(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		try {
			System.out.println("TimeOutRunnable " + i + " 正在休眠");
			Thread.sleep(20 * 1000L);
			System.out.println("TimeOutRunnable " + i + " 继续执行");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
