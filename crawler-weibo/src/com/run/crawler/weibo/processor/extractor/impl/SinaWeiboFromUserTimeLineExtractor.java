package com.run.crawler.weibo.processor.extractor.impl;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class SinaWeiboFromUserTimeLineExtractor extends JsonExtractor {

	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);
		if (jsonObject.containsKey("error_code"))
			return weiboPage;

		JSONArray infoJSONArray = jsonObject.getJSONArray("statuses");

		if (infoJSONArray.isEmpty())
			return weiboPage;

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();

		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String timestamp = json.getString("created_at");
			String text = json.getString("text");
			String count = json.getString("reposts_count");
			String mcount = json.getString("comments_count");
			String fromurl = json.getString("source");
			String id = json.getString("id");

			JSONObject userJSONObject = json.getJSONObject("user");

			String openid = userJSONObject.getString("id");
			String nickName = userJSONObject.getString("screen_name");
			String name = userJSONObject.getString("name");

			String retweetedText = "";
			if (json.containsKey("retweeted_status")) {
				JSONObject retweeted_statusJson = json
						.getJSONObject("retweeted_status");
				retweetedText = retweeted_statusJson.getString("text");
			}

			log.debug("timestamp=" + timestamp);
			log.debug("text=" + text);
			log.debug("count=" + count);
			log.debug("mcount=" + mcount);
			log.debug("fromurl=" + fromurl);
			log.debug("id=" + id);
			log.debug("openid=" + openid);
			log.debug("nickName=" + nickName);
			log.debug("name=" + name);
			log.debug("origText=" + retweetedText);
			log.debug("*******************************************\n");
			velocityBean.setAccountId(weiboPage.getAccountId());
			velocityBean.setAccount(weiboPage.getAccount());
			velocityBean.setText(text);
			velocityBean.setCount(count);
			velocityBean.setMcount(mcount);
			velocityBean.setFromurl(fromurl);
			velocityBean.setId(id);
			velocityBean.setOpenid(openid);
			velocityBean.setName(name);
			velocityBean.setNickName(nickName);
			velocityBean.setTimestamp(timestamp);
			velocityBean.setAccount(nickName);
			velocityBean.setOrigtext(retweetedText);

			velocityBean.setCategory(weiboPage.getTask().getI());
			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			/**
			 * 用于下翻页
			 */
			weiboPage.getState().setLastId(id);

			velocityBean.genMd5(id);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("SinaWeiboFromUserTimeLineExtractor 处理完成");

		return weiboPage;
	}
}
