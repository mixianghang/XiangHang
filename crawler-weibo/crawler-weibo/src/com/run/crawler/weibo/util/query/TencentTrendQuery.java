package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentTrendQuery extends Query{
	/**
	 * 返回数据的格式（json或xml）
	 */
	private Format format = Format.JSON;
	
	/**
	 * 请求个数（最多20）
	 */
	private String reqnum = "20";
	
	/**
	 * 请求位置，第一次请求时填0，继续填上次返回的pos
	 */
	private String pos = "0";

	/**
	 * @return the format
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(Format format) {
		this.format = format;
	}

	/**
	 * @return the reqnum
	 */
	public String getReqnum() {
		return reqnum;
	}

	/**
	 * @param reqnum the reqnum to set
	 */
	public void setReqnum(String reqnum) {
		this.reqnum = reqnum;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}
}
