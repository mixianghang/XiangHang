package com.run.crawler.weibo.processor.extractor.impl;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TencentTrendExtractor extends JsonExtractor {
	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		String content = weiboPage.getContent();

		log.debug(content);

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

		String pos = dataJSONObject.getString("pos");
		/**
		 * 最后一条记录时间戳 用于下翻页
		 */
		weiboPage.getState().setTimestamp(pos);

		JSONArray infoJSONArray = dataJSONObject.getJSONArray("info");

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = infoJSONArray.iterator();

		while (iterator.hasNext()) {
			VelocityBean velocityBean = VelocityBeanPool.borrowObject();
			JSONObject json = iterator.next();
			String trendName = json.getString("name");
			String trendKeywords = json.getString("keywords");
			String trendId = json.getString("id");
			String trendFavnum = json.getString("favnum");
			String trendTweetnum = json.getString("tweetnum");

			velocityBean.setHasNext(hasNext);
			velocityBean.setTrendName(trendName);
			velocityBean.setTrendKeywords(trendKeywords);
			velocityBean.setTrendId(trendId);
			velocityBean.setTrendFavnum(trendFavnum);
			velocityBean.setTrendTweetnum(trendTweetnum);

			velocityBean.setTask(weiboPage.getTask());
			velocityBean.setJob(weiboPage.getJob());

			velocityBean.genMd5(trendId);

			weiboPage.getVelocityBeanList().add(velocityBean);
		}

		log.info("TencentTrendExtractor 处理完成");

		return weiboPage;
	}
}
