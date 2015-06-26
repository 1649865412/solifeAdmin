package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.system.dao.PaymentMethodDao;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for PaymentMethod.
*/
public class PaymentMethodDaoImpl extends HibernateGenericDaoImpl<PaymentMethod> implements PaymentMethodDao {

	@Override
	public List<PaymentMethod> findDeletedPaymentMethods() {
		return findByHql("from PaymentMethod vo where vo.status =? order by vo.sortOrder", Constants.STATUS_DELETED);
	}

	@Override
	public List<PaymentMethod> findUnDeletedPaymentMethods() {
		return findByHql("from PaymentMethod vo where vo.status <>? order by sortOrder",Constants.STATUS_DELETED);
	}
}
