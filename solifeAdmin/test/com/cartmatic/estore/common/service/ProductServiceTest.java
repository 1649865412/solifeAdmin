package com.cartmatic.estore.common.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductServiceTest extends BaseTransactionalTestCase{
	@Autowired
	private ProductService productService=null;

	@Test
	public void testIsInCategoryBySku() throws Exception {
		boolean flag=productService.isInCategoryBySku(118, 131);
		System.out.println(flag);
	}
	
}
