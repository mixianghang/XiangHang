package com.run.crawler.weibo.util.query;

public class TwitterSearchQuery extends Query {

	private String q;

	private String count = "100";

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
