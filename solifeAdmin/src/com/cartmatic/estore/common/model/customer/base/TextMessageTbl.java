package com.cartmatic.estore.common.model.customer.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * TextMessage Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class TextMessageTbl extends BaseObject implements Serializable {

    protected Integer textMessageId;
	protected String phone;
	protected String email;


	/**
	 * Default Empty Constructor for class TextMessage
	 */
	public TextMessageTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class TextMessage
	 */
	public TextMessageTbl (
		 Integer in_textMessageId
        ) {
		this.setTextMessageId(in_textMessageId);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="textMessageId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getTextMessageId() {
		return this.textMessageId;
	}
	
	/**
	 * Set the textMessageId
	 */	
	public void setTextMessageId(Integer aValue) {
		this.textMessageId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 * @spring.validator type="required"
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 * @spring.validator type="required"
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof TextMessageTbl)) {
			return false;
		}
		TextMessageTbl rhs = (TextMessageTbl) object;
		return new EqualsBuilder()
				.append(this.textMessageId, rhs.textMessageId)
				.append(this.phone, rhs.phone)
				.append(this.email, rhs.email)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.textMessageId) 
				.append(this.phone) 
				.append(this.email) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("textMessageId", this.textMessageId) 
				.append("phone", this.phone) 
				.append("email", this.email) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "textMessageId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return textMessageId;
	}

}