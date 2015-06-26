package com.cartmatic.estore.common.model.inventory;

import java.util.Date;

/**
 * @author kedou
 *
 */
public class SkuInventoryVO {
	/**
	 * 4或5时，表示没有库存数量限制
	 * 
	 * 1或2时，availableToCartQty表示可以添加到购物车的数量
	 * 
	 * 当type为
	 * 1表示有库存才库存购买,即availableQuantity必须有相应数量存在
	 * 2表示预订，即availablePreOrBackOrderedQty必须有相应数量存在
	 * 4表示该产品是无限库存，不受库存管理，availableQuantity、availablePreOrBackOrderedQty等数据为空
	 * 5表示预订，但不可受预订数量限制
	 */
	
	private Short type;

	/**
	 * 可售数量
	 */
	private Integer availableQuantity=0;
	
	/**
	 * 可预订的数量
	 */
	private Integer availablePreOrBackOrderedQty=0;
	
	/**
	 * 可以添加到购物车的数量
	 */
	private Integer availableToCartQty=0;
	
	/**
	 * 产品销售规则
	 */
	private Short availabilityRule;
	
	/**
	 * 预计到时时间或上市时间
	 */
	private Date expectedRestockDate;

	/**
	 * 4或5时，表示没有库存数量限制
	 * 
	 * 1或2时，availableToCartQty表示可以添加到购物车的数量
	 * 
	 * 当type为
	 * 1表示有库存才库存购买,即availableQuantity必须有相应数量存在
	 * 2表示预订，即availablePreOrBackOrderedQty必须有相应数量存在
	 * 4表示该产品是无限库存，不受库存管理，availableQuantity、availablePreOrBackOrderedQty等数据为空
	 * 5表示预订，但不可受预订数量限制
	 * @return
	 */
	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Integer getAvailablePreOrBackOrderedQty() {
		return availablePreOrBackOrderedQty;
	}

	public void setAvailablePreOrBackOrderedQty(Integer availablePreOrBackOrderedQty) {
		this.availablePreOrBackOrderedQty = availablePreOrBackOrderedQty;
	}

	public Date getExpectedRestockDate() {
		return expectedRestockDate;
	}

	public void setExpectedRestockDate(Date expectedRestockDate) {
		this.expectedRestockDate = expectedRestockDate;
	}

	public Integer getAvailableToCartQty() {
		return availableToCartQty;
	}

	public void setAvailableToCartQty(Integer availableToCartQty) {
		this.availableToCartQty = availableToCartQty;
	}

	public Short getAvailabilityRule() {
		return availabilityRule;
	}

	public void setAvailabilityRule(Short availabilityRule) {
		this.availabilityRule = availabilityRule;
	}

}
