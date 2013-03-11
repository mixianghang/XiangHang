package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class ExtractorOkRule implements Rule {

	@Override
	public boolean accept(WeiboPage weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (weiboPage.getVelocityBeanList().size() == 0) {
			weiboPage.setPreProcessOk(false);
			return false;
		} else {
			weiboPage.setPreProcessOk(true);
			return true;
		}
	}
}
