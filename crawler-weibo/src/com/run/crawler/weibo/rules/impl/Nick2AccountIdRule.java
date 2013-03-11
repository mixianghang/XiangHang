package com.run.crawler.weibo.rules.impl;

import java.util.ArrayList;
import java.util.List;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.cache.AccountCache;

public class Nick2AccountIdRule extends AbstractRule {

	{
		this.job = Job.Weibo;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		List<VelocityBean> list = weiboPage.getVelocityBeanList();
		List<VelocityBean> total = new ArrayList<VelocityBean>();
		for (VelocityBean velocityBean : list) {
			/**
			 * 这里读取缓存，将nick与accountId对应上
			 */
			String screenName = velocityBean.getAccount();
			if (screenName != null && !"".equals(screenName)) {
				List<Long> lists = AccountCache.getInstance().getAccountIdList(
						weiboPage.getTask(), screenName);
				log.info("lists=" + lists);
				if (lists != null && lists.size() > 0) {
					for (long id : lists) {
						try {
							VelocityBean vb = (VelocityBean) velocityBean
									.clone();
							vb.setAccountId(id);
							vb.genMd5(id + vb.getId());// VelocityBean的MD5码不能只根据微博id生成,这样多个同昵称的重点只能保存一份数据.
							total.add(vb);
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		weiboPage.setVelocityBeanList(total);
		return true;
	}
}
