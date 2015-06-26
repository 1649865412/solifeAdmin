package com.cartmatic.estore.common.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.ProductReviewManager;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.customer.WishlistItem;
import com.cartmatic.estore.common.service.CustomerService;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.MembershipManager;
import com.cartmatic.estore.customer.service.WishlistItemManager;
import com.cartmatic.estore.customer.service.WishlistManager;
import com.cartmatic.estore.order.service.OrderMessageManager;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * 前台用户服务类
 * @author huangwm210 2009-1-9
 *
 */
public class CustomerServiceImpl implements CustomerService {
	private CustomerManager customerManager;
	private AddressManager addressManager;	
    private WishlistManager wishlistManager;
    private WishlistItemManager wishlistItemManager;
    private MembershipManager membershipManager;    
    private OrderMessageManager orderMessageManager;
    private ProductReviewManager productReviewManager;
    
    public void setMembershipManager(MembershipManager membershipManager) {
		this.membershipManager = membershipManager;
	}
    
	public void setWishlistItemManager(WishlistItemManager wishlistItemManager) {
		this.wishlistItemManager = wishlistItemManager;
	}

	public void setWishlistManager(WishlistManager wishlistManager) {
		this.wishlistManager = wishlistManager;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}
	
	public void setOrderMessageManager(OrderMessageManager avalue)
	{
		orderMessageManager = avalue;
	}
	
	public void setProductReviewManager(ProductReviewManager avalue)
	{
		productReviewManager = avalue;
	}

	public List<Address> getAllShippingAddressByAppuserId(Integer appuserId) {
		// TODO Auto-generated method stub
		return addressManager.getAllShippingAddressByAppuserId(appuserId);
	}

	public Address getDefBillingAddr(Integer appuserId) {
		return null;
	}

	public Address getDefShippingAddr(Integer appuserId) {
		return addressManager.getDefShippingAddr(appuserId);
	}
	
	public Customer getByAppUserId(Integer appUserId) {
		return this.customerManager.getById(appUserId);
	}

	public WishlistItem getWishlistItem(Integer wishlistItemId) {
		return this.wishlistItemManager.getById(wishlistItemId);
	}

	public void saveCustomerAddAsDef(Address address) {
		addressManager.save(address);
	}

	public void setAddressAsDefault(Integer addressId) {
		// TODO Auto-generated method stub
		if(RequestContext.isAnonymousUser())return;
		Address ad = addressManager.getById(addressId);
		if(ad != null){
			Address add = addressManager.getDefShippingAddr(RequestContext.getCurrentUserId());
			if(add!=null){
			   add.setIsDefaultShippingAddress(Constants.FLAG_FALSE);
			   addressManager.save(add);
			}
			ad.setIsDefaultShippingAddress(Constants.FLAG_TRUE);
			addressManager.save(ad);
		}
	}

	public void delAddress(Integer addressId) {
		// TODO Auto-generated method stub
		if(RequestContext.isAnonymousUser())return;
		addressManager.deleteById(addressId);
	}

	public void setWishListAsDefault(Integer wishListId) {
		// TODO Auto-generated method stub
		if(wishListId != null && !RequestContext.isAnonymousUser()){
				wishlistManager.updateDefaultWishlist(wishListId, RequestContext.getCurrentUserId());
		}
	}

	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}
	
	public List<Membership> getMembershipByName(String membershipName) {
		return membershipManager.getMembershipByName(membershipName);
	}
	
	public int getCountCustomerReviews(Integer storeId,Integer customerId)
	{
		return productReviewManager.getCountCustomerReviews(storeId,customerId);
	}
	
}
