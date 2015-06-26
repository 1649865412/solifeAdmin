package com.cartmatic.estore.common.model.system.base;

import java.io.Serializable;
import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.core.model.BaseObject;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * RegionItem Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class RegionItemTbl extends BaseObject implements Serializable {

    protected Integer regionItemId;
	protected Integer version;
	protected com.cartmatic.estore.common.model.system.Region region;
	protected Region item;


	/**
	 * Default Empty Constructor for class RegionItem
	 */
	public RegionItemTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RegionItem
	 */
	public RegionItemTbl (
		 Integer in_regionItemId
        ) {
		this.setRegionItemId(in_regionItemId);
    }

	
	public com.cartmatic.estore.common.model.system.Region getRegion () {
		return region;
	}	
	
	public void setRegion (com.cartmatic.estore.common.model.system.Region in_region) {
		this.region = in_region;
	}
    
	
	/**
	 * 	 * @return Integer
	 */
	public Integer getRegionId() {
		return this.getRegion()==null?null:this.getRegion().getRegionId();
	}
	
	/**
	 * Set the regionId
	 */	
	public void setRegionId(Integer aValue) {
	    if (aValue==null) {
	    	region = null;
	    } else {
	        region = new com.cartmatic.estore.common.model.system.Region(aValue);
	        region.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}	

	/**
	 * 	 * @return Integer
     * @hibernate.id column="regionItemId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getRegionItemId() {
		return this.regionItemId;
	}
	
	/**
	 * Set the regionItemId
	 */	
	public void setRegionItemId(Integer aValue) {
		this.regionItemId = aValue;
	}	

	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 * @spring.validator type="required"
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	


	public Region getItem() {
		return item;
	}

	public void setItem(Region item) {
		this.item = item;
	}
	
	/**
	 * @return Integer
	 */
	public Integer getItemId() {
		return this.getItem()==null?null:this.getItem().getRegionId();
	}
	
	/**
	 * Set the regionId
	 */	
	public void setItemId(Integer aValue) {
	    if (aValue==null) {
	    	item = null;
	    } else {
	    	item = new com.cartmatic.estore.common.model.system.Region(aValue);
	    	item.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } 
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RegionItemTbl)) {
			return false;
		}
		RegionItemTbl rhs = (RegionItemTbl) object;
		return new EqualsBuilder()
				.append(this.regionItemId, rhs.regionItemId)
						.append(this.version, rhs.version)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.regionItemId) 
						.append(this.version) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("regionItemId", this.regionItemId) 
						.append("version", this.version) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "regionItemId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return regionItemId;
	}

}