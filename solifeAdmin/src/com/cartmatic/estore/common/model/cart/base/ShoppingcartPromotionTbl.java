package com.cartmatic.estore.common.model.cart.base;

import java.io.Serializable;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.core.model.BaseObject;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ShoppingcartPromotion Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShoppingcartPromotionTbl extends BaseObject implements Serializable {

    protected Integer shoppingcartPromotionId;
	protected Shoppingcart shoppingcart;
	protected Integer promoRuleId;
	protected String promotionName;


	/**
	 * Default Empty Constructor for class ShoppingcartPromotion
	 */
	public ShoppingcartPromotionTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShoppingcartPromotion
	 */
	public ShoppingcartPromotionTbl (
		 Integer in_shoppingcartPromotionId
        ) {
		this.setShoppingcartPromotionId(in_shoppingcartPromotionId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="shoppingcartPromotionId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getShoppingcartPromotionId() {
		return this.shoppingcartPromotionId;
	}
	
	/**
	 * Set the shoppingcartPromotionId
	 */	
	public void setShoppingcartPromotionId(Integer aValue) {
		this.shoppingcartPromotionId = aValue;
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
		if (!(object instanceof ShoppingcartPromotionTbl)) {
			return false;
		}
		ShoppingcartPromotionTbl rhs = (ShoppingcartPromotionTbl) object;
		return new EqualsBuilder()
				.append(this.shoppingcartPromotionId, rhs.shoppingcartPromotionId)
				.append(this.shoppingcart, rhs.shoppingcart)
				.append(this.promoRuleId, rhs.promoRuleId)
				.append(this.promotionName, rhs.promotionName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.shoppingcartPromotionId) 
				.append(this.shoppingcart) 
				.append(this.promoRuleId) 
				.append(this.promotionName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shoppingcartPromotionId", this.shoppingcartPromotionId) 
				.append("shoppingCartId", this.shoppingcart) 
				.append("promoRuleId", this.promoRuleId) 
				.append("promotionName", this.promotionName) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shoppingcartPromotionId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return shoppingcartPromotionId;
	}

	public Shoppingcart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(Shoppingcart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}


}