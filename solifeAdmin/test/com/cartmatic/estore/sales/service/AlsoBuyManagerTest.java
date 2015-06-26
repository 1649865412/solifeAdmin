package com.cartmatic.estore.sales.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;


public class AlsoBuyManagerTest extends BaseTransactionalTestCase
{
	@Autowired
	ProductManager productManager;
	@Autowired
	AlsoBuyManager alsoBuyManager;
	
	
	@Test
	@Rollback(false)
	public void testSaveAlsoBuyProducts(){
		//this.setDefaultRollback(false);
		List<Integer> list = new ArrayList<Integer>();
		list.add(new Integer("1"));
		list.add(new Integer("2"));
		list.add(new Integer("3"));
		alsoBuyManager.saveAlsoBuyProducts(list);
	}
	
	@Test
	public void testRemoveAlsoBuyProductsByProductId(){
		alsoBuyManager.removeAlsoBuyProductsByProductId(new Integer("1"));
	}
	
	@Test
	public void testGetProductsBySourceIdList(){
		/*List<Integer> productIds = new ArrayList<Integer>();
		productIds.add(new Integer("1"));
		productIds.add(new Integer("2"));
		productIds.add(new Integer("3"));
		List<Product> list = alsoBuyManager.getProductsBySourceIdList(productIds, 0, -1);
		for(Product product : list){
			logger.info("id==="+product.getProductId()+";name==="+product.getProductName());
		}*/
	}
}
