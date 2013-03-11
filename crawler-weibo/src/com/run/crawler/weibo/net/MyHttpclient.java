package com.run.crawler.weibo.net;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.listener.NetListener;
import com.run.crawler.weibo.net.listener.NetListenerChain;
import com.run.crawler.weibo.util.cache.VariableCache;

public class MyHttpclient {

	Logger log = Logger.getLogger(this.getClass());

	private final int CONNECTION_TIMEOUT = VariableCache.MyHttpclient.CONNECTION_TIMEOUT;
	private NetListener netListener = null;
	DefaultHttpClient httpClient = null;

	public MyHttpclient() {
		netListener = NetListenerChain.newInstance();
		httpClient = new DefaultHttpClient();
		httpClient = sslClient(httpClient);
		httpClient.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.getParams().setParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
	}

	Header[] header = null;

	public String requestGet(String url, String queryString) {
		String responseData = null;

		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}
		log.debug("url:" + url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Connection", "close");
		if (header != null && header.length != 0) {
			for (int i = 0; i < header.length; i++) {
				httpGet.addHeader(header[i]);
			}
		}
		try {
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				log.error("statusCode=" + statusCode);
				netListener.work(statusCode + "");
				log.error("HttpGet Method failed: " + response.getStatusLine());
				return null;
			}
			responseData = EntityUtils.toString(response.getEntity());
		} catch (Exception e1) {
			// 发生网络异常
			netListener.work(e1.getMessage());
			log.warn("发生网络异常........");
			log.error(e1.getMessage());
		} finally {
			httpGet.releaseConnection();
		}
		return responseData;
	}

	public String requestPost(String url, String queryString) {
		String responseData = null;
		HttpPost httpPost = new HttpPost(url);
		if (header != null && header.length != 0) {
			for (int i = 0; i < header.length; i++) {
				httpPost.addHeader(header[i]);
			}
		}
		if (queryString != null && !queryString.equals("")) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (String ss : queryString.split("&")) {
				String[] s = ss.split("=");
				nvps.add(new BasicNameValuePair(s[0], s[1]));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
		try {
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				log.error("HttpGet Method failed: " + response.getStatusLine());
				return null;
			}
			responseData = EntityUtils.toString(response.getEntity());
		} catch (IOException e1) {
			log.warn("发生网络异常........");
			// 发生网络异常
			netListener.work(e1.getMessage());
			log.error(e1.getMessage());
		} finally {
			httpPost.releaseConnection();
		}
		return responseData;
	}

	public void addHeader(Header... header) {
		this.header = header;
	}

	/**
	 * 为client添加验证
	 * 
	 * @param client
	 * @return
	 */
	private DefaultHttpClient sslClient(DefaultHttpClient client) {

		/**
		 * 处理ssl
		 */
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("TLS");
			sc.init(null, getTrustingManager(),
					new java.security.SecureRandom());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		SSLSocketFactory socketFactory = new SSLSocketFactory(sc,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", 443, socketFactory);
		client.getConnectionManager().getSchemeRegistry().register(sch);
		return client;
	}

	private TrustManager[] getTrustingManager() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
				// Do nothing
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
				// Do nothing
			}

		} };
		return trustAllCerts;
	}

	public static void main(String[] args) {
		String s = new MyHttpclient().requestGet("http://news.sina.com.cn/c/2013-02-28/064626381852.shtml",
				null);
		System.out.println(s);
	}
}
