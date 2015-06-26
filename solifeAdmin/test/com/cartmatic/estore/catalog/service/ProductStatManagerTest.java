package com.cartmatic.estore.catalog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductStatManagerTest  extends BaseTransactionalTestCase{
	@Autowired
	private ProductStatManager productStatManager=null;
	
	@Test
	public void testMethod(){
		doSubtractProductBuyCount();
	}
	
	public void doAddProductBuyCount(){
		productStatManager.doAddProductBuyCount(1,2, 10);
		
	}
	
	public void doSubtractProductBuyCount(){
		productStatManager.doSubtractProductBuyCount(1,2, 5);
		
	}
}