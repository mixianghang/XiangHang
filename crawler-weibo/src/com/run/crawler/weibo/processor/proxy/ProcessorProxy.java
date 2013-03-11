package com.run.crawler.weibo.processor.proxy;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.processor.AbstractProcessor;
import com.run.crawler.weibo.processor.Processor;
import com.run.crawler.weibo.rules.Rule;
import com.run.crawler.weibo.rules.impl.RuleChain;

public class ProcessorProxy extends AbstractProcessor implements Proxy {

	Processor processor;

	public ProcessorProxy(Processor processor) {
		this.processor = processor;
	}

	private final RuleChain beforeRule = new RuleChain();
	private final RuleChain afterRule = new RuleChain();

	/**
	 * 添加默认before rule
	 */
	{
		// this.addBeforeRules(new DefaultBeforeRule());
	}

	@Override
	public boolean before(WeiboPage weiboPage) {
		return beforeRule.rule(weiboPage);
	}

	@Override
	public boolean after(WeiboPage weiboPage) {
		return afterRule.rule(weiboPage);
	}

	@Override
	public Proxy addBeforeRules(Rule rule) {
		beforeRule.add(rule);
		return this;
	}

	@Override
	public Proxy addAfterRules(Rule rule) {
		afterRule.add(rule);
		return this;
	}

	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		try {
			if (before(weiboPage)) {
				processor.process(weiboPage);
				after(weiboPage);
			}
		} catch (Exception e) {
			/**
			 * 对处理器错误统一处理
			 */
			weiboPage.setErrorNum(weiboPage.getErrorNum() + 1);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return weiboPage;
	}
}
