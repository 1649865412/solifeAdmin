package com.cartmatic.estore.supplier.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.supplier.dao.SupplierDao;
import com.cartmatic.estore.supplier.service.SupplierManager;


/**
 * Manager implementation for Supplier, responsible for business processing, and communicate between web and persistence layer.
 */
public class SupplierManagerImpl extends GenericManagerImpl<Supplier> implements SupplierManager {
	
	private SupplierDao supplierDao = null;

	private CustomerManager customerManager=null;
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	/**
	 * @param supplierDao
	 *            the supplierDao to set
	 */
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = supplierDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Supplier entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Supplier entity) {

	}

	public Supplier getSupplierByCode(String supplierCode) {
		Supplier supplier=dao.findUniqueByProperty("supplierCode", supplierCode);
		return supplier;
	}
	
	public Supplier getLast()
	{
		return supplierDao.getLast();
	}

	@Override
	public void save(Supplier entity) {
		onSave(entity);
		boolean isNew=entity.getId()==null;
		super.save(entity);
		//20130314供应商超级管理员可以为空
		if(isNew&&entity.getAdminId()!=null){
			Customer supplierUser=customerManager.getById(entity.getAdminId());
			if(supplierUser!=null){
				supplierUser.setSupplier(entity);
				customerManager.save(supplierUser);
			}
		}
	}

	public List<Supplier> findActiveSuppliers() {
		List<Supplier>supplierList=dao.findByPropertyOrdered("status",Constants.STATUS_ACTIVE,"supplierName",true);
		supplierList=setDefaultSupplierFist(supplierList);
		return supplierList;
	}

	public List<Supplier> getAllByDefaultSortBy() {
		List<Supplier>supplierList=dao.getAllOrdered("supplierName",true);
		supplierList=setDefaultSupplierFist(supplierList);
		return supplierList;
	}
	
	private List<Supplier> setDefaultSupplierFist(List<Supplier> supplierList){
		for (int i = 0; i < supplierList.size(); i++) {
			Supplier supplier=supplierList.get(i);
			if(supplier.getSupplierId().intValue()==-1){
				supplierList.add(0, supplierList.remove(i));
				break;
			}
		}
		return supplierList;
	}
}
