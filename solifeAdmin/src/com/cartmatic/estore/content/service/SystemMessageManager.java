package com.cartmatic.estore.content.service;

import com.cartmatic.estore.common.model.content.SystemMessage;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SystemMessage, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SystemMessageManager extends GenericManager<SystemMessage> {
    
	/**
	 * add system message 
	 * @param message
	 * @param MSG_TYP
	 * @param appUser
	 */
    public void addSystemMessage(String message,Short MSG_TYP,AppUser appUser);
}