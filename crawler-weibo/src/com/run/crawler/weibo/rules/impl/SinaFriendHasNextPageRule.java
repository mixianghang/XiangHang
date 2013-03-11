package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.SinaFriendQuery;

public class SinaFriendHasNextPageRule extends WriterHasNextPageRule {

	{
		this.setTask(Task.Sina);
		this.setJob(Job.Friend);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		Query query = sonWeiboPage.getQuery();
		if (query instanceof SinaFriendQuery) {
			SinaFriendQuery sinaFriendQuery = (SinaFriendQuery) query;
			/**
			 * ���ﳬ��VariableCache��extractorTotalNum��
			 * sinaFriendTotalNum��next_cursor����0 ���Ի�������ȡ��һҳ������ȥ�ػ��� ����Ͳ���������
			 */
			int next_cursor = weiboPage.getState().getNext_cursor();
			sinaFriendQuery.setCursor(next_cursor);
		}
		return true;
	}
}
