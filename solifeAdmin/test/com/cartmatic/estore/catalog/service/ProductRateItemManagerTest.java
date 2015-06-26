package com.cartmatic.estore.catalog.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductRateItemManagerTest extends BaseTransactionalTestCase
{
	@Autowired
	private ProductRateItemManager productRateItemManager = null;

	@Test
	public void testfindProductRateItems() throws Exception {
		List<ProductRateItem> productRateItems= productRateItemManager.findProductRateItemsByProductType(1);
		for (ProductRateItem productRateItem : productRateItems) {
			System.out.println(productRateItem);
		}
	 }
	    
}
