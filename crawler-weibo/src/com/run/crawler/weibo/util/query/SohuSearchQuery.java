package com.run.crawler.weibo.util.query;

public class SohuSearchQuery extends Query {

	private String q;

	private String page = "1";

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
