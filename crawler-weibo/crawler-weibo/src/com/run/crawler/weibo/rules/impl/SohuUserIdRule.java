package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.GenWeiboPageFromAccount;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;


/**
 * 负责保存Sohu重点人属性微博Id
 * @author liuxiongwei
 *
 */
public class SohuUserIdRule extends AbstractRule {
	
	{
		this.setTask(Task.Sohu);
		this.setJob(Job.UserShow);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
	  if(weiboPage.getVelocityBeanList().size() > 0) {
	    VelocityBean bean = weiboPage.getVelocityBeanList().get(0);
	    Account account = new Account();
	    account.setAccountId(bean.getAccountId());
	    account.setNickName(bean.getAccount());
	    account.setUserId(bean.getOpenid());
	    account.setTask(weiboPage.getTask());
	    
	    WeiboPage friendWeiboPage = GenWeiboPageFromAccount.ofSohuFriend(account);
	    WeiboPage followerWeiboPage = GenWeiboPageFromAccount.ofSohuFollower(account);
	    weiboPage.setSonWeiboPages(new WeiboPage[]{friendWeiboPage, followerWeiboPage});
	    weiboPage.setPreProcessOk(false);
	    return true;
	  }
    return false;
	}
}
