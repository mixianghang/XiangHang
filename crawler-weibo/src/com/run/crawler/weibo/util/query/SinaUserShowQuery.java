package com.run.crawler.weibo.util.query;

public class SinaUserShowQuery extends Query {
	/**
	 * 需要查询的用户昵称
	 */
	private String screen_name;

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

}
