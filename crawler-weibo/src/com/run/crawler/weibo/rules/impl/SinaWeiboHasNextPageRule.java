package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.SinaWeiboQuery;

public class SinaWeiboHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Sina);
		this.setJob(Job.Weibo);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];

		// sonWeiboPage.setQuery(new SinaWeiboQuery());

		Query query = sonWeiboPage.getQuery();
		String oldMaxId = weiboPage.getState().getLastId();
		if (query instanceof SinaWeiboQuery) {
			SinaWeiboQuery sinaWeiboQuery = (SinaWeiboQuery) query;
			/**
			 * 注意这里必须要减一，因为默认返回<=max_id的微博
			 */
			sinaWeiboQuery.setMax_id(Long.valueOf(oldMaxId) - 1);
		}

		log.info("SinaWeiboHasNextPageRule 处理完成..." + sonWeiboPage);

		return true;
	}
}
