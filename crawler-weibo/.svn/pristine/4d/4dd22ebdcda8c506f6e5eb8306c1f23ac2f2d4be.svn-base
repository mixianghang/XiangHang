package com.run.crawler.weibo.processor.extractor.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;

public class TwitterTrendExtractor extends JsonExtractor {

	/**
	 * 提取热点话题信息
	 */
	@Override
	public WeiboPage process(WeiboPage weiboPage) {
			log.info("正在被TwitterWeiboExtractor进行处理中...... ");
			List<VelocityBean> beans = new ArrayList<VelocityBean>();
			JSONArray trends = JSONArray.fromObject(weiboPage.getContent());
			for (int i = 0; i < trends.size(); i++) {
				try {
					JSONObject trend = trends.getJSONObject(i);
					VelocityBean bean = VelocityBeanPool.borrowObject();
					String country = trend.getString("country");
					String countryCode = trend.getString("countryCode");
					String name = trend.getString("name");
					String parentid = trend.getString("parentid");
					String url = trend.getString("url");
					String woeid = trend.getString("woeid");
					
	                bean.setName(name);
	                bean.setFromurl(url);
	                bean.setId(woeid);
	                bean.setJob(weiboPage.getJob());
	                bean.setTask(weiboPage.getTask());
	                bean.genMd5(woeid);
	                
	                beans.add(bean);
				    log.debug("country=" + country);
				 	log.debug("countryCode=" + countryCode);
					log.debug("name=" + name);
					log.debug("parentid=" + parentid);
					log.debug("url=" + url);
					log.debug("woeid=" + woeid);
					log.debug("size=" + trends.size());
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
