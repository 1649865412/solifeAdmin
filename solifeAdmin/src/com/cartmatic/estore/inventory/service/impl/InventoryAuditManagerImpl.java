package com.cartmatic.estore.inventory.service.impl;

import com.cartmatic.estore.common.model.inventory.InventoryAudit;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.inventory.dao.InventoryAuditDao;
import com.cartmatic.estore.inventory.service.InventoryAuditManager;


/**
 * Manager implementation for InventoryAudit, responsible for business processing, and communicate between web and persistence layer.
 */
public class InventoryAuditManagerImpl extends GenericManagerImpl<InventoryAudit> implements InventoryAuditManager {

	private InventoryAuditDao inventoryAuditDao = null;

	/**
	 * @param inventoryAuditDao
	 *            the inventoryAuditDao to set
	 */
	public void setInventoryAuditDao(InventoryAuditDao inventoryAuditDao) {
		this.inventoryAuditDao = inventoryAuditDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = inventoryAuditDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(InventoryAudit entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(InventoryAudit entity) {

	}

}
