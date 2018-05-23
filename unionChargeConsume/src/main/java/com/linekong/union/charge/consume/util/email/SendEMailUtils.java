package com.linekong.union.charge.consume.util.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.linekong.union.charge.consume.util.log.LoggerUtil;

public class SendEMailUtils {
	//建立会话
	private Message message;
	private Session s;
	/**
	 * 初始化发送邮件配置信息
	 */
	public SendEMailUtils(){
		Properties props = System.getProperties();
		props.setProperty(EmailConfig.KEY_SMTP, EmailConfig.VALUE_SMTP);
		props.put(EmailConfig.KEY_PROPS, "true");
		s = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.SEND_UNAME, EmailConfig.SEND_PWD);
            }
		});
		s.setDebug(true);
		message = new MimeMessage(s);
	}
	/**
	 * 执行发送邮件
	 * @param String headName	  邮件标题
	 * @param String sendHtml	  邮件正文内容
	 * @param String receiveUser 接收者
	 */
	public void sendEmail(String headName, String sendHtml,
            String receiveUser){
		try {
			// 发件人
	        InternetAddress from = new InternetAddress(EmailConfig.SEND_USER);
			message.setFrom(from);
			// 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(headName);
            String content = sendHtml.toString();
            // 邮件内容,也可以使纯文本"text/plain"
            message.setContent(content, "text/html;charset=GBK");
            message.saveChanges();
            Transport transport = s.getTransport("smtp");
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport.connect(EmailConfig.VALUE_SMTP, EmailConfig.SEND_UNAME, EmailConfig.SEND_PWD);
            // 发送
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            LoggerUtil.info(SendEMailUtils.class, "send email to "+receiveUser+" success");

		} catch (AddressException e) {
			e.printStackTrace();
			LoggerUtil.error(SendEMailUtils.class, "send email to "+receiveUser+" error:"+e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			LoggerUtil.error(SendEMailUtils.class, "send email to "+receiveUser+" error:"+e.getMessage());
		}

	}
	
	public static void main(String[] args) {
		SendEMailUtils s = new SendEMailUtils();
		s.sendEmail("测试邮件发送", "测试邮件发送信息。。。。", "qijiangwei@8864.com");
	}

}
