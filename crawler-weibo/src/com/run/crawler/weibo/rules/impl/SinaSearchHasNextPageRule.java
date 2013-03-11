package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;

public class SinaSearchHasNextPageRule extends WriterHasNextPageRule {

	@Override
	public boolean rule(WeiboPage weiboPage) {
		super.rule(weiboPage);
		String url = "";
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		sonWeiboPage.setUrl(url);
		return false;
	}
}
