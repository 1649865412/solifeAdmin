package com.cartmatic.estore.supplier.dao;

import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PurchaseOrder.
 */
public interface PurchaseOrderDao extends GenericDao<PurchaseOrder> {
	public String getMaxOrderNo();
}