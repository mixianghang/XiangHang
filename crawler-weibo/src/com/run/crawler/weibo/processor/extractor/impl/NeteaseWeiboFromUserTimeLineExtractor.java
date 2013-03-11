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

public class NeteaseWeiboFromUserTimeLineExtractor extends JsonExtractor {

	private SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format2 = new SimpleDateFormat(
			"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	/**
	 * 提取微博信息
	 */
	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		log.info("正在被NeteastWeiboExtractor进行处理中...... ");
		log.info(weiboPage.getContent());
		List<VelocityBean> beans = new ArrayList<VelocityBean>();

		JSONArray array = JSONArray.fromObject(weiboPage.getContent());
		for (int i = 0; i < array.size(); i++) {
			try {
				VelocityBean bean = VelocityBeanPool.borrowObject();
				JSONObject weibo = array.getJSONObject(i);
				JSONObject user = (JSONObject) weibo.get("user");
				String weiboID = weibo.getString("id");
				String text = weibo.getString("text");
				String screenName = user.getString("screen_name");
				String name = user.getString("name");
				String publishDate = format1.format(format2.parse(weibo
						.getString("created_at")));
				String userID = user.getString("id");
				String rcount = weibo.getString("retweet_count");
				String source = weibo.getString("source");
				String geo = weibo.getString("geo");
				String fansnum = user.getString("followers_count");
				String idolnum = user.getString("friends_count");
				String verified = user.getString("verified");
				String oriText = weibo.getString("in_reply_to_status_text");
				if (oriText != null && !"null".equals(oriText)) {
					bean.setOrigtext(oriText);
				}
				bean.setAccountId(weiboPage.getAccountId());
				bean.setAccount(weiboPage.getAccount());
				bean.setId(weiboID);
				bean.setJob(weiboPage.getJob());
				bean.setTask(weiboPage.getTask());
				bean.setName(name);
				bean.setNickName(screenName);
				bean.setOpenid(userID);
				bean.setText(text);
				bean.setCount(rcount);
				bean.setFromurl(source);
				bean.setTimestamp(publishDate);
				bean.setIsvip(verified);
				bean.setGeo(geo);
				bean.setFansnum(fansnum);
				bean.setIdolnum(idolnum);
				bean.genMd5(weiboID);
				beans.add(bean);

				log.info("weiboID=" + weiboID);
				log.info("text=" + text);
				log.info("oriText=" + oriText);
				log.info("screenName=" + screenName);
				log.info("name=" + name);
				log.info("publishDate=" + publishDate);
				log.info("job=" + weiboPage.getJob());
				log.info("task=" + weiboPage.getTask());
				log.info("userID=" + userID);
				log.info("source=" + source);
				log.info("publishDate=" + publishDate);
				log.info("*******************************************\n");

				/**
				 * 下一页ID的游标位置
				 */
				String nextCursor = weibo.getString("cursor_id");
				weiboPage.getState().setCursor_id(nextCursor);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		weiboPage.getVelocityBeanList().addAll(beans);
		return weiboPage;
	}
}
