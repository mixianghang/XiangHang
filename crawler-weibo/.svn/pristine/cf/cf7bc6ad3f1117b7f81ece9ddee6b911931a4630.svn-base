package com.run.crawler.weibo.framework;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.frontier.Frontier;
import com.run.crawler.weibo.rules.impl.CheckNeteaseFrontierRule;
import com.run.crawler.weibo.rules.impl.CheckSinaFrontierRule;
import com.run.crawler.weibo.rules.impl.CheckSohuFrontierRule;
import com.run.crawler.weibo.rules.impl.CheckTencentFrontierRule;
import com.run.crawler.weibo.rules.impl.CheckTwitterFrontierRule;
import com.run.crawler.weibo.rules.impl.CheckWeiboPageIsStartNowRule;
import com.run.crawler.weibo.rules.impl.FrontierDisableRule;
import com.run.crawler.weibo.util.cache.VariableCache;

public class Controller {

	Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private boolean isPaused = false;
	private ReentrantLock pauseLock = new ReentrantLock();
	private Condition unpaused = pauseLock.newCondition();

	
	@SuppressWarnings("unused")
	private long time;
	@SuppressWarnings("unused")
	private TimeUnit timeUnit;

	private final int corePoolSize = VariableCache.Controller.corePoolSize;
	private final int maximumPoolSize = VariableCache.Controller.maximumPoolSize;
	private final long keepAliveTime = VariableCache.Controller.keepAliveTime;
	private final int blockingQueueSize = VariableCache.Controller.blockingQueueSize;
	private final TimeUnit unit = TimeUnit.MINUTES;
	private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(
			blockingQueueSize);
	
	private final BlockingQueue<Runnable> sinaWorkQueue = new LinkedBlockingQueue<Runnable>(
			blockingQueueSize);

	private final ThreadPoolExecutor PausableThreadPoolExecutor = new ThreadPoolExecutor(
			corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	private final ThreadPoolExecutor sinatThreadPoolExecutor = new ThreadPoolExecutor(
			corePoolSize, maximumPoolSize, keepAliveTime, unit, sinaWorkQueue);

	public void start() {

		log.info("controller start...");
		final Frontier frontier = new Frontier(this);
		
		frontier.addRule(new FrontierDisableRule());
		frontier.addRule(CheckTwitterFrontierRule.getInstance());
		frontier.addRule(CheckSinaFrontierRule.newInstance());
		frontier.addRule(CheckTencentFrontierRule.newInstance());
        frontier.addRule(CheckNeteaseFrontierRule.getInstance());
        frontier.addRule(CheckSohuFrontierRule.getInstance());
		frontier.addRule(new CheckWeiboPageIsStartNowRule());
		/**
		 * 线程池，多线程启动work
		 */
		while (true) {
			try {
				log.info("	等待任务中.......");
				WeiboPage weiboPage = frontier.next();
				if (null == weiboPage)
					continue;
				switch(weiboPage.getTask()){
				case Sina:
					sinatThreadPoolExecutor.execute(new Work(weiboPage));
					break;
				default:
					PausableThreadPoolExecutor.execute(new Work(weiboPage));
				}
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}
	

	public void pause() {
	  
	}

	public void pause(long minute) {
		log.info("Controller pause...." + minute / 1000 + " s");
		try {
			Thread.sleep(minute);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	public void pause(long time, TimeUnit timeUnit) {
		pauseLock.lock();
		try {
			isPaused = true;
			this.time = time;
			this.timeUnit = timeUnit;
		} finally {
			pauseLock.unlock();
		}
	}

	public void resume() {
		pauseLock.lock();
		try {
			isPaused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}

	public void stop() {
		PausableThreadPoolExecutor.shutdown();
		System.exit(0);
	}

	public static void main(String[] args) {
		if(args.length == 0){
			// VariableCache.init("");
		}else{
			VariableCache.init(args[0]);
		}
		PropertyConfigurator.configure(VariableCache.Log4j.propertiesFile);
		new Controller().start();
	}
}
