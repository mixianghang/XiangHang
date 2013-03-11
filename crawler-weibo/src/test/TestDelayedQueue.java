package test;

import java.util.concurrent.DelayQueue;

import com.run.crawler.weibo.util.oauth.util.DelayedOauth;

public class TestDelayedQueue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DelayQueue<DelayedOauth> oauthDelayQueue = new DelayQueue<DelayedOauth>();
		DelayedOauth d1 = new DelayedOauth(null);
		d1.setDelayTime(100000);

		DelayedOauth d2 = new DelayedOauth(null);
		d2.setDelayTime(200000);

		DelayedOauth d3 = new DelayedOauth(null);
		d3.setDelayTime(300000);

		oauthDelayQueue.add(d1);
		oauthDelayQueue.add(d2);
		oauthDelayQueue.add(d3);

		System.out.println(oauthDelayQueue.toArray().length);
	}

}
