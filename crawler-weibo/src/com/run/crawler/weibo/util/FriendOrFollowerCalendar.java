package com.run.crawler.weibo.util;

import java.util.Calendar;

public class FriendOrFollowerCalendar {

	/**
	 * ��ȡ���������賿2���ʱ��,��λ(��)
	 * 
	 * @return
	 */
	public static int getStartTime() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.set(Calendar.HOUR_OF_DAY, 0);
		tomorrow.set(Calendar.MINUTE, 0);
		tomorrow.set(Calendar.SECOND, 0);
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		tomorrow.add(Calendar.HOUR_OF_DAY, 1);

		Calendar today = Calendar.getInstance();

		return (int) (tomorrow.getTimeInMillis() - today.getTimeInMillis()) / 1000;
	}
}
