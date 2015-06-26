package com.cartmatic.estore.catalog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductReviewTest extends BaseTransactionalTestCase{
	@Autowired
	private ProductReviewManager productReviewManager=null;

	@Test
	public void test(){
		/*int c=productReviewManager.getCountActiceProductReviews(6186);
		productReviewManager.getSumActiceProductReviewRates(6186);*/
	}
	
}
