package com.run.crawler.weibo.processor.extractor.impl;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TencentSearchExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

    JSONObject jsonObject = JSONObject.fromObject(content);

		String errcode = jsonObject.getString("errcode");
		if(!"0".equals(errcode)){
			return weiboPage;
		}
		JSONObject dataJSONObject = jsonObject.getJSONObject("data");
		String hasNext = dataJSONObject.getString("hasnext");
		
		if("0".equals(hasNext)){
			weiboPage.getState().setHasNextPage(true);
		}else{
			weiboPage.getState().setHasNextPage(false);
		}
		
		String timestamp = dataJSONObject.getString("timestamp");
		/**
		 * 最后一条记录时间戳
		 * 用于下翻页
		 */
		weiboPage.getState().setTimestamp(timestamp);
		
		JSONArray infoJSONArray = dataJSONObject.getJSONArray("info");

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();

		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String text = json.getString("text");
			String origtext = json.getString("origtext");
			String count = json.getString("count");
			String mcount = json.getString("mcount");
			String fromurl = json.getString("fromurl");
			String id = json.getString("id");
			String image = json.getString("image");
			String openid = json.getString("openid");
			String nick = json.getString("nick");
			String self = json.getString("self");
			String type = json.getString("type");
			String location = json.getString("location");
			String emotionurl = json.getString("emotionurl");

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
			velocityBean.setType(type);
			velocityBean.setLocation(location);
			velocityBean.setEmotionurl(emotionurl);

			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(id);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("TencentWeiboExtractor 处理完成");

		return weiboPage;
	}
}
