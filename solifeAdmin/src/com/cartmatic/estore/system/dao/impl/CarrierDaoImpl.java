package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.CarrierDao;
import com.cartmatic.estore.common.model.system.Carrier;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for Carrier.
*/
public class CarrierDaoImpl extends HibernateGenericDaoImpl<Carrier> implements CarrierDao {

	@Override
	public List<Carrier> findActiveCarriers() {
		return findByHql("select i from Carrier i where i.deleted=0 and i.status=1");
	}

}
