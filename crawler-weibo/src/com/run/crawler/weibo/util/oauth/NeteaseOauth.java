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
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.util.TimerUtil;
import com.run.crawler.weibo.util.cache.VariableCache;

public class NeteaseOauth {

	public Logger log = Logger.getLogger(this.getClass());

	private final long expires_in = 24 * TimerUtil._1hour;

	volatile int i = 0;
	int length = 0;

	List<Oauth> list = new ArrayList<Oauth>();

	public String getOauthUrl() {
		i++;
		return list.get(i % length).oauthUrl();
	}

	private NeteaseOauth() {
		try {
			init();
			synchronized (list) {
				for (Oauth oauth : list) {
					oauth.reset();
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		length = list.size();

		/**
		 * 一天更新一次授权
		 */
		new Timer("NeteastOauth").schedule(new TimerTask() {
			@Override
			public void run() {
				synchronized (list) {
					for (Oauth oauth : list) {
						oauth.reset();
					}
					try {
						saveOauth2File();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}, 23 * TimerUtil._1hour, 23 * TimerUtil._1hour);
	}

	private void init() throws IOException {

		String fileName = VariableCache.NeteaseOauth.neteaseOauthFile;
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
			String client_id = json.getString("client_id");
			String client_secret = json.getString("client_secret");
			String grant_type = json.getString("grant_type");
			String refresh_token = json.getString("refresh_token");
			String access_token = json.getString("access_token");
			if ("".equals(client_id) || "".equals(client_secret)
					|| "".equals(grant_type) || "".equals(refresh_token)) {
				continue;
			}
			log.info("client_id=" + client_id);
			log.info("client_secret=" + client_secret);
			log.info("grant_type=" + grant_type);
			log.info("refresh_token=" + refresh_token);
			log.info("access_token=" + access_token);
			list.add(new Oauth(client_id, client_secret, grant_type,
					refresh_token, access_token, expires_in));
		}
	}

	private static class Proxy {
		private static NeteaseOauth neteastOauth = new NeteaseOauth();
	}

	public static NeteaseOauth newInstance() {
		return Proxy.neteastOauth;
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure(VariableCache.Log4j.propertiesFile);
		List<Oauth> li = newInstance().list;
		for (Oauth oauth : li) {
			oauth.reset();
		}
	}

	public class Oauth {
		private final String client_id;
		private String client_secret;
		private final String grant_type;
		private String refresh_token;
		private String access_token;

		public String getRefresh_token() {
			return refresh_token;
		}

		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}

		public String getClient_secret() {
			return client_secret;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public void setClient_secret(String client_secret) {
			this.client_secret = client_secret;
		}

		public String getClient_id() {
			return client_id;
		}

		public String getGrant_type() {
			return grant_type;
		}

		public long getExpires_in() {
			return expires_in;
		}

		private final long expires_in;

		public Oauth(String client_id, String client_secret, String grant_type,
				String refresh_token, String access_token, long expires_in) {
			this.client_id = client_id;
			this.client_secret = client_secret;
			this.grant_type = grant_type;
			this.refresh_token = refresh_token;
			this.access_token = access_token;
			this.expires_in = expires_in;
		}

		/**
		 * 获取授权
		 * 
		 * @return
		 */
		public String oauthUrl() {
			return "access_token=" + this.access_token;
		}

		/**
		 * 重置授权
		 */
		public void reset() {
			log.info("reset");
			String url = "https://api.t.163.com/oauth2/access_token";
			StringBuilder sb = new StringBuilder();
			sb.append("client_id=").append(this.client_id);
			sb.append("&client_secret=").append(this.client_secret);
			sb.append("&grant_type=refresh_token");
			sb.append("&").append("refresh_token=").append(this.refresh_token);
			String content = new MyHttpclient().requestGet(url, sb.toString());
			log.debug("content:" + content);
			if (null == content || "".equals(content))
				return;
			JSONObject object = JSONObject.fromObject(content);
			String refresh_token = object.getString("refresh_token");
			String access_token = object.getString("access_token");
			this.refresh_token = refresh_token;
			this.access_token = access_token;
			log.info("access_token:" + access_token);
			log.info("refresh_token:" + refresh_token);
		}
	}

	private void saveOauth2File() throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (Oauth oauth : list) {
			JSONObject object = new JSONObject();
			object.put("client_id", oauth.getClient_id());
			object.put("client_secret", oauth.getClient_secret());
			object.put("grant_type", oauth.getGrant_type());
			object.put("refresh_token", oauth.getRefresh_token());
			object.put("access_token", oauth.getAccess_token());
			jsonArray.add(object);
		}
		File file = new File(VariableCache.NeteaseOauth.neteaseOauthFile);
		if (file.exists())
			file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(
						VariableCache.NeteaseOauth.neteaseOauthFile)));
		writer.write(jsonArray.toString(1));
		writer.close();
	}
}
