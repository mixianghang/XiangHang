package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.cache.WeiboPageStartTimeCache;

/**
 * �޸�weibopage��startTime״̬ ֻ���µ����񱻴���ɹ���isNew��Ϊfalse
 * 
 * @author jerry
 * 
 */
public class CheckWeiboPageIsStartNowRule extends AbstractRule {

	@Override
	public boolean rule(WeiboPage weiboPage) {

		if (VariableCache.CheckWeiboPageIsStartNowRule.isOpenStartNowOfNewWeiboPage) {
			return true;
		} else {
			Task task = weiboPage.getTask();
			Job job = weiboPage.getJob();
			weiboPage.setNew(false);
			int time = new WeiboPageStartTimeCache().getStartTime(task, job);
			weiboPage.setStartTime(time);
			log.info(task + " : " + job + "��������ʱ�� : " + time + " s");
			return false;
		}
	}

	@Override
	public boolean accept(WeiboPage weiboPage) {
		if (weiboPage.isNew()) {
			return true;
		} else {
			return false;
		}
	}
}
