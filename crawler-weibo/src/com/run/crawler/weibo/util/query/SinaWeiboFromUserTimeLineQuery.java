package com.run.crawler.weibo.util.query;

public class SinaWeiboFromUserTimeLineQuery extends Query {

	/**
	 * 重点人昵称
	 */
	private String screen_name;
	/**
	 * 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
	 */
	private long max_id = 0;
	/**
	 * 单页返回的记录条数，最大不超过100，默认为20。
	 */
	private int count = 50;

	public long getMax_id() {
		return max_id;
	}

	public void setMax_id(long max_id) {
		this.max_id = max_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
}
