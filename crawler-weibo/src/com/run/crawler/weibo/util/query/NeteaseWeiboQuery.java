package com.run.crawler.weibo.util.query;

public class NeteaseWeiboQuery extends Query {

	private String since_id;

	public String getSince_id() {
		return since_id;
	}

	public void setSince_id(String since_id) {
		this.since_id = since_id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	private String count;

}
