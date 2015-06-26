package com.cartmatic.estore.supplier.service;

import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PurchaseOrder, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface PurchaseOrderManager extends GenericManager<PurchaseOrder> {
	public PurchaseOrder createPurchaseOrder(Integer supplierId);
}
