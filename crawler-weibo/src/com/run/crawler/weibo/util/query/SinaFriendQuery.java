package com.run.crawler.weibo.util.query;

public class SinaFriendQuery extends Query {
	/**
	 * 需要查询的用户昵称
	 */
	private String screen_name;

	/**
	 * 单页返回的记录条数，默认为50，最大不超过200
	 */
	private int count = 50;

	/**
	 * 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 */
	private int cursor = 0;
	/**
	 * 返回值中user字段中的status字段开关， 0：返回完整status字段、 1：status字段仅返回status_id， 默认为1。
	 */
	private int trim_status = 1;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public int getTrim_status() {
		return trim_status;
	}

	public void setTrim_status(int trim_status) {
		this.trim_status = trim_status;
	}
}
