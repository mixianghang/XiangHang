package com.run.crawler.weibo.processor.fetcher;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.net.MyHttpclient;
import com.run.crawler.weibo.processor.AbstractProcessor;
public class Fetcher extends AbstractProcessor {

	public WeiboPage process(WeiboPage weiboPage) {
		
		String content = null;
		String url = weiboPage.getUrl();
		String oauthUrl = weiboPage.getOauthUrl();
		String queryString = (weiboPage.getQuery() == null)? "":weiboPage.getQuery().toString();
		MyHttpclient myHttpclient = new MyHttpclient();
		myHttpclient.addHeader(weiboPage.getHttpHeader());
		if (null != oauthUrl && !"".equals(oauthUrl))
			queryString = oauthUrl + "&" + queryString;
		switch (weiboPage.getHttpMethod()) {
		case GET:
			content = myHttpclient.requestGet(url, queryString);
			break;
		case POST:
			content = myHttpclient.requestPost(url, queryString);
			break;
		default:
			log.info("fetcher ¶ªµôÈÎÎñ....." + weiboPage.getHttpMethod());
			break;
		}
		weiboPage.setContent(content);
		return weiboPage;
	}

}
