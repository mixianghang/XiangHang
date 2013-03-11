package com.run.crawler.weibo.util.oauth.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import weibo4j.model.MySSLSocketFactory;

import com.run.crawler.weibo.util.cache.VariableCache;

public class SinaOauthRefresh {
	static Logger log = Logger.getLogger(SinaOauthRefresh.class);

	final static String loginName = VariableCache.SinaOauth.loginName;

	final static String loginPassword = VariableCache.SinaOauth.loginPassword;

	// https://api.weibo.com/oauth2/authorize?client_id=2464646934&redirect_uri=http://weibo.com/jerry3g&response_type=code
	private static void refresh(HttpClient client, Oauth sinaOauth)
			throws Exception {
		String code = "";
		String url = "https://api.weibo.com/oauth2/authorize";/*
															 * 模拟登录的地址，https://api
															 * .
															 * weibo.com/oauth2/
															 * authorize
															 */
		PostMethod postMethod = new PostMethod(url);

		postMethod.addParameter("client_id", sinaOauth.getClient_id());
		postMethod.addParameter("redirect_uri", sinaOauth.getRedirect_uri());
		postMethod.addParameter("userId", loginName);
		postMethod.addParameter("passwd", loginPassword);
		postMethod.addParameter("state", "1qaz2wsx3edc");
		postMethod.addParameter("isLoginSina", "0");
		postMethod.addParameter("action", "submit");
		postMethod.addParameter("response_type", "code");// code
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("Referer", url + "&from=sina"));// 伪装eferer
		headers.add(new Header("Host", "api.weibo.com"));
		headers.add(new Header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0"));

		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);

		try {
			client.executeMethod(postMethod);
		} catch (HttpException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int status = postMethod.getStatusCode();

		if (status != 302) {
			System.err.println("refresh token failed");
			String responseBody = new String(postMethod.getResponseBody(),
					postMethod.getResponseCharSet());
			log.error(responseBody);
		}
		Header location = postMethod.getResponseHeader("Location");
		String state = "";
		if (location != null) {
			String retUrl = location.getValue();
			log.info("retUrl = " + retUrl);
			Matcher m = Pattern.compile("state=([^&]*?)&code=([^&]*+)")
					.matcher(retUrl);
			if (m.find()) {
				state = m.group(1);
				code = m.group(2);
			}
		}
		log.info("state = " + state);
		log.info("code = " + code);

		if (!"1qaz2wsx3edc".equals(state)) {
			log.error("token 被攻击............");
			throw new Exception("token 被攻击");
		}

		/**
		 * 获取accessToken
		 */
		log.info("开始获取token.............");
		String refreshTokenUrl = "https://api.weibo.com/oauth2/access_token";
		postMethod = new PostMethod(refreshTokenUrl);

		postMethod.addParameter("client_id", sinaOauth.getClient_id());
		postMethod.addParameter("client_secret", sinaOauth.getClient_secret());
		postMethod.addParameter("grant_type", "authorization_code");
		postMethod.addParameter("code", code);
		postMethod.addParameter("redirect_uri", sinaOauth.getRedirect_uri());
		param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		int response = 0;
		String entity = "";
		try {
			response = client.executeMethod(postMethod);
			if (response == 200) {
				entity = new String(postMethod.getResponseBody(),
						postMethod.getResponseCharSet());
			}
			postMethod.releaseConnection();
		} catch (ClientProtocolException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		log.info("entity = " + entity);
		JSONObject jsonObject = JSONObject.fromObject(entity);
		String access_token = jsonObject.getString("access_token");
		log.info("access_token: " + access_token);
		sinaOauth.setAccess_token(access_token);
		log.info("获取accesstoken结束.......");
	}

	public static void refresh(Oauth... sinaOauthList) throws Exception {
		log.info("refresh into....");

		HttpClientParams clientParams = new HttpClientParams();
		// 避免cookie警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		HttpClient client = new HttpClient(clientParams);

		/**
		 * 澶勭悊SSL
		 */
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);

		for (Oauth sinaOauth : sinaOauthList) {
			refresh(client, sinaOauth);
		}
		log.info("regresh end .............");
	}
}
