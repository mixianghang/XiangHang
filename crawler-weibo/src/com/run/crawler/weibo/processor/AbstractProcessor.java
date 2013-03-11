package com.run.crawler.weibo.processor;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.util.Job;
import com.run.crawler.weibo.util.Priority;
import com.run.crawler.weibo.util.Task;

public class AbstractProcessor implements Processor {

	public Logger log = Logger.getLogger(this.getClass());

	private Priority priority = Priority.Tall;
	private Task task = Task.All;
	private Job job = Job.All;

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public WeiboPage process(WeiboPage weiboPage) {
		return weiboPage;
	}

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
