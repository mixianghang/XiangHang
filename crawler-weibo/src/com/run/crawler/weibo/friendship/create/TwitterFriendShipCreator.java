package com.run.crawler.weibo.friendship.create;

import net.sf.json.JSONObject;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.TwitterOauth;

/**
 * ��ע�ص���΢��
 * 
 * @author liuxiongwei
 * 
 */
public class TwitterFriendShipCreator {
	private static final Logger log = Logger
			.getLogger(TwitterFriendShipCreator.class);

	/**
	 * ��ע����
	 * 
	 * @param screen_name
	 */
	public static boolean createFriendShip(String screen_name) {
		log.info("���ڹ�ע����" + screen_name + ".................................");
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
			log.warn("��ע����:" + screen_name + "ʧ��!");
			return false;
		}
		log.info("��ע����:" + screen_name + " �ɹ�!");
		return true;

	}

	/**
	 * ȡ����ע
	 * 
	 * @param screen_name
	 */
	public static boolean destroyFriendShip(String screen_name) {
		log.info("����ȡ����ע����" + screen_name + ".................................");
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
			log.warn("ȡ����ע����:" + screen_name + "ʧ��");
			return false;
		}
		log.info("ȡ����ע����:" + screen_name + " �ɹ�!");
		return true;
	}

}
