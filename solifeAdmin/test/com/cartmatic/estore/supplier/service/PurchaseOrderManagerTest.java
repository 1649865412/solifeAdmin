package com.cartmatic.estore.supplier.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.supplier.PurchaseOrder;
import com.cartmatic.estore.common.model.supplier.PurchaseOrderItem;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class PurchaseOrderManagerTest extends BaseTransactionalTestCase{

	@Autowired
	private PurchaseOrderManager mgr = null;
	
	@Test
	public void testCreatePurchaseOrder()
	{
		PurchaseOrder po = mgr.createPurchaseOrder(-1);
		Assert.assertNotNull(po);
		Assert.assertEquals(po.getSupplierName(), "N/A");
		System.out.println(po.getPurchaseOrderName());
		System.out.println(po.getPurchaseOrderItems().size());
		for (PurchaseOrderItem item: po.getPurchaseOrderItems())
		{
			logger.info(item.getOrderSku().getOrderShipment().getShipmentNo());
			System.out.println(item.getOrderSku().getOrderShipment().getShipmentNo());
		}
	}
	
}
