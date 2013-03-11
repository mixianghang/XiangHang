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
			 * �����ȡ���棬��nick��accountId��Ӧ��
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
							vb.genMd5(id + vb.getId());// VelocityBean��MD5�벻��ֻ����΢��id����,�������ͬ�ǳƵ��ص�ֻ�ܱ���һ������.
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
