package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.oauth.NeteaseOauth;

public class UpdateNeteaseOauthRule extends AbstractRule {

	{
		this.setTask(Task.Netease);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		weiboPage.setOauthUrl(NeteaseOauth.newInstance().getOauthUrl());
		return true;
	}
}
