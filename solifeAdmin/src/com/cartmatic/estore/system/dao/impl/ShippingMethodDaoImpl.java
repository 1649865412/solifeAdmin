package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.system.dao.ShippingMethodDao;
import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ShippingMethod.
*/
public class ShippingMethodDaoImpl extends HibernateGenericDaoImpl<ShippingMethod> implements ShippingMethodDao {

	@Override
	public List<ShippingMethod> getShippingMethodsAllOrder() {
		return findByHql("from ShippingMethod where deleted !=1  order by carrier.id asc,shippingMethodId asc");
	}

	@Override
	public List<ShippingMethod> findNormalShippingMethods() {
		return findByHql("select s from ShippingMethod s,Carrier c where c.carrierId=s.carrier.carrierId and c.deleted=0 and s.deleted=0 and s.status=1 and c.status=1");
	}

}
