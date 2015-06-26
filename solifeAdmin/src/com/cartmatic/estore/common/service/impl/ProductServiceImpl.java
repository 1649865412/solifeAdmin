package com.cartmatic.estore.common.service.impl;


import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.service.ProductService;

public class ProductServiceImpl implements ProductService{
	private ProductManager				productManager				= null;
	private ProductSkuManager	productSkuManager	= null;
    private ProductCategoryManager productCategoryManager = null;
    private CategoryManager	categoryManager	= null;
	private ProductStatManager productStatManager=null; 
	


	public void setProductStatManager(ProductStatManager productStatManager) {
		this.productStatManager = productStatManager;
	}


	public Product getProduct(Integer productId) {
		Product product=productManager.getById(productId);
		return product;
	}



	public Product getProductByProductCode(String productCode) {
		return productManager.getProductByProductCode(productCode);
	}

	public ProductSku getProductSku(Integer productSkuId) {
		return productSkuManager.getById(productSkuId);
	}

	public ProductSku getProductSkuByProductSkuCode(String productSkuCode) {
		return productSkuManager.getProductSkuByProductSkuCode(productSkuCode);
	}


	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public boolean isInCategoryBySku(Integer productSkuId, Integer categoryId) {
		if(categoryId.intValue()==1||categoryId.intValue()==2){return true;}
		boolean flag = false;
		ProductSku productSku=productSkuManager.getById(productSkuId);
		Category category=categoryManager.getById(categoryId);
		if(productSku!=null&&category!=null){
			flag=productCategoryManager.isInCategory(productSku.getProductId(), category.getCategoryPath()+category.getCategoryId()+".");
		}
		return flag;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setProductCategoryManager(
			ProductCategoryManager productCategoryManager) {
		this.productCategoryManager = productCategoryManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void doAddProductBuyCount(Integer storeId,Integer productId, Integer quantity) {
		productStatManager.doAddProductBuyCount(storeId,productId, quantity);
	}
 
	public void doSubtractProductBuyCount(Integer storeId,Integer productId, Integer quantity) {
		productStatManager.doSubtractProductBuyCount(storeId,productId, quantity);
	}
}