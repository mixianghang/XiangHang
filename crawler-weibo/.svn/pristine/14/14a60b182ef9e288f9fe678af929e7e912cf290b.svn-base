package com.run.crawler.weibo.net.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

	/**
	 * 邮件发送器  
	 * @author liuxiongwei
	 *
	 */
public class MailSender implements Sender{
	    /**  
	     * 发送以文本格式邮件  
	     * @param mailInfo 待发送的邮件的信息  
	     */   
	   @Override
       public boolean sendTextMail(MailInfo mailInfo) {   
         /**
          *  判断是否需要身份认证   
          */
    	 Authenticator authenticator = null;   
         Properties pro = mailInfo.getProperties();  
         if (mailInfo.isValidate()) {   
	           /**
	            * 如果需要身份认证，则创建一个密码验证器   
	            */
	           authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
         }  
         /**
          * 根据邮件会话属性和密码验证器构造一个发送邮件的session   
          */
         Session sendMailSession = Session.getDefaultInstance(pro,authenticator);   
         try {   
			     /**
			      *  根据session创建一个邮件消息   
			      */
			     Message mailMessage = new MimeMessage(sendMailSession);   
			     /**
			      *  创建邮件发送者地址   
			      */
			     Address from = new InternetAddress(mailInfo.getFromAddress());   
			     /**
			      * 设置邮件消息的发送者   
			      */
			     mailMessage.setFrom(from);   
			     /**
			      *  创建邮件的接收者地址，并设置到邮件消息中   
			      */
			     Address to = new InternetAddress(mailInfo.getToAddress());   
			     mailMessage.setRecipient(Message.RecipientType.TO,to);   
			     /**
			      *  设置邮件消息的主题   
			      */
			     mailMessage.setSubject(mailInfo.getSubject());   
			     /**
			      *  设置邮件消息发送的时间   
			      */
			     mailMessage.setSentDate(new Date()); 
			     
				   /**
				    * 新建一个MimeMultipart对象用来存放多个BodyPart对象,包括正文、附件
				    */
				  Multipart multipart = new MimeMultipart();
				  
			     /** 
			      * 设置邮件消息的主要内容   
			      */
			     BodyPart text = new MimeBodyPart();
			     text.setContent(mailInfo.getContent()== null ? "" : mailInfo.getContent(), "text/html; charset=utf-8");
			     multipart.addBodyPart(text);
			     
			     /**
			      * 添加附件
			      */
			     addAttachments(multipart, mailInfo.getAttachFileNames());
			     
		         /**
		          *  将MiniMultipart对象设置为邮件内容   
		          */
			     mailMessage.setContent(multipart); 
			     
			     /**
			      *  发送邮件   
			      */
			     Transport.send(mailMessage);  
			     return true;   
         } catch (MessagingException ex) {   
             ex.printStackTrace();   
         }   
         return false;   
       }   
         
       /**  
         * 发送HTML格式发送邮件  
         * @param mailInfo 待发送的邮件信息  
         */   
	   @Override
       public  boolean sendHtmlMail(MailInfo mailInfo){   
         /**
          *  判断是否需要身份认证   
          */
         MailAuthenticator authenticator = null;  
         Properties pro = mailInfo.getProperties();  
         /**
          * 如果需要身份认证，则创建一个密码验证器    
          */
         if (mailInfo.isValidate()) {   
           authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());  
         }   
         /**
          *  根据邮件会话属性和密码验证器构造一个发送邮件的session   
          */
         Session sendMailSession = Session.getDefaultInstance(pro,authenticator);   
         try {   
		         /**
		          *  根据session创建一个邮件消息   
		          */
		         Message mailMessage = new MimeMessage(sendMailSession);   
		         /**
		          *  创建邮件发送者地址   
		          */
		         Address from = new InternetAddress(mailInfo.getFromAddress());   
		         /**
		          * 设置邮件消息的发送者   
		          */
		         mailMessage.setFrom(from);   
		         /**
		          *  创建邮件的接收者地址，并设置到邮件消息中   
		          */
		         Address to = new InternetAddress(mailInfo.getToAddress());   
		         /**
		          *  Message.RecipientType.TO属性表示接收者的类型为TO   
		          */
		         mailMessage.setRecipient(Message.RecipientType.TO,to);   
		         /**
		          *  设置邮件消息的主题   
		          */
		         mailMessage.setSubject(mailInfo.getSubject());   
		         /**
		          *  设置邮件消息发送的时间   
		          */
		         mailMessage.setSentDate(new Date());   
		         
				   /**
				    * 新建一个MimeMultipart对象用来存放多个BodyPart对象,包括正文、附件
				    */
				 Multipart multipart = new MimeMultipart();
				  
		         /**
		          *  创建一个包含HTML内容的MimeBodyPart   
		          */
		         BodyPart html = new MimeBodyPart();   
		         /**
		          * 设置HTML内容   
		          */
		         html.setContent(mailInfo.getContent()== null ? "" : mailInfo.getContent(), "text/html; charset=utf-8");   
		         multipart.addBodyPart(html);   
		         
			     /**
			      * 添加附件
			      */
			     addAttachments(multipart, mailInfo.getAttachFileNames());
		         /**
		          *  将MiniMultipart对象设置为邮件内容   
		          */
		         mailMessage.setContent(multipart);   
		         /**
		          *  发送邮件   
		          */
		         Transport.send(mailMessage);   
		         return true;   
         } catch (MessagingException ex) {   
             	 ex.printStackTrace();   
         }   
         return false;   
       }   
       
       /**
        * 添加多个附件
        * @param attachmentsFileNames
        * @return
        * @throws MessagingException
        * @throws IOException
        */
       private void  addAttachments(Multipart multipart, String[] attachmentsFileNames) {

    
    	   if (attachmentsFileNames == null || attachmentsFileNames.length == 0) {
    	       /**
    	        * 没有附件则直接返回
    	        */
    		   return ;
    	   }
           for (String fileName : attachmentsFileNames) {
        	   if (fileName != null && !"".equals(fileName)) {
        		   BodyPart bodyPart = new MimeBodyPart();
                   FileDataSource dataSource = new FileDataSource(fileName);
                   DataHandler handler = new DataHandler(dataSource);
                   try {
                	   bodyPart.setDataHandler(handler);   
					   
					   /**
					    * 加上文件名将作为附件发送,否则将作为信件的文本内容;MimeUtility.encodeText()函数是把中文的文件名转换为base64编码,
					    * 可以用MimeUtility.decodeText()解码;只有编码后你所发送的邮件才能被其他软件正确解析否则乱码!
					    */
                	   
                	   int position = (fileName.lastIndexOf("\\") != -1) ? fileName.lastIndexOf("\\") + 1 : fileName.lastIndexOf("/") + 1;
                	   String attachmentName = fileName.substring(position, fileName.length());
					   bodyPart.setFileName(MimeUtility.encodeText(attachmentName));
					   
					   /**
					    * 将含有附件的BodyPart加入到MimeMultipart对象中
					    */
					   multipart.addBodyPart(bodyPart);
				 } catch (Exception e) {
				 	continue;
				 }
        	   }
           }
       }
   }   

