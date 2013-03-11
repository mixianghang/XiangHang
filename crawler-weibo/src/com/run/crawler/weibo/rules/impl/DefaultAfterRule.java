package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class DefaultAfterRule implements Rule {

	@Override
	public boolean accept(WeiboPage weiboPage) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		weiboPage.setPreProcessOk(true);
		return true;
	}

}
