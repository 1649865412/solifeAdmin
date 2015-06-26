package com.cartmatic.estore.customer.dao.impl;

import com.cartmatic.estore.common.model.customer.Favorite;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.FavoriteDao;

/**
 * Dao implementation for Favorite.
*/
public class FavoriteDaoImpl extends HibernateGenericDaoImpl<Favorite> implements FavoriteDao {

	public Favorite loadFavorite(Integer storeId,Integer customerId, Integer productId)
	{
		return (Favorite) this.findUnique("from Favorite f where f.store.storeId=? and f.customer.appuserId=? and f.product.productId=?",storeId, customerId, productId);
	}
	
	public Long countFavorites(Integer storeId, Integer productId)
	{
		String hql = "select count(f.favoriteId) FROM Favorite f where f.store.storeId=? and f.product.productId =?";
        Long count = (Long)findUnique(hql, new Object[]{storeId,productId});
        if(count == null)
        	count = new Long(0);
		return count;
	}
}
