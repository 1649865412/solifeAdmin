package com.cartmatic.estore.customer.service.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Wishlist;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.WishlistDao;
import com.cartmatic.estore.customer.service.WishlistManager;


/**
 * Manager implementation for Wishlist, responsible for business processing, and communicate between web and persistence layer.
 */
public class WishlistManagerImpl extends GenericManagerImpl<Wishlist> implements WishlistManager {

	private WishlistDao wishlistDao = null;

	/**
	 * @param wishlistDao
	 *            the wishlistDao to set
	 */
	public void setWishlistDao(WishlistDao wishlistDao) {
		this.wishlistDao = wishlistDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = wishlistDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Wishlist entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Wishlist entity) {

	}

	public void addDefaultWishlist(Customer customer) {
		//add a default wishlist for this customer.
		Wishlist wishlist=new Wishlist();
		wishlist.setCustomer(customer);
		wishlist.setIsDefault(Wishlist.DEFAULT_LIST);
		wishlist.setShareLevel(Wishlist.SHARED_LEVEL_ANY);
		wishlist.setWishListTitle(Wishlist.WISHLIST_DEFAULT_TITLE);
		wishlist.setWishListType(Wishlist.WISH_LIST_TYPE_NORMAL);
		save(wishlist);
	}

	public void updateDefaultWishlist(Integer wishlistId, Integer customerId) {
		wishlistDao.updateDefaultWishlist(wishlistId, customerId);
	}

	public Wishlist getCustomerDefaultWishlist(Integer customerId) {
		return wishlistDao.getCustomerDefaultWishlist(customerId);
	}

	public List<Wishlist> getAllByCustomerId(Integer customerId) {
		List<Wishlist>wishlist=wishlistDao.findByProperty("customer.appuserId", customerId);
		return wishlist;
	}

}
