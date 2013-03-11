package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentFollowerQuery extends Query {
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

	/**
	 * 获取模式，默认为0 
	 * mode=0，旧模式，新粉丝在前，只能拉取1000个
	 * mode=1，新模式，最多可拉取1万粉丝，暂不支持排序
	 */
	private int mode = 0;

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

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
