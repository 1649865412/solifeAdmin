package com.cartmatic.estore.common.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.customer.Wishlist;
import com.cartmatic.estore.common.model.customer.WishlistItem;
import com.cartmatic.estore.core.model.PagingBean;

/**
 * 前台用户服务类
 * @author huangwm210 2009-1-9
 *
 */
public interface CustomerService {

	/**
	 * 获取默认的运输地址
	 * @param appuserId
	 * @return
	 */
	public Address getDefShippingAddr(Integer appuserId);
	
	/**
	 * 将前台客户第一次结账时的地址保存为默认的运输地址
	 * @param address
	 */
	public void saveCustomerAddAsDef(Address address);
	/**
	 * 获取默认的发票地址
	 * TODO 目前好似没有使用上(空实现)
	 * @param appuserId
	 * @return
	 */
	public Address getDefBillingAddr(Integer appuserId);
	/**
	 * 获取用户所有的运输地址
	 * @param appuserId
	 *        当前登陆用户的id
	 * @return
	 */
	public List<Address> getAllShippingAddressByAppuserId(Integer appuserId);
	
	public Customer getByAppUserId(Integer appUserId);
	
	public WishlistItem getWishlistItem(Integer wishlistItemId);
	
	/**
	 * 将用户某个地址设置为默认
	 * @param addressId
	 */
	public void setAddressAsDefault(Integer addressId);
	
	/**
	 * 删除某个地址
	 * @param addressId
	 */
	public void delAddress(Integer addressId);
	
	/**
	 * 将某个意愿清单设置为默认
	 * @param wishList
	 */
	public void setWishListAsDefault(Integer wishListId);
	
	/**
	 * 通过等级的名称来获取Membership
	 * @param membershipName
	 * @return
	 */
	public List<Membership> getMembershipByName(String membershipName);
	
	
	public int getCountCustomerReviews(Integer storeId,Integer customerId);
}
