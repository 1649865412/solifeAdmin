/*
 * Created on Sep 12, 2006
 * 
 */

package com.cartmatic.estore.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.system.service.SystemQueueManager;
import com.cartmatic.estoresa.catalog.web.action.ProductController;

/**
 * @author Ryan
 * 
 */
public class MailEngineTestCase extends BaseTransactionalTestCase {

	@Autowired
	protected MailEngine		mailEngine	= null;
	@Autowired
	private SystemQueueManager	mailQueueManager;
	@Autowired
	private SalesOrderManager salesOrderManager=null;

		
	/**
	 * 下单
	 */

//	@Test
//	@Rollback(false)
	public void salesOrderNotificationEmail(){
		SalesOrder salesOrder=salesOrderManager.getSalesOrderByOrderNo("DH121350002");
		salesOrderManager.sendNotificationEmail(OrderConstants.MAIL_TYPE_PLACE_ORDER, salesOrder);		
	}
	
	/**
	 * 注册成功
	 */
	@Test
	@Rollback(false)
	public void registerSuccess(){
		mailEngine.sendSimpleTemplateMail("activateUser.vm", new HashMap<String, Object>(), null, null, "kedou@linkdigi.com");
	}
	
}
