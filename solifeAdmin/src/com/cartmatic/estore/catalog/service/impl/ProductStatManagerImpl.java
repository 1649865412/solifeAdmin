package com.cartmatic.estore.catalog.service.impl;

import java.math.BigDecimal;

import com.cartmatic.estore.catalog.dao.ProductStatDao;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.StoreManager;


/**
 * Manager implementation for ProductStat, responsible for business processing, and communicate between web and persistence layer.
 */
public class ProductStatManagerImpl extends GenericManagerImpl<ProductStat> implements ProductStatManager {
	private ProductStatDao productStatDao = null;
	private ProductManager productManager=null;
	private StoreManager storeManager=null;
	
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	/**
	 * @param productStatDao
	 *            the productStatDao to set
	 */
	public void setProductStatDao(ProductStatDao productStatDao) {
		this.productStatDao = productStatDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productStatDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(ProductStat entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(ProductStat entity) {

	}

	private ProductStat newProductStat(Integer storeId,Integer productId) {
		Store store=storeManager.getById(storeId);
		Product product=productManager.getById(productId);
		ProductStat productStat=new ProductStat();
		productStat.setBuyCount(0);
		productStat.setAverageRate(new BigDecimal(0));
		productStat.setReviewCount(0);
		productStat.setProduct(product);
		productStat.setStore(store);
		return productStat;
	}

	public void doAddProductBuyCount(Integer storeId,Integer productId, Integer quantity) {
		if(logger.isInfoEnabled()){
			logger.info("产品:"+productId+",销售数量增加："+quantity);
		}
		ProductStat productStat=getProductStatForUpdate(storeId,productId);
		productStat.setBuyCount(productStat.getBuyCount()+quantity);
		dao.save(productStat);
		CatalogHelper.getInstance().indexNotifyUpdateEvent(productId);
	}

	public void doSubtractProductBuyCount(Integer storeId,Integer productId, Integer quantity) {
		if(logger.isInfoEnabled()){
			logger.info("产品:"+productId+",销售数量减少："+quantity);
		}
		ProductStat productStat=getProductStatForUpdate(storeId,productId);
		productStat.setBuyCount(productStat.getBuyCount()-quantity);
		dao.save(productStat);
		CatalogHelper.getInstance().indexNotifyUpdateEvent(productId);
	}

	@Override
	public ProductStat getProductStat(Integer storeId, Integer productId) {
		ProductStat productStat=productStatDao.getProductStat(storeId, productId);
		if(productStat==null){
			productStat=newProductStat(storeId, productId);
		}
		return productStat;
	}

	@Override
	public ProductStat getProductStatForUpdate(Integer storeId, Integer productId) {
		ProductStat productStat=productStatDao.getProductStat(storeId, productId);
		if(productStat==null){
			return newProductStat(storeId, productId);
		}
		return dao.loadForUpdate(productStat.getId());
	}

}
