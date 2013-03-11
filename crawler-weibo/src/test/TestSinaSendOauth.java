package test;

import com.run.crawler.weibo.util.oauth.SinaOauth;

public class TestSinaSendOauth {

	/**
	 * TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SinaOauth oauth = SinaOauth.newInstance();
		for (int i = 0; i < 320; i++) {
			oauth.getSendOauthUrl();
		}
	}
}
