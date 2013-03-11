package com.run.crawler.weibo.entity;

public class State implements Cloneable{
	
	/**
	 * 实际存储的数量
	 */
	private int storeNum = 0;
	
	/**
	 * 此次存储出现重复记录的数量
	 * 为WriterOkRule服务
	 */
	private int storeRepeatNum = 0;
	
	/**
	 * 新浪粉丝，下一页游标
	 */
	private int next_cursor = 0;
	
	private String cursor_id;
  /**
	 * 用于判断提取Twitter好友、粉丝页数
	 */
	private int pageNo = 0;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 新浪微博最后一条记录的ID,用于翻页
	 */
	private String lastId = "";
	
	/**
	 * 是否还有下一页
	 */
	private boolean hasNextPage = true;
	
	/**
	 * 腾讯微博最后一条记录时间戳
	 */
	private String timestamp = "";

	private boolean isFetcheOk = false;
	
	private boolean isExtractorOk = false;
	
	private boolean isWriterOk = false;
	
	
	public int getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(int storeNum) {
		this.storeNum = storeNum;
	}
	
	@Override
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getStoreRepeatNum() {
		return storeRepeatNum;
	}

	public void setStoreRepeatNum(int storeRepeatNum) {
		this.storeRepeatNum = storeRepeatNum;
	}

	public int getNext_cursor() {
		return next_cursor;
	}

	public void setNext_cursor(int next_cursor) {
		this.next_cursor = next_cursor;
	}

	public String getLastId() {
		return lastId;
	}

	public void setLastId(String lastId) {
		this.lastId = lastId;
	}

	public boolean isWriterOk() {
		return isWriterOk;
	}

	public void setWriterOk(boolean isWriterOk) {
		this.isWriterOk = isWriterOk;
	}

	public boolean isExtractorOk() {
		return isExtractorOk;
	}

	public void setExtractorOk(boolean isExtractorOk) {
		this.isExtractorOk = isExtractorOk;
	}

	public boolean isFetcheOk() {
		return isFetcheOk;
	}

	public void setFetcheOk(boolean isFetcheOk) {
		this.isFetcheOk = isFetcheOk;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

  public String getCursor_id() {
    return cursor_id;
  }

  public void setCursor_id(String cursor_id) {
    this.cursor_id = cursor_id;
  }
	
}
