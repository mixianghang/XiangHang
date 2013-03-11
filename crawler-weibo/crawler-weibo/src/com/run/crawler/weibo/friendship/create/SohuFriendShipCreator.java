package com.run.crawler.weibo.friendship.create;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.oauth.SohuOauth;

/**
 * ��ע�ص���΢��
 * 
 * @author liuxiongwei
 * 
 */
public class SohuFriendShipCreator {
  private static final Logger log = Logger.getLogger(SohuFriendShipCreator.class);

  /**
   * ��ע����
   * 
   * @param nick_name
   */
  public static boolean createFriendShip(String nick_name) {
    log.info("���ڹ�ע����" + nick_name + ".................................");
    String url = "http://api.t.sohu.com/friendships/create/" + encode(nick_name) + ".json";
    log.info("url=" + url);
    String signature = SohuOauth.newInstance().generatePostSignature(url);
    MyHttpclient myHttpclient = new MyHttpclient();
    myHttpclient.addHeader(new BasicHeader("Authorization", signature));
    String content = myHttpclient.requestPost(url, null);
    log.info("content=" + content);
    if (content == null || "".equals(content)) {
      log.warn("��ע����:" + nick_name + "ʧ��!");
      return false;
    }
    log.info("��ע����:" + nick_name + " �ɹ�!");
    return true;

  }

  /**
   * ȡ����ע
   * 
   * @param nick_name
   */
  public static boolean destroyFriendShip(String nick_name) {
    log.info("����ȡ����ע����" + nick_name + ".................................");
    String url = "http://api.t.sohu.com/friendships/destroy/" + encode(nick_name) + ".json";
    String signature = SohuOauth.newInstance().generatePostSignature(url);
    MyHttpclient myHttpclient = new MyHttpclient();
    myHttpclient.addHeader(new BasicHeader("Authorization", signature));
    String content = myHttpclient.requestPost(url, null);
    log.info("content=" + content);
    if (content == null || "".equals(content)) {
      log.warn("ȡ����ע����:" + nick_name + "ʧ��!");
      return false;
    }
    log.info("ȡ����ע����:" + nick_name + " �ɹ�!");
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
