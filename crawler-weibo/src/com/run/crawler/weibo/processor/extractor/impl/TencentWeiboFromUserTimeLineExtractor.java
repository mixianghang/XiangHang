package com.run.crawler.weibo.processor.extractor.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TencentWeiboFromUserTimeLineExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);

		String ret = jsonObject.getString("ret");
		if (!"0".equals(ret)) {
			return weiboPage;
		}

		String errcode = jsonObject.getString("errcode");
		if (!"0".equals(errcode)) {
			return weiboPage;
		}

		if ("null".equals(jsonObject.getString("data"))) {
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

		if (infoJSONArray.isEmpty())
			return weiboPage;

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String text = json.getString("text");
			String count = json.getString("count");
			String mcount = json.getString("mcount");
			String fromurl = json.getString("fromurl");
			String id = json.getString("id");
			String image = json.getString("image");
			String openid = json.getString("openid");
			String nick = json.getString("nick");
			String self = json.getString("self");
			String timestamp = json.getString("timestamp");
			String type = json.getString("type");
			String location = json.getString("location");
			String emotionurl = json.getString("emotionurl");
			String origtext = "";
			if (json.containsKey("source")) {
				JSONObject j = json.getJSONObject("source");
				if (null != j && !j.toString().equals("null")) {
					origtext = j.getString("text");
				}
			}

			c.setTimeInMillis(Long.parseLong(timestamp) * 1000);
			log.debug("text=" + text);
			log.debug("origtext=" + origtext);
			log.debug("count=" + count);
			log.debug("mcount=" + mcount);
			log.debug("fromurl=" + fromurl);
			log.debug("id=" + id);
			log.debug("image=" + image);
			log.debug("openid=" + openid);
			log.debug("nick=" + nick);
			log.debug("self=" + self);
			log.debug("timestamp=" + f.format(c.getTime()));
			log.debug("type=" + type);
			log.debug("location=" + location);
			log.debug("emotionurl=" + emotionurl);
			log.debug("*******************************************\n");

			velocityBean.setAccountId(weiboPage.getAccountId());
			velocityBean.setAccount(weiboPage.getAccount());
			velocityBean.setHasNext(hasNext);
			velocityBean.setText(text);
			velocityBean.setOrigtext(origtext);
			velocityBean.setCount(count);
			velocityBean.setMcount(mcount);
			velocityBean.setFromurl(fromurl);
			velocityBean.setId(id);
			velocityBean.setImage(image);
			velocityBean.setOpenid(openid);
			velocityBean.setNickName(nick);
			velocityBean.setSelf(self);
			velocityBean.setTimestamp(timestamp);
			velocityBean.setType(type);
			velocityBean.setLocation(location);
			velocityBean.setEmotionurl(emotionurl);

			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(weiboPage.getAccountId() + "" + id);

			weiboPage.getVelocityBeanList().add(velocityBean);

			/**
			 * 最后一条记录时间戳 用于下翻页
			 */
			weiboPage.getState().setTimestamp(timestamp);
		}

		log.info("TencentWeiboFromUserTimeLineExtractor 处理完成......................");

		return weiboPage;
	}
}
