package com.run.crawler.weibo.net.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件认证
 * 
 * @author liuxiongwei
 * 
 */
public class MailAuthenticator extends Authenticator {

	/**
	 * 登陆邮件发送服务器的用户名
	 */
	String userName = null;

	/**
	 * 登陆邮件发送服务器的密码
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
