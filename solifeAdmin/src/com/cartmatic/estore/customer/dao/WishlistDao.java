package com.cartmatic.estore.customer.dao;

import com.cartmatic.estore.common.model.customer.Wishlist;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Wishlist.
 */
public interface WishlistDao extends GenericDao<Wishlist> {
	/**
     * set a wishlist to default wishlist for customer.
     * @param wishlistId
     * @param customerId
     */
    public void updateDefaultWishlist(Integer wishlistId,Integer customerId);
    
    
    /**
     * get a customer default wishlist 
     * @param customerId
     * @return
     */
    public Wishlist getCustomerDefaultWishlist(Integer customerId);
}