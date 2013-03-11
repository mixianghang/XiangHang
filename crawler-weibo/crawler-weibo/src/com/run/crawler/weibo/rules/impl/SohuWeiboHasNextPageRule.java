package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.query.Query;
import com.run.crawler.weibo.util.query.SohuWeiboQuery;

public class SohuWeiboHasNextPageRule extends WriterHasNextPageRule {
	
	{
		this.setTask(Task.Sohu);
		this.setJob(Job.Weibo);
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {

		if(!weiboPage.isHasNextPage())
			return false;
		super.rule(weiboPage);
		
		int pageNo = weiboPage.getState().getPageNo() + 1;
		/**
		 * 下一页pageNo的游标位置
		 */
	  weiboPage.getState().setPageNo(pageNo);
		log.info("pageNo=" + pageNo);
		WeiboPage sonWeiboPage = weiboPage.getSonWeiboPages()[0];
		Query query = sonWeiboPage.getQuery();
		if(query instanceof SohuWeiboQuery){
		  SohuWeiboQuery sohuWeiboQuery = (SohuWeiboQuery)query;
		  sohuWeiboQuery.setPage(pageNo);
		}
		log.info("SohuWeiboHasNextPageRule 处理完成....");
		
		return true;
	}
}
