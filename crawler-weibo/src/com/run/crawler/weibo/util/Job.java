package com.run.crawler.weibo.util;

public enum Job {
	Weibo(1), Friend(2), Follower(3), Search(4), UserShow(5), Trend(6), WeiboFromUserTimeLine(
			7), All(0);

	int i = 0;

	public int getI() {
		return i;
	}

	Job(int i) {
		this.i = i;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
