package com.cartmatic.estore.customer.service.impl;

import com.cartmatic.estore.common.model.customer.TextMessage;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.service.TextMessageManager;
import com.cartmatic.estore.customer.dao.TextMessageDao;


/**
 * Manager implementation for TextMessage, responsible for business processing, and communicate between web and persistence layer.
 */
public class TextMessageManagerImpl extends GenericManagerImpl<TextMessage> implements TextMessageManager {

	private TextMessageDao textMessageDao = null;

	/**
	 * @param textMessageDao
	 *            the textMessageDao to set
	 */
	public void setTextMessageDao(TextMessageDao textMessageDao) {
		this.textMessageDao = textMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = textMessageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TextMessage entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TextMessage entity) {

	}

}
