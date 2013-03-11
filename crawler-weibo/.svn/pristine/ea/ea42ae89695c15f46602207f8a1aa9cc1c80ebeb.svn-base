package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class CheckSinaFrontierRule extends AbstractRule {
	final long minStartTime;
	volatile long preStartTime = System.currentTimeMillis();
	
	private CheckSinaFrontierRule(){
	  this.setTask(Task.Sina);
    this.setJob(Job.All);
    
    minStartTime = this.task.getRateControl();
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
	  if(minStartTime <= 0)
	      return true;
		long currentTime = System.currentTimeMillis();
		if((currentTime - preStartTime) < minStartTime){
			return false;
		}else{
			preStartTime = currentTime;
			return true;
		}
	}
	
	private static class Proxy{
		private static CheckSinaFrontierRule checkSinaFrontierRule = new CheckSinaFrontierRule();
	}
	
	public static CheckSinaFrontierRule newInstance(){
		return Proxy.checkSinaFrontierRule;
	}
}
