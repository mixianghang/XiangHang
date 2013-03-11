package com.run.crawler.weibo.net.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 邮件发送器代理
 * 
 * @author liuxiongwei
 * 
 */
public class MailSenderProxy implements Sender {

	private Log log = LogFactory.getLog(this.getClass());
	private MailSender mailSender = new MailSender();

	@Override
	public boolean sendTextMail(MailInfo mailInfo) {
		setAdditionalInfo(mailInfo);
		log.info("正在发送邮件，请稍候.......");
		boolean success = mailSender.sendTextMail(mailInfo);
		if (success)
			log.info("恭喜你，邮件已经成功发送!");
		else
			log.info("非常抱歉，您刚才发送邮件失败!");
		return success;
	}

	@Override
	public boolean sendHtmlMail(MailInfo mailInfo) {
		setAdditionalInfo(mailInfo);
		log.info("正在发送邮件，请稍候.......");
		boolean success = mailSender.sendHtmlMail(mailInfo);
		if (success)
			log.info("恭喜你，邮件已经成功发送!");
		else
			log.info("非常抱歉，您刚才发送邮件失败!");
		return success;
	}

	public void setAdditionalInfo(MailInfo mailInfo) {
		mailInfo.setMailServerHost("smtp.126.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("xiongweil@126.com");// 您的邮箱
		mailInfo.setPassword("XXXXXXX");// 您的邮箱密码
		mailInfo.setFromAddress("xiongweil@126.com");
		mailInfo.setToAddress("xiongweil5211314@163.com");
		mailInfo.setSubject("网络出现异常");
	}

	public static MailSenderProxy getInstance() {
		return Proxy.sender;
	}

	static class Proxy {
		public static final MailSenderProxy sender = new MailSenderProxy();
	}
}
