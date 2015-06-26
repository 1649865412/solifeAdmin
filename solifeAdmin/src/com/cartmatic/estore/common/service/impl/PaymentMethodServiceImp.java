package com.cartmatic.estore.common.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.service.PaymentMethodService;
import com.cartmatic.estore.system.service.PaymentHistoryManager;
import com.cartmatic.estore.system.service.PaymentMethodManager;
import com.cartmatic.estore.system.service.StoreManager;

public class PaymentMethodServiceImp implements PaymentMethodService {

	private PaymentMethodManager paymentMethodManager;
	private PaymentHistoryManager paymentHistoryManager;
	
	private StoreManager storeManager = null;
	
	

	public List<PaymentMethod> getPaymentMethodByCart(boolean isVirtual) {
		// TODO Auto-generated method stub
		//List<PaymentMethod> list = paymentMethodManager.findAllActivePaymentMethods();
		List<PaymentMethod> list = new ArrayList<PaymentMethod>(); 
//		int storeId = ConfigUtil.getInstance().getStore().getId();
//		Store store = this.storeManager.getById(storeId);
//		Set<PaymentMethod> methods = store.getPaymentMethods();
		Set<PaymentMethod> methods = ConfigUtil.getInstance().getStore().getPaymentMethods();
		if(!isVirtual)
		{
			for (PaymentMethod m: methods)
			{
				list.add(m);
			}
			Collections.sort(list);
			return list;
		}
		else
		{
			//List<PaymentMethod> l = new ArrayList<PaymentMethod>();
			for(PaymentMethod m : methods)
			{
				if(m.getIsCod()==0)
					list.add(m);
			}
			Collections.sort(list);
		    return list;
		}
	}

	public PaymentMethod getPaymentById(Integer id) {
		return paymentMethodManager.getById(id);
	}

	public void savePaymentHistoryForDemo(String orderNo,BigDecimal amount,Integer paymentMethodId) {
		this.paymentHistoryManager.savePaymentHistoryForDemo(orderNo,amount, paymentMethodId);
	}
	
	public PaymentMethod getPaymentMethodByCode(String code)
	{
		return paymentMethodManager.getPaymentMethodByCode(code);
	}
	
	public void setPaymentHistoryManager(PaymentHistoryManager paymentHistoryManager) {
		this.paymentHistoryManager = paymentHistoryManager;
	}
	public void setPaymentMethodManager(PaymentMethodManager paymentMethodManager) {
		this.paymentMethodManager = paymentMethodManager;
	}

	public StoreManager getStoreManager() {
		return storeManager;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}
}
