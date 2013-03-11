package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class FrontierDisableRule implements Rule {
	final int max = 5;

	@Override
	public boolean accept(WeiboPage weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		int errorNum = weiboPage.getErrorNum();
		if (errorNum >= max)
			weiboPage.setDisAbled(true);
		return true;
	}
}
