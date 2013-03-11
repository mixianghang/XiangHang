package com.run.crawler.weibo.processor.post;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.processor.AbstractProcessor;
import com.run.crawler.weibo.util.Priority;

public class PostProcessor extends AbstractProcessor {

	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		/**
		 * ���¸�weiboPage�ĳ�����Ϣ
		 */
		if (!weiboPage.isPreProcessOk()) {
			weiboPage.setErrorNum(weiboPage.getErrorNum() + 1);
		} else {
			weiboPage.setErrorNum(0);
		}
		/**
		 * ��Ǹ�weiboPage�Ѿ���������� һ��Ҫ�������
		 */
		weiboPage.setProcessed(true);
		log.info("PostProcessor�Ѿ��������..............................\n\n\n");
		weiboPage.setJumpTo(Priority.First);
		weiboPage.reset();
		return weiboPage;
	}
}
