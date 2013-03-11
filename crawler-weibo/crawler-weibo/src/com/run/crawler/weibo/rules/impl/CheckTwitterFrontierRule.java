package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.VariableCache;

public class CheckTwitterFrontierRule extends AbstractRule {
	
	private static final CheckTwitterFrontierRule checkTwitterRule = new CheckTwitterFrontierRule();
	
	long minStartTime = 0L;
	long preStartTime = 0L;
	
	{
		this.setTask(Task.Twitter);
		this.setJob(Job.All);
		minStartTime = VariableCache.Task.twitterRateControl;
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
	  if(minStartTime <= 0)
	      return true;
		long currentTime = System.currentTimeMillis();
		if((currentTime - preStartTime) < minStartTime){
			return false;
		}
		preStartTime = currentTime;
		return true;
	}
	
	public static CheckTwitterFrontierRule getInstance() {
		return checkTwitterRule;
	}
	
	
	
	
}
