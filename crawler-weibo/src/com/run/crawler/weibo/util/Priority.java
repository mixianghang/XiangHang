package com.run.crawler.weibo.util;

public enum Priority {

	First(1), Second(2), Third(3), Fourth(4),

	/**
	 * 最高优先级
	 */
	Tall(Integer.MAX_VALUE),
	/**
	 * 最低优先级
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
