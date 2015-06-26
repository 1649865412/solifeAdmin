/*
 * Created on Aug 9, 2006
 * 
 */

package com.cartmatic.estore.core.util;

import java.io.Serializable;

/**
 * @author Ryan
 * 
 */
public class OnlineUser implements Serializable {
	String	ipAddr;

	String	sessionId;

	Integer	userId;

	String	userName;

	/**
	 * @param sessionId
	 * @param userName
	 * @param ipAddr
	 * @param userId
	 */
	public OnlineUser(String sessionId, String userName, Integer userId,
			String ipAddr) {
		super();
		this.sessionId = sessionId;
		this.userName = userName;
		this.ipAddr = ipAddr;
		this.userId = userId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}
}
