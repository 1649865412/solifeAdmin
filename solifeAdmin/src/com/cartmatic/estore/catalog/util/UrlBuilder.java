package com.cartmatic.estore.catalog.util;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.content.Content;

public interface UrlBuilder {
	public String getProductCategoryUrl(Category category);
	
	public String getContentCategoryUrl(Category category);
	
	public Category getContentCategoryByUri(String uri);
	
	public Category getProductCategoryByUri(String uri);
	
	/**
	 * 获取产品访问URI 
	 * @param product 产品
	 * @param categoryId 产品所在目录Id，可以为null，非null时明确指定目录，（主要是额为目录）
	 * @return
	 */
	public String getProductUrl(Product product,Integer catalogId,Integer categoryId);
	
	/**
	 * 目前内容不存在额外目录，因此本方法可以前后台调用
	 * @param content
	 * @param navigatorCategorys
	 * @return
	 */
	public String getContentUrl(Content content);
	
	/**
	 * 根据产品URL获取产品Id及目录Id
	 * @param uri
	 * @return
	 */
	public Integer[] getIdsByProductUrl(String uri);
	
	public Integer getIdByContentUri(String uri);
}
