package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Attribute;
import com.run.crawler.weibo.util.GenWeiboPageFromAccount;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.TwitterFollowerQuery;

public class TwitterFollowerHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Twitter);
		this.setJob(Job.Follower);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {

		if (!weiboPage.isHasNextPage())
			return false;
		/**
		 * 下一页ID的游标位置
		 */
		String nextCursor = weiboPage.getState().getCursor_id();

		log.info("next_cursor=" + nextCursor);

		/**
		 * nextCursor不为0，表示存在下一页
		 */
		if ("0".equals(nextCursor)) {
			return false;
		}
		super.rule(weiboPage);
		WeiboPage nextWeiboPage = weiboPage.getSonWeiboPages()[0];
		nextWeiboPage.setAttribute(Attribute.Son);
		TwitterFollowerQuery query = (TwitterFollowerQuery) nextWeiboPage
				.getQuery();
		query.setCursor(String.valueOf(nextCursor));
		nextWeiboPage.setQuery(query);
		nextWeiboPage.setStartTime(GenWeiboPageFromAccount.TWITTER_INTERVAL);
		return true;

	}
}
