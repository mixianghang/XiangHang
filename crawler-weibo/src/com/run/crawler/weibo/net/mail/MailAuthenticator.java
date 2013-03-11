package com.run.crawler.weibo.net.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * �ʼ���֤
 * 
 * @author liuxiongwei
 * 
 */
public class MailAuthenticator extends Authenticator {

	/**
	 * ��½�ʼ����ͷ��������û���
	 */
	String userName = null;

	/**
	 * ��½�ʼ����ͷ�����������
	 */
	String password = null;

	public MailAuthenticator() {
	}

	public MailAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
