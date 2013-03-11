package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.TencentFriendQuery;

public class TencentFriendHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Tencent);
		this.setJob(Job.Friend);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		Query query = sonWeiboPage.getQuery();
		if (query instanceof TencentFriendQuery) {
			TencentFriendQuery tencentFriendQuery = (TencentFriendQuery) query;
			int startindex = tencentFriendQuery.getStartindex();
			int reqNum = tencentFriendQuery.getReqnum();
			tencentFriendQuery.setStartindex(startindex + reqNum);
		}
		return true;
	}
}
