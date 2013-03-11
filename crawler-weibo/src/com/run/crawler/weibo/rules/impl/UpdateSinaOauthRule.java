package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.oauth.SinaOauth;

public class UpdateSinaOauthRule extends AbstractRule {

	{
		this.setTask(Task.Sina);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		weiboPage.setOauthUrl(SinaOauth.newInstance().getOauthUrl());
		return true;
	}
}
