package com.cartmatic.estore.order.dao;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for SalesOrderGeoip.
 */
public interface SalesOrderGeoipDao extends GenericDao<SalesOrderGeoip> {
	public SalesOrderGeoip getSalesOrderGeoipByOrderNoAndActionType(String orderNo, Short actionType);
}