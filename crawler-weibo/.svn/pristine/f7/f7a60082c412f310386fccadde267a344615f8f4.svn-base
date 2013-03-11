package com.run.crawler.weibo.util.oauth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.cache.VariableCache;

public class SohuOauth {
	private static final Logger log = Logger.getLogger(SohuOauth.class);
	volatile int i = 0;
	List<Oauth> list = new ArrayList<Oauth>();
	
	private static final SohuOauth sohuOauth = new SohuOauth();
	
	public static SohuOauth newInstance() {
		return sohuOauth;
	}
	public class Oauth {
		private String consumerKey;
		private String consumerSecret;
		private String accessToken;
		private String accessTokenSecret;

		
		
		public Oauth(String consumerKey, String consumerSecret,
				String accessToken, String accessTokenSecret) {
			super();
			this.consumerKey = consumerKey;
			this.consumerSecret = consumerSecret;
			this.accessToken = accessToken;
			this.accessTokenSecret = accessTokenSecret;
		}

		public String getConsumerKey() {
			return consumerKey;
		}

		public void setConsumerKey(String consumerKey) {
			this.consumerKey = consumerKey;
		}

		public String getConsumerSecret() {
			return consumerSecret;
		}

		public void setConsumerSecret(String consumerSecret) {
			this.consumerSecret = consumerSecret;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}

		public String getAccessTokenSecret() {
			return accessTokenSecret;
		}

		public void setAccessTokenSecret(String accessTokenSecret) {
			this.accessTokenSecret = accessTokenSecret;
		}

	}

	private SohuOauth() {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	private  void init() throws IOException{
		String fileName = VariableCache.SohuOauth.sohuOauthOauthFile;
		log.info("init fileName: "+fileName);
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
			String consumerKey = json.getString("consumerKey");
			String consumerSecret = json.getString("consumerSecret");
			String accessToken = json.getString("accessToken");
			String accessTokenSecret = json.getString("accessTokenSecret");
			list.add(new Oauth(consumerKey, consumerSecret, accessToken,
					accessTokenSecret));
		}
	}

	public  void generateSignature(WeiboPage weiboPage) {
		try {
			
			i = (++i)%list.size();
			Oauth oauth = list.get(i);
			String queryString = (weiboPage.getQuery() == null) ? ""
					: weiboPage.getQuery().toString();
			String urlStr = (queryString == null || "".equals(queryString)) ? weiboPage
					.getUrl() : weiboPage.getUrl() + "?" + queryString;
			URL url = new URL(urlStr);
			HttpURLConnection request = (HttpURLConnection) url
					.openConnection();
			AbstractOAuthConsumer consumer = new DefaultOAuthConsumer(
					oauth.getConsumerKey(), oauth.getConsumerSecret());
			consumer.setTokenWithSecret(oauth.getAccessToken(), oauth.getAccessTokenSecret());
			String signature = consumer.signature(request);
			/**
			 * weiboPage原来的请求头信息
			 */
			Header[] headers = weiboPage.getHttpHeader();
			int length = (headers == null) ? 0 : headers.length;

			Header[] newHeaders = new Header[length + 1];
			if (headers != null && length > 0)
				System.arraycopy(headers, 0, newHeaders, 0, length);
			/**
			 * 新添加OAuth授权头信息
			 */
			newHeaders[length] = new BasicHeader("Authorization", signature);
			weiboPage.setHttpHeader(newHeaders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成Post OAuth授权
	 * 
	 * @param url
	 * @return
	 */
	public  String generatePostSignature(String urlStr) {
		String signature = "";
		try {
			i = (++i)%list.size();
			Oauth oauth = list.get(i);
			URL url = new URL(urlStr);
			HttpURLConnection request = (HttpURLConnection) url
					.openConnection();
			request.setRequestMethod("POST");
			AbstractOAuthConsumer consumer = new DefaultOAuthConsumer(
					oauth.getConsumerKey(), oauth.getConsumerSecret());
			consumer.setTokenWithSecret(oauth.getAccessToken(), oauth.getAccessTokenSecret());
			signature = consumer.signature(request);
			log.info("i=" + i);
			log.info("curl --get '" + urlStr + "' --header 'Authorization:"
					+ signature + "' --verbose;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signature;
	}
}
