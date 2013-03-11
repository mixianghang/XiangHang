package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentUserShowQuery extends Query {
	/**
	 * �������ݵĸ�ʽ��json��xml��
	 */
	private Format format = Format.JSON;

	/**
	 * �û��ʻ�������ѡ��
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