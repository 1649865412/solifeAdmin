package com.cartmatic.estore.system.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.PaymentMethodManager;
import com.cartmatic.estore.system.dao.PaymentMethodDao;


/**
 * Manager implementation for PaymentMethod, responsible for business processing, and communicate between web and persistence layer.
 */
public class PaymentMethodManagerImpl extends GenericManagerImpl<PaymentMethod> implements PaymentMethodManager {

	private PaymentMethodDao paymentMethodDao = null;

	/**
	 * @param paymentMethodDao
	 *            the paymentMethodDao to set
	 */
	public void setPaymentMethodDao(PaymentMethodDao paymentMethodDao) {
		this.paymentMethodDao = paymentMethodDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = paymentMethodDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(PaymentMethod entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(PaymentMethod entity) {

	}

	@Override
	public PaymentMethod getPaymentMethodByCode(String code) {
		return paymentMethodDao.findUniqueByProperty("paymentMethodCode", code);
	}

	@Override
	public List<PaymentMethod> findAllActivePaymentMethods() {
		return paymentMethodDao.findByPropertyOrdered("status", new Short("1"), "sortOrder", true);
	}

	@Override
	public List<PaymentMethod> findDeletedPaymentMethods() {
		return paymentMethodDao.findDeletedPaymentMethods();
	}

	@Override
	public List<PaymentMethod> findUnDeletedPaymentMethods() {
		return paymentMethodDao.findUnDeletedPaymentMethods();
	}

	@Override
	public void updateBatchPaymentMethods(List<PaymentMethod> paymentMethodList) {
		PaymentMethod pm = null;
        PaymentMethod paymentMethod = null;
        for (int i = 0; i < paymentMethodList.size(); i++) {
            pm = (PaymentMethod) paymentMethodList.get(i);
            paymentMethod = dao.loadById((pm.getPaymentMethodId()));
            //set the update value
            paymentMethod.setProtocol(pm.getProtocol());
            paymentMethod.setSortOrder(pm.getSortOrder());
            paymentMethod.setStatus(pm.getStatus());
            paymentMethod.setPaymentMethodName(pm.getPaymentMethodName());
            paymentMethod.setPaymentMethodDetail(pm.getPaymentMethodDetail());
            //update each
            dao.save(paymentMethod);
        }
	}

}
