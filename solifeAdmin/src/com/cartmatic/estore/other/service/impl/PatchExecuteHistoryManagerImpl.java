package com.cartmatic.estore.other.service.impl;

import com.cartmatic.estore.common.model.other.PatchExecuteHistory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.other.service.PatchExecuteHistoryManager;
import com.cartmatic.estore.other.dao.PatchExecuteHistoryDao;


/**
 * Manager implementation for PatchExecuteHistory, responsible for business processing, and communicate between web and persistence layer.
 */
public class PatchExecuteHistoryManagerImpl extends GenericManagerImpl<PatchExecuteHistory> implements PatchExecuteHistoryManager {

	private PatchExecuteHistoryDao patchExecuteHistoryDao = null;

	/**
	 * @param patchExecuteHistoryDao
	 *            the patchExecuteHistoryDao to set
	 */
	public void setPatchExecuteHistoryDao(PatchExecuteHistoryDao patchExecuteHistoryDao) {
		this.patchExecuteHistoryDao = patchExecuteHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = patchExecuteHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(PatchExecuteHistory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(PatchExecuteHistory entity) {

	}

}
