package com.cartmatic.estore.supplier.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.supplier.dao.PurchaseOrderItemDao;
import com.cartmatic.estore.supplier.service.PurchaseOrderItemManager;


/**
 * Manager implementation for PurchaseOrderItem, responsible for business processing, and communicate between web and persistence layer.
 */
public class PurchaseOrderItemManagerImpl extends GenericManagerImpl<PurchaseOrderItem> implements PurchaseOrderItemManager {

	private PurchaseOrderItemDao purchaseOrderItemDao = null;

	public List<PurchaseOrderItem> getAwaitingPoItemBySupplier(Integer supplierId)
	{
		return this.purchaseOrderItemDao.getAwaitingPoItemBySupplier(supplierId);
	}
	/**
	 * @param purchaseOrderItemDao
	 *            the purchaseOrderItemDao to set
	 */
	public void setPurchaseOrderItemDao(PurchaseOrderItemDao purchaseOrderItemDao) {
		this.purchaseOrderItemDao = purchaseOrderItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = purchaseOrderItemDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(PurchaseOrderItem entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(PurchaseOrderItem entity) {

	}
	public void deleteByOrderSkuId(Integer orderSkuId) {
		List<PurchaseOrderItem> purchaseOrderItemList=purchaseOrderItemDao.findByProperty("orderSku.orderSkuId", orderSkuId);
		for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
			delete(purchaseOrderItem);
		}
	}

}
