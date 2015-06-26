
package com.cartmatic.estore.system.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ShippingRateManagerTest extends BaseTransactionalTestCase {

	private static final Log	logger	= LogFactory.getLog(ShippingRateManagerTest.class);

	@Autowired
	private ShippingRateManager shippingRateManager;
	
	@Test
	public void testFindShippingRateByRegionNames(){
/*		System.out.println("【" + shippingRateManager.findShippingRateByRegionNames("中国","广东", "广州").size()+"】");
		System.out.println("【" + shippingRateManager.findShippingRateByRegionNames("中国","广东", "").size()+"】");
		System.out.println("【" + shippingRateManager.findShippingRateByRegionNames("中国","广西", "").size()+"】");
		System.out.println("【" + shippingRateManager.findShippingRateByRegionNames("中国","", "").size()+"】");
		System.out.println("【" + shippingRateManager.findShippingRateByRegionNames("加拿大","", "").size()+"】");*/
		System.out.println("size=【" + shippingRateManager.findShippingRateByRegionNames("中国","北京", "北京").size()+"】");
	}
	
}
