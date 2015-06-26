package com.cartmatic.estore.order.service;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;

public interface GeoIpCollector {

	public SalesOrderGeoip getGeoIpByAddress(String address);
	public SalesOrderGeoip getGeoIpByIp(String ip) throws Exception;
}
