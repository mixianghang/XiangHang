package com.run.crawler.weibo.processor.extractor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TencentFriendExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);

		String errcode = jsonObject.getString("errcode");
		if (!"0".equals(errcode)) {
			return weiboPage;
		}
		JSONObject dataJSONObject = jsonObject.getJSONObject("data");
		String hasNext = dataJSONObject.getString("hasnext");

		if ("0".equals(hasNext)) {
			weiboPage.getState().setHasNextPage(true);
		} else {
			weiboPage.getState().setHasNextPage(false);
		}

		JSONArray infoJSONArray = dataJSONObject.getJSONArray("info");

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String relationtime = f.format(date);
		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String name = json.getString("name");
			String openid = json.getString("openid");
			String nick = json.getString("nick");
			String head = json.getString("headurl");
			String fansnum = json.getString("fansnum");
			String idolnum = json.getString("idolnum");

			log.debug("name=" + name);
			log.debug("openid=" + openid);
			log.debug("nick=" + nick);
			log.debug("headurl=" + head);
			log.debug("fansnum=" + fansnum);
			log.debug("idolnum=" + idolnum);
			log.debug("*******************************************\n");
			velocityBean.setAccountId(weiboPage.getAccountId());
			velocityBean.setAccount(weiboPage.getAccount());
			velocityBean.setHasNext(hasNext);
			velocityBean.setName(name);
			velocityBean.setOpenid(name);
			velocityBean.setNickName(nick);
			velocityBean.setHead(head);
			velocityBean.setFansnum(fansnum);
			velocityBean.setIdolnum(idolnum);
			velocityBean.setRelationtime(relationtime);
			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(openid);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("TencentFriendExtractor 处理完成");

		return weiboPage;
	}
}
