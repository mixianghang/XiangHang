package com.run.crawler.weibo.net.listener;

import org.apache.log4j.Logger;



/**
 * 
 * @author jerry
 *�����������Ӳ����õļ�����
 */
public class HttpDisabledListener implements NetListener {
	Logger log = Logger.getLogger(this.getClass());
	final int max = 5;
	int i = 0;
	long preErrorTime = 0L;
	
	public String work(String message) {
		long thisErrorTime = System.currentTimeMillis();
		/**
		 * 10�������������ִ���
		 */
		if((thisErrorTime - preErrorTime)/1000 <10){
			i++;
		}else{
			i = 0;
		}
		preErrorTime = thisErrorTime;
		if(i >= max){
			i = 0;
			pause();
			return "alarm";
		}
		return message;
	}
	
	/**
	 * ʹϵͳ���ߣ���ͣ
	 */
	private void pause(){
		System.err.println("system pause");
		try {
			Thread.sleep(10*6000);	//����10����
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}
}
