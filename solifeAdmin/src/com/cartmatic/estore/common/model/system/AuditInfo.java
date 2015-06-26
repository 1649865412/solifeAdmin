/**
 * 
 */
package com.cartmatic.estore.common.model.system;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * 
 */
public class AuditInfo {
	private String entityType;

	private String entityAuditInfo;

	private Map<String, String> actionNameMappings;

	private String supplementUrl;

	/**
	 * @return the actionNameMappings
	 */
	public Map<String, String> getActionNameMappings() {
		return this.actionNameMappings;
	}

	/**
	 * Set methodName - actionName mappings. default is methodName
	 * 
	 * @param actionNameMappings
	 *            the actionNameMappings to set
	 */
	public void setActionNameMappings(Map<String, String> actionNameMappings) {
		this.actionNameMappings = actionNameMappings;
	}

	/**
	 * Add one methodName - actionName mapping, can add multiple times
	 * 
	 * @param methodName
	 * @param actionName
	 */
	public void addActionNameMappings(String methodName, String actionName) {
		if (actionNameMappings == null) {
			actionNameMappings = new HashMap<String, String>();
		}
		actionNameMappings.put(methodName, actionName);
	}

	/**
	 * @return the entityAuditInfo
	 */
	public String getEntityAuditInfo() {
		return this.entityAuditInfo;
	}

	/**
	 * Set detailed entity info to be audit, default is the entity.toString().
	 * 
	 * @param entityAuditInfo
	 *            the entityAuditInfo to set
	 */
	public void setEntityAuditInfo(String entityAuditInfo) {
		this.entityAuditInfo = entityAuditInfo;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return this.entityType;
	}

	/**
	 * Set entity type/name, default is null.
	 * 
	 * @param entityType
	 *            the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the supplementUrl
	 */
	public String getSupplementUrl() {
		return this.supplementUrl;
	}

	/**
	 * Set the partial url after ?, so can form the full url which can be clicked by admin. Leading & or ? is not
	 * required.
	 * 
	 * @param supplementUrl
	 *            the supplementUrl to set
	 */
	public void setSupplementUrl(String supplementUrl) {
		this.supplementUrl = supplementUrl;
	}

}
