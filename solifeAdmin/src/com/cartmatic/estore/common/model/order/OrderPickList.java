package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.OrderPickListTbl;

/**
 * Model class for OrderPickList. Add not database mapped fileds in this class.
 */
public class OrderPickList extends OrderPickListTbl {

  	/**
	 * Default Empty Constructor for class OrderPickList
	 */
	public OrderPickList () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； orderPickListName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getOrderPickListName () {
		if (orderPickListId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderPickListId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPickList
	 */
	public OrderPickList (
		 Integer in_orderPickListId
		) {
		super (
		  in_orderPickListId
		);
	}
	
	public void addOrderShipment(OrderShipment orderShipment){
		this.getOrderShipments().add(orderShipment);
	}

}
