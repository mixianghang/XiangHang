package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public class NeteaseSearchHasNextPageRule extends WriterHasNextPageRule{

	{
		this.setTask(Task.Netease);
		this.setJob(Job.Friend);
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
		super.rule(weiboPage);
		String url = "";
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		sonWeiboPage.setUrl(url);
		return false;
	}
}
