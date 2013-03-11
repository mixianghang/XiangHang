package com.run.crawler.weibo.processor.extractor.impl;

import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TencentUserShowExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);

		String errcode = jsonObject.getString("errcode");
		if (!"0".equals(errcode)) {
			return weiboPage;
		}

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		VelocityBean velocityBean = VelocityBeanPool.borrowObject();
		String name = dataJSONObject.getString("name");
		String openid = dataJSONObject.getString("openid");
		String nick = dataJSONObject.getString("nick");
		String head = dataJSONObject.getString("head");
		String fansnum = dataJSONObject.getString("fansnum");
		String idolnum = dataJSONObject.getString("mutual_fans_num");
		String isvip = dataJSONObject.getString("isvip");

		velocityBean.setAccountId(weiboPage.getAccountId());
		velocityBean.setName(name);
		velocityBean.setOpenid(openid);
		velocityBean.setNickName(nick);
		velocityBean.setHead(head);
		velocityBean.setFansnum(fansnum);
		velocityBean.setIdolnum(idolnum);
		velocityBean.setIsvip(isvip);

		velocityBean.setTask(weiboPage.getTask());
		velocityBean.setJob(weiboPage.getJob());

		velocityBean.genMd5(openid);

		weiboPage.getVelocityBeanList().add(velocityBean);

		log.info("TencentUserShowExtractor 处理完成");

		return weiboPage;
	}
}
