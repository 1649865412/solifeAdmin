package com.cartmatic.estore.common.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.order.dao.OrderSkuDao;

public class SupplierServiceTest extends BaseTransactionalTestCase
{
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private OrderSkuDao orderSkuDao;
	
	@Test
	@Rollback(false)
	public void testCreatePurchaseOrderItem()
	{
		for (int i = 21; i < 40; i ++)
		{
			OrderSku sku = orderSkuDao.loadById(i);
			supplierService.createPurchaseOrderItem(sku);
		}
	}	
	
}
