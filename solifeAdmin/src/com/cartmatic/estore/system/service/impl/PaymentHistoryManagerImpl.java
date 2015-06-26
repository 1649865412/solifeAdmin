package com.cartmatic.estore.system.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.service.PaymentHistoryManager;
import com.cartmatic.estore.system.dao.PaymentHistoryDao;


/**
 * Manager implementation for PaymentHistory, responsible for business processing, and communicate between web and persistence layer.
 */
public class PaymentHistoryManagerImpl extends GenericManagerImpl<PaymentHistory> implements PaymentHistoryManager {

	private PaymentHistoryDao paymentHistoryDao = null;

	/**
	 * @param paymentHistoryDao
	 *            the paymentHistoryDao to set
	 */
	public void setPaymentHistoryDao(PaymentHistoryDao paymentHistoryDao) {
		this.paymentHistoryDao = paymentHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = paymentHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(PaymentHistory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(PaymentHistory entity) {

	}

	@Override
	public void savePaymentHistoryForDemo(String orderNo, BigDecimal amount,
			Integer paymentMethodId) {
		if(logger.isDebugEnabled()){
			logger.debug("enter savePaymentHistoryForDemo method... orderNo="+orderNo + ",paymentMethodId"+paymentMethodId);
		}
		PaymentHistory ph=new PaymentHistory();
		ph.setOrderNo(orderNo);
		ph.setAmount(amount);
		ph.setPaymentMethodId(paymentMethodId);
		ph.setFlag(Constants.PaymentHistory_FLAG_SUCCESS);
		ph.setIsBrowsed(new Short((short)0));
		
		ph.setCreateTime(new java.util.Date());
		this.dao.save(ph);
		
	}

	@Override
	public List<PaymentHistory> getAllSuccessUnBrowsed() {
		 return paymentHistoryDao.getAllByFlagIsBrowsed(PaymentHistory.FLAG_SUCCESS,PaymentHistory.UNBROWSED);
	}

	@Override
	public boolean getIsExistPaymentHistory(String orderNo,
			Integer paymentMethodId, BigDecimal amount, Short flag) {
		return this.paymentHistoryDao.getIsExistPaymentHistory(orderNo, paymentMethodId, amount, flag);
//		return false;
	}
	
}
