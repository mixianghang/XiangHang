package com.run.crawler.weibo.processor.extractor.impl;

import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class SinaUserShowExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject json = JSONObject.fromObject(content);
		if (json.containsKey("error_code"))
			return weiboPage;

		VelocityBean velocityBean = VelocityBeanPool.borrowObject();
		String name = json.getString("name");
		String openid = json.getString("id");
		String nick = json.getString("screen_name");
		String head = json.getString("profile_image_url");
		String sex = json.getString("gender");
		String location = json.getString("location");
		String fansnum = json.getString("friends_count");
		String idolnum = json.getString("followers_count");
		String description = json.getString("description");

		velocityBean.setAccountId(weiboPage.getAccountId());
		velocityBean.setName(name);
		velocityBean.setOpenid(openid);
		velocityBean.setNickName(nick);
		velocityBean.setHead(head);
		velocityBean.setSex(sex);
		velocityBean.setLocation(location);
		velocityBean.setFansnum(fansnum);
		velocityBean.setIdolnum(idolnum);
		velocityBean.setDescription(description);

		velocityBean.setTask(weiboPage.getTask());
		velocityBean.setJob(weiboPage.getJob());

		velocityBean.genMd5(openid);

		weiboPage.getVelocityBeanList().add(velocityBean);

		log.info("SinaUserShowExtractor 处理完成");

		return weiboPage;
	}
}
