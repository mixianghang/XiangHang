package com.run.crawler.weibo.util.oauth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.cache.VariableCache;

/**
 * OAuth授权类
 * 
 * @author liuxiongwei
 * 
 */
public class TwitterOauth {
	private static final Logger log = Logger.getLogger(TwitterOauth.class);

	volatile int i = 0;
	List<Oauth> list = new ArrayList<Oauth>();

	private static final TwitterOauth twitterOauth = new TwitterOauth();

	public static TwitterOauth newInstance() {
		return twitterOauth;
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

	private static Configuration conf = ConfigurationContext.getInstance();
	private static OAuthAuthorization oAuth = new OAuthAuthorization(conf);

	private TwitterOauth() {
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	private void init() throws IOException {
		String fileName = VariableCache.TwitterOauth.twitterOauthFile;
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
			String consumerKey = json.getString("consumerKey");
			String consumerSecret = json.getString("consumerSecret");
			String accessToken = json.getString("accessToken");
			String accessTokenSecret = json.getString("accessTokenSecret");
			list.add(new Oauth(consumerKey, consumerSecret, accessToken,
					accessTokenSecret));
		}
	}

	/**
	 * 生成OAuth授权
	 * 
	 * @param url
	 * @return
	 */
	public void generateSignature(WeiboPage weiboPage) {
		i = (++i) % list.size();
		Oauth oauth = list.get(i);
		oAuth.setOAuthConsumer(oauth.getConsumerKey(),
				oauth.getConsumerSecret());
		oAuth.setOAuthAccessToken(new AccessToken(oauth.getAccessToken(), oauth
				.getAccessTokenSecret()));
		String signature = "";
		String method = weiboPage.getHttpMethod() == null ? "GET" : weiboPage
				.getHttpMethod().toString();
		String queryString = (weiboPage.getQuery() == null) ? "" : weiboPage
				.getQuery().toString();
		String url = (queryString == null || "".equals(queryString)) ? weiboPage
				.getUrl() : weiboPage.getUrl() + "?" + queryString;
		try {
			signature = oAuth.generateAuthorizationHeader(method, url, null,
					oAuth.getOAuthAccessToken());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
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
		log.info("signature=" + signature);
	}

	/**
	 * 生成Post OAuth授权
	 * 
	 * @param url
	 * @return
	 */
	public String generateSignature(String url, String queryStr) {
		i = (++i) % list.size();
		Oauth oauth = list.get(i);
		oAuth.setOAuthConsumer(oauth.getConsumerKey(),
				oauth.getConsumerSecret());
		oAuth.setOAuthAccessToken(new AccessToken(oauth.getAccessToken(), oauth
				.getAccessTokenSecret()));
		String signature = null;
		try {
			signature = oAuth.generateAuthorizationHeader("POST", url + "?"
					+ queryStr, null, oAuth.getOAuthAccessToken());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return signature;
	}

}
