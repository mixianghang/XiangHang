package com.run.crawler.weibo.processor.post;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.processor.AbstractProcessor;
import com.run.crawler.weibo.util.Priority;

public class PostProcessor extends AbstractProcessor {

	@Override
	public WeiboPage process(WeiboPage weiboPage) {

		/**
		 * 更新该weiboPage的出错信息
		 */
		if (!weiboPage.isPreProcessOk()) {
			weiboPage.setErrorNum(weiboPage.getErrorNum() + 1);
		} else {
			weiboPage.setErrorNum(0);
		}
		/**
		 * 标记该weiboPage已经被处理完成 一定要放在最后
		 */
		weiboPage.setProcessed(true);
		log.info("PostProcessor已经处理完毕..............................\n\n\n");
		weiboPage.setJumpTo(Priority.First);
		weiboPage.reset();
		return weiboPage;
	}
}
