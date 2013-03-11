package com.run.crawler.weibo.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestTimeOutThreadPool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		TimeOutThreadPoolExecutor timeOutThreadPoolExecutor = new TimeOutThreadPoolExecutor(3,3,0,TimeUnit.SECONDS,workQueue);
		
		for(int i=0;i<10;i++){
			Runnable runnable = new TimeOutRunnable(i);
			timeOutThreadPoolExecutor.execute(runnable);
		}
		timeOutThreadPoolExecutor.shutdown();
	}

}
