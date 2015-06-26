package com.cartmatic.estore.catalog.dao;


import java.util.List;

import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ProductCategory.
 */
public interface ProductCategoryDao extends GenericDao<ProductCategory> {
	
	/**
	 * 目录直属产品数量（非已删除产品）
	 * @param categoryId
	 * @return
	 */
	public Integer getNotDeletedProductCountByCategory(Integer categoryId);
	

	
	/**
	 * 获取产品所在目录最大的sortOrder
	 * @param categoryId
	 * @return
	 */
	public Integer getMaxSortOrder(Integer categoryId);


	
	
	/**
	 * 判断product是否在该目录下,包含父目录
	 * @param productId
	 * @param categoryPath
	 * @return
	 */
	public boolean isInCategory(Integer productId, String categoryPath);
	
	/**
	 * 通过完整的Product categoryPath获取相应的目录
	 * （完整的Path，由所有目录id及产品id组成）
	 * @param productCategoryPath
	 * @return
	 */
	public Category getCategoryByProductCategoryPath(String productCategoryPath);
	
	public List<ProductCategory> findProductCategories(Integer catalogId, Integer categoryId);

}