package com.run.crawler.weibo.util.query;

public class TencentFriendShipsCreateQuery extends Query {
	/**
	 * ��Ҫ��ѯ���û��ǳ�
	 */
	private String name;

	/**
	 * �����ʽ
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
