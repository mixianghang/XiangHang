package com.run.crawler.weibo.util;

import com.run.crawler.weibo.util.cache.VariableCache;

public enum Task {
	Sina(2, VariableCache.Task.sinaRateControl,
			VariableCache.Task.sinaFriendShipProcessInterval), Tencent(3,
			VariableCache.Task.tencentRateControl), Twitter(1,
			VariableCache.Task.twitterRateControl,
			VariableCache.Task.twitterFriendShipProcessInterval), Sohu(4,
			VariableCache.Task.sohuRateControl), Netease(5,
			VariableCache.Task.neteaseRateControl), All(0, 0L);
	int i = 0;
	/**
	 * 访问频率控制,单位ms
	 */
	long rateControl = 1000L;

	long friendShipProcessInterval = 2000L;

	public long getRateControl() {
		return rateControl;
	}

	public int getI() {
		return i;
	}

	Task(int i, long rateControl) {
		this.rateControl = rateControl;
		this.i = i;
	}

	Task(int i, long rateControl, long friendShipCreateInterval) {
		this.rateControl = rateControl;
		this.i = i;
		this.friendShipProcessInterval = friendShipCreateInterval;
	}

	/**
	 * 根据类别获取Task
	 * 
	 * @param categroy
	 * @return
	 */
	public static Task getTask(int categroy) {
		Task task = null;
		switch (categroy) {
		case 2:
			task = Task.Sina;
			break;
		case 3:
			task = Task.Tencent;
			break;
		case 1:
			task = Task.Twitter;
			break;
		case 4:
			task = Task.Sohu;
			break;
		case 5:
			task = Task.Netease;
			break;
		default:
			task = Task.All;
		}
		return task;
	}

	public static long getMinRateControl() {
		long minRateControl = Math.min(Task.Sina.getRateControl(),
				Task.Tencent.getRateControl());
		minRateControl = Math
				.min(minRateControl, Task.Twitter.getRateControl());
		minRateControl = Math.min(minRateControl, Task.Sohu.getRateControl());
		minRateControl = Math
				.min(minRateControl, Task.Netease.getRateControl());
		return minRateControl;
	}

	public long getFriendShipProcessInterval() {
		return friendShipProcessInterval;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
