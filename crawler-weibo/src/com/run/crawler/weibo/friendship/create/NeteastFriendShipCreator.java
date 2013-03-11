package com.run.crawler.weibo.friendship.create;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.NeteaseOauth;

/**
 * 关注重点人微博
 * 
 * @author liuxiongwei
 * 
 */
public class NeteastFriendShipCreator {
	private static final Logger log = Logger
			.getLogger(NeteastFriendShipCreator.class);

	/**
	 * 关注好友
	 * 
	 * @param screen_name
	 */
	public static boolean createFriendShip(String screen_name) {
		log.info("正在关注好友" + screen_name + ".................................");
		String url = "https://api.t.163.com/friendships/create.json";
		String queryStr = "screen_name=" + screen_name + "&"
				+ NeteaseOauth.newInstance().getOauthUrl();
		MyHttpclient myHttpclient = new MyHttpclient();
		String content = myHttpclient.requestPost(url, queryStr);
		log.info("content=" + content);
		if (content == null || "".equals(content)) {
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
		String url = "https://api.t.163.com/friendships/destroy.json";
		String queryStr = "screen_name=" + screen_name + "&"
				+ NeteaseOauth.newInstance().getOauthUrl();
		MyHttpclient myHttpclient = new MyHttpclient();
		String content = myHttpclient.requestPost(url, queryStr);
		log.info("content=" + content);
		if (content == null || "".equals(content)) {
			log.warn("取消关注好友:" + screen_name + "失败");
			return false;
		}
		log.info("取消关注好友:" + screen_name + " 成功!");
		return true;
	}

	public static void main(String[] args) {
		createFriendShip("yanglan");
	}

}
