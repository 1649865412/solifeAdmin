package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.StoreManager;
import com.cartmatic.estore.system.dao.StoreDao;


/**
 * Manager implementation for Store, responsible for business processing, and communicate between web and persistence layer.
 */
public class StoreManagerImpl extends GenericManagerImpl<Store> implements StoreManager {

	private StoreDao storeDao = null;
	
	private CategoryManager categoryManager=null;

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	/**
	 * @param storeDao
	 *            the storeDao to set
	 */
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = storeDao;
	}

	public List<Store> getAllActiveStores()
	{
		List<Store> storeList=this.findByProperty("status", Constants.STATUS_ACTIVE);
		//@TODO 临时调整，为了加载数据避免no session
		for (Store store : storeList)
		{
			store.getCatalog().getName();
			store.getCatalog().getCategory().getCategoryName();
		}
		return storeList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Store entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Store entity) {
		//新建商店时，需要同时创建一个目录，其代表商店在内容的根目录
		if(entity.getStoreId()==null){
			Category category=new Category();
			category.setCategoryCode("store_"+entity.getCode());
			category.setCategoryName(entity.getName());
			category.setCategoryDescription("StoreId:"+entity.getId());
			category.setStatus(Constants.STATUS_ACTIVE);
			category.setSortOrder(0);
			category.setIsLinkCategory(Constants.FLAG_FALSE);
			category.setCategoryType(Constants.CATEGORY_TYPE_CONTENT);
			categoryManager.save(category);
			
			entity.setCategory(category);
			storeDao.save(entity);
			category.setStore(entity);
		}else{
			Category category=entity.getCategory();
			category.setCategoryCode("store_"+entity.getCode());
			category.setCategoryName(entity.getName());
			categoryManager.save(category);
		}
	}

}
