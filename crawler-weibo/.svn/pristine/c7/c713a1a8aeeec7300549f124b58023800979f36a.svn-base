package com.run.crawler.weibo.util.filter;


public class FilterChain extends Filter {
	
	private Filter root = null;
	
	private Filter first = null;
	
	private Filter last = null;
	
	@Override
	public boolean contains(String key) {
		Filter findFilter = contains(first,key);
		if(findFilter == null){
			add(last,key);
			return false;
		}else{
			add(findFilter.pre,key);
			return true;	
		}
	}
	
	private Filter contains(Filter filter,String key){
		if(filter == null)
			return null;
		if(filter.contains(key))
			return filter;
		return contains(filter.next,key);
	}

	@Override
	@Deprecated
	public void add(String key) {
		add(last,key);
	}
	
	private void add(Filter filter,String key){
		if(filter == null)
			return;
		filter.add(key);
		add(filter.pre,key);
	}
	
	public FilterChain(){
		root = new BloomFilter();
		
		this.first = root;
		this.last = root;
		
		this.addFilter(new RedisFilter());
	}
	
	public void addFilter(Filter filter){
		this.last.next = filter;
		filter.pre = last;
		this.last = filter;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb = filters(first,sb);
		return sb.toString();
	}
	
	private StringBuilder filters(Filter filter,StringBuilder sb){
		if(filter == null){
			sb.append("END");
			return sb;
		}
		sb.append(filter).append("--->");
		return filters(filter.next,sb);
	}
}
