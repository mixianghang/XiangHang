package com.run.crawler.weibo.rules;

import com.run.crawler.weibo.entity.WeiboPage;

public interface Rule {
	public boolean rule(WeiboPage weiboPage);

	public boolean accept(WeiboPage weiboPage);
}
