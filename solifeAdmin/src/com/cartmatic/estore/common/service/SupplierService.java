package com.cartmatic.estore.common.service;

import com.cartmatic.estore.common.model.order.OrderSku;

public interface SupplierService {
	public void createPurchaseOrderItem(OrderSku orderSku);
}
