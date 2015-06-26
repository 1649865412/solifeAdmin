package com.cartmatic.estore.inventory.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cartmatic.estore.common.model.inventory.Inventory;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.inventory.dao.InventoryDao;

/**
 * Dao implementation for Inventory.
*/
public class InventoryDaoImpl extends HibernateGenericDaoImpl<Inventory> implements InventoryDao {
	public Inventory getInventoryBySkuCode(String skuCode){
		return (Inventory) findUnique("from Inventory i where i.productSku.productSkuCode=?", skuCode);
	}
	
	public Integer getInventoryIdBySku(final Integer skuId){
		Integer id=(Integer) findUnique("select i.inventoryId from Inventory i where i.productSku.productSkuId=?", skuId);
/*		System.out.println("--------------"+i);
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery("select i from Inventory i where i.inventoryId=?");
				query.setInteger(0, skuId);
				query.setLockMode("i", LockMode.UPGRADE);
				return query.uniqueResult();
			}
		}); */
		return id;
	}

	public List<Inventory> getLackStockProductSkuLimit(final Integer maxSize) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer();
				sb.append("from Inventory i where (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<0");
				Query query = session.createQuery(sb.toString());
				query.setMaxResults(maxSize);
				List<Inventory> rows = (List<Inventory>) query.list();
				return rows;
			}

		});
		return list;
	}
	
	
	public List<Inventory> getLackStockActiveProductSkuLimit(final Integer maxSize) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer();
				sb.append("from Inventory i where i.productSku.product.status=1 and (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<0");
				Query query = session.createQuery(sb.toString());
				query.setMaxResults(maxSize);
				List<Inventory> rows = (List<Inventory>) query.list();
				return rows;
			}

		});
		return list;
	}
	
	public List<Inventory> getLowStockProductSkuLimit(final Integer maxSize) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer();
				sb.append("from Inventory i where (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<i.reorderMinimnm");
				Query query = session.createQuery(sb.toString());
				query.setMaxResults(maxSize);
				List<Inventory> rows = (List<Inventory>) query.list();
				return rows;
			}

		});
		return list;
	}
	
	
	public List<Inventory> getLowStockActiveProductSkuLimit(final Integer maxSize) {
		List list;
		list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sb = new StringBuffer();
				sb.append("from Inventory i where i.productSku.product.status=1 and (i.quantityOnHand-i.reservedQuantity-i.allocatedQuantity-i.preOrBackOrderedQty)<i.reorderMinimnm");
				Query query = session.createQuery(sb.toString());
				query.setMaxResults(maxSize);
				List<Inventory> rows = (List<Inventory>) query.list();
				return rows;
			}

		});
		return list;
	}
	
	
	
	

}
