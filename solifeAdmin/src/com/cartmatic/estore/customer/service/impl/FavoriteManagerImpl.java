package com.cartmatic.estore.customer.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.util.Assert;

import com.cartmatic.estore.catalog.service.ProductStatManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.ProductStat;
import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.customer.dao.FavoriteDao;
import com.cartmatic.estore.customer.service.FavoriteManager;


/**
 * Manager implementation for Favorite, responsible for business processing, and communicate between web and persistence layer.
 */
public class FavoriteManagerImpl extends GenericManagerImpl<Favorite> implements FavoriteManager {

	private FavoriteDao favoriteDao = null;
	private ProductStatManager productStatManager;
	/**
	 * @param favoriteDao
	 *            the favoriteDao to set
	 */
	public void setFavoriteDao(FavoriteDao favoriteDao) {
		this.favoriteDao = favoriteDao;
	}

	public void setProductStatManager(ProductStatManager avalue)
	{
		productStatManager = avalue;
	}
	
	public Favorite loadFavorite(Integer storeId,Integer customerId, Integer productId)
	{
		return favoriteDao.loadFavorite(storeId,customerId, productId);
	}
	
	/**
	 * 更新productstat记录
	 * @param productId
	 */
	private void updateProductStat(Integer productId)
	{
		ProductStat productStat = productStatManager.getProductStatForUpdate(ConfigUtil.getInstance().getStore().getStoreId(), productId);
		long count = favoriteDao.countByProperty("product.productId", productId);
		productStat.setFavoriteCount(new Integer(Long.toString(count)));
		productStatManager.save(productStat);
		CatalogHelper.getInstance().indexNotifyUpdateEvent(productId);
	}
	
	@Override
	public void save(Favorite entity)
	{
		Integer productId = entity.getProductId();
		super.save(entity);
		super.flush();
		updateProductStat(productId);
	}
	
	@Override
	protected void delete(Favorite entity) 
	{
		Integer productId = entity.getProductId();
		super.delete(entity);
		super.flush();
		updateProductStat(productId);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = favoriteDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Favorite entity) {
		
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Favorite entity) {
		
	}

	@Override
	public void updateFavoriteStat(Integer storeId, Integer productId) {
		Assert.notNull(storeId);
		Assert.notNull(productId);
		ProductStat productStat=productStatManager.getProductStatForUpdate(storeId, productId);
		Long favoriteCount=favoriteDao.countFavorites(storeId,productId);
		productStat.setFavoriteCount(favoriteCount.intValue());
		productStatManager.save(productStat);
	}
}
