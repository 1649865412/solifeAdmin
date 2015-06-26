package com.cartmatic.estore.customer.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.AddressModel;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.AddressDao;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for Address, responsible for business processing, and communicate between web and persistence layer.
 */
public class AddressManagerImpl extends GenericManagerImpl<Address> implements AddressManager {

	private AddressDao addressDao = null;

	/**
	 * @param addressDao
	 *            the addressDao to set
	 */
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = addressDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Address entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Address entity) {

	}

	public List<Address> getAllShippingAddressByAppuserId(Integer appuserId) {
		return addressDao.getAllShippingAddressByAppuserId(appuserId);
	}

	public Address getDefShippingAddr(Integer appuserId) {
		return  addressDao.getDefShippingAddr(appuserId);
	}

	public Address getDefBillingAddress(Integer appuserId) {
		return  addressDao.getDefBillingAddress(appuserId);
	}

	public List<Address> getAllBillingAddressByAppuserId(Integer appuserId) {
		return addressDao.getAllBillingAddressByAppuserId(appuserId);
	}

	public Address getAddressByIdAndAppUserId(Integer addressId, Integer appuserId) {
		Address address=addressDao.getById(addressId);
		if(address!=null){
			if(address.getAppUser().getAppuserId().intValue()!=appuserId)
				address=null;
		}
		return address;
	}

	public void createShippingAndBilling(AddressModel addressModel) {
		//创建运输地址
		Address shippingAddress=new Address();
		shippingAddress.setTitle(addressModel.getTitle());
		shippingAddress.setFirstname(addressModel.getFirstname());
		shippingAddress.setLastname(addressModel.getLastname());
		shippingAddress.setAddress(addressModel.getAddress());
		shippingAddress.setAddress2(addressModel.getAddress2());
		shippingAddress.setCountryId(addressModel.getCountryId());
		shippingAddress.setCountryName(addressModel.getCountryName());
		shippingAddress.setStateId(addressModel.getStateId());
		shippingAddress.setStateName(addressModel.getStateName());
		shippingAddress.setCityId(addressModel.getCityId());
		shippingAddress.setCityName(addressModel.getCityName());
		shippingAddress.setZip(addressModel.getZip());
		shippingAddress.setTelephone(addressModel.getTelephone());
		shippingAddress.setAddressType(Constants.ADDRESS_TYPE_SHIPPING);
        shippingAddress.setAppUser(addressModel.getAppUser());
        
        //创建发票地址
        Address billingAddress=null;
        if(addressModel.getAppUser()!=null){
            billingAddress=getDefBillingAddress(addressModel.getAppUser().getAppuserId());
        }
        if(billingAddress==null){
        	billingAddress=new Address();
        	billingAddress.setIsDefaultBillingAddress(Constants.FLAG_TRUE);
        }
        billingAddress.setTitle(addressModel.getB_title());
        billingAddress.setFirstname(addressModel.getB_firstname());
        billingAddress.setLastname(addressModel.getB_lastname());
        billingAddress.setAddress(addressModel.getB_address());
        billingAddress.setAddress2(addressModel.getB_address2());
        billingAddress.setCountryId(addressModel.getB_countryId());
        billingAddress.setCountryName(addressModel.getB_countryName());
        billingAddress.setStateId(addressModel.getB_stateId());
        billingAddress.setStateName(addressModel.getB_stateName());
        billingAddress.setCityId(addressModel.getB_cityId());
        billingAddress.setCityName(addressModel.getB_cityName());
        billingAddress.setZip(addressModel.getB_zip());
        billingAddress.setTelephone(addressModel.getB_telephone());
        billingAddress.setAddressType(Constants.ADDRESS_TYPE_BILLING);
        billingAddress.setAppUser(addressModel.getAppUser());
        save(billingAddress);
        save(shippingAddress);
        addressModel.setAddressId(shippingAddress.getAddressId());
	}

}
