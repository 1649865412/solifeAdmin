package com.cartmatic.estore.content.service.impl;

import com.cartmatic.estore.common.model.content.SystemMessage;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.content.service.SystemMessageManager;
import com.cartmatic.estore.content.dao.SystemMessageDao;


/**
 * Manager implementation for SystemMessage, responsible for business processing, and communicate between web and persistence layer.
 */
public class SystemMessageManagerImpl extends GenericManagerImpl<SystemMessage> implements SystemMessageManager {

	private SystemMessageDao systemMessageDao = null;

	/**
	 * @param systemMessageDao
	 *            the systemMessageDao to set
	 */
	public void setSystemMessageDao(SystemMessageDao systemMessageDao) {
		this.systemMessageDao = systemMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = systemMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SystemMessage entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SystemMessage entity) {

	}

	@Override
	public void addSystemMessage(String message, Short MSG_TYP, AppUser appUser) {
		SystemMessage systemMessage=new SystemMessage();
		systemMessage.setAppUser(appUser);	    
		systemMessage.setMessageHtml(message);
		systemMessage.setStatus(new Short((short)0));
		systemMessage.setMessageType(SystemMessage.MSG_TYPE_ORDER);
		save(systemMessage);
	}

}
