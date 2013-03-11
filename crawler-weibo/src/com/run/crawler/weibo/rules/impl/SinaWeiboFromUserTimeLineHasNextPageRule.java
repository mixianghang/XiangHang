package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.SinaWeiboFromUserTimeLineQuery;

public class SinaWeiboFromUserTimeLineHasNextPageRule extends
		WriterHasNextPageRule {

	{
		this.setTask(Task.Sina);
		this.setJob(Job.WeiboFromUserTimeLine);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];

		Query query = sonWeiboPage.getQuery();
		String oldMaxId = weiboPage.getState().getLastId();
		if (query instanceof SinaWeiboFromUserTimeLineQuery) {
			SinaWeiboFromUserTimeLineQuery sinaWeiboFromUserTimeLineQuery = (SinaWeiboFromUserTimeLineQuery) query;
			/**
			 * ע���������Ҫ��һ����ΪĬ�Ϸ���<=max_id��΢��
			 */
			sinaWeiboFromUserTimeLineQuery
					.setMax_id(Long.valueOf(oldMaxId) - 1);
		}

		log.info("SinaWeiboFromUserTimeLineHasNextPageRule �������..."
				+ sonWeiboPage);

		return true;
	}
}
