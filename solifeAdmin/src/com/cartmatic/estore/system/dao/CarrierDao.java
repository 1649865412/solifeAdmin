package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.Carrier;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Carrier.
 */
public interface CarrierDao extends GenericDao<Carrier> {
	public List<Carrier> findActiveCarriers();
}