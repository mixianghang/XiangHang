package com.run.crawler.weibo.util.query;

public class SinaWeiboFromUserTimeLineQuery extends Query {

	/**
	 * �ص����ǳ�
	 */
	private String screen_name;
	/**
	 * ��ָ���˲������򷵻�IDС�ڻ����max_id��΢����Ĭ��Ϊ0��
	 */
	private long max_id = 0;
	/**
	 * ��ҳ���صļ�¼��������󲻳���100��Ĭ��Ϊ20��
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
