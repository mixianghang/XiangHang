package com.run.crawler.weibo.net.mail;

public class MailClient {

	public static void main(String[] args) {
		// �������Ҫ�������ʼ�
		MailInfo mailInfo = new MailInfo();
		mailInfo.setSubject("�����������");
		mailInfo.setContent("������������");
		mailInfo.setAttachFileNames(new String[] { "E:\\resource\\htmlcleaner-2.2.jar" });
		MailSenderProxy.getInstance().sendTextMail(mailInfo);

	}
}
