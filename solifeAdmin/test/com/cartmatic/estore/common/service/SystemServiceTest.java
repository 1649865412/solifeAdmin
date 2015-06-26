
package com.cartmatic.estore.common.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.system.service.CarrierManager;

public class SystemServiceTest extends BaseTransactionalTestCase{
	@Autowired
	CarrierManager carrierManager;

	@Test
	public void testCarrier() throws Exception {
		carrierManager.deleteById(4);		
		System.out.println("---------------------------------------------");
	}
}
