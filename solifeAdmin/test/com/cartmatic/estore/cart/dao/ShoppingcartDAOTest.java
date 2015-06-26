package com.cartmatic.estore.cart.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.cart.dao.ShoppingcartDao;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ShoppingcartDAOTest extends BaseTransactionalTestCase {
    @Autowired
	private ShoppingcartDao shoppingcartDao = null;
    
    @Test
	public void testGetActiveShoppingcartById() throws Exception{
		//Integer shoppingcartId=new Integer(1);
		//Shoppingcart cart = shoppingcartDao.getActiveShoppingcartById(shoppingcartId);
		//Assert.assertNotNull(cart);
	}	
}