package com.cartmatic.estore.catalog.service.impl;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.dao.CatalogDao;


/**
 * Manager implementation for Catalog, responsible for business processing, and communicate between web and persistence layer.
 */
public class CatalogManagerImpl extends GenericManagerImpl<Catalog> implements CatalogManager {

	private CatalogDao catalogDao = null;
	
	private CategoryManager categoryManager=null;
	
	
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	/**
	 * @param catalogDao
	 *            the catalogDao to set
	 */
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = catalogDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Catalog entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Catalog catalog) {
		//新建商品目录时，需要同时创建一个商品分类，其代表商品目录的根商品分类
		if(catalog.getCatalogId()==null){
			Category category=new Category();
			category.setCategoryCode("catalog_"+catalog.getCode());
			category.setCategoryName(catalog.getCatalogName());
			category.setCategoryDescription("CatalogId:"+catalog.getId());
			category.setStatus(Constants.STATUS_ACTIVE);
			category.setSortOrder(0);
			category.setCategoryType(Constants.CATEGORY_TYPE_CATALOG);
			categoryManager.save(category);
			catalog.setCategory(category);
			catalogDao.save(catalog);
			category.setCatalog(catalog);
		}else{
			Category category=catalog.getCategory();
			category.setCategoryCode("catalog_"+catalog.getCode());
			category.setCategoryName(catalog.getCatalogName());
			categoryManager.save(category);
		}
		
	}

	@Override
	public Catalog getByCode(String code) {
		return catalogDao.findUniqueByProperty("code", code);
	}

	@Override
	public Integer deleteCatalog(Integer catalogId) {
		Catalog catalog=getById(catalogId);
		if(catalog==null){
			return -1;
		}
		if(categoryManager.isContainSubCategoriesOrProducts(catalog.getCategoryId())){
			return 2;
		}
		delete(catalog);
		categoryManager.deleteById(catalog.getCategoryId());
		return 1;
	}

}
