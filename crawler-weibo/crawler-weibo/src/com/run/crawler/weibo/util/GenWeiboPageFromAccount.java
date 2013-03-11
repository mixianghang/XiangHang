package com.run.crawler.weibo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.Account;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.oauth.NeteaseOauth;
import com.run.crawler.weibo.util.oauth.TencentOauth;
import com.run.crawler.weibo.util.query.NeteaseFollowerQuery;
import com.run.crawler.weibo.util.query.NeteaseFriendQuery;
import com.run.crawler.weibo.util.query.NeteaseSearchQuery;
import com.run.crawler.weibo.util.query.NeteaseUserShowQuery;
import com.run.crawler.weibo.util.query.NeteaseWeiboFromUserTimelineQuery;
import com.run.crawler.weibo.util.query.NeteaseWeiboQuery;
import com.run.crawler.weibo.util.query.SinaFollowerQuery;
import com.run.crawler.weibo.util.query.SinaFriendQuery;
import com.run.crawler.weibo.util.query.SinaHourlyTrendQuery;
import com.run.crawler.weibo.util.query.SinaUserShowQuery;
import com.run.crawler.weibo.util.query.SinaWeiboFromUserTimeLineQuery;
import com.run.crawler.weibo.util.query.SinaWeiboQuery;
import com.run.crawler.weibo.util.query.SohuFollowerQuery;
import com.run.crawler.weibo.util.query.SohuFriendQuery;
import com.run.crawler.weibo.util.query.SohuSearchQuery;
import com.run.crawler.weibo.util.query.SohuUserShowQuery;
import com.run.crawler.weibo.util.query.SohuWeiboQuery;
import com.run.crawler.weibo.util.query.TencentFollowerQuery;
import com.run.crawler.weibo.util.query.TencentFriendQuery;
import com.run.crawler.weibo.util.query.TencentSearchQuery;
import com.run.crawler.weibo.util.query.TencentTrendQuery;
import com.run.crawler.weibo.util.query.TencentUserShowQuery;
import com.run.crawler.weibo.util.query.TencentWeiboFromUserTimeLineQuery;
import com.run.crawler.weibo.util.query.TencentWeiboQuery;
import com.run.crawler.weibo.util.query.TwitterFollowerQuery;
import com.run.crawler.weibo.util.query.TwitterFriendQuery;
import com.run.crawler.weibo.util.query.TwitterSearchQuery;
import com.run.crawler.weibo.util.query.TwitterUserShowQuery;
import com.run.crawler.weibo.util.query.TwitterWeiboFromUserTimelineQuery;
import com.run.crawler.weibo.util.query.TwitterWeiboQuery;

public class GenWeiboPageFromAccount {

  static Logger log = Logger.getLogger(GenWeiboPageFromAccount.class);
  public static final int SINA_INTERVAL = 2;
  public static final int TENCENT_INTERVAL = 2;
  public static final int TWITTER_INTERVAL = 2;
  public static final int NETEASE_INTERVAL = 2;
  public static final int SOHU_INTERVAL = 2;
  
