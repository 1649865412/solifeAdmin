package com.cartmatic.estore.supplier.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;

public class SupplierProductManagerTest  extends BaseTransactionalTestCase{
	@Autowired
	private ProductManager productManager=null;
	@Autowired
	private ProductMainManager productMainManager=null;
	

	@Test
	public void test(){
		productSetSupplier();
	}
	
	public void productSetSupplier(){
		List<Product> productList=productManager.getAll();
		for (Product product : productList) {
			if(product.getProductId()>=5000&&product.getProductId()<6000){
				Supplier supplier=product.getSupplier();
				if(supplier==null){
					productMainManager.saveProductSuppliersAction(product, -1, new Integer[]{-1});
				}else{
					productMainManager.saveProductSuppliersAction(product, supplier.getSupplierId(), new Integer[]{supplier.getSupplierId()});
				}
//				System.out.println(product);
			}
		}		
	}
}
