package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentWeiboQuery extends Query{
	/**
	 * 返回数据的格式（json或xml）
	 */
	private Format format = Format.JSON;
	/**
	 * 分页标识（0：第一页，1：向下翻页，2：向上翻页）
	 */
	private int pageflag = 0;
	/**
	 * 本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	 */
	private String pagetime = "0";
	/**
	 * 每次请求记录的条数（1-70条）
	 */
	private int reqnum = 50;
	/**
	 * 拉取类型（需填写十进制数字）
	 * 0x1 原创发表 0x2 转载 如需拉取多个类型请使用|，如(0x1|0x2)得到3，则type=3即可，填零表示拉取所有类型
	 */
	private final int type = 0;
	/**
	 * 内容过滤。0-表示所有类型，1-带文本，2-带链接，4-带图片，8-带视频，0x10-带音频 建议不使用contenttype为1的类型，如果要拉取只有文本的微博，建议使用0x80
	 */
	private int contenttype = 0;
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public int getPageflag() {
		return pageflag;
	}
	public void setPageflag(int pageflag) {
		this.pageflag = pageflag;
	}
	public String getPagetime() {
		return pagetime;
	}
	public void setPagetime(String pagetime) {
		this.pagetime = pagetime;
	}
	public int getReqnum() {
		return reqnum;
	}
	public void setReqnum(int reqnum) {
		this.reqnum = reqnum;
	}
	public int getContenttype() {
		return contenttype;
	}
	public void setContenttype(int contenttype) {
		this.contenttype = contenttype;
	}
	public int getType() {
		return type;
	}
}
