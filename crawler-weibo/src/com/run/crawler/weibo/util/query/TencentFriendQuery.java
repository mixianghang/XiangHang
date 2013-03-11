package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentFriendQuery extends Query {
	/**
	 * 返回数据的格式（json或xml）
	 */
	private Format format = Format.JSON;

	/**
	 * 用户帐户名（可选）
	 */
	private String name = "";

	/**
	 * 起始位置（第一页填0，继续向下翻页：填【reqnum*（page-1）】）
	 */
	private int startindex = 0;

	/**
	 * 请求个数(1-30)
	 */
	private int reqnum = 30;

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

	public int getStartindex() {
		return startindex;
	}

	public void setStartindex(int startindex) {
		this.startindex = startindex;
	}

	public int getReqnum() {
		return reqnum;
	}

	public void setReqnum(int reqnum) {
		this.reqnum = reqnum;
	}
}
