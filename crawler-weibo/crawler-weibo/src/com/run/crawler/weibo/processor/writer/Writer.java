package com.run.crawler.weibo.processor.writer;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.io.VecolityWrite;
import com.run.crawler.weibo.io.VecolityWriteCache;
import com.run.crawler.weibo.processor.AbstractProcessor;

public class Writer extends AbstractProcessor {
	
	private final VecolityWrite vecolityWrite = VecolityWriteCache.newInstance();

	public WeiboPage process(WeiboPage weiboPage) {
		log.info("正在被Writer进行处理中....................................");
		int j = weiboPage.getVelocityBeanList().size();
		log.info("VelocityBeanList().size()=" + j);
		int i = vecolityWrite.write(weiboPage.getVelocityBeanList());
		/**
		 * j - i 表示经过去重处理后出项重复记录的数量
		 */
		weiboPage.getState().setStoreRepeatNum(j - i);
		weiboPage.getState().setStoreNum(i);
		/**
		 * 将此次存储的记录条数设置为weiboPage的优先级
		 * 有问题(如果nextPage为true，则不准)不影响程序
		 */
		weiboPage.setPriority(i);
		/**
		 * 为了与WriterOkRule配合
		 */
		weiboPage.getVelocityBeanList().clear();
		return weiboPage;
	}

}
