package com.cartmatic.estore.order.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.order.dao.SalesOrderGeoipDao;

/**
 * Dao implementation for SalesOrderGeoip.
*/
public class SalesOrderGeoipDaoImpl extends HibernateGenericDaoImpl<SalesOrderGeoip> implements SalesOrderGeoipDao {

	public SalesOrderGeoip getSalesOrderGeoipByOrderNoAndActionType(String orderNo, Short actionType) {
		List<SalesOrderGeoip> salesOrderGeoipList=this.findByHql("from SalesOrderGeoip sog where sog.orderNo=? and sog.actionType=?", new Object[]{orderNo,actionType});
		if(salesOrderGeoipList==null||salesOrderGeoipList.size()==0)
			return null;
		SalesOrderGeoip salesOrderGeoip=(SalesOrderGeoip)salesOrderGeoipList.get(salesOrderGeoipList.size()-1);
		return salesOrderGeoip;
	}

}
