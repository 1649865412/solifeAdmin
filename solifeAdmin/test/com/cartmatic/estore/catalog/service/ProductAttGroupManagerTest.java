package com.cartmatic.estore.catalog.service;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductAttGroupItem;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductAttGroupManagerTest extends BaseTransactionalTestCase
{
	@Autowired
	private ProductAttGroupManager productAttGroupManager;

	@Test
	public void testfindProductAttGroups() throws Exception {
		List<ProductAttGroup>productAttGroups=productAttGroupManager.findProductAttGroupsByProductType(1);
		for (ProductAttGroup productAttGroup : productAttGroups) {
			System.out.println(productAttGroup);
			Set<ProductAttGroupItem> productAttGroupItems=productAttGroup.getProductAttGroupItems();
			for (ProductAttGroupItem productAttGroupItem : productAttGroupItems) {
				System.out.println(productAttGroupItem.getAttribute());
			}
		}
	}
}
