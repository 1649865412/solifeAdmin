package com.cartmatic.estore.supplier.service;

import java.util.List;

import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PurchaseOrderItem, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface PurchaseOrderItemManager extends GenericManager<PurchaseOrderItem> {
	public List<PurchaseOrderItem> getAwaitingPoItemBySupplier(Integer supplierId);
	public void deleteByOrderSkuId(Integer orderSkuId);
}
