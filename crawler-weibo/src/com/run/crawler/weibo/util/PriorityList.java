package com.run.crawler.weibo.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.run.crawler.weibo.processor.AbstractProcessor;

public class PriorityList<E extends AbstractProcessor> extends LinkedList<E> {

	/**
	 * �̳���LinkedList �ö�����˳������ģ����Բ���LinkedListҪ��
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
