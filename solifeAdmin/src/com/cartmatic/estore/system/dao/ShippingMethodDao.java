package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ShippingMethod.
 */
public interface ShippingMethodDao extends GenericDao<ShippingMethod> {
	public List<ShippingMethod> getShippingMethodsAllOrder();

	public List<ShippingMethod> findNormalShippingMethods();
}