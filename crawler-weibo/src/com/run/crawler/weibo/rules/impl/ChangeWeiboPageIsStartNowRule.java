package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.cache.WeiboPageStartTimeCache;

/**
 * 修改weibopage将新任务的处理周期调为正常
 * 
 * @author jerry
 * 
 */
public class ChangeWeiboPageIsStartNowRule extends AbstractRule {

	@Override
	public boolean rule(WeiboPage weiboPage) {

		weiboPage.setNew(false);
		int startTime = new WeiboPageStartTimeCache().getStartTime(
				weiboPage.getTask(), weiboPage.getJob());
		weiboPage.setStartTime(startTime);
		log.info("ChangeWeiboPageIsStartNowRule 处理完毕....设置任务时间  ；" + startTime);
		return true;
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
