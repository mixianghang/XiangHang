package com.run.crawler.weibo.net.listener;

import org.apache.log4j.Logger;

import com.run.crawler.weibo.net.mail.MailInfo;

/**
 * 
 * @author jerry
 *用来报警的监听器
 */
public class AlarmListener implements NetListener {
	Logger log = Logger.getLogger(this.getClass());
	
	public String work(String message) {
		/**
		 * alarm
		 */
		if("alarm".equals(message)){
			log.error("error alarm isabled");
			MailInfo mailInfo = new MailInfo();
		    mailInfo.setSubject("设置邮箱标题");   
		    mailInfo.setContent("设置邮箱内容");  
		    mailInfo.setAttachFileNames(new String[]{"附件1", "附件2","附件3",});
			//MailSenderProxy.getInstance().sendTextMail(new MailInfo());//发送邮件
		}
		return null;
	}
}
