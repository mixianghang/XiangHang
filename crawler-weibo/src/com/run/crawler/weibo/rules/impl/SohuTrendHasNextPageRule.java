package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class SohuTrendHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Sohu);
		this.setJob(Job.Trend);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		return true;
	}
}
