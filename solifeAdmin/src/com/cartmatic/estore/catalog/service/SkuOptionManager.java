package com.cartmatic.estore.catalog.service;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SkuOption, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SkuOptionManager extends GenericManager<SkuOption> {
	/**
	 * 获取产品类型的所有SkuOption选项
	 * 按ProductTypeSkuOption的sortOrder排序
	 * @return
	 */
	public List<SkuOption> findSkuOptionsByProductType(Integer productTypeId);
	
	/**
	 * 获取产品类型的所有激活的SkuOption选项
	 * 按ProductTypeSkuOption的sortOrder排序
	 * @return
	 */
	public List<SkuOption> findActiveSkuOptionsByProductType(Integer productTypeId);

	
	/**
	 * 获取与产品类型没有关联的激活的SkuOption选项
	 * @param productTypeId
	 * @return
	 */
	public List<SkuOption> findActiveSkuOptionsExcludeRefProductType(Integer productTypeId);
	
	/**
	 * 根据SKU选项编码查找相应的SKU选项
	 * @param skuOptionCode
	 * @return
	 */
	public SkuOption getSkuOptionByCode(String skuOptionCode);
}
