package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentFriendQuery extends Query {
	/**
	 * �������ݵĸ�ʽ��json��xml��
	 */
	private Format format = Format.JSON;

	/**
	 * �û��ʻ�������ѡ��
	 */
	private String name = "";

	/**
	 * ��ʼλ�ã���һҳ��0���������·�ҳ���reqnum*��page-1������
	 */
	private int startindex = 0;

	/**
	 * �������(1-30)
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
