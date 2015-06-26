package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ShippingMethod, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ShippingMethodManager extends GenericManager<ShippingMethod> {
	public List<ShippingMethod> getShippingMethodsAllOrder();
	
	public List<ShippingMethod> findNormalShippingMethods();
}
