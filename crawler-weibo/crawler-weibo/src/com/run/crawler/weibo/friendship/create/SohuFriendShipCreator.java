package com.run.crawler.weibo.friendship.create;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.SohuOauth;

/**
 * 关注重点人微博
 * 
 * @author liuxiongwei
 * 
 */
public class SohuFriendShipCreator {
  private static final Logger log = Logger.getLogger(SohuFriendShipCreator.class);

  /**
   * 关注好友
   * 
   * @param nick_name
   */
  public static boolean createFriendShip(String nick_name) {
    log.info("正在关注好友" + nick_name + ".................................");
    String url = "http://api.t.sohu.com/friendships/create/" + encode(nick_name) + ".json";
    log.info("url=" + url);
    String signature = SohuOauth.newInstance().generatePostSignature(url);
    MyHttpclient myHttpclient = new MyHttpclient();
    myHttpclient.addHeader(new BasicHeader("Authorization", signature));
    String content = myHttpclient.requestPost(url, null);
    log.info("content=" + content);
    if (content == null || "".equals(content)) {
      log.warn("关注好友:" + nick_name + "失败!");
      return false;
    }
    log.info("关注好友:" + nick_name + " 成功!");
    return true;

  }

  /**
   * 取消关注
   * 
   * @param nick_name
   */
  public static boolean destroyFriendShip(String nick_name) {
    log.info("正在取消关注好友" + nick_name + ".................................");
    String url = "http://api.t.sohu.com/friendships/destroy/" + encode(nick_name) + ".json";
    String signature = SohuOauth.newInstance().generatePostSignature(url);
    MyHttpclient myHttpclient = new MyHttpclient();
    myHttpclient.addHeader(new BasicHeader("Authorization", signature));
    String content = myHttpclient.requestPost(url, null);
    log.info("content=" + content);
    if (content == null || "".equals(content)) {
      log.warn("取消关注好友:" + nick_name + "失败!");
      return false;
    }
    log.info("取消关注好友:" + nick_name + " 成功!");
    return true;
  }

  public static void main(String[] args) {
    destroyFriendShip("yanglan");
  }

  private final static String encode(String nickName) {
    String charSet = "utf-8";
    String nick = null;
    try {
      nick = URLEncoder.encode(nickName, charSet);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return nick;
  }
}
