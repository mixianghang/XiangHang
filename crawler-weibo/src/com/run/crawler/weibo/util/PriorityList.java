package com.run.crawler.weibo.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.run.crawler.weibo.processor.AbstractProcessor;

public class PriorityList<E extends AbstractProcessor> extends LinkedList<E> {

	/**
	 * 继承自LinkedList 该队列是顺序遍历的，所以采用LinkedList要好
	 */
	private static final long serialVersionUID = 1L;

	private Comparator<E> comparator = null;

	public PriorityList(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean add(E e) {
		if (super.contains(e))
			return false;
		super.add(e);
		Collections.sort(this, comparator);
		return true;
	}
}
