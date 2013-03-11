package com.run.crawler.weibo.friendship.create;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.TencentOauth;
import com.run.crawler.weibo.util.query.TencentFriendShipsCreateQuery;
import com.run.crawler.weibo.util.query.TencentFriendShipsDestoryQuery;

public class TencentFriendships {
	static Logger log = Logger.getLogger(SinaFriendships.class);

	/**
	 * 关注
	 * 
	 * @param nickName
	 * @return
	 */
	public static boolean create(String nickName) {
		String url = "http://open.t.qq.com/api/friends/add";
		TencentFriendShipsCreateQuery tencentFriendShipsCreateQuery = new TencentFriendShipsCreateQuery();
		tencentFriendShipsCreateQuery.setName(nickName);
		StringBuilder sb = new StringBuilder();
		sb.append(TencentOauth.newInstance().getOauthUrl());
		sb.append("&");
		sb.append(tencentFriendShipsCreateQuery);
		String result = new MyHttpclient().requestPost(url, sb.toString());
		log.debug(result);
		if ("".equals(result) || null == result)
			return false;
		JSONObject jsonObject = JSONObject.fromObject(result);
		if (jsonObject.containsKey("ret")) {
			String ret = jsonObject.getString("ret");
			if ("0".equals(ret)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 取消关注
	 * 
	 * @param nickName
	 * @return
	 */
	public static boolean destory(String nickName) {
		String url = "http://open.t.qq.com/api/friends/del";
		TencentFriendShipsDestoryQuery tencentFriendShipsDestoryQuery = new TencentFriendShipsDestoryQuery();
		tencentFriendShipsDestoryQuery.setName(nickName);
		StringBuilder sb = new StringBuilder();
		sb.append(TencentOauth.newInstance().getOauthUrl());
		sb.append("&");
		sb.append(tencentFriendShipsDestoryQuery);
		String result = new MyHttpclient().requestPost(url, sb.toString());
		log.debug(result);
		if ("".equals(result) || null == result)
			return false;
		JSONObject jsonObject = JSONObject.fromObject(result);
		if (jsonObject.containsKey("ret")) {
			String ret = jsonObject.getString("ret");
			if ("0".equals(ret)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("conf/log4j.properties");
		String nickName = "任志强";
		boolean create = create(nickName);
		System.out.println(create);
	}
}
