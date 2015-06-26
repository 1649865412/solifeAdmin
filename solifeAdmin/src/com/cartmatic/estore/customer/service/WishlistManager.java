package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Wishlist;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Wishlist, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface WishlistManager extends GenericManager<Wishlist> {
	 /**
     *  add default wishlist for customer.
     */
    public void addDefaultWishlist(Customer customer);
    
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
    
    /**
     * get a customer wishlist List.
     * @param customerId
     * @return
     */
    public List<Wishlist> getAllByCustomerId(Integer customerId);
}
