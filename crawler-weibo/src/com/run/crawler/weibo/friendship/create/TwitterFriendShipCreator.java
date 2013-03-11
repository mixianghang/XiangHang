package com.run.crawler.weibo.friendship.create;

import net.sf.json.JSONObject;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.TwitterOauth;

/**
 * 关注重点人微博
 * 
 * @author liuxiongwei
 * 
 */
public class TwitterFriendShipCreator {
	private static final Logger log = Logger
			.getLogger(TwitterFriendShipCreator.class);

	/**
	 * 关注好友
	 * 
	 * @param screen_name
	 */
	public static boolean createFriendShip(String screen_name) {
		log.info("正在关注好友" + screen_name + ".................................");
		String url = "https://api.twitter.com/1.1/friendships/create.json";
		String queryStr = "follow=true&";
		queryStr += "screen_name=" + screen_name;
		String signature = TwitterOauth.newInstance().generateSignature(url,
				queryStr);
		MyHttpclient myHttpclient = new MyHttpclient();
		myHttpclient.addHeader(new BasicHeader("Authorization", signature));
		String content = myHttpclient.requestPost(url, queryStr);
		JSONObject jsonObject = JSONObject.fromObject(content);
		log.debug("content=" + content);
		if (jsonObject.containsKey("errors")) {
			log.warn("关注好友:" + screen_name + "失败!");
			return false;
		}
		log.info("关注好友:" + screen_name + " 成功!");
		return true;

	}

	/**
	 * 取消关注
	 * 
	 * @param screen_name
	 */
	public static boolean destroyFriendShip(String screen_name) {
		log.info("正在取消关注好友" + screen_name + ".................................");
		String url = "https://api.twitter.com/1.1/friendships/destroy.json";
		String queryStr = "screen_name=" + screen_name;
		String signature = TwitterOauth.newInstance().generateSignature(url,
				queryStr);
		MyHttpclient myHttpclient = new MyHttpclient();
		myHttpclient.addHeader(new BasicHeader("Authorization", signature));
		String content = myHttpclient.requestPost(url, queryStr);
		log.debug("content=" + content);
		JSONObject jsonObject = JSONObject.fromObject(content);
		if (jsonObject.containsKey("errors")) {
			log.warn("取消关注好友:" + screen_name + "失败");
			return false;
		}
		log.info("取消关注好友:" + screen_name + " 成功!");
		return true;
	}

}
