/**
 * 
 */

package com.cartmatic.estore.core.search;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Ryan
 * 
 */
public class SearchFilter implements Serializable {

	private static final long	serialVersionUID	= -446793117846087068L;

	private String				allow;

	private String				filterName;

	/**
	 * @return the allow
	 */
	public String getAllow() {
		return this.allow;
	}

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return this.filterName;
	}

	/**
	 * @param allow
	 *            the allow to set
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}

	/**
	 * @param filterName
	 *            the filterName to set
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("filterName", filterName)
				.append("allowed controls", allow).toString();
	}
}
