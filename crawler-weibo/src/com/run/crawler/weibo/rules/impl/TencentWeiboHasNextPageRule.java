package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.TencentWeiboQuery;

public class TencentWeiboHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Tencent);
		this.setJob(Job.Weibo);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		Query query = sonWeiboPage.getQuery();
		String timestamp = sonWeiboPage.getState().getTimestamp();
		if (query instanceof TencentWeiboQuery) {
			TencentWeiboQuery tencentWeiboQuery = (TencentWeiboQuery) query;
			tencentWeiboQuery.setPagetime(timestamp);
			tencentWeiboQuery.setPageflag(1);
		}

		log.info("TencentWeiboHasNextPageRule 处理完成...." + sonWeiboPage);

		return true;
	}
}
