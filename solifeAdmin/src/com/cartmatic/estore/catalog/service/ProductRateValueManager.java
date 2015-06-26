package com.cartmatic.estore.catalog.service;

import com.cartmatic.estore.common.model.catalog.ProductRateValue;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ProductRateValue, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ProductRateValueManager extends GenericManager<ProductRateValue> {
	public void deleteAllByProductId(Integer productId);
}
