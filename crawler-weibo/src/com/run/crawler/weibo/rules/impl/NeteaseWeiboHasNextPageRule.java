package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.NeteaseWeiboQuery;
import com.run.crawler.weibo.util.query.Query;

public class NeteaseWeiboHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Netease);
		this.setJob(Job.Weibo);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {

		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		/**
		 * 下一页cursor_id的游标位置
		 */
		String nextCursor = weiboPage.getState().getCursor_id();
		log.info("cursor_id=" + nextCursor);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		Query query = sonWeiboPage.getQuery();
		if (query instanceof NeteaseWeiboQuery) {
			NeteaseWeiboQuery neteastWeiboQuery = (NeteaseWeiboQuery) query;
			neteastWeiboQuery.setSince_id(nextCursor);
		}
		log.info("NeteastWeiboHasNextPageRule 处理完成....");

		return true;
	}
}
