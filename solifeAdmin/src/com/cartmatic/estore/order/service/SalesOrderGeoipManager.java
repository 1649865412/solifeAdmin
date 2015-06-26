package com.cartmatic.estore.order.service;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for SalesOrderGeoip, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface SalesOrderGeoipManager extends GenericManager<SalesOrderGeoip> {
	public SalesOrderGeoip getSalesOrderGeoipByOrderNoAndActionType(String orderNo,Short actionType);
}
