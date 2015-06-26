package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Brand, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface BrandManager extends GenericManager<Brand> {
	/**
	 * 获取所有品牌（按sortOrder排序）
	 * @return
	 */
	public List<Brand> findAllBrands();
	
	
	/**
	 * 根据品牌编码获取品牌
	 * @param brandCode
	 * @return
	 */
	public Brand getBrandByCode(String brandCode);
	
	/**
	 * 根据品牌名称查找相应的品牌
	 * @param brandName 
	 * @return
	 */
	public List<Brand> findBrandByName(String brandName);
}
