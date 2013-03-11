package com.run.crawler.weibo.frontier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.GenWeiboPageFromAccount;
import com.run.crawler.weibo.util.Task;
import com.run.crawler.weibo.util.cache.AccountCache;
import com.run.crawler.weibo.util.cache.AccountCacheInterface;
import com.run.crawler.weibo.util.cache.AccountDBCache;
import com.run.crawler.weibo.util.cache.VariableCache;

public class FrontierTaskInit {

  private Frontier frontier;

  public FrontierTaskInit(Frontier frontier) {
    this.frontier = frontier;
  }

  List<WeiboPage> getTaskFromFile(String fileName) {
    AccountCacheInterface accountCacheInterface = null;
    if (VariableCache.Controller.isStartFromDB) {
      AccountDBCache accountDBCache = AccountDBCache.newInstance();
      accountDBCache.setFrontier(frontier);
      accountCacheInterface = accountDBCache;
    } else {
      AccountCache accountCache = AccountCache.getInstance();
      accountCache.registerFrontier(frontier);
      accountCacheInterface = accountCache;
    }

    List<WeiboPage> list = new ArrayList<WeiboPage>();
    try {
      List<Account> listAccount = accountCacheInterface.getAll();
		Map<Task, AtomicInteger> taskMap = new HashMap<Task, AtomicInteger>();
		/**
		  * 加载所有除了weibo的任务
		 */
		for (Account account : listAccount) {
			if (taskMap.containsKey(account.getTask())) {
				taskMap.get(account.getTask()).addAndGet(1);
				account.setOrder(taskMap.get(account.getTask()).get());
			} else {
				taskMap.put(account.getTask(), new AtomicInteger(0));
				account.setOrder(0);
			}
			list.addAll(GenWeiboPageFromAccount.ofAllExceptWeibo(account));
		}
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    /**
     * 2.5版本默认采用userTimeLine采集
     */
    if(VariableCache.Controller.isStartFromDB){
      return list;
    }
    
    /**
     * 加载weibo任务
     */
    if (VariableCache.FrontierTaskInit.twitterStart) {
      list.add(GenWeiboPageFromAccount.ofTwitterWeibo());
    }

    if (VariableCache.FrontierTaskInit.sinaStart) {
      list.add(GenWeiboPageFromAccount.ofSinaWeibo());
    }
    return list;
  }
}
