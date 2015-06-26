package com.cartmatic.estore.supplier.dao;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Supplier.
 */
public interface SupplierDao extends GenericDao<Supplier> {
	public Supplier getLast();
}