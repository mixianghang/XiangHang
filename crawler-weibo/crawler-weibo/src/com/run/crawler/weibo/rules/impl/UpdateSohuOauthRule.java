package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.oauth.SohuOauth;

public class UpdateSohuOauthRule extends AbstractRule {

  {
    this.setTask(Task.Sohu);
  }

  @Override
  public boolean rule(WeiboPage weiboPage) {
    SohuOauth.newInstance().generateSignature(weiboPage);
    return true;
  }
}
