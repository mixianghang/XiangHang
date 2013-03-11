package com.run.crawler.weibo.util.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.run.crawler.weibo.util.TimerUtil;

public class VariableCache {
	static Logger log = Logger.getLogger(AccountRedisCache.class);
	private static PropertiesConfiguration properties;
	static {
		String weiboHome = StringUtils.stripToEmpty(System
				.getProperty("crawler.weibo.home"));
		weiboHome += weiboHome.length() == 0 ? "" : File.separator;
		String fileName = weiboHome + "conf/variableCache.properties";
		try {
			properties = new PropertiesConfiguration(fileName);
		} catch (ConfigurationException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @author jerry vecolityWriteCache配置
	 */
	public static class vecolityWriteCache {
		public static long delay = Integer.parseInt(properties.getString(
				"vecolityWriteCache.delay", "30")) * TimerUtil._1s;
		public static long period = Integer.parseInt(properties.getString(
				"vecolityWriteCache.period", "30")) * TimerUtil._1s;
	}

	public static class vecolityWriter {
		public static String folder = properties.getString(
				"vecolityWriter.folder", "velocity/");
		public static String propertiesFile = properties.getString(
				"vecolityWriter.propertiesFile", "conf/velocity.properties");
	}

	public static class AccountCache {
		public static String accountFolder = properties.getString(
				"keyPerson.account", "account/");
		public static String bakAccountFolder = properties.getString(
				"keyPerson.account.bak", "account.bak/");
		public static long reloadInterval = Long.parseLong(properties
				.getString("accountCache.reloadInterval", "60")) * 1000;
		public static long maxFailedNo = Long.parseLong(properties.getString(
				"accountCache.maxFailedNo", "3"));

	}

	/**
	 * 
	 * @author jerry net包配置
	 */
	public static class net {
		public static Timer timer = new Timer("main");
	}

	/**
	 * 
	 * @author jerry Frontier配置
	 */
	public static class Frontier {
		public static String TaskFileName = properties.getString(
				"frontier.TaskFileName", "");
		public static int checkTime = Integer.parseInt(properties.getString(
				"frontier.checkTime", "5"));
	}

	public static class extractorTotalNum {
		public static int sinaFollowerTotalNum = Integer.parseInt(properties
				.getString("extractorTotalNum.sinaFollowerTotalNum", "55"));
		public static int sinaFriendTotalNum = Integer.parseInt(properties
				.getString("extractorTotalNum.sinaFriendTotalNum", "22"));

		public static long twitterWeiboTotalNum = Long.parseLong(properties
				.getString("extractorTotalNum.twitterWeiboTotalNum", "200"));

		public static int neteaseWeiboTotalNum = Integer.parseInt(properties
				.getString("extractorTotalNum.neteaseWeiboTotalNum", "200"));
		public static int neteaseSearchResultNo = Integer.parseInt(properties
				.getString("extractorTotalNum.neteaseSearchResultNo", "20"));

		public static int sohuWeiboTotalNum = Integer.parseInt(properties
				.getString("extractorTotalNum.sohuWeiboTotalNum", "200"));
		public static int sohuSearchResultNo = Integer.parseInt(properties
				.getString("extractorTotalNum.sohuSearchResultNo", "20"));
	}

	/**
	 * writerOkRule
	 * 
	 * @author boy
	 * 
	 */
	public static class writerOkRule {
		/**
		 * 允许存储出现重复记录的最大条数
		 */
		public static int maxStoreRepeatNum = Integer.parseInt(properties
				.getString("writerOkRule.maxStoreRepeatNum", "5"));
	}

	public static class Task {
		public static long sinaRateControl = Long.parseLong(properties
				.getString("Task.sinaRateControl", "3")) * 1000;
		public static long tencentRateControl = Long.parseLong(properties
				.getString("Task.tencentRateControl", "3")) * 1000;
		public static long twitterRateControl = Long.parseLong(properties
				.getString("Task.twitterRateControl", "6")) * 1000;
		public static long sohuRateControl = Long.parseLong(properties
				.getString("Task.sohuRateControl", "3")) * 1000;
		public static long neteaseRateControl = Long.parseLong(properties
				.getString("Task.neteaseRateControl", "5")) * 1000;
		public static long sinaFriendShipProcessInterval = Long
				.parseLong(properties.getString(
						"Task.sinaFriendShipProcessInterval", "6")) * 1000;
		public static long twitterFriendShipProcessInterval = Long
				.parseLong(properties.getString(
						"Task.twitterFriendShipProcessInterval", "2")) * 1000;
	}

	/**
	 * 
	 * @author jerry JredisFilter配置
	 */
	public static class JredisFilter {
		public static Map<String, Integer> hostAndPort = init();

		private static Map<String, Integer> init() {
			String host2Port = properties.getString("JredisFilter.host2port",
					"117.18.113.5:6379");
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String host2port : host2Port.split(";")) {
				String[] ss = host2port.split(":");
				map.put(ss[0], Integer.parseInt(ss[1]));
			}
			return map;
		}
	}

	public static class AccountRedisCache {
		public static String host = properties.getString(
				"AccountRedisCache.host", "117.18.113.5");
		public static int port = Integer.parseInt(properties.getString(
				"AccountRedisCache.port", "6380"));
	}

	public static class Log4j {
		public static String propertiesFile = properties.getString(
				"Log4j.propertiesFile", "conf/log4j.properties");
	}

	public static class Controller {
		public static int corePoolSize = Integer.parseInt(properties.getString(
				"Controller.corePoolSize", "50"));
		public static int maximumPoolSize = Integer.parseInt(properties
				.getString("Controller.maximumPoolSize", "1000"));
		public static long keepAliveTime = Long.parseLong(properties.getString(
				"Controller.keepAliveTime", "1000"));
		public static int blockingQueueSize = Integer.parseInt(properties
				.getString("Controller.blockingQueueSize", "10000"));

		public static boolean isStartFromDB = Boolean.parseBoolean(properties
				.getString("Controller.isStartFromDB", "false"));
	}

	/**
	 * VelocityBeanPool配置
	 * 
	 * @author jerry
	 * 
	 */
	public static class VelocityBeanPool {
		public static int maxActive = Integer.parseInt(properties.getString(
				"VelocityBeanPool.maxActive", "20"));
		public static long maxWait = Integer.parseInt(properties.getString(
				"VelocityBeanPool.maxWait", "1000"));
	}

	public static class MyHttpclient {
		public static int CONNECTION_TIMEOUT = Integer.parseInt(properties
				.getString("MyHttpclient.CONNECTION_TIMEOUT", "2000"));
	}

	public static class SinaOauth {
		public static boolean startRefresh = Boolean.parseBoolean(properties
				.getString("SinaOauth.startRefresh", "true"));

		public static String sinaOauthFile = properties.getString(
				"SinaOauth.sinaOauthFile", "conf/SinaOauth.oauth");

		public static String loginName = properties.getString(
				"SinaOauth.loginName", "18977646723@189.cn");

		public static String loginPassword = properties.getString(
				"SinaOauth.loginPassword", "hello2771012");
	}

	public static class TencnentOauth {
		public static String tencentOauthFile = properties.getString(
				"TencnentOauth.tencentOauthFile", "conf/TencentOauth.oauth");
	}

	public static class TwitterOauth {
		public static String twitterOauthFile = properties.getString(
				"TwitterOauth.twitterOauthFile", "conf/TwitterOauth.oauth");
	}

	public static class NeteaseOauth {
		public static String neteaseOauthFile = properties.getString(
				"NeteaseOauth.neteaseOauthFile", "conf/NeteaseOauth.oauth");
	}

	public static class SohuOauth {
		public static String sohuOauthOauthFile = properties.getString(
				"SohuOauth.sohuOauthOauthFile", "conf/SohuOauth.oauth");
	}

	public static class GenWeiboPageFromAccount {

		public static int sinaWeiboStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.sinaWeiboStartTime", "180"));
		public static int sinaFriendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sinaFriendStartTime",
						"72000"));
		public static int sinaFollowerStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sinaFollowerStartTime",
						"72000"));
		public static int sinaSearchStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.sinaSearchStartTime", "60"));
		public static int sinaTrendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sinaTrendStartTime",
						"43200"));
		public static int sinaUserShowStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sinaUserShowStartTime",
						"10"));

		public static int tencentWeiboStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.tencentWeiboStartTime",
						"300"));
		public static int tencentWeiboFromUserTimeLineStartTime = Integer
				.parseInt(properties
						.getString(
								"GenWeiboPageFromAccount.tencentWeiboFromUserTimeLineStartTime",
								"300"));
		public static int tencentFriendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.tencentFriendStartTime",
						"72000"));
		public static int tencentFollowerStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.tencentFollowerStartTime",
						"72000"));
		public static int tencentSearchStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.tencentSearchStartTime",
						"300"));
		public static int tencentTrendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.tencentTrendStartTime",
						"7200"));
		public static int tencentUserShowStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.tencentUserShowStartTime",
						"10"));

		public static int twitterWeiboStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.twitterWeiboStartTime",
						"60"));
		public static int twitterFollowerStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.twitterFollowerStartTime",
						"72000"));
		public static int twitterFriendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.twitterFriendStartTime",
						"72000"));
		public static int twitterSearchStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.twitterSearchStartTime",
						"120"));
		public static int twitterTrendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.twitterTrendStartTime",
						"7200"));
		public static int twitterUserShowStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.twitterUserShowStartTime",
						"7200"));

		public static int neteaseWeiboStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.neteaseWeiboStartTime",
						"60"));
		public static int neteaseFriendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.neteaseFriendStartTime",
						"72000"));
		public static int neteaseFollowerStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.neteaseFollowerStartTime",
						"72000"));
		public static int neteaseSearchStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.neteaseSearchStartTime",
						"60"));
		public static int neteaseTrendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.neteaseTrendStartTime",
						"72000"));
		public static int neteaseUserShowStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.neteaseUserShowStartTime",
						"72000"));

		public static int sohuWeiboStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.sohuWeiboStartTime", "300"));
		public static int sohuFriendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sohuFriendStartTime",
						"72000"));
		public static int sohuFollowerStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sohuFollowerStartTime",
						"72000"));
		public static int sohuSearchStartTime = Integer
				.parseInt(properties.getString(
						"GenWeiboPageFromAccount.sohuSearchStartTime", "300"));
		public static int sohuTrendStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sohuTrendStartTime",
						"72000"));
		public static int sohuUserShowStartTime = Integer.parseInt(properties
				.getString("GenWeiboPageFromAccount.sohuUserShowStartTime",
						"72000"));
	}

	// fix CRAWEIBO-31
	public static class FrontierTaskInit {
		public static boolean twitterStart = Boolean.parseBoolean(properties
				.getString("FrontierTaskInit.twitterStart", "false"));

		public static boolean sinaStart = Boolean.parseBoolean(properties
				.getString("FrontierTaskInit.sinaStart", "false"));

		public static boolean tencentStart = Boolean.parseBoolean(properties
				.getString("FrontierTaskInit.tencentStart", "false"));

		public static boolean neteaseStart = Boolean.parseBoolean(properties
				.getString("FrontierTaskInit.neteaseStart", "false"));

		public static boolean sohuStart = Boolean.parseBoolean(properties
				.getString("FrontierTaskInit.sohuStart", "false"));
	}

	public static class CheckWeiboPageIsStartNowRule {
		public static boolean isOpenStartNowOfNewWeiboPage = Boolean
				.parseBoolean(properties
						.getString(
								"CheckWeiboPageIsStartNowRule.isOpenStartNowOfNewWeiboPage",
								"true"));
	}

	public static class AccountDBCache {
		public static String queryString = properties.getString(
				"AccountDBCache.queryString", "");
		public static String host = properties.getString("AccountDBCache.host",
				"");
		public static String name = properties.getString("AccountDBCache.name",
				"");
		public static String password = properties.getString(
				"AccountDBCache.password", "");
	}

	/**
	 * 缓存初始化
	 * 
	 * @return
	 */
	public static PropertiesConfiguration init(String fileName) {

		// if ("".equals(fileName) || null == fileName) {
		// String weiboHome =
		// StringUtils.stripToEmpty(System.getProperty("crawler.weibo.home"));
		// weiboHome += weiboHome.length() == 0 ? "" : File.separator;
		// fileName = weiboHome + "conf/variableCache.properties";
		// }

		PropertiesConfiguration properties = null;
		try {
			properties = new PropertiesConfiguration(fileName);
		} catch (ConfigurationException e) {
			log.error(e.getMessage());
		}

		VariableCache.properties = properties;
		return properties;
	}

	public static void main(String[] args) {
		System.out.println(properties);
	}

}
