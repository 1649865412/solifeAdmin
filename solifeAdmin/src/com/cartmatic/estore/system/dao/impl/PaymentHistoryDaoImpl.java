package com.cartmatic.estore.system.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.system.dao.PaymentHistoryDao;
import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for PaymentHistory.
*/
public class PaymentHistoryDaoImpl extends HibernateGenericDaoImpl<PaymentHistory> implements PaymentHistoryDao {

	@Override
	public List<PaymentHistory> getAllByFlagIsBrowsed(Short flag,Short isBrowsed) {
		String hql="from PaymentHistory where flag=? and isBrowsed=?";
        return findByHql(hql,new Object[]{flag,isBrowsed});
	}

	@Override
	public boolean getIsExistPaymentHistory(String orderNo,
			Integer paymentMethodId, BigDecimal amount, Short flag) {
		String hql="from PaymentHistory ph where ph.orderNo=? and ph.paymentMethodId=? and ph.amount=? and ph.flag=?";
    	List list=findByHql(hql, new Object[]{orderNo,paymentMethodId,amount,flag});
    	if(list.size()>0) return true;
    	return false;
	}

}
