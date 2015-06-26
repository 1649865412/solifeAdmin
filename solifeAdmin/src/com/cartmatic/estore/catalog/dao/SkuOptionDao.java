package com.cartmatic.estore.catalog.dao;

import java.util.List;

import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for SkuOption.
 */
public interface SkuOptionDao extends GenericDao<SkuOption> {
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
	
}