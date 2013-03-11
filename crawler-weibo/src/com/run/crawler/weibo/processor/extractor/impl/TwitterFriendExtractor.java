package com.run.crawler.weibo.processor.extractor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TwitterFriendExtractor extends JsonExtractor {

	/**
	 * 提取好友个人属性信息
	 */
	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		log.info("正在被TwitterFriendExtractor进行处理中...... ");
		JSONObject jsonObject = JSONObject.fromObject(weiboPage.getContent());
		JSONArray users = jsonObject.getJSONArray("users");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String relationtime = f.format(date);
		String nextCursor = jsonObject.getString("next_cursor");
		weiboPage.getState().setCursor_id(nextCursor);
		for (int i = 0; i < users.size(); i++) {
			JSONObject user = users.getJSONObject(i);
			VelocityBean bean = VelocityBeanPool.borrowObject();
			String screenName = user.getString("screen_name");
			String name = user.getString("name");
			String userID = user.getString("id");
			String location = user.getString("location");
			String fansnum = user.getString("followers_count");
			String idolnum = user.getString("friends_count");

			bean.setAccountId(weiboPage.getAccountId());
			bean.setAccount(weiboPage.getAccount());
			bean.setJob(weiboPage.getJob());
			bean.setTask(weiboPage.getTask());
			bean.setName(name);
			bean.setNickName(screenName);
			bean.setOpenid(screenName);
			bean.setFansnum(fansnum);
			bean.setIdolnum(idolnum);
			bean.setLocation(location);
			bean.setRelationtime(relationtime);
			bean.genMd5(userID);

			weiboPage.getVelocityBeanList().add(bean);
			log.debug("screenName=" + screenName);
			log.debug("name=" + name);
			log.debug("userID=" + userID);
			log.debug("location=" + location);
			log.debug("followers=" + fansnum);
			log.debug("friends=" + idolnum);
			log.debug("md5=" + bean.getMd5());
			log.debug("*********************************************************");

		}

		return weiboPage;
	}
}
