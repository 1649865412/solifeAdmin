
package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for PaymentMethod, responsible for business processing, and
 * communicate between web and persistence layer.
 * 
 */
public interface PaymentMethodManager extends GenericManager<PaymentMethod> {
	public PaymentMethod getPaymentMethodByCode(String code);

	public List<PaymentMethod> findAllActivePaymentMethods();

	public List<PaymentMethod> findUnDeletedPaymentMethods();

	public List<PaymentMethod> findDeletedPaymentMethods();
	
	public void updateBatchPaymentMethods(List<PaymentMethod> paymentMethodList);
}
