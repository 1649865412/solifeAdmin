package com.cartmatic.estore.customer.dao;

import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Favorite.
 */
public interface FavoriteDao extends GenericDao<Favorite> {
	public Favorite loadFavorite(Integer storeId,Integer customerId, Integer productId);
	public Long countFavorites(Integer storeId, Integer productId);
}