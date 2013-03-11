package com.run.crawler.weibo.rules.impl;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.WriterHasNextPageRule;
import com.run.crawler.weibo.util.Attribute;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.query.NeteaseFriendQuery;

public class NeteaseFriendHasNextPageRule extends WriterHasNextPageRule {
	
	{
		this.setTask(Task.Netease);
		this.setJob(Job.Friend);
	}
	
	@Override
	public boolean rule(WeiboPage weiboPage) {
	  
	   if(!weiboPage.isHasNextPage())
	      return false;
		/**
		 * 下一页ID的游标位置
		 */
		int nextCursor = weiboPage.getState().getNext_cursor();
		
		log.info("next_cursor=" + nextCursor );
		
		/**
		 * nextCursor不为-1，表示存在下一页
		 */
		if(-1 == nextCursor){
			return false;
		}
		super.rule(weiboPage);
		WeiboPage nextWeiboPage = weiboPage.getSonWeiboPages()[0];
		nextWeiboPage.setAttribute(Attribute.Son);
		NeteaseFriendQuery query = (NeteaseFriendQuery)nextWeiboPage.getQuery();
		query.setCursor(String.valueOf(nextCursor));
		nextWeiboPage.setQuery(query);
		return true;

	}
}
