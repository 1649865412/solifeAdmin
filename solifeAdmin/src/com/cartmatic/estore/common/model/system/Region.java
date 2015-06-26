package com.cartmatic.estore.common.model.system;

import java.util.List;

import com.cartmatic.estore.common.model.system.base.RegionTbl;

/**
 * Model class for Region. Add not database mapped fileds in this class.
 */
public class Region extends RegionTbl {
	private String parentRegionName;
	
	private List<Region> subRegion;
	
	public String getParentRegionName() {
		return parentRegionName;
	}
	public void setParentRegionName(String parentRegionName) {
		this.parentRegionName = parentRegionName;
	}
	

  	/**
	 * Default Empty Constructor for class Region
	 */
	public Region () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Region
	 */
	public Region (
		 Integer in_regionId
		) {
		super (
		  in_regionId
		);
	}

	public List<Region> getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(List<Region> subRegion) {
		this.subRegion = subRegion;
	}
}