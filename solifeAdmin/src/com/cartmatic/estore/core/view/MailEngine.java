
package com.cartmatic.estore.core.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.visitors.HtmlPage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.common.util.CurrencyConverter;
import com.cartmatic.estore.common.util.DateConverter;
import com.cartmatic.estore.core.util.VelocityUtil;
import com.cartmatic.estore.system.model.EmailQueue;
import com.cartmatic.estore.system.service.SystemQueueManager;

/**
 * 提供邮件处理的统一处理，基于Velocity和邮件队列。支持Html、附件、多个收件人等。
 * [说明：]虽然实现了队列，但这是应用级别的，为解决远程邮件服务器慢的问题。收件人不存在等错误是无法捕获的，也就是只能知道是否邮件发送到服务器但无法知道服务器是否把邮件发送到收件人。能捕获的错误一般有连接不到SMTP服务器（如没有配置或配置错误或服务器停止服务），发送时Timeout等。
 * 
 * @author Ryan Peng
 */
public class MailEngine {

	protected static final Log	logger = LogFactory.getLog(MailEngine.class);

	//private String				defaultFrom;

	private SystemQueueManager  queueManager;
	
	private JavaMailSenderImpl		mailSender;

	private boolean				queueDisabled		= false;

	private VelocityUtil		velocityUtil;

	public void init()
	{
		ConfigUtil conf = ConfigUtil.getInstance();
		mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding(conf.getMailDefaultEncoding());
		mailSender.setHost(conf.getMailHost());
		if (conf.getIsMailAuth())
		{
			mailSender.setUsername(conf.getMailUsername());
			mailSender.setPassword(conf.getMailPassword());
		}
		mailSender.setProtocol(conf.getMailProtocol());
		mailSender.setPort(conf.getMailPort());			
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", conf.getIsMailAuth());
		javaMailProperties.put("mail.smtp.timeout", conf.getMailTimeout() == null? "30000" : conf.getMailTimeout());
		javaMailProperties.put("mail.debug", "false");
		if (conf.getIsMailTlsEnable())
		{
			javaMailProperties.put("mail.smtp.starttls.enable", true);
			javaMailProperties.put("mail.smtp.socketFactory.port", conf.getMailPort());
			javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
		}
		mailSender.setJavaMailProperties(javaMailProperties);
	}
	/**
	 * 把组装好的邮件（格式是MimeMessage）加入邮件发送队列，为方便管理也记录标题等信息。
	 * 
	 * @param mailSubject
	 * @param mailFrom
	 * @param mailTos
	 * @param mimeMessage
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void addEmailToQueue(String mailSubject, String mailFrom,
			String[] mailTos, MimeMessage mimeMessage) {
		//mailTos字段1024字节限制，如果mailTos大于1024字符长度则进行拆分存储
		if(mailTos==null || mailTos.length==0)
			return;
		ByteArrayOutputStream baos = null;
		try {
			// 数据库储存用blob所以要先转为byte[]
			baos = new ByteArrayOutputStream();
			mimeMessage.writeTo(baos);
			
		} catch (Throwable e) {
			throw new RuntimeException("Unknow add email to queue error.", e);
		}
		
		int loopSize = mailTos.length;
		int fieldSize = 0;
		StringBuilder subMailTos = new StringBuilder();
		for(int i=0; i<loopSize; i++){
			subMailTos.append(mailTos[i]).append(',');
			fieldSize = subMailTos.length();
			if(i<loopSize-2)
				fieldSize += mailTos[i+1].length();
			//1024字节限制
			if(fieldSize>1024 || i==loopSize-1){
				subMailTos.deleteCharAt(subMailTos.length()-1);
				EmailQueue email = new EmailQueue();
				email.setMimeMessage(baos.toByteArray());
				email.setMailTos(subMailTos.toString());
				
				SystemQueue queue = new SystemQueue();
				queue.setTitle(mailSubject);
				queue.setQueueType(SystemQueue.TYPE_EMAIL);
				queue.setTargetEntiy(email);
				queue.setNextRetryTime(new Date());
				queueManager.save(queue);
				fieldSize=0;
				subMailTos = new StringBuilder();
			}
		}
		
	}

	/**
	 * Send a HTML email, the final HTML content is ready. Usually you should
	 * not call this method directly. You may want to use velocity to generate
	 * the mail contents first.
	 * 
	 * @param from
	 * @param tos
	 * @param cc
	 * @param replyTo
	 * @param subject
	 * @param htmlMsgContent
	 * @param attachedFileName
	 * @param attachedFile
	 * @param inline
	 */
	private void composeAndSendEmail(final String from, final String[] tos,
			final String cc, final String replyTo, final String subject,
			final String htmlMsgContent, final String attachedFileName,
			final File attachedFile, final boolean inline) {
		String finalFrom = (from == null || from.trim().length() == 0) ? ConfigUtil.getInstance().getDefaultSystemEmail()
				: from;

		MimeMessage message = composeEmail(finalFrom, tos, cc, replyTo,
				subject, htmlMsgContent, attachedFileName, attachedFile, inline);
		//开发模式是直接打印邮件
		if(ConfigUtil.getInstance().getIsDevMode())
		{
			System.out.println(subject);
			System.out.println(htmlMsgContent);
		}
		// 根据配置控制是否使用队列，这样对使用本地服务器的情况会更快
		if (queueDisabled) {
			try {
				mailSender.send(message);
			} catch (Throwable e) {
				logger
						.error(
								"Error sending single email, add to mail queue to rery.",
								e);
				addEmailToQueue(subject, finalFrom, tos, message);
			}
		} else {
			addEmailToQueue(subject, finalFrom, tos, message);
		}
	}