  public static WeiboPage ofTencentFriend(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://open.t.qq.com/api/friends/mutual_list";
    String nick = encode(nickName);
    TencentFriendQuery tencentFriendQuery = new TencentFriendQuery();
    tencentFriendQuery.setName(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    weiboPage.setQuery(tencentFriendQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.Friend);
    weiboPage.setStartTime(account.getOrder() * TENCENT_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  public static WeiboPage ofTencentFollower(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://open.t.qq.com/api/friends/user_fanslist";
    String nick = encode(nickName);
    TencentFollowerQuery tencentFollowerQuery = new TencentFollowerQuery();
    tencentFollowerQuery.setName(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    weiboPage.setQuery(tencentFollowerQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.Follower);
    weiboPage.setStartTime(account.getOrder() * TENCENT_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  public static WeiboPage ofTencentWeibo() {
    String url = "https://open.t.qq.com/api/statuses/home_timeline";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    weiboPage.setQuery(new TencentWeiboQuery());
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.Weibo);
    weiboPage.setStartTime(10);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  public static WeiboPage ofTencentWeiboFromUserTimeLine(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "http://open.t.qq.com/api/statuses/user_timeline";
    String nick = encode(nickName);
    TencentWeiboFromUserTimeLineQuery tencentWeiboFromUserTimeLineQuery =
        new TencentWeiboFromUserTimeLineQuery();
    tencentWeiboFromUserTimeLineQuery.setName(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    weiboPage.setQuery(tencentWeiboFromUserTimeLineQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.WeiboFromUserTimeLine);
    weiboPage.setStartTime(account.getOrder() * TENCENT_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    weiboPage.setPriority(1);
    return weiboPage;
  }

  /**
   * 腾讯搜索
   * 
   * @return
   */
  public static WeiboPage ofTencentSearch() {
    String url = "http://open.t.qq.com/api/search/t";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    TencentSearchQuery tencentSearchQuery = new TencentSearchQuery();
    tencentSearchQuery.setKeyword("");
    weiboPage.setQuery(tencentSearchQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.Search);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 用户个人属性
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofTencentUserShow(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://open.t.qq.com/api/user/other_info";
    String nick = encode(nickName);
    TencentUserShowQuery tencentUserShowQuery = new TencentUserShowQuery();
    tencentUserShowQuery.setName(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    weiboPage.setQuery(tencentUserShowQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.UserShow);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 腾讯热点话题
   * 
   * @return
   */
  public static WeiboPage ofTencentTrend() {
    String url = "http://open.t.qq.com/api/trends/ht";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setOauthUrl(TencentOauth.newInstance().getOauthUrl());
    TencentTrendQuery tencentTrendQuery = new TencentTrendQuery();
    weiboPage.setQuery(tencentTrendQuery);
    weiboPage.setTask(Task.Tencent);
    weiboPage.setJob(Job.Trend);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 新浪好友
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofSinaFriend(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://api.weibo.com/2/friendships/friends.json";
    String nick = encode(nickName);
    SinaFriendQuery SinaFriendQuery = new SinaFriendQuery();
    SinaFriendQuery.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(SinaFriendQuery);
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.Friend);
    weiboPage.setStartTime(account.getOrder() * SINA_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  public static WeiboPage ofSinaFollower(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://api.weibo.com/2/friendships/followers.json";
    String nick = encode(nickName);
    SinaFollowerQuery sinaFollowerQuery = new SinaFollowerQuery();
    sinaFollowerQuery.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(sinaFollowerQuery);
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.Follower);
    weiboPage.setStartTime(account.getOrder() * SINA_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  public static WeiboPage ofSinaWeiboFromUserTimeLine(Account account) {
    long accountId = account.getAccountId();
    String nickName = account.getNickName();
    String url = "https://api.weibo.com/2/statuses/user_timeline.json";
    String nick = encode(nickName);
    SinaWeiboFromUserTimeLineQuery sinaWeiboFromUserTimeLineQuery =
        new SinaWeiboFromUserTimeLineQuery();
    sinaWeiboFromUserTimeLineQuery.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(accountId);
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(sinaWeiboFromUserTimeLineQuery);
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.WeiboFromUserTimeLine);
    weiboPage.setStartTime(account.getOrder() * SINA_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    weiboPage.setPriority(1);
    return weiboPage;
  }

  public static WeiboPage ofSinaWeibo() {
    String url = "https://api.weibo.com/2/statuses/home_timeline.json";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(new SinaWeiboQuery());
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.Weibo);
    weiboPage.setStartTime(5);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 新浪一小时热点话题
   * 
   * @return
   */
  public static WeiboPage ofSinaHourlyTrend() {
    String url = "https://api.weibo.com/2/trends/hourly.json";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(new SinaHourlyTrendQuery());
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.Trend);
    weiboPage.setStartTime(10);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 新浪用户个人属性
   * 
   * @return
   */
  public static WeiboPage ofSinaUserShow(Account account) {
    String url = "https://api.weibo.com/2/users/show.json";
    WeiboPage weiboPage = new WeiboPage();
    String nick = account.getNickName();
    weiboPage.setUrl(url);
    SinaUserShowQuery sinaUserShowQuery = new SinaUserShowQuery();
    sinaUserShowQuery.setScreen_name(encode(nick));
    weiboPage.setQuery(sinaUserShowQuery);
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setTask(Task.Sina);
    weiboPage.setJob(Job.UserShow);
    weiboPage.setStartTime(account.getOrder() * SINA_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Twitter 粉丝主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofTwitterFollower(Account account) {
    String url = "https://api.twitter.com/1.1/followers/list.json";
    TwitterFollowerQuery query = new TwitterFollowerQuery();
    String nick = account.getNickName();
    query.setScreen_name(nick);
    query.setCursor("-1");
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.Follower);
    weiboPage.setStartTime(account.getOrder() * TWITTER_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Twitter 好友主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofTwitterFriend(Account account) {
    String url = "https://api.twitter.com/1.1/friends/list.json";
    TwitterFriendQuery query = new TwitterFriendQuery();
    String nick = account.getNickName();
    query.setScreen_name(nick);
    query.setCursor("-1");
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.Friend);
    weiboPage.setStartTime(account.getOrder() * TWITTER_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Twitter weibo任务
   * 
   * @return
   */
  public static WeiboPage ofTwitterWeibo() {
    String url = "https://api.twitter.com/1.1/statuses/home_timeline.json";
    TwitterWeiboQuery query = new TwitterWeiboQuery();
    query.setCount((int) VariableCache.extractorTotalNum.twitterWeiboTotalNum);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.Weibo);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Twitter weiboFromUserTimeLine任务
   * 
   * @return
   */
  public static WeiboPage ofTwitterWeiboFromUserTimeline(Account account) {
    String url = "https://api.twitter.com/1.1/statuses/user_timeline.json";
    TwitterWeiboFromUserTimelineQuery query = new TwitterWeiboFromUserTimelineQuery();
    String nick = account.getNickName();
    query.setScreen_name(nick);
    query.setCount((int) VariableCache.extractorTotalNum.twitterWeiboTotalNum);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setAccount(nick);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.WeiboFromUserTimeLine);
    weiboPage.setStartTime(account.getOrder() * TWITTER_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    weiboPage.setPriority(1);
    return weiboPage;
  }

  /**
   * 初始化Twitter weibo重点人用户属性主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofTwitterUserShow(Account account) {
    String url = "http://api.twitter.com/1.1/users/show.json";
    String nick = account.getNickName();
    TwitterUserShowQuery query = new TwitterUserShowQuery();
    query.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.UserShow);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * *******初始化Twitter 搜索任务****
   * 
   * @return
   */
  public static WeiboPage ofTwitterSearch() {
    String url = "https://api.twitter.com/1.1/search/tweets.json";
    TwitterSearchQuery query = new TwitterSearchQuery();
    query.setQ("钓鱼岛");
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.Search);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * *******初始化Twitter 热门话题任务*****
   * 
   * @return
   */
  public static WeiboPage ofTwitterTrend() {
    String url = "https://api.twitter.com/1.1/trends/available.json";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setTask(Task.Twitter);
    weiboPage.setJob(Job.Trend);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化netease 粉丝主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofNeteaseFollower(Account account) {
    String url = "https://api.t.163.com/statuses/followers.json";
    String nick = account.getNickName();
    NeteaseFollowerQuery query = new NeteaseFollowerQuery();
    query.setScreen_name(nick);
    query.setCursor("-1");
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Follower);
    weiboPage.setStartTime(account.getOrder() * NETEASE_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化netease 好友主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofNeteaseFriend(Account account) {
    String url = "https://api.t.163.com/statuses/friends.json";
    String nick = account.getNickName();
    NeteaseFriendQuery query = new NeteaseFriendQuery();
    query.setScreen_name(nick);
    query.setCursor("-1");
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Friend);
    weiboPage.setStartTime(account.getOrder() * NETEASE_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化netease weibo任务
   * 
   * @return
   */
  public static WeiboPage ofNeteaseWeibo() {
    String url = "https://api.t.163.com/statuses/home_timeline.json";
    NeteaseWeiboQuery query = new NeteaseWeiboQuery();
    query.setCount(String.valueOf(VariableCache.extractorTotalNum.neteaseWeiboTotalNum));
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Weibo);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化netease weibo任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofNeteaseWeiboFromUserTimeline(Account account) {
    String url = "https://api.t.163.com/statuses/user_timeline.json";
    NeteaseWeiboFromUserTimelineQuery query = new NeteaseWeiboFromUserTimelineQuery();
    query.setCount(String.valueOf(VariableCache.extractorTotalNum.neteaseWeiboTotalNum));
    String nick = account.getNickName();
    query.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.WeiboFromUserTimeLine);
    weiboPage.setStartTime(account.getOrder() * NETEASE_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    weiboPage.setPriority(1);
    return weiboPage;
  }

  /**
   * 初始化netease weibo重点人用户属性主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofNeteaseUserShow(Account account) {
    String url = "https://api.t.163.com/users/show.json";
    String nick = account.getNickName();
    NeteaseUserShowQuery query = new NeteaseUserShowQuery();
    query.setScreen_name(nick);
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.UserShow);
    weiboPage.setStartTime(account.getOrder() * NETEASE_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * *******初始化netease 搜索任务****
   * 
   * @return
   */
  public static WeiboPage ofNeteaseSearch() {
    String url = "http://api.t.163.com/1/statuses/search.json";
    NeteaseSearchQuery query = new NeteaseSearchQuery();
    query.setPer_page(String.valueOf(VariableCache.extractorTotalNum.neteaseSearchResultNo));
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Search);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * *******初始化netease 热门话题任务*****
   * 
   * @return
   */
  public static WeiboPage ofNeteaseTrend() {
    String url = "http://api.t.163.com/statuses/topRetweets/oneWeek.json";
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setUrl(url);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Trend);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Sohu weibo任务
   * 
   * @return
   */
  public static WeiboPage ofSohuWeiboFromUserTimeLine(Account account) {
	String url = "http://api.t.sohu.com/statuses/user_timeline/" + encode(account.getNickName()) +  ".json";
    SohuWeiboQuery query = new SohuWeiboQuery();
    query.setCount(String.valueOf(VariableCache.extractorTotalNum.sohuWeiboTotalNum));
    String nick = account.getNickName();
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.getState().setPageNo(1);
    weiboPage.setUrl(url);
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(nick);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Sohu);
    weiboPage.setJob(Job.WeiboFromUserTimeLine);
    weiboPage.setStartTime(account.getOrder() * SOHU_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    weiboPage.setPriority(1);
    return weiboPage;
  }

  /**
   * 初始化Sohu 粉丝主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofSohuFollower(Account account) {
    String url = "http://api.t.sohu.com/statuses/followers/" + account.getUserId() + ".json";
    SohuFollowerQuery query = new SohuFollowerQuery();
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.getState().setPageNo(1);
    weiboPage.setUrl(url);
    weiboPage.setAccount(account.getNickName());
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Sohu);
    weiboPage.setJob(Job.Follower);
    weiboPage.setStartTime(account.getOrder() * SOHU_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Sohu 好友主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofSohuFriend(Account account) {
    String url = "http://api.t.sohu.com/statuses/friends/" + account.getUserId() + ".json";
    SohuFriendQuery query = new SohuFriendQuery();
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(account.getNickName());
    weiboPage.getState().setPageNo(1);
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Sohu);
    weiboPage.setJob(Job.Friend);
    weiboPage.setStartTime(account.getOrder() * SOHU_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 初始化Sohu weibo重点人用户属性主任务
   * 
   * @param account
   * @return
   */
  public static WeiboPage ofSohuUserShow(Account account) {
    String url = "http://api.t.sohu.com/users/show/" + encode(account.getNickName()) +  ".json";
    SohuUserShowQuery query = new SohuUserShowQuery();
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setAccountId(account.getAccountId());
    weiboPage.setAccount(account.getNickName());
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Sohu);
    weiboPage.setJob(Job.UserShow);
    weiboPage.setStartTime(account.getOrder() * SOHU_INTERVAL);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * *******初始化Sohu 搜索任务****
   * 
   * @return
   */
  public static WeiboPage ofSohuSearch() {
    String url = "http://api.t.sohu.com/statuses/search.json";
    SohuSearchQuery query = new SohuSearchQuery();
    query.setQ(encode("钓鱼岛"));
    WeiboPage weiboPage = new WeiboPage();
    weiboPage.setOauthUrl(NeteaseOauth.newInstance().getOauthUrl());
    weiboPage.setUrl(url);
    weiboPage.setQuery(query);
    weiboPage.setTask(Task.Netease);
    weiboPage.setJob(Job.Search);
    weiboPage.setStartTime(0);
    weiboPage.setHttpMethod(HttpMethod.GET);
    return weiboPage;
  }

  /**
   * 加载除了weibo以外的所有任务
   * 
   * @param account
   * @return
   */
  public static List<WeiboPage> ofAllExceptWeibo(Account account) {

    List<WeiboPage> list = new ArrayList<WeiboPage>();
    switch (account.getTask()) {
      case Sina:
        if (VariableCache.FrontierTaskInit.sinaStart) {
          list.add(GenWeiboPageFromAccount.ofSinaFollower(account));
          list.add(GenWeiboPageFromAccount.ofSinaFriend(account));
          /**
           * 2.5版本默认采用userTimeLine采集
           */
          if (VariableCache.Controller.isStartFromDB) {
            list.add(GenWeiboPageFromAccount.ofSinaWeiboFromUserTimeLine(account));
          }
        }
        break;
      case Tencent:
        if (VariableCache.FrontierTaskInit.tencentStart) {
            list.add(GenWeiboPageFromAccount.ofTencentWeiboFromUserTimeLine(account));
            list.add(GenWeiboPageFromAccount.ofTencentFollower(account));
            list.add(GenWeiboPageFromAccount.ofTencentFriend(account));

        }
        break;
      case Twitter:
        if (VariableCache.FrontierTaskInit.twitterStart) {
          /**
           * 2.5版本默认采用userTimeLine采集
           */
          if (VariableCache.Controller.isStartFromDB) {
            list.add(GenWeiboPageFromAccount.ofTwitterWeiboFromUserTimeline(account));
          }
          list.add(GenWeiboPageFromAccount.ofTwitterFollower(account));
          list.add(GenWeiboPageFromAccount.ofTwitterFriend(account));
        }
        break;
      case Netease:
        if (VariableCache.FrontierTaskInit.neteaseStart) {
          list.add(GenWeiboPageFromAccount.ofNeteaseWeiboFromUserTimeline(account));
          list.add(GenWeiboPageFromAccount.ofNeteaseFollower(account));
          list.add(GenWeiboPageFromAccount.ofNeteaseFriend(account));
        }
        break;
      case Sohu:
        if (VariableCache.FrontierTaskInit.sohuStart) {
          list.add(GenWeiboPageFromAccount.ofSohuWeiboFromUserTimeLine(account));
          list.add(GenWeiboPageFromAccount.ofSohuUserShow(account));
        }
        break;
    }
    return list;
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
