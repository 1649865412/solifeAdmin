package com.cartmatic.estore.common.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.system.Wrap;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class CheckoutServiceTest extends BaseTransactionalTestCase{
	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private ProductSkuManager productSkuManager;

	
	
	/*public void testCaculateTaxes(){
		Shoppingcart shoppingcart=new Shoppingcart();
		Integer countryId=78254,stated=78335,cityId=null;
		ShoppingcartItem shoppingcartItem1=new ShoppingcartItem();
		ShoppingcartItem shoppingcartItem2=new ShoppingcartItem();
		
		shoppingcartItem1.setProductSku(productSkuManager.getById(1));
		shoppingcartItem1.setDiscountPrice(BigDecimal.valueOf(1000));
		shoppingcartItem1.setQuantity(5);
		
		shoppingcartItem2.setProductSku(productSkuManager.getById(4));
		shoppingcartItem2.setDiscountPrice(BigDecimal.valueOf(2000));
		shoppingcartItem2.setQuantity(3);
		
		shoppingcart.addShoppingCartItem(shoppingcartItem1);
		shoppingcart.addShoppingCartItem(shoppingcartItem2);
		try {
			checkoutService.caculateTaxes(shoppingcart, countryId, stated, cityId);
			System.out.println("shoppingcartItem1=【" + shoppingcartItem1.getTaxName()+","+shoppingcartItem1.getTax()+"】");
			System.out.println("shoppingcartItem2=【" + shoppingcartItem2.getTaxName()+","+shoppingcartItem2.getTax()+"】");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	@Test
	public void testGetShippingExpence() throws Exception {
		BigDecimal weight1=new BigDecimal(4);
		BigDecimal weight2=new BigDecimal(5);
		System.out.println("a1=【" + checkoutService.getShippingExpence(1, weight1, 1)+"】");
		System.out.println("a2=【" + checkoutService.getShippingExpence(2, weight2, 1)+"】");
		List<Wrap> wrapList= checkoutService.getSystemWraps();
		System.out.println("wrapList.size()=【" + wrapList.size()+"】");
	}
	
}
