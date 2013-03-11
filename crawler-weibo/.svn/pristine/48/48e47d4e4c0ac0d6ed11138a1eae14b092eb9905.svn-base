package com.run.crawler.weibo.processor.extractor.impl;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class SinaHourlyTrendExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		JSONObject jsonObject = JSONObject.fromObject(content);
		if (jsonObject.containsKey("error_code"))
			return weiboPage;
		JSONObject trendsJSONObject = jsonObject.getJSONObject("trends");
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = trendsJSONObject.keys();
		JSONArray jsonArray = null;
		while (iterator.hasNext()) {
			String key = iterator.next();
			if ("as_of".equals(key))
				continue;
			jsonArray = trendsJSONObject.getJSONArray(key);
			break;
		}

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iter = jsonArray.iterator();

		while (iter.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iter.next();
			String text = json.getString("name");
			String mcount = json.getString("amount");

			velocityBean.setText(text);
			velocityBean.setMcount(mcount);

			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(text);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("SinaTrendExtractor 处理完成");

		return weiboPage;
	}
}
