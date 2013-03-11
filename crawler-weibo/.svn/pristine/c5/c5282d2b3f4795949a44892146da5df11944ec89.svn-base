package com.run.crawler.weibo.rules.impl;

import java.util.ArrayList;
import java.util.List;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.Rule;

public class RuleChain implements Rule {
	
	private final List<Rule> list = new ArrayList<Rule>(4);

	@Override
	public boolean accept(WeiboPage  weiboPage) {
		return true;
	}

	@Override
	public boolean rule(WeiboPage  weiboPage) {
		boolean b = true;
		for(Rule rule : list){
			if(rule.accept(weiboPage))
				b &= rule.rule(weiboPage);
		}
		return b;
	}
	
	public RuleChain add(Rule rule){
		list.add(rule);
		return this;
	}

}
