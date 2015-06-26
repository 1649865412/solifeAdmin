package com.cartmatic.estore.catalog.service.impl;


import java.util.List;

import com.cartmatic.estore.catalog.dao.ProductCategoryDao;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ProductCategory, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductCategoryManagerImpl extends GenericManagerImpl<ProductCategory> implements ProductCategoryManager {

	private ProductCategoryDao productCategoryDao = null;

	/**
	 * @param productCategoryDao
	 *            the productCategoryDao to set
	 */
	public void setProductCategoryDao(ProductCategoryDao productCategoryDao) {
		this.productCategoryDao = productCategoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productCategoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductCategory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductCategory entity) {

	}

	public Integer getMaxSortOrder(Integer categoryId) {
		return productCategoryDao.getMaxSortOrder(categoryId);
	}




	public Integer getNotDeletedProductCountByCategory(Integer categoryId) {
		return productCategoryDao.getNotDeletedProductCountByCategory(categoryId);
	}

	public boolean isInCategory(Integer productId, String categoryPath) {
		return productCategoryDao.isInCategory(productId, categoryPath);
	}

	public Category getCategoryByProductCategoryPath(String productCategoryPath) {
		return productCategoryDao.getCategoryByProductCategoryPath(productCategoryPath);
	}

	@Override
	public List<ProductCategory> findProductCategories(Integer catalogId, Integer categoryId) {
		return productCategoryDao.findProductCategories(catalogId, categoryId);
	}
	
	
}