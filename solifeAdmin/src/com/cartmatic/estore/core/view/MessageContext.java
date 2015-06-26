/*
 * Created on Oct 9, 2007
 * 
 */

package com.cartmatic.estore.core.view;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.util.HtmlUtils;

import com.cartmatic.estore.common.helper.ConfigUtil;

public class MessageContext {

	/**
	 * We target for HTML mail message, so set to true for all mails.
	 */
	boolean			defaultHtmlEscape	= false;

	MessageSource	messageSource		= null;

	/**
	 * 
	 */
	public MessageContext() {
	}

	/**
	 * Retrieve the message for the given code, using the defaultHtmlEscape
	 * setting.
	 * 
	 * @param code
	 *            code of the message
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException
	 *             if not found
	 */
	public String getMessage(String code) throws NoSuchMessageException {
		return getMessage(code, null, defaultHtmlEscape);
	}

	/**
	 * I don't know why,The param with array in velocity is explain a List. So
	 * we use a List to match array param of velocity.
	 * 
	 * @param code
	 *            fmt key
	 * @param args
	 *            fmt args
	 * @return
	 * @throws NoSuchMessageException
	 */
	public String getMessage(String code, List args)
			throws NoSuchMessageException {
		return getMessage(code, args.toArray(), defaultHtmlEscape);
	}

	/**
	 * Retrieve the message for the given code, using the defaultHtmlEscape
	 * setting.
	 * 
	 * @param code
	 *            code of the message
	 * @param args
	 *            arguments for the message, or <code>null</code> if none
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException
	 *             if not found
	 */
	public String getMessage(String code, Object[] args)
			throws NoSuchMessageException {
		return getMessage(code, args, defaultHtmlEscape);
	}

	/**
	 * Retrieve the message for the given code.
	 * 
	 * @param code
	 *            code of the message
	 * @param args
	 *            arguments for the message, or <code>null</code> if none
	 * @param htmlEscape
	 *            HTML escape the message?
	 * @return the message
	 * @throws org.springframework.context.NoSuchMessageException
	 *             if not found
	 */
	public String getMessage(String code, Object[] args, boolean htmlEscape)
			throws NoSuchMessageException {
		String msg = messageSource.getMessage(code, args, ConfigUtil
				.getInstance().getSystemLocale());
		return (htmlEscape ? HtmlUtils.htmlEscape(msg) : msg);
	}

	/**
	 * Retrieve the message for the given code.
	 * 
	 * @param code
	 *            code of the message
	 * @param args
	 *            arguments for the message, or <code>null</code> if none
	 * @param defaultMessage
	 *            String to return if the lookup fails
	 * @param htmlEscape
	 *            HTML escape the message?
	 * @return the message
	 */
	public String getMessage(String code, Object[] args, String defaultMessage,
			boolean htmlEscape) {
		String msg = messageSource.getMessage(code, args, defaultMessage,
				ConfigUtil.getInstance().getSystemLocale());
		return (htmlEscape ? HtmlUtils.htmlEscape(msg) : msg);
	}

	/**
	 * Retrieve the message for the given code, using the defaultHtmlEscape
	 * setting.
	 * 
	 * @param code
	 *            code of the message
	 * @param defaultMessage
	 *            String to return if the lookup fails
	 * @return the message
	 */
	public String getMessage(String code, String defaultMessage) {
		return getMessage(code, null, defaultMessage, defaultHtmlEscape);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}