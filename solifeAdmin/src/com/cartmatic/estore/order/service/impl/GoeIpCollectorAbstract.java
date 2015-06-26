package com.cartmatic.estore.order.service.impl;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.order.service.GeoIpCollector;

public abstract class GoeIpCollectorAbstract implements GeoIpCollector{

	/**
	 * Read the GEO info via Google map Service with address.
	 * @param address info
	 * @return SalesOrderGeoip mode.
	 */
	public SalesOrderGeoip getGeoIpByAddress(String address)
	{
		
		return null;
	}
}
