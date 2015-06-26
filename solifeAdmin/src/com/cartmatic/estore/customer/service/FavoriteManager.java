package com.cartmatic.estore.customer.service;

import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Favorite, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface FavoriteManager extends GenericManager<Favorite> {
	public Favorite loadFavorite(Integer storeId,Integer customerId, Integer productId);
	public void updateFavoriteStat(Integer storeId,Integer productId);
}
