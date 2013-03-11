package com.run.crawler.weibo.processor.chain;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.processor.AbstractProcessor;
import com.run.crawler.weibo.util.PriorityComparator;
import com.run.crawler.weibo.util.PriorityList;

public class ProcessorChain extends AbstractProcessor {

	private final PriorityComparator<AbstractProcessor> priorityComparator = new PriorityComparator<AbstractProcessor>();

	private final PriorityList<AbstractProcessor> priorityList = new PriorityList<AbstractProcessor>(
			priorityComparator);

	public WeiboPage process(WeiboPage weiboPage) {
		if (null == weiboPage)
			return null;
		WeiboPage wp = null;
		for (AbstractProcessor abstractProcessor : priorityList) {
			if (!abstractProcessor.accept(weiboPage))
				continue;
			if (abstractProcessor.getPriority().toInt() >= weiboPage
					.getJumpTo().toInt()) {
				wp = abstractProcessor.process(weiboPage);
			}
		}
		return wp;
	}

	public ProcessorChain addProcessor(AbstractProcessor abstractProcessor) {
		priorityList.add(abstractProcessor);
		return this;
	}

	public ProcessorChain removeProcessor(AbstractProcessor abstractProcessor) {
		priorityList.remove(abstractProcessor);
		return this;
	}

}
