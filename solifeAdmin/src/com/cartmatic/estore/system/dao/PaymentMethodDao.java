package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PaymentMethod.
 */
public interface PaymentMethodDao extends GenericDao<PaymentMethod> {
	public List<PaymentMethod> findUnDeletedPaymentMethods();
	
	public List<PaymentMethod> findDeletedPaymentMethods();
}