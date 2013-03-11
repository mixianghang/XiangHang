package com.run.crawler.weibo.util;

import java.util.Comparator;

import com.run.crawler.weibo.processor.AbstractProcessor;

public class PriorityComparator<T extends AbstractProcessor> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return o1.getPriority().getI() - o2.getPriority().getI();
	}
	
}
