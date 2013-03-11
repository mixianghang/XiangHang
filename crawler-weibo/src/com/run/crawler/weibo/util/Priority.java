package com.run.crawler.weibo.util;

public enum Priority {

	First(1), Second(2), Third(3), Fourth(4),

	/**
	 * ������ȼ�
	 */
	Tall(Integer.MAX_VALUE),
	/**
	 * ������ȼ�
	 */
	Low(Integer.MIN_VALUE);

	private final int i;

	public int getI() {
		return i;
	}

	private Priority(int i) {
		this.i = i;
	}

	public int toInt() {
		return i;
	}
}
