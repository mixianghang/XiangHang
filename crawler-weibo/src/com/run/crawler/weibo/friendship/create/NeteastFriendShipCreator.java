package com.run.crawler.weibo.friendship.create;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.NeteaseOauth;

/**
 * ��ע�ص���΢��
 * 
 * @author liuxiongwei
 * 
 */
public class NeteastFriendShipCreator {
	private static final Logger log = Logger
			.getLogger(NeteastFriendShipCreator.class);

	/**
	 * ��ע����
	 * 
	 * @param screen_name
	 */
	public static boolean createFriendShip(String screen_name) {
		log.info("���ڹ�ע����" + screen_name + ".................................");
		String url = "https://api.t.163.com/friendships/create.json";
		String queryStr = "screen_name=" + screen_name + "&"
				+ NeteaseOauth.newInstance().getOauthUrl();
		MyHttpclient myHttpclient = new MyHttpclient();
		String content = myHttpclient.requestPost(url, queryStr);
		log.info("content=" + content);
		if (content == null || "".equals(content)) {
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
		String url = "https://api.t.163.com/friendships/destroy.json";
		String queryStr = "screen_name=" + screen_name + "&"
				+ NeteaseOauth.newInstance().getOauthUrl();
		MyHttpclient myHttpclient = new MyHttpclient();
		String content = myHttpclient.requestPost(url, queryStr);
		log.info("content=" + content);
		if (content == null || "".equals(content)) {
			log.warn("ȡ����ע����:" + screen_name + "ʧ��");
			return false;
		}
		log.info("ȡ����ע����:" + screen_name + " �ɹ�!");
		return true;
	}

	public static void main(String[] args) {
		createFriendShip("yanglan");
	}

}
