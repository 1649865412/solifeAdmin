package com.cartmatic.estore.common.model.order.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OrderPromotion Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OrderPromotionTbl extends BaseObject implements Serializable {

    protected Integer orderPromotionId;
	protected Integer promoRuleId;
	protected String couponNo;
	protected String promotionName;
	protected com.cartmatic.estore.common.model.order.SalesOrder salesOrder;


	/**
	 * Default Empty Constructor for class OrderPromotion
	 */
	public OrderPromotionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OrderPromotion
	 */
	public OrderPromotionTbl (
		 Integer in_orderPromotionId
        ) {
		this.setOrderPromotionId(in_orderPromotionId);
    }

	
	public com.cartmatic.estore.common.model.order.SalesOrder getSalesOrder () {
		return salesOrder;
	}	
	
	public void setSalesOrder (com.cartmatic.estore.common.model.order.SalesOrder in_salesOrder) {
		this.salesOrder = in_salesOrder;
	}
    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="orderPromotionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getOrderPromotionId() {
		return this.orderPromotionId;
	}
	
	/**
	 * Set the orderPromotionId
	 */	
	public void setOrderPromotionId(Integer aValue) {
		this.orderPromotionId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 */
	public Integer getSalesOrderId() {
		return this.getSalesOrder()==null?null:this.getSalesOrder().getSalesOrderId();
	}
	
	/**
	 * Set the salesOrderId
	 */	
	public void setSalesOrderId(Integer aValue) {
	    if (aValue==null) {
	    	salesOrder = null;
	    } else {
	        salesOrder = new com.cartmatic.estore.common.model.order.SalesOrder(aValue);
	        salesOrder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="promoRuleId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getPromoRuleId() {
		return this.promoRuleId;
	}
	
	/**
	 * Set the promoRuleId
	 * @spring.validator type="required"
	 */	
	public void setPromoRuleId(Integer aValue) {
		this.promoRuleId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="couponNo" type="java.lang.String" length="16" not-null="false" unique="false"
	 */
	public String getCouponNo() {
		return this.couponNo;
	}
	
	/**
	 * Set the couponNo
	 */	
	public void setCouponNo(String aValue) {
		this.couponNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="promotionName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getPromotionName() {
		return this.promotionName;
	}
	
	/**
	 * Set the promotionName
	 * @spring.validator type="required"
	 */	
	public void setPromotionName(String aValue) {
		this.promotionName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OrderPromotionTbl)) {
			return false;
		}
		OrderPromotionTbl rhs = (OrderPromotionTbl) object;
		return new EqualsBuilder()
				.append(this.orderPromotionId, rhs.orderPromotionId)
						.append(this.promoRuleId, rhs.promoRuleId)
				.append(this.couponNo, rhs.couponNo)
				.append(this.promotionName, rhs.promotionName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderPromotionId) 
						.append(this.promoRuleId) 
				.append(this.couponNo) 
				.append(this.promotionName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderPromotionId", this.orderPromotionId) 
						.append("promoRuleId", this.promoRuleId) 
				.append("couponNo", this.couponNo) 
				.append("promotionName", this.promotionName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "orderPromotionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return orderPromotionId;
	}

}