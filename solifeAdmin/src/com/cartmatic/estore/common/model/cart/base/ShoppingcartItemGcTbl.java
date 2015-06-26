package com.cartmatic.estore.common.model.cart.base;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.cart.ShoppingcartItem;
import com.cartmatic.estore.core.model.BaseObject;

/**
 * ShoppingcartItemGc Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ShoppingcartItemGcTbl extends BaseObject implements Serializable {

    /**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected Integer shoppingcartItemGcId;
	protected String purchaser;
	protected String recipient;
	protected String recipientEmail;
	protected java.math.BigDecimal giftCertAmt= new BigDecimal(0);
	protected String message;
	
	private ShoppingcartItem shoppingcartItem;
	/**
	 * Default Empty Constructor for class ShoppingcartItemGc
	 */
	public ShoppingcartItemGcTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ShoppingcartItemGc
	 */
	public ShoppingcartItemGcTbl (
		 Integer in_shoppingcartItemGcId
        ) {
		this.setShoppingcartItemGcId(in_shoppingcartItemGcId);
    }

    



	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ShoppingcartTbl)) {
			return false;
		}
		ShoppingcartItemGcTbl rhs = (ShoppingcartItemGcTbl) object;
		return new EqualsBuilder()
				.append(this.shoppingcartItemGcId, rhs.shoppingcartItemGcId)
				.append(this.recipient, rhs.recipient)
				.append(this.purchaser, rhs.purchaser)
				.append(this.message, rhs.message)
				.append(this.recipientEmail, rhs.recipientEmail)
				.append(this.giftCertAmt, rhs.giftCertAmt)
				.isEquals();
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("shoppingcartItemGcId", this.shoppingcartItemGcId) 
				.append("recipient", this.recipient) 
				.append("purchaser", this.purchaser) 
				.append("message", this.message) 
				.append("recipientEmail", this.recipientEmail) 
				.append("giftCertAmt", this.giftCertAmt) 
				.toString();
	}
	@Override
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280556, -700257971)
				.append(this.shoppingcartItemGcId) 
				.append(this.recipient) 
				.append(this.purchaser) 
				.append(this.message) 
				.append(this.recipientEmail) 
				.toHashCode();
	}	

	public Integer getShoppingcartItemGcId() {
		return shoppingcartItemGcId;
	}

	public void setShoppingcartItemGcId(Integer shoppingcartItemGcId) {
		this.shoppingcartItemGcId = shoppingcartItemGcId;
	}

	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public java.math.BigDecimal getGiftCertAmt() {
		return giftCertAmt;
	}

	public void setGiftCertAmt(java.math.BigDecimal giftCertAmt) {
		this.giftCertAmt = giftCertAmt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ShoppingcartItem getShoppingcartItem() {
		return shoppingcartItem;
	}

	public void setShoppingcartItem(ShoppingcartItem shoppingcartItem) {
		this.shoppingcartItem = shoppingcartItem;
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "shoppingcartItemGcId";
	}

}