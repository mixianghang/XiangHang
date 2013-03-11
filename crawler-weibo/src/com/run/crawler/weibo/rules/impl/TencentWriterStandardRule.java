package com.run.crawler.weibo.rules.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;

public class TencentWriterStandardRule extends AbstractRule {

	{
		this.setTask(Task.Tencent);
	}

	@Override
	public boolean rule(WeiboPage weiboPage) {
		for (VelocityBean velocityBean : weiboPage.getVelocityBeanList()) {
			standard(velocityBean);
		}
		return true;
	}

	private void standard(VelocityBean velocityBean) {

		String content = velocityBean.getText();
		if (!"".equals(content)) {
			content = content.replaceAll("\\r\\n", "").replaceAll("\\s", "");
			velocityBean.setText(content);
		}

		String time = velocityBean.getTimestamp();
		if (!"".equals(time)) {
			velocityBean.setTimestamp(formatTime(time));
		}

		String url = velocityBean.getFromurl();
		if (!"".equals(url)) {
			velocityBean.setFromurl(url.replaceAll("\\r\\n", ""));
		}
	}

	private String formatTime(String time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(time) * 1000);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(c.getTime());
	}
}
