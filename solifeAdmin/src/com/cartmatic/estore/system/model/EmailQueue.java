package com.cartmatic.estore.system.model;

import java.io.Serializable;

public class EmailQueue implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1295851270291494869L;
	
	protected byte[]			mimeMessage;
	protected String mailTos;

	public byte[] getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(byte[] mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public String getMailTos() {
		return mailTos;
	}

	public void setMailTos(String mailTos) {
		this.mailTos = mailTos;
	}
	
	
}
