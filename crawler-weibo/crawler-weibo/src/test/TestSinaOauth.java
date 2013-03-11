package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.PropertyConfigurator;

import com.run.crawler.weibo.util.oauth.util.Oauth;
import com.run.crawler.weibo.util.oauth.util.SinaOauthRefresh;

public class TestSinaOauth {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("conf/log4j.properties");
		
		Oauth oauth = new Oauth("700115051","44d36666703c47899d30101124ba2f1e","http://apps.weibo.com/asfcasdvcasdvvdfv","code","","");
//		list.add(new Oauth("3942434916","1b67fecfc1347493563eec7856d078ec","http://weibo.com/jerry3g","code","",""));
		
		
		try {
			SinaOauthRefresh.refresh(oauth);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(oauth.oauthUrl());
		
	}
}
