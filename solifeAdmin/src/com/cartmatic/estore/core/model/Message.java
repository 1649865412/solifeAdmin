package com.cartmatic.estore.core.model;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.core.util.I18nUtil;


public class Message {
	public static final MessageStatus DEFAULT_STATUS = MessageStatus.INFO;
	
	private MessageStatus status;

	private String information;
	
	public static Message error(String key, Object...objects) {
		return new Message(MessageStatus.ERROR, key, objects);
	}

	public static Message info(String key, Object...objects) {
		return new Message(MessageStatus.INFO, key, objects);
	}
	
	/**
	 * 使用 info(String key, Object...objects)代替
	 * @param msg
	 * @return
	 */
	@Deprecated
	public static  Message infoMsg(String msg){
		Message message=new Message();
		message.setInformationMsg(msg);
		message.setStatus(MessageStatus.INFO);
		return message;
	}
	
	/**
	 * 使用 error(String key, Object...objects)代替
	 * @param msg
	 * @return
	 */
	@Deprecated
	public static  Message errorMsg(String msg){
		//临时增加非Key 提示
		Message message=new Message();
		message.setInformationMsg(msg);
		message.setStatus(MessageStatus.ERROR);
		return message;
	}

	private Message() {
	}

	private Message(String key) {
		this(DEFAULT_STATUS, key, new Object[0]);
	}

	private Message(MessageStatus status, String key, Object ...objects) {
		if (status == null)
			this.status = DEFAULT_STATUS;
		else {
			this.status = status;
		}
		setInformation(key, objects);
	}

	public void setInformation(String key, Object... objects) {
		if (key == null) {
			this.information = "NULL";
		} else {
			this.information = getMessageTextByKey(key, objects);
		}
	}
	
	public void setInformationMsg(String msg){
		this.information =msg;
	}
	
	public static String getMessageTextByKey(String key, Object[] params) {
		return I18nUtil.getInstance().getMessage(key, params);
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public static void main(String[] args) {
		Message.info("ssss");
		Message.info("ssss","aaaa");
		Message.info("ssss",new Object[]{"aaaa","bbbb"});
		Message.info("ssss","cccc","ddd","eeee");
	}
	
}
