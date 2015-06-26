package com.cartmatic.estore.imports.handler.customer;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.imports.handler.PersistenceHandler;
import com.cartmatic.estore.imports.model.ImportModel;

public class CustomerPersistenceHandlerImpl implements PersistenceHandler {
    private CustomerManager customerManager;
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void saveOrUpdate(ImportModel importModel) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) importModel.getTarget();
		customer.setDeleted(Constants.MARK_NOT_DELETED);
		customer.setStatus(Constants.STATUS_ACTIVE);
		customer.setStore(importModel.getStore());
        customerManager.save(customer);
        importModel.setResult("1");
        
	}

	public void validate(ImportModel importModel) {
		// TODO Auto-generated method stub

	}

}
