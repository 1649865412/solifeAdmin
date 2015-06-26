package com.cartmatic.estore.catalog.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class ProductDaoTest extends BaseTransactionalTestCase
{
	@Autowired
	private ProductManager productManager=null;
	@Autowired
	private ProductMainManager productMainManager=null;
	@Autowired
	private ProductSkuManager	productSkuManager	= null;
	

	
	public void test() throws Exception{
		Product product=productManager.getById(107);
//		Product product2=productMainManager.doCopyProduct(product);
//		System.out.println(product2);
//		ProductSku productSku=productSkuManager.getProductSkuByProductSkuCode("copy 20081112002");
//		System.out.println(productSku);
	}
	
	public void copy() throws Exception{
		Product product=productManager.getById(88);
		System.out.println("A_____"+product);
		Product product2=new Product();
//		BeanUtils.copyProperties(product2,product);
		product2=(Product)BeanUtils.cloneBean(product);
		System.out.println("B_____"+product2);
		System.out.println(product==product2);
		product.setProductType(null);
//		System.out.println(product2.getProductType());
		product2.setProductId(null);
		product2.setProductCode(product2.getProductCode()+"2");
		productManager.save(product2);
	}	
}
