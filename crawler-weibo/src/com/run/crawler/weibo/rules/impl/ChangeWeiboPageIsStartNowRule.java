package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.cache.WeiboPageStartTimeCache;

/**
 * �޸�weibopage��������Ĵ������ڵ�Ϊ����
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
		log.info("ChangeWeiboPageIsStartNowRule �������....��������ʱ��  ��" + startTime);
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
