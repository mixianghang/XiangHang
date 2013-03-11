package com.run.crawler.weibo.processor.extractor.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.entity.pool.VelocityBeanPool;
import com.run.crawler.weibo.processor.extractor.JsonExtractor;
import com.run.crawler.weibo.util.Job;

public class SohuSearchExtractor extends JsonExtractor {

	private  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private  SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	
	/**
	 * 提取搜索信息
	 */
	@Override
	public WeiboPage  process(WeiboPage  weiboPage) {

		try {
			log.info("SohuSearchExtractor is processing...... ");
			List<VelocityBean> beans = new ArrayList<VelocityBean>();
      JSONObject json = JSONObject.fromObject(weiboPage.getContent());
      JSONArray array = json.getJSONArray("statuses");
			for(int i=0; i< array.size(); i++) {
				try {
					VelocityBean bean = VelocityBeanPool.borrowObject();
					JSONObject weibo = array.getJSONObject(i);
					JSONObject user = (JSONObject) weibo.get("user");
					String weiboID = weibo.getString("id");
					String text = weibo.getString("text");
					String screenName = user.getString("screen_name");
					String name = user.getString("name");
					String publishDate = format1.format(format2.parse(weibo.getString("created_at")));
					String userID = user.getString("id");
					String geo = weibo.getString("geo");
					String fansnum = user.getString("followers_count");
					String idolnum = user.getString("friends_count");
					String mcount = weibo.getString("comments_count");
					
					
					bean.setId(weiboID);
					bean.setJob(Job.Weibo);
					bean.setTask(weiboPage.getTask());
					bean.setName(name);
					bean.setNickName(screenName);
					bean.setOpenid(userID);
					bean.setText(text);
					bean.setMcount(mcount);
					bean.setTimestamp(publishDate);
					if(geo != null && !"".equals(geo)) {
						bean.setGeo(geo);
					}
					bean.setFansnum(fansnum);
					bean.setIdolnum(idolnum);
					bean.genMd5(weiboID);
					beans.add(bean);
					
					
				
					log.debug("weiboID=" + weiboID);
					log.debug("text=" + text);
					log.debug("screenName=" + screenName);
					log.debug("name=" + name);
					log.debug("publishDate=" + publishDate);
					log.debug("job=" + weiboPage.getJob());
					log.debug("task=" + weiboPage.getTask());
					log.debug("userID=" + userID);
					log.debug("publishDate=" + publishDate);
					log.debug("md5=" + bean.getMd5());
					log.debug("*******************************************\n");
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}
			weiboPage.getVelocityBeanList().addAll(beans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weiboPage;
	}

}
