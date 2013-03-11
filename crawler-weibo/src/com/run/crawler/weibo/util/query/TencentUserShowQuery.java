package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentUserShowQuery extends Query {
	/**
	 * 返回数据的格式（json或xml）
	 */
	private Format format = Format.JSON;

	/**
	 * 用户帐户名（可选）
	 */
	private String name = "";

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
