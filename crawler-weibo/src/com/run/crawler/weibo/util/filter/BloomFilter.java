package com.run.crawler.weibo.util.filter;

import java.io.Serializable;
import java.util.BitSet;

public class BloomFilter extends Filter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private int defaultSize = 2 << 24;// 2的24次方。

	private int basic = defaultSize - 1;

	private BitSet bits;// 内存置位

	int check(int h) {
		return basic & h;
	}

	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	public BloomFilter() {
		bits = new BitSet(defaultSize);
	}

	@Override
	public boolean contains(String id) {
		if (id == null) {
			return true;
		}
		int pos1 = check(hash(id.hashCode() + 2));
		int pos2 = check(hash(id.hashCode() + 4));
		int pos3 = check(hash(id.hashCode() + 6));
		if (bits.get(pos1) && bits.get(pos2) && bits.get(pos3)) {
			return true;
		}
		return false;
	}

	@Override
	public void add(String id) {

		log.debug("BloomFilter add: " + id);

		if (id == null) {
			return;
		}
		int pos1 = check(hash(id.hashCode() + 2));
		int pos2 = check(hash(id.hashCode() + 4));
		int pos3 = check(hash(id.hashCode() + 6));
		bits.set(pos1);
		bits.set(pos2);
		bits.set(pos3);
	}
}
