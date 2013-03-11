package com.run.crawler.weibo.rules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.Attribute;

public class WriterHasNextPageRule extends AbstractRule {

	public Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean rule(WeiboPage weiboPage) {
		log.info("正在WriterHasNextPageRule被处理中................................");
		WeiboPage sonWeiboPage = (WeiboPage) weiboPage.clone();
		sonWeiboPage.setAttribute(Attribute.Son);
		/**
		 * 立即启动
		 */
		sonWeiboPage.setStartTime(0);
		sonWeiboPage.reset();
		weiboPage.setSonWeiboPages(sonWeiboPage);
		return true;
	}
}
