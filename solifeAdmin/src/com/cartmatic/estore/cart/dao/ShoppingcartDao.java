package com.cartmatic.estore.cart.dao;

import java.util.Date;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Shoppingcart Data Access Object (DAO) interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public interface ShoppingcartDao extends GenericDao<Shoppingcart> {
	//public Shoppingcart getActiveShoppingcartById(Integer shoppingcartId);	
	//public void merge(Shoppingcart shoppingcart);
	public void delByShoppingcartItemId(final Integer shoppingcartItemId);
	public void removeShoppingcart(Shoppingcart cart);
	public void cleanShoppingcartByUpdatetime(final Date updatetime);
	public Shoppingcart getShoppingcartByUuid(String uuid);
	public Shoppingcart getShoppingcartByCustomer(Customer customer, Store store);
}
