package com.run.crawler.weibo.util.query;

import com.run.crawler.weibo.util.Format;

public class TencentSearchQuery extends Query{
	/**
	 * 返回数据的格式（json或xml）
	 */
	private Format format = Format.JSON;
	
	/**
	 * 搜索关键字（1-128字节）
	 */
	private String keyword = "";
	
	/**
	 * 每页大小（1-30个）
	 */
	private String pagesize = "30";
	
	/**
	 * 页码
	 */
	private String page = "0";
	
	/**
	 * 消息的正文类型（按位使用）
		0-所有，0x01-纯文本，0x02-包含url，0x04-包含图片，0x08-包含视频，0x10-包含音频 
	 */
	private String contenttype = "0";
	
	/**
	 * 排序方式
		0-表示按默认方式排序(即时间排序(最新)) 
	 */
	private String sorttype = "0";
	
	/**
	 * 消息的类型（按位使用）
		0-所有，1-原创发表，2 转载，8-回复(针对一个消息，进行对话)，0x10-空回(点击客人页，进行对话) 
	 */
	private String msgtype = "0";
	
	/**
	 * 搜索类型
	0-默认搜索类型（现在为模糊搜索） 
	1-模糊搜索：时间参数starttime和endtime间隔小于一小时，时间参数会调整为starttime前endtime后的整点，即调整间隔为1小时 
	8-实时搜索：选择实时搜索，只返回最近几分钟的微博，时间参数需要设置为最近的几分钟范围内才生效，并且不会调整参数间隔
	 */
	private String searchtype = "0";

	/**
	 * 开始时间，用UNIX时间表示（从1970年1月1日0时0分0秒起至现在的总秒数）
	 */
	private String starttime ="0";
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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the pagesize
	 */
	public String getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the contenttype
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * @param contenttype the contenttype to set
	 */
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	/**
	 * @return the sorttype
	 */
	public String getSorttype() {
		return sorttype;
	}

	/**
	 * @param sorttype the sorttype to set
	 */
	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}

	/**
	 * @return the msgtype
	 */
	public String getMsgtype() {
		return msgtype;
	}

	/**
	 * @param msgtype the msgtype to set
	 */
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	/**
	 * @return the searchtype
	 */
	public String getSearchtype() {
		return searchtype;
	}

	/**
	 * @param searchtype the searchtype to set
	 */
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
}
