package com.cartmatic.estore.common.service.impl;

import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{

	protected CategoryManager	categoryManager	= null;
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public Category getCategoryById(Integer categoryId) {
		return categoryManager.getById(categoryId);
	}

	@Override
	public Category getProductCategoryByCode(Integer catalogId, String categoryCode) {
		return categoryManager.getProductCategoryByCode(catalogId, categoryCode);
	}

	@Override
	public Category getContentCategoryByCode(Integer storeId, String categoryCode) {
		return categoryManager.getContentCategoryByCode(storeId, categoryCode);
	}

}
