package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderAddressTbl;

/**
 * Model class for OrderAddress. Add not database mapped fileds in this class.
 */
public class OrderAddress extends OrderAddressTbl {

  	/**
	 * Default Empty Constructor for class OrderAddress
	 */
	public OrderAddress () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderAddressName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderAddressName () {
		if (orderAddressId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.getAddress1();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderAddress
	 */
	public OrderAddress (
		 Integer in_orderAddressId
		) {
		super (
		  in_orderAddressId
		);
	}
	
	public OrderAddress clone(){
		OrderAddress oa = new OrderAddress();
		oa.setAddress1(this.getAddress1());
		oa.setAddress2(this.getAddress2());
		oa.setCity(this.getCity());
		oa.setCountry(this.getCountry());
		oa.setFaxNumber(this.getFaxNumber());
		oa.setFirstname(this.getFirstname());
		oa.setLastname(this.getLastname());
		oa.setPhoneNumber(this.getPhoneNumber());
		oa.setPostalcode(this.getPostalcode());
		oa.setState(this.getState());
		oa.setTitle(this.getTitle());
		return oa;
	}

}
