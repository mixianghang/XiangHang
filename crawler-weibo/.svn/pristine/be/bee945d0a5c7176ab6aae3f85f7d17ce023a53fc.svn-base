package com.run.crawler.weibo.rules;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Task;

public abstract class AbstractRule implements Rule {

	
	public Logger log = Logger.getLogger(this.getClass());
	
	public Task task = Task.All;
	public Job job = Job.All;
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		return false;
	}

	@Override
	public boolean accept(WeiboPage weiboPage) {
		boolean a = false;
		boolean b = false;
		if (this.task == Task.All || this.task == weiboPage.getTask())
			a = true;
		if (this.job == Job.All || this.job == weiboPage.getJob())
			b = true;
		return a && b;
	}

}
