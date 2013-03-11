package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;
import com.run.crawler.weibo.util.cache.VariableCache;

public class WriterOkRule implements Rule {

	@Override
	public boolean accept(WeiboPage weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {

		/**
		 * �ж��Ƿ���Ҫ���������� �����ظ���ȡҲ���ܶ�����
		 */
		int maxStoreRereatNum = VariableCache.writerOkRule.maxStoreRepeatNum;
		if (weiboPage.getState().getStoreRepeatNum() <= maxStoreRereatNum) {
			if (weiboPage.getState().isHasNextPage()) {
				weiboPage.setHasNextPage(true);
			}
		}

		if (weiboPage.getVelocityBeanList().size() == 0) {
			weiboPage.setPreProcessOk(true);
			return true;
		} else {
			weiboPage.setPreProcessOk(false);
			return false;
		}
	}

}
