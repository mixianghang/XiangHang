package com.run.crawler.weibo.util.query;

public class TencentFriendShipsCreateQuery extends Query {
	/**
	 * 需要查询的用户昵称
	 */
	private String name;

	/**
	 * 请求格式
	 */
	private String format = "json";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
