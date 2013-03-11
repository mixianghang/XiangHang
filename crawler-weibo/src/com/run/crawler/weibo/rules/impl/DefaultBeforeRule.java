package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class DefaultBeforeRule implements Rule {

	@Override
	public boolean accept(WeiboPage weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (weiboPage.isPreProcessOk()) {
			return true;
		}
		return false;
	}

}
