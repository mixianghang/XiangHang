package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.oauth.TencentOauth;

public class UpdateTencentOauthRule extends AbstractRule {

	{
		this.setTask(Task.Tencent);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
		return true;
	}
}
