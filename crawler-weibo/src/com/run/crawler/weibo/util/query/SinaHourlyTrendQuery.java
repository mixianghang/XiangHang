package com.run.crawler.weibo.util.query;

public class SinaHourlyTrendQuery extends Query {

	/**
	 * �Ƿ�ֻ��ȡ��ǰӦ�õ����ݡ�0Ϊ���������ݣ���1Ϊ�ǣ�����ǰӦ�ã���Ĭ��Ϊ0��
	 */
	private String base_app = "0";

	public String getBase_app() {
		return base_app;
	}

	public void setBase_app(String base_app) {
		this.base_app = base_app;
	}
}
