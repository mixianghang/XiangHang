package com.run.crawler.weibo.util.oauth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.TimerUtil;
import com.run.crawler.weibo.util.cache.VariableCache;

public class TencentOauth {

	public Logger log = Logger.getLogger(this.getClass());

	Lock lock = new ReentrantLock();

	private final long expires_in = 6 * 24 * TimerUtil._1hour;

	volatile int i = 0;
	int length = 0;

	List<Oauth> list = new ArrayList<Oauth>();

	public String getOauthUrl() {
		lock.lock();
		i++;
		String oauthUrl = list.get(i % length).oauthUrl();
		lock.unlock();
		return oauthUrl;
	}

	private TencentOauth() {
		try {
			lock.lock();
			init();
			lock.unlock();
		} catch (IOException e) {
			log.error("授权初始化失败 : " + e.getMessage());
			e.printStackTrace();
		}

		/**
		 * 一天更新一次授权
		 */
		new Timer("TencentOauth").schedule(new TimerTask() {
			@Override
			public void run() {
				lock.lock();
				Iterator<Oauth> iterator = list.iterator();
				while (iterator.hasNext()) {
					Oauth oauth = null;
					try {
						oauth = iterator.next();
						oauth.reset();
					} catch (Exception e) {
						oauth.setAccess_token("");
						log.error(e.getMessage());
					} finally {
						try {
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				try {
					outPutOauth();
				} catch (IOException e) {
					log.error("输出oauth错误 : " + e.getMessage());
					e.printStackTrace();
				}

				try {
					init();
				} catch (IOException e) {
					e.printStackTrace();
				}
				lock.unlock();

			}
		}, 0, 11 * TimerUtil._1hour);
	}

	/**
	 * 将oauth写到磁盘
	 * 
	 * @throws IOException
	 */
	private void outPutOauth() throws IOException {
		String fileName = VariableCache.TencnentOauth.tencentOauthFile;
		log.info("output fileName: " + fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(new File(fileName))));

		JSONArray jsonArray = new JSONArray();

		for (Oauth oauth : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("oauth_consumer_key", oauth.oauth_consumer_key);
			jsonObject.put("access_token", oauth.access_token);
			jsonObject.put("openid", oauth.openid);
			jsonObject.put("clientip", oauth.clientip);
			jsonObject.put("refresh_token", oauth.refresh_token);
			jsonArray.add(jsonObject);
		}
		String outPutString = jsonArray.toString(1);
		bufferedWriter.write(outPutString);
		bufferedWriter.flush();
		bufferedWriter.close();
	}

	private void init() throws IOException {

		list.clear();

		String fileName = VariableCache.TencnentOauth.tencentOauthFile;
		log.info("init fileName: " + fileName);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileName))));
		StringBuilder sb = new StringBuilder();
		String s = "";
		while ((s = bufferedReader.readLine()) != null) {
			sb.append(s);
		}

		bufferedReader.close();

		JSONArray jsonArray = JSONArray.fromObject(sb.toString());
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject json = iterator.next();
			String oauth_consumer_key = json.getString("oauth_consumer_key");
			String access_token = json.getString("access_token");
			String openid = json.getString("openid");
			String clientip = json.getString("clientip");
			String refresh_token = json.getString("refresh_token");

			if ("".equals(oauth_consumer_key) || "".equals(access_token)
					|| "".equals(openid) || "".equals(clientip)
					|| "".equals(refresh_token)) {
				continue;
			}

			list.add(new Oauth(oauth_consumer_key, access_token, openid,
					clientip, refresh_token, expires_in));
		}
		length = list.size();
	}

	private static class Proxy {
		private static TencentOauth tencentOauth = new TencentOauth();
	}

	public static TencentOauth newInstance() {
		return Proxy.tencentOauth;
	}

	public class Oauth {

		private final String oauth_consumer_key;
		private String access_token;
		private final String openid;
		private final String clientip;
		private final String oauth_version = "2.a";
		private final String scope = "all";
		/**
		 * 刷新token
		 */
		private String refresh_token;

		public String getRefresh_token() {
			return refresh_token;
		}

		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}

		public String getOpenid() {
			return openid;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		/**
		 * 过期时间
		 */
		@SuppressWarnings("unused")
		private final long expires_in;

		public Oauth(String oauth_consumer_key, String access_token,
				String openid, String clientip, String refresh_token,
				long expires_in) {
			this.oauth_consumer_key = oauth_consumer_key;
			this.access_token = access_token;
			this.openid = openid;
			this.clientip = clientip;

			this.refresh_token = refresh_token;
			this.expires_in = expires_in;
		}

		public String oauthUrl() {
			String s = "&";
			StringBuilder sb = new StringBuilder();
			sb.append("oauth_consumer_key=").append(oauth_consumer_key)
					.append(s);
			sb.append("access_token=").append(access_token).append(s);
			sb.append("openid=").append(openid).append(s);
			sb.append("clientip=").append(clientip).append(s);
			sb.append("oauth_version=").append(oauth_version).append(s);
			sb.append("scope=").append(scope);
			return sb.toString();
		}

		/**
		 * 重置授权
		 */
		public void reset() {
			log.info("reset");
			String url = "https://open.t.qq.com/cgi-bin/oauth2/access_token";
			StringBuilder sb = new StringBuilder();
			sb.append("client_id=").append(this.oauth_consumer_key);
			sb.append("&grant_type=refresh_token");
			sb.append("&").append("refresh_token=").append(this.refresh_token);

			String content = new MyHttpclient().requestGet(url, sb.toString());
			log.debug("content:" + content);

			if (null == content || "".equals(content))
				return;
			String[] str1 = content.split("&");
			Map<String, String> map = new HashMap<String, String>();
			for (String s : str1) {
				String str2[] = s.split("=");
				if (str2.length < 2) {
					continue;
				}
				map.put(str2[0], str2[1]);
			}
			String access_token = map.get("access_token");
			String refresh_token = map.get("refresh_token");
			log.info("access_token:" + access_token);
			log.info("refresh_token:" + refresh_token);
			this.access_token = access_token;
			this.refresh_token = refresh_token;
		}
	}

	public static void main(String[] args) {
		System.out.println(newInstance().getOauthUrl());

		try {
			Thread.sleep(5 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(newInstance().getOauthUrl());
		System.out.println(newInstance().getOauthUrl());
	}
}
