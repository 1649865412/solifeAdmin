package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Store, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface StoreManager extends GenericManager<Store> {
	public List<Store> getAllActiveStores();
	
}
