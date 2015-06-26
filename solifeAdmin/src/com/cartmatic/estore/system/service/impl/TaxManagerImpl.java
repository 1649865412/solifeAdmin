package com.cartmatic.estore.system.service.impl;

import com.cartmatic.estore.common.model.system.Tax;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.system.dao.TaxDao;
import com.cartmatic.estore.system.service.TaxManager;



/**
 * Tax Business Delegate (Proxy) implementation to handle communication between web and
 * persistence layer. Implementation of TaxManager interface.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
 */
public class TaxManagerImpl extends GenericManagerImpl<Tax> implements TaxManager {
	
    private TaxDao taxDao;

	public void setTaxDao(TaxDao taxDao) {
		this.taxDao = taxDao;
	}

	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		this.dao = taxDao;
	}

	@Override
	protected void onDelete(Tax entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(Tax entity) {
		// TODO Auto-generated method stub
		
	}
}
