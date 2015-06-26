package com.cartmatic.estore.order.service.impl;

import com.cartmatic.estore.common.model.order.TransferRecord;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.order.dao.TransferRecordDao;
import com.cartmatic.estore.other.service.TransferRecordManager;


/**
 * Manager implementation for TransferRecord, responsible for business processing, and communicate between web and persistence layer.
 */
public class TransferRecordManagerImpl extends GenericManagerImpl<TransferRecord> implements TransferRecordManager {

	private TransferRecordDao transferRecordDao = null;

	/**
	 * @param transferRecordDao
	 *            the transferRecordDao to set
	 */
	public void setTransferRecordDao(TransferRecordDao transferRecordDao) {
		this.transferRecordDao = transferRecordDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = transferRecordDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(TransferRecord entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(TransferRecord entity) {

	}

}
