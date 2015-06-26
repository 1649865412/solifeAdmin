package com.cartmatic.estore.catalog.service;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Catalog, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface CatalogManager extends GenericManager<Catalog> {
	public Catalog getByCode(String code);
	
	public Integer deleteCatalog(Integer catalogId);
}
