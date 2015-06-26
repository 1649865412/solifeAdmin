package com.cartmatic.estore.common.service;


import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;

public interface ProductService {
	
	public Product getProduct(Integer productId);
	
	/**
	 * 根据ProductCode获取相应产品
	 * @param productCode
	 * @return
	 */
	public Product getProductByProductCode(String productCode);
	
	public ProductSku getProductSku(Integer productSkuId);
	
	public ProductSku getProductSkuByProductSkuCode(String productSkuCode);

	
	
	/**
	 * 判断sku是否在该目录下
	 * @param productSkuId
	 * @param categoryId
	 * @return
	 */
	public boolean isInCategoryBySku(Integer productSkuId,Integer categoryId);
	

	
	/**
	 * 为产品增加已售数量
	 * （订单完成时调用）
	 * @param productId
	 * @param quantity 增加数量(成功销售的数量)
	 */
	public void doAddProductBuyCount(Integer storeId,Integer productId,Integer quantity);
	
	/**
	 * 为产品减少已售数量
	 * @param productId
	 * @param quantity 减少数量
	 */
	public void doSubtractProductBuyCount(Integer storeId,Integer productId,Integer quantity);
	
}