	private MimeMessage composeEmail(final String from, final String[] tos,
			final String cc, final String replyTo, final String subject,
			final String htmlMsgContent, final String attachedFileName,
			final File attachedFile, final boolean inline) {
		Assert
				.isTrue(
						tos != null && tos.length > 0 && tos[0] != null
								&& tos[0].trim().length() > 0,
						"Recipient found empty while sending a email, no further processing. Mail subject is:"
								+ subject);

		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message,
					attachedFile != null);

			helper.setFrom(from);
			helper.setTo(tos);
			if (cc != null && !"".equals(cc)) {
				helper.setCc(cc);
			}
			if (replyTo != null && !"".equals(replyTo)) {
				helper.setReplyTo(replyTo);
			}

			helper.setSubject(subject);
			// use the true flag to indicate the text included is HTML
			helper.setText(htmlMsgContent, true);

			// add attachments
			if (attachedFile != null) {
				if (inline) {
					helper.addInline(attachedFileName, attachedFile);
				} else {
					helper.addAttachment(attachedFileName, attachedFile);
				}
			}
		} catch (Throwable e) {
			throw new RuntimeException("Error occured when composing email.", e);
		}

		return message;
	}

	/**
	 * 要注意如果用多个job来运行，要处理好并发问题，fetchMailListToProcess应加上单独的事务控制。
	 * <P>
	 * 这里没有用一个mail session发多个邮件来提高性能，因为这样无法得到每个邮件是否发送成功。
	 */
	public void sendQueuedEmails(byte[] messages) throws Throwable{
		MimeMessage message = mailSender.createMimeMessage(new ByteArrayInputStream(messages));
		//对收件人分拆为一封一封发送
		Address mailTos[] = message.getRecipients(Message.RecipientType.TO);
		for(Address to: mailTos){
			message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{(InternetAddress)to});
			mailSender.send(message);
		}
	}

	/**
	 * Send very simple HTML mail, no attachment needed, no template needed.
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param htmlMsgContent
	 */
	/*public void sendSimpleHtmlMail(final String from, final String[] to,
			final String subject, final String htmlMsgContent) {
		composeAndSendEmail(from, to, null, null, subject, htmlMsgContent,
				null, null, false);
	}*/

	/**
	 * Send simple template based HTML email to multiple recepients, no
	 * attachment needed.
	 * 
	 * @param templateName
	 * @param model
	 * @param subject
	 *            邮件标题，可空；空的时候会从邮件内容分析出HTML的TITLE字段作为标题
	 * @param from
	 *            发件人，可空；空的时候使用系统缺省发件人
	 * @param tos
	 *            收件人email，可以是一个（字符串）或多个（字符串数组）
	 */
	public void sendSimpleTemplateMail(final String templateName,
			final Map<String, Object> model, 
			final String subject,
			final String from, 
			final String... tos) 
	{
		sendTemplateMail(templateName, model, subject, from, tos, null, null,
				null, null, false, null);
	}
	
	/**
	 * 可以指定回复的邮件地址.
	 * @param templateName
	 * @param model
	 * @param subject
	 * @param from
	 * @param replyTo 回复地址
	 * @param tos
	 */
	public void sendSimpleTemplateMail(final String templateName,
			final String replyTo, 
			final Map<String, Object> model, 
			final String subject,
			final String from, 			
			final String... tos) 
	{
		sendTemplateMail(templateName, model, subject, from, tos, null, replyTo,
				null, null, false, null);
	}
	
	public void sendSimpleTemplateMailByStoreCode(final String templateName,
			final String storeCode,
			final Map<String, Object> model, 
			final String subject,
			final String from, 
			final String... tos) 
	{
		sendTemplateMail(templateName, model, subject, from, tos, null, null,
				null, null, false, storeCode);
	}

	/**
	 * 基于模版邮件的简化版本，以邮件的HTML的TITLE作为标题，使用系统缺省的发件人。推荐尽量使用本方法。
	 * 
	 * @param templateName
	 * @param model
	 * @param tos
	 *            收件人email，可以是一个（字符串）或多个（字符串数组）
	 */
	//@Deprecated
	//public void sendSimpleTemplateMail2(final String templateName,
	//		final Map<String, Object> model, final String... tos) {
	//	sendSimpleTemplateMail(templateName, model, null, null, tos);
	//}

	/**
	 * 可用系统参数控制是否启用的简化的发送基于模版的邮件的方法。基于模版邮件的简化版本，以邮件的HTML的TITLE作为标题，使用系统缺省的发件人。推荐尽量使用本方法。
	 * 
	 * @param emailCode
	 *            邮件的编码，例如placeOrder,registration；主要用来决定使用的模版名(emailCode+.vm)，以及其控制参数（Is+EmailCode+EmailEnabled）
	 * @param model
	 *            传给Velocity的数据
	 * @param tos
	 *            一到多个收件人
	 */
	/**public void sendSimpleTemplateMail3(final String emailCode,
			final Map<String, Object> model, final String... tos) {
		// 测试用,方便直接找出相应模板
		model.put("templeteName", emailCode);
		if (ConfigUtil.getInstance().getIsEmailEnabled(emailCode)) {
			sendTemplateMail(emailCode + ".vm", model, null, null, tos, null,
					null, null, null, false);
		}
	}*/

	/**
	 * Send email and use template (velocity) to compose the content.
	 * 
	 * @param templateName
	 * @param model
	 * @param subject
	 * @param from
	 * @param tos
	 * @param cc
	 * @param replyTo
	 * @param attachedFileName
	 * @param attachedFile
	 * @param inline
	 */
	private void sendTemplateMail(final String templateName,
			final Map<String, Object> model, 
			final String subject,
			final String from, 
			final String[] tos, 
			final String cc,
			final String replyTo, 
			final String attachedFileName,
			final File attachedFile, 
			final boolean inline,
			final String storeCode) 
	{
		String mailContent = null;
		String mailSubject = subject;
		Map<String, Object> model2 = new HashMap<String, Object>();
		model2.putAll(model);
		String code = storeCode;
		Store store = null;
		if (storeCode == null)
		{
			store = ConfigUtil.getInstance().getStore();
			code = store.getCode();
		}
		else
			store = ConfigUtil.getInstance().getStore(storeCode);
		model2.put("store", store);
		model2.put("dateConverter", new DateConverter());
		model2.put("currencyConverter", new CurrencyConverter());
		String siteUrl = store.getSiteUrl();
		model2.put(Constants.CONTEXT_PATH, siteUrl);
		model2.put(Constants.CONTEXT_RES_PATH, siteUrl+"/resources");
		model2.put("mediaPath", store.getMediaUrlPath());
		model2.put(Constants.CONFIG,ConfigUtil.getInstance());
		

		try {
			mailContent = velocityUtil.mergeTemplateIntoString(templateName, model2, code);
			if (subject == null) {// parse from mail content title tag
				Parser myParser = Parser.createParser(mailContent, Constants.DEFAULT_ENCODING);
				HtmlPage visitor = new HtmlPage(myParser);
				myParser.visitAllNodesWith(visitor);
				mailSubject = visitor.getTitle();
			}
		} catch (Throwable e) {
			throw new RuntimeException(
					"Email template processing error, Check log for detail infomation. Template path: "
							+ templateName, e);
		}
		model2.clear();
		model2 = null;

		composeAndSendEmail(from, tos, cc, replyTo, mailSubject, mailContent,
				attachedFileName, attachedFile, inline);
	}

//	public void setDefaultFrom(String defaultFrom) {
//		this.defaultFrom = defaultFrom;
//	}

	

	/**
	 * 设置是否禁用邮件发送队列，如果Smtp服务器在本地，可禁用；对远程或有大量的邮件建议启用
	 * 
	 * @param queueDisabled
	 */
	public void setQueueDisabled(boolean queueDisabled) {
		this.queueDisabled = queueDisabled;
	}

	/**
	 * @param velocityUtil
	 *            the velocityUtil to set
	 */
	public void setVelocityUtil(VelocityUtil velocityUtil) {
		this.velocityUtil = velocityUtil;
	}
	
	public void setSystemQueueManager(SystemQueueManager avalue)
	{
		this.queueManager = avalue;
	}

}
