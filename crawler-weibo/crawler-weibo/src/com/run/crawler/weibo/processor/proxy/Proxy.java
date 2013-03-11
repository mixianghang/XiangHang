package com.run.crawler.weibo.processor.proxy;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public interface Proxy {
	boolean before(WeiboPage weiboPage);

	boolean after(WeiboPage weiboPage);

	Proxy addBeforeRules(Rule rule);

	Proxy addAfterRules(Rule rule);
}
