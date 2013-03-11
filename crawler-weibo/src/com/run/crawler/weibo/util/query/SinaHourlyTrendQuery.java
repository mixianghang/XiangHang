package com.run.crawler.weibo.util.query;

public class SinaHourlyTrendQuery extends Query {

	/**
	 * 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
	 */
	private String base_app = "0";

	public String getBase_app() {
		return base_app;
	}

	public void setBase_app(String base_app) {
		this.base_app = base_app;
	}
}
