package com.cartmatic.estore.customer.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.AddressDao;

/**
 * Dao implementation for Address.
*/
public class AddressDaoImpl extends HibernateGenericDaoImpl<Address> implements AddressDao {

	public List<Address> getAllShippingAddressByAppuserId(Integer appuserId) {
		List<Address>addressList=findByHql("from Address vo where vo.addressType=1 and vo.appUser.appuserId=?",appuserId);
		return addressList;
	}

	public Address getDefShippingAddr(Integer appuserId) {
		String hql="from Address vo where vo.appUser.appuserId=? and vo.isDefaultShippingAddress=1 order by addressId desc";
		Address address=(Address) findUnique(hql,appuserId);
		return address;
	}

	public Address getDefBillingAddress(Integer appuserId) {
		String query="from Address vo where vo.appUser.appuserId=? and vo.isDefaultBillingAddress=1 order by addressId desc";
		Address address=(Address) findUnique(query, appuserId);
		return address;
	}

	public List<Address> getAllBillingAddressByAppuserId(Integer appuserId) {
		List<Address>addressList=findByHql("from Address vo where vo.addressType=2 and vo.appUser.appuserId=?",appuserId);
		return addressList;
	}


}
