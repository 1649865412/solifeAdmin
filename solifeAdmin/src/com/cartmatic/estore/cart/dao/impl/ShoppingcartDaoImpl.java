package com.cartmatic.estore.cart.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.cart.dao.ShoppingcartDao;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Shoppingcart Data Access Object (DAO) implementation. Developer introduced
 * interfaces should be declared here. Won't get overwritten.
 */
public class ShoppingcartDaoImpl extends HibernateGenericDaoImpl<Shoppingcart> implements ShoppingcartDao { 	

	public void delByShoppingcartItemId(final Integer shoppingcartItemId){
		final String hql="delete from ShoppingcartItem where shoppingcartItemId=?";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				Query query=session.createQuery(hql);
				query.setInteger(0, shoppingcartItemId);
				Integer rows=query.executeUpdate();
				session.flush();
				return rows;
			}
		});
	}
	
	

	public Shoppingcart getShoppingcartByUuid(String uuid) {
		// TODO Auto-generated method stub
		String hql = "from Shoppingcart where uuid=?";
		Shoppingcart cart = (Shoppingcart) findUnique(hql, uuid);
		return cart;
		
	}

	public void removeShoppingcart(final Shoppingcart cart) {
		// TODO Auto-generated method stub
		if(cart!=null){
			final String hql = "delete from Shoppingcart where uuid=?";
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException,SQLException {
					Query query=session.createQuery(hql);
					query.setString(0, cart.getUuid());
					Integer rows=query.executeUpdate();
					session.flush();
					return rows;
				}
			});
		}
	}
	
	public void cleanShoppingcartByUpdatetime(final Date updatetime) {
		// TODO Auto-generated method stub
		if(updatetime != null)
		{
			final String hql = "delete from Shoppingcart where updateTime < ?";
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException,SQLException {
					Query query=session.createQuery(hql);
					query.setDate(0, updatetime);
					Integer rows=query.executeUpdate();
					session.flush();
					return rows;
				}
			});
		}
	}
	
	public Shoppingcart getShoppingcartByCustomer(Customer customer, Store store)
	{
		if(customer!=null){
			String hql = "from Shoppingcart c where c.customerId=? and c.store.storeId=?";
			List<Shoppingcart> list = this.findByHql(hql, new Object[]{customer.getAppuserId(), store.getStoreId()});
			if(list != null&&list.size()!=0)
				return list.get(0);
			else
				return null;
		}
		else
			return null;
	}
}