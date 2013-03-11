package com.run.crawler.weibo.util;

public enum Format {
	JSON, XML;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
