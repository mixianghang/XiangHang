package com.run.crawler.weibo.util.query;

import java.lang.reflect.Field;

public abstract class Query implements Cloneable {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		String s = "&";
		for (int i = 0, len = fields.length; i < len; i++) {
			try {
				String varName = fields[i].getName();
				boolean accessFlag = fields[i].isAccessible();
				fields[i].setAccessible(true);
				Object o = fields[i].get(this);
				if(o != null && !"".equals(o.toString())) {
				    sb.append(varName).append("=").append(o);
					sb.append(s);
				}
				fields[i].setAccessible(accessFlag);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		int index = sb.toString().lastIndexOf(s);
		if (index == -1) {
			return "";
		}
		return sb.toString().substring(0, index);
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
