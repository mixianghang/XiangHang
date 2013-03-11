package com.run.crawler.weibo.util.query;

public class NeteaseTrendQuery extends Query {

	private String type = "sixHours";
	private String size = "50";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
