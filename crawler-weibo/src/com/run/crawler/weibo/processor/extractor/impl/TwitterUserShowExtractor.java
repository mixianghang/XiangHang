package com.run.crawler.weibo.processor.extractor.impl;

import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TwitterUserShowExtractor extends JsonExtractor {

	/**
	 * 提取个人属性信息
	 */
	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		log.info("正在被TwitterUserShowExtractor进行处理中...... ");
		JSONObject user = JSONObject.fromObject(weiboPage.getContent());
		VelocityBean bean = VelocityBeanPool.borrowObject();
		String screenName = user.getString("screen_name");
		String name = user.getString("name");
		String description = user.getString("description");
		String userID = user.getString("id");
		String location = user.getString("location");

		bean.setAccount(screenName);
		bean.setAccountId(weiboPage.getAccountId());
		bean.setOpenid(userID);
		bean.setName(name);
		bean.setNickName(screenName);
		bean.setLocation(location);
		bean.setJob(weiboPage.getJob());
		bean.setTask(weiboPage.getTask());
		bean.setDescription(description);
		bean.genMd5(userID);

		weiboPage.getVelocityBeanList().add(bean);
		log.debug("screenName=" + screenName);
		log.debug("name=" + name);
		log.debug("description=" + description);
		log.debug("userID=" + userID);
		log.debug("location=" + location);
		log.debug("md5=" + bean.getMd5());

		return weiboPage;
	}

}
