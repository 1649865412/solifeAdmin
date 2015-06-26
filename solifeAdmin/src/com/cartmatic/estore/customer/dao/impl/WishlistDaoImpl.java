package com.cartmatic.estore.customer.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Wishlist;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.WishlistDao;

/**
 * Dao implementation for Wishlist.
*/
public class WishlistDaoImpl extends HibernateGenericDaoImpl<Wishlist> implements WishlistDao {

	public void updateDefaultWishlist(Integer wishlistId, Integer customerId) {
		String hql = "update Wishlist set isDefault=? where customer.appuserId=? and wishlistId<>?";
		Query query = getSession().createQuery(hql);
		query.setShort(0, Wishlist.NOT_DEFAULT_LIST.shortValue());
		query.setInteger(1, customerId.intValue());
		query.setInteger(2, wishlistId.intValue());
		query.executeUpdate();

		hql = "update Wishlist set isDefault=? where customer.appuserId=? and wishlistId=?";
		query = getSession().createQuery(hql);
		query.setShort(0, Wishlist.DEFAULT_LIST.shortValue());
		query.setInteger(1, customerId.intValue());
		query.setInteger(2, wishlistId.intValue());
		query.executeUpdate();
	}

	public Wishlist getCustomerDefaultWishlist(Integer customerId) {
		String hql="from Wishlist where customer.appuserId=? and isDefault=?";
        List<Wishlist> list=findByHql(hql,new Object[]{customerId,Constants.FLAG_TRUE});
        Wishlist wishlist=null;
        if(list.size()>0){
            wishlist=(Wishlist)list.get(0);
        }else{
        	hql="from Wishlist where customer.appuserId=? order by wishlistId asc ";
        	list=findByHql(hql,new Object[]{customerId});
        	if(list.size()>0){
        		return(Wishlist)list.get(0);
        	}
        }
        return wishlist;
	}

}
