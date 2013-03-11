package com.run.crawler.weibo.util.query;

public class SinaFollowerQuery extends Query {
	/**
	 * ��Ҫ��ѯ���û��ǳ�
	 */
	private String screen_name;
	/**
	 * ��ҳ���صļ�¼������Ĭ��Ϊ50����󲻳���200 ����Ҫ̫����Ϊһ��ʱ�����µķ�˿����ܶ� ���ؽ����totalnumΪ50�����ֻ��ȡ50��
	 */
	private int count = 20;
	/**
	 * ���ؽ�����α꣬��һҳ�÷���ֵ���next_cursor����һҳ��previous_cursor��Ĭ��Ϊ0
	 */
	private int cursor = 0;
	/**
	 * ����ֵ��user�ֶ��е�status�ֶο��أ� 0����������status�ֶΡ� 1��status�ֶν�����status_id�� Ĭ��Ϊ1��
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
