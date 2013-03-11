package com.run.crawler.weibo.net.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �ʼ�����������
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
		log.info("���ڷ����ʼ������Ժ�.......");
		boolean success = mailSender.sendTextMail(mailInfo);
		if (success)
			log.info("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
		else
			log.info("�ǳ���Ǹ�����ղŷ����ʼ�ʧ��!");
		return success;
	}

	@Override
	public boolean sendHtmlMail(MailInfo mailInfo) {
		setAdditionalInfo(mailInfo);
		log.info("���ڷ����ʼ������Ժ�.......");
		boolean success = mailSender.sendHtmlMail(mailInfo);
		if (success)
			log.info("��ϲ�㣬�ʼ��Ѿ��ɹ�����!");
		else
			log.info("�ǳ���Ǹ�����ղŷ����ʼ�ʧ��!");
		return success;
	}

	public void setAdditionalInfo(MailInfo mailInfo) {
		mailInfo.setMailServerHost("smtp.126.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("xiongweil@126.com");// ��������
		mailInfo.setPassword("XXXXXXX");// ������������
		mailInfo.setFromAddress("xiongweil@126.com");
		mailInfo.setToAddress("xiongweil5211314@163.com");
		mailInfo.setSubject("��������쳣");
	}

	public static MailSenderProxy getInstance() {
		return Proxy.sender;
	}

	static class Proxy {
		public static final MailSenderProxy sender = new MailSenderProxy();
	}
}
