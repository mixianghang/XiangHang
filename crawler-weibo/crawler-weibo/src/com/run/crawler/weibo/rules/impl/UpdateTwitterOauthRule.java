package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.oauth.TwitterOauth;

public class UpdateTwitterOauthRule extends AbstractRule {

	{
		this.setTask(Task.Twitter);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		TwitterOauth.newInstance().generateSignature(weiboPage);
		return true;
	}
}
