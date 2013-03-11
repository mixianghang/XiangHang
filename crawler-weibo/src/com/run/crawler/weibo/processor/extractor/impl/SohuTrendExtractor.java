package com.run.crawler.weibo.processor.extractor.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class SohuTrendExtractor extends JsonExtractor {

	/**
	 * 提取热点话题信息
	 */
	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		log.info("正在被SohuTrendExtractor进行处理中...... ");
		List<VelocityBean> beans = new ArrayList<VelocityBean>();
		JSONArray trends = JSONArray.fromObject(weiboPage.getContent());
		for (int i = 0; i < trends.size(); i++) {
			try {
				JSONObject trend = trends.getJSONObject(i);
				VelocityBean bean = VelocityBeanPool.borrowObject();
				String trendKeywords = trend.getString("text");
				String id = trend.getString("id");
				String retweet_count = trend.getString("retweet_count");

				bean.setTrendId(id);
				bean.setJob(weiboPage.getJob());
				bean.setTask(weiboPage.getTask());
				bean.setTrendKeywords(trendKeywords);
				bean.setTrendTweetnum(retweet_count);
				bean.genMd5(id);

				beans.add(bean);
				log.debug("id=" + id);
				log.debug("trendKeywords=" + trendKeywords);
				log.debug("retweet_count=" + retweet_count);
				log.debug("md5=" + bean.getMd5());
				log.debug("*******************************************\n");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		weiboPage.getVelocityBeanList().addAll(beans);
		return weiboPage;
	}
}
