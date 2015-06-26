package com.cartmatic.estore.cart.service;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ShoppingcartItemManagerTest extends BaseTransactionalTestCase
{
	@Autowired
	private ShoppingcartItemManager shoppingcartItemManager;
	@Autowired
	private ProductSkuManager productSkuManager;
	@Test
	public void testAccessory()
	{
		ProductSku productsku = productSkuManager.getById(200);
		ShoppingcartItem item = shoppingcartItemManager.newShoppingcartItem(productsku, 2, "1", (short)0);
		Assert.assertNotNull(item.getAccessories());
		Assert.assertEquals(item.getDiscountPrice(), productsku.getPrice().add(new BigDecimal("2.99")));
		System.out.println(item.getAccessories());
		System.out.println(item.getDiscountPrice()+" "+productsku.getPrice());
		item = shoppingcartItemManager.newShoppingcartItem(productsku, 2, "1,4", (short)0);
		Assert.assertNotNull(item.getAccessories());
		System.out.println(item.getDiscountPrice()+" "+productsku.getPrice());
		System.out.println(item.getAccessories());
	}
	
}
