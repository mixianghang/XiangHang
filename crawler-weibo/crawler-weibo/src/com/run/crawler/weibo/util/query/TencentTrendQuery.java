package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentTrendQuery extends Query{
	/**
	 * �������ݵĸ�ʽ��json��xml��
	 */
	private Format format = Format.JSON;
	
	/**
	 * ������������20��
	 */
	private String reqnum = "20";
	
	/**
	 * ����λ�ã���һ������ʱ��0���������ϴη��ص�pos
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
