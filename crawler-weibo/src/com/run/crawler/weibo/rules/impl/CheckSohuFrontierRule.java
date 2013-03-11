package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class CheckSohuFrontierRule extends AbstractRule {

	private static final CheckSohuFrontierRule checkSohuFrontierRule = new CheckSohuFrontierRule();

	long minStartTime;
	long preStartTime = System.currentTimeMillis();

	{
		this.setTask(Task.Sohu);
		this.setJob(Job.All);
		minStartTime = this.task.getRateControl();
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (minStartTime <= 0)
			return true;
		long currentTime = System.currentTimeMillis();
		if ((currentTime - preStartTime) < minStartTime) {
			return false;
		} else {
			preStartTime = currentTime;
			return true;
		}
	}

	public static CheckSohuFrontierRule getInstance() {
		return checkSohuFrontierRule;
	}

}
