package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.base.AddressTbl;

/**
 * Model class for Address. Add not database mapped fileds in this class.
 */
public class Address extends AddressTbl {
	
	public boolean getIsDefault(){
		if(this.isDefaultShippingAddress!=null&&this.isDefaultShippingAddress.equals(Constants.FLAG_TRUE))
			return true;
		else
			return false;
	}

  	/**
	 * Default Empty Constructor for class Address
	 */
	public Address () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； addressName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAddressName () {
		if (addressId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.address;
	}
	
	/**
	 * Default Key Fields Constructor for class Address
	 */
	public Address (
		 Integer in_addressId
		) {
		super (
		  in_addressId
		);
	}
	
	/**
	 * 组装地址字符串，用于比较
	 * @see OrderShipment - getOrderAddressString
	 * @return
	 * @author pengzhirong
	 */
	public String getAddressString(){
		return this.getCountryName().trim()+(this.getStateName()!=null?this.getStateName().trim():"")
						+(this.getCityName()!=null?this.getCityName().trim():"")
						+(this.getAddress()!=null?this.getAddress().trim():"") 
						+(this.getAddress2()!=null?this.getAddress2().trim():"");
	}

}
