package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.SohuFriendQuery;

public class SohuFollowerHasNextPageRule extends WriterHasNextPageRule {
	
	{
		this.setTask(Task.Sohu);
		this.setJob(Job.Follower);
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
	   if(!weiboPage.isHasNextPage())
	      return false;
    /**
     * ÏÂÒ»Ò³ºÅ
     */
    int pageNo = weiboPage.getState().getPageNo() + 1;
    
    super.rule(weiboPage);
    WeiboPage nextWeiboPage = weiboPage.getSonWeiboPages()[0];
    nextWeiboPage.getState().setPageNo(pageNo);
    nextWeiboPage.setStartTime(10);
    Query query = nextWeiboPage.getQuery();
    if(query instanceof SohuFriendQuery){
      SohuFriendQuery sohuFriendQuery = (SohuFriendQuery)query;
      sohuFriendQuery.setPage(pageNo);
    }
    return true;
	}
}
