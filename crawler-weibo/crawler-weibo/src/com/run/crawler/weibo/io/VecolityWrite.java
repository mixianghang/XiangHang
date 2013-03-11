package com.run.crawler.weibo.io;

import java.util.List;

import com.run.crawler.weibo.entity.VelocityBean;

public interface VecolityWrite {
	public int write(VelocityBean velocityBean);
	
	public int write(List<VelocityBean> list);
}
