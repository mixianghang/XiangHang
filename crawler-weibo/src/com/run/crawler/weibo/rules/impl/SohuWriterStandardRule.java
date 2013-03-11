package com.run.crawler.weibo.rules.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.run.crawler.weibo.entity.VelocityBean;
import com.run.crawler.weibo.entity.WeiboPage;
import com.run.crawler.weibo.rules.AbstractRule;
import com.run.crawler.weibo.util.Task;

/**
 * 负责Sohu落地文件规范化处
 * 
 * @author liuxiongwei
 * 
 */
public class SohuWriterStandardRule extends AbstractRule {

	{
		this.setTask(Task.Sohu);
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
	}

	private String formatTime(String time) {
		String dateStr = "";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		try {
			dateStr = format1.format(format2.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}
}
