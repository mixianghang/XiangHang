package com.run.crawler.weibo.util.oauth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.util.TimerUtil;
import com.run.crawler.weibo.util.cache.VariableCache;
import com.run.crawler.weibo.util.oauth.util.DelayedOauth;
import com.run.crawler.weibo.util.oauth.util.DelayedOauth.Type;
import com.run.crawler.weibo.util.oauth.util.Oauth;
import com.run.crawler.weibo.util.oauth.util.SinaOauthRefresh;

public class SinaOauth {

	public Logger log = Logger.getLogger(this.getClass());

	final boolean isLimit = false;

	/**
	 * 正式存储授权队列
	 */
	private BlockingQueue<DelayedOauth> oauthUrlQueue = new LinkedBlockingQueue<DelayedOauth>();
	private BlockingQueue<DelayedOauth> sendOauthUrlQueue = new LinkedBlockingQueue<DelayedOauth>();

	/**
	 * 临时存储授权队列
	 */
	DelayQueue<DelayedOauth> oauthDelayQueue = new DelayQueue<DelayedOauth>();
	DelayQueue<DelayedOauth> sendOauthDelayQueue = new DelayQueue<DelayedOauth>();

	List<Oauth> list = new ArrayList<Oauth>();

	/**
	 * 获取读请求的授权
	 * 
	 * @return
	 */
	public String getOauthUrl() {
		while(oauthUrlQueue.size() ==0){
			try {
				Thread.sleep(10*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s = "";
		try {
			DelayedOauth oauth = oauthUrlQueue.take();
			oauth.reset();
			oauthDelayQueue.add(oauth);
			s = oauth.oauthUrl();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.warn("getOauthUrl url = " + s);
		log.warn("oauthUrlQueue.size = " + oauthUrlQueue.size());
		return s;
	}

	/**
	 * 获取写请求的授权
	 * 
	 * @return
	 */
	public String getSendOauthUrl() {
		while(sendOauthUrlQueue.size() ==0){
			try {
				Thread.sleep(10*1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String s = "";
		DelayedOauth oauth = null;
		try {
			oauth = sendOauthUrlQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		oauth.reset();
		sendOauthDelayQueue.add(oauth);
		s = oauth.oauthUrl();
		log.warn("getSendOauthUrl url = " + s);
		log.warn("sendOauthUrlQueue.size = " + sendOauthUrlQueue.size());
		return s;
	}

	private SinaOauth() {

		updateOauthDelayedQueue();
		updateSendOauthDelayedQueue();

		try {
			init();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 初始化非陪授权的队列
	 */
	private void initBlockingQueue() {
		oauthUrlQueue.clear();
		sendOauthUrlQueue.clear();

		oauthDelayQueue.clear();
		sendOauthDelayQueue.clear();

		int size = list.size();
		if (size == 0) {
			log.error("请配置授权..............");
			return;
		}

		/**
		 * 长度为每小时可以处理的授权个数 读每个授权每小时90次 写请求每个授权每小时60次 读不够可以调用写
		 */
		oauthUrlQueue = new LinkedBlockingQueue<DelayedOauth>();
		sendOauthUrlQueue = new LinkedBlockingQueue<DelayedOauth>();
		
		ArrayList<DelayedOauth> arrayList = new ArrayList<DelayedOauth>();
		
		for(int i = 0;i<size;i++){
			for(int j = 0;j<150;j++){
				arrayList.add(new DelayedOauth(list.get(i)));
			}
		}
		shuffle(arrayList);
		
		oauthUrlQueue.addAll(arrayList.subList(0, arrayList.size()-80));
		for(DelayedOauth oauth : arrayList.subList(arrayList.size()-80,arrayList.size())) {
		  oauth.setType(Type.WRITE);
	    sendOauthUrlQueue.add(oauth);
		}
		
		log.info("oauthUrlQueue.size = " + oauthUrlQueue.size());
		log.info("sendOauthUrlQueue.size = " + sendOauthUrlQueue.size());
	}
	
	private void shuffle(ArrayList<DelayedOauth> arraylist){
		log.info("打乱顺序。。。。。。。。。。。。。。。"+arraylist.size());
		int size = arraylist.size();
		if(size < 150){
			return;
		}
		int temp = size/2;
		Random random = new Random();
		int i = 10000;
		while(i>=0){
			int k = random.nextInt(size);
			int m = (k+temp)&(size-1);
			DelayedOauth delayedOauth = arraylist.get(k);
			arraylist.set(k, arraylist.get(m));
			arraylist.set(m, delayedOauth);
			i--;
		}
	}

	private void init() throws IOException {
		String fileName = VariableCache.SinaOauth.sinaOauthFile;
		log.info("init fileName :" + fileName);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileName))));
		StringBuilder sb = new StringBuilder();
		String s = "";
		while ((s = bufferedReader.readLine()) != null) {
			sb.append(s);
		}

		bufferedReader.close();

		JSONArray jsonArray = JSONArray.fromObject(sb.toString());
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			JSONObject json = iterator.next();
			String client_id = json.getString("client_id");
			String client_secret = json.getString("client_secret");
			String redirect_uri = json.getString("redirect_uri");
			String response_type = json.getString("response_type");
			String code = json.getString("code");
			String access_token = json.getString("access_token");

			if ("".equals(client_id) || "".equals(client_secret)
					|| "".equals(redirect_uri) || "".equals(response_type)) {
				continue;
			}

			list.add(new Oauth(client_id, client_secret, redirect_uri,
					response_type, code, access_token));
		}

		long temp = 11 * TimerUtil._1hour;
		if (VariableCache.SinaOauth.startRefresh) {
			temp = 0;
		} else {
			initBlockingQueue();
		}

		/**
		 * 一天更新一次授权
		 */
		new Timer("SinaOauth").schedule(new TimerTask() {
			@Override
			public void run() {

				Iterator<Oauth> iterator = list.iterator();
				while (iterator.hasNext()) {
					Oauth oauth = iterator.next();
					try {
						SinaOauthRefresh.refresh(oauth);
					} catch (Exception e) {
						iterator.remove();
						log.error("获取acces_token出错................");
						log.error(e.getMessage());
					}
				}

				initBlockingQueue();
			}
		}, temp, 11 * TimerUtil._1hour);
	}

	private void updateOauthDelayedQueue() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						DelayedOauth d = oauthDelayQueue.take();
						oauthUrlQueue.offer(d);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(r).start();
	}

	private void updateSendOauthDelayedQueue() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						DelayedOauth d = sendOauthDelayQueue.take();
						sendOauthUrlQueue.offer(d);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(r).start();
	}

	private static class Proxy {
		private static SinaOauth sinaOauth = new SinaOauth();
	}

	public static SinaOauth newInstance() {
		return Proxy.sinaOauth;
	}
	
	public static void main(String[] args){
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		
		for(int i = 0;i<10000;i++){
			arrayList.add(i);
		}
		
		System.out.println(Arrays.toString(arrayList.toArray(new Integer[0])));
		
		int size = arrayList.size();
		if(size < 10){
			return;
		}
		int temp = 40;
		Random random = new Random();
		int i = 10000;
		while(i>=0){
			int k = random.nextInt(size);
			int m = (k+temp)&(size-1);
			Integer ig = arrayList.get(k);
			arrayList.set(k, arrayList.get(m));
			arrayList.set(m, ig);
			i--;
		}
		System.out.println(Arrays.toString(arrayList.toArray(new Integer[0])));
	}
}
