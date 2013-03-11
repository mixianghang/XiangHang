package com.run.crawler.weibo.processor.extractor.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class SinaFollowerExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);
		if (jsonObject.containsKey("error_code"))
			return weiboPage;

		/**
		 * 下一页游标 为了产生子任务
		 */
		int next_cursor = jsonObject.getInt("next_cursor");
		weiboPage.getState().setNext_cursor(next_cursor);

		JSONArray infoJSONArray = jsonObject.getJSONArray("users");

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String relationtime = f.format(date);
		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String name = json.getString("name");
			String openid = json.getString("id");
			String nick = json.getString("screen_name");
			String head = json.getString("profile_image_url");
			String sex = json.getString("gender");
			String location = json.getString("location");
			String fansnum = json.getString("followers_count");
			String idolnum = json.getString("friends_count");
			String timestamp = json.getString("created_at");

			log.debug("name=" + name);
			log.debug("head=" + head);
			log.debug("openid=" + openid);
			log.debug("nick=" + nick);
			log.debug("sex=" + sex);
			log.debug("fansnum=" + fansnum);
			log.debug("location=" + location);
			log.debug("idolnum=" + idolnum);
			log.debug("*******************************************\n");
			velocityBean.setAccountId(weiboPage.getAccountId());
			try {
				velocityBean.setAccount(URLDecoder.decode(weiboPage.getAccount(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			velocityBean.setName(name);
			velocityBean.setOpenid(nick);
			velocityBean.setNickName(nick);
			velocityBean.setHead(head);
			velocityBean.setSex(sex);
			velocityBean.setLocation(location);
			velocityBean.setFansnum(fansnum);
			velocityBean.setIdolnum(idolnum);
			velocityBean.setRelationtime(relationtime);
			velocityBean.setTimestamp(timestamp);

			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(openid);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("SinaFollowerExtractor 处理完成");

		return weiboPage;
	}
	
}
