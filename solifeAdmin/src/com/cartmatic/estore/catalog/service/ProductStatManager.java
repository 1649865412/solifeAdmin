package com.cartmatic.estore.catalog.service;

import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductStat, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductStatManager extends GenericManager<ProductStat> {
	public ProductStat getProductStat(Integer storeId,Integer productId);

	public ProductStat getProductStatForUpdate(Integer storeId,Integer productId);
	
	/**
	 * 为产品增加已售数量
	 * @param productId
	 * @param quantity 增加数量
	 */
	public void doAddProductBuyCount(Integer storeId,Integer productId,Integer quantity);
	
	/**
	 * TODO 目前不考虑减少
	 * 为产品减少已售数量
	 * @param productId
	 * @param quantity 减少数量
	 */
	public void doSubtractProductBuyCount(Integer storeId,Integer productId,Integer quantity);
}