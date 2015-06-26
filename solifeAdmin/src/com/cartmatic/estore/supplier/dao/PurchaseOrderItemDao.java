package com.cartmatic.estore.supplier.dao;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PurchaseOrderItem.
 */
public interface PurchaseOrderItemDao extends GenericDao<PurchaseOrderItem> {

	public List<PurchaseOrderItem> getAwaitingPoItemBySupplier(Integer supplierId);
}