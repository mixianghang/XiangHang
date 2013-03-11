package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class TwitterUserShowFrontierRule extends AbstractRule {
	
	private static final TwitterUserShowFrontierRule checkTwitterRule = new TwitterUserShowFrontierRule();
	
	long minStartTime = 0L;
	long preStartTime = 0;
	
	{
		this.setTask(Task.Twitter);
		this.setJob(Job.All);
		minStartTime = this.task.getRateControl();
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
		long currentTime = System.currentTimeMillis();
		if((currentTime - preStartTime) < minStartTime){
			return false;
		}
		preStartTime = currentTime;
		return true;
	}
	
	public static TwitterUserShowFrontierRule getInstance() {
		return checkTwitterRule;
	}
	
	
	
	
}
