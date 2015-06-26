package com.cartmatic.estore.common.service;


import com.cartmatic.estore.common.model.catalog.Category;

public interface CategoryService {
	
	public Category getCategoryById(Integer categoryId);
	
	/**
	 * 根据编码获取商品分类
	 * @param catalogId
	 * @param categoryCode
	 * @return
	 */
	public Category getProductCategoryByCode(Integer catalogId,String categoryCode);
	
	
	/**
	 * 根据编码获取内容目录
	 * @param storeId
	 * @param categoryCode
	 * @return
	 */
	public Category getContentCategoryByCode(Integer storeId,String categoryCode);
}
