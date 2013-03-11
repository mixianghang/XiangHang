package com.run.crawler.weibo.net.mail;

public class MailClient {

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("设置邮箱内容");
		mailInfo.setAttachFileNames(new String[] { "E:\\resource\\htmlcleaner-2.2.jar" });
		MailSenderProxy.getInstance().sendTextMail(mailInfo);

	}
}
