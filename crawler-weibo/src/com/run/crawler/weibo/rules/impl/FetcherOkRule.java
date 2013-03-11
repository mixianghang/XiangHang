package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class FetcherOkRule implements Rule {

	@Override
	public boolean accept(WeiboPage weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		if (null == weiboPage.getContent() || "".equals(weiboPage.getContent())) {
			weiboPage.setPreProcessOk(false);
			return false;
		} else {
			weiboPage.setPreProcessOk(true);
			return true;
		}
	}

}
