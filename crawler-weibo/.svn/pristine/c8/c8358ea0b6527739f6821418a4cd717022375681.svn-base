package com.run.crawler.weibo.util.filter;

import org.apache.log4j.Logger;

public abstract class Filter {
	
	Logger log = Logger.getLogger(this.getClass());
	
	public Filter pre = null;
	public Filter getPre() {
		return pre;
	}
	public void setPre(Filter pre) {
		this.pre = pre;
	}
	public Filter getNext() {
		return next;
	}
	public void setNext(Filter next) {
		this.next = next;
	}
	public Filter next = null;
	public abstract boolean contains(String key);
	public abstract void add(String key);
	
	@Override
	public String toString(){
		String pk = this.getClass().getPackage().getName();
		String cn = this.getClass().getName();
		return cn.replace(pk, "");
	}
}
