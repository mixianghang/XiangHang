package com.run.crawler.weibo.friendship.create;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import weibo4j.Friendships;
import weibo4j.model.WeiboException;

import com.run.crawler.weibo.util.oauth.SinaOauth;

public class SinaFriendships {

	static Logger log = Logger.getLogger(SinaFriendships.class);

	/**
	 * 关注
	 * 
	 * @param nickName
	 * @return
	 */
	public static boolean create(String nickName) {
		log.info("sina 关注好友.........." + nickName);
		try {
			Thread.sleep(30 * 1000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Friendships fm = new Friendships();
		String s = SinaOauth.newInstance().getSendOauthUrl();
		log.info(s);
		if (null == s) {
			return false;
		}
		fm.client.setToken(s.replace("access_token=", ""));
		try {
			fm.createFriendshipsByName(nickName);
		} catch (WeiboException e) {
			String error = e.getError();
			if ("already followed".equals(error)) {
				log.warn("好友:" + nickName + " 已经被关注!");
				return true;
			} else {
				log.error("nickName: " + nickName + " Error: " + e.getError());
				log.error("nickName: " + nickName + " Message: "
						+ e.getMessage());
				return false;
			}
		}
		log.warn("好友" + nickName + "关注成功!");
		return true;
	}

	/**
	 * 取消关注
	 * 
	 * @param nickName
	 * @return
	 */
	public static boolean destory(String nickName) {
		log.info("sina 取消关注好友.........." + nickName);
		try {
			Thread.sleep(30 * 1000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(10 * 1000L);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Friendships fm = new Friendships();
		String s = SinaOauth.newInstance().getSendOauthUrl();
		if (null == s) {
			return false;
		}
		fm.client.setToken(s.replace("access_token=", ""));
		try {
			fm.destroyFriendshipsDestroyByName(nickName);
		} catch (WeiboException e) {
			String error = e.getError();
			if ("not followed".equals(error)) {
				log.warn("好友:" + nickName + " 已经被取消关注!");
				return true;
			} else {
				log.error(e.getError());
				log.error(e.getMessage());
				return false;
			}
		}
		log.warn("好友" + nickName + "取消关注成功!");
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("conf/log4j.properties");
		String nickName = "黑人建州";
		boolean create = SinaFriendships.create(nickName);
		System.out.println(create);
	}
}
