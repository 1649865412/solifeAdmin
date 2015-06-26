/**
 * 
 */
package com.cartmatic.estore.common.model.system;

/**
 * @author Ryan
 * 
 */
public interface Auditable {

	
	/**
	 * Provide detailed audit info for current entity.
	 * <pre>
	 *  public AuditInfo getAuditInfo(String methodName) {
	 *	  AuditInfo auditInfo = new AuditInfo();
	 *	  // Optional, Set entity type/name, default is null. $[...] is a i18n mark.
	 *	  auditInfo.setEntityType("$[menu.viewSystemConfigs]");
	 *	  // Optional, Set the partial url after ?, so can form the full url which can be clicked by admin. Leading & or ?
	 *	  // is not required.
	 *	  auditInfo.setSupplementUrl("");
	 *	  // Optional, but recommended, Set detailed entity info to be audit, default is the entity.toString().
	 *	  StringBuilder sb = new StringBuilder();
	 *	  sb.append("{change [").append(getConfigKey()).append("] from [").append(oldValue).append("] to [").append(
	 *			configValue).append("]}");
	 *	  auditInfo.setEntityAuditInfo(sb.toString());
	 *	  // Optional, Add one methodName - actionName mapping, can add multiple times. default is methodName.
	 *	  auditInfo.addActionNameMappings("saveSystemConfig", "Update");
	 *    return auditInfo;
	 *  }
	 *  </pre>
	 * @param methodName the name of the method is invoking, can use to compose more friendly message
	 * @return AuditInfo
	 */
	public AuditInfo getAuditInfo(String methodName);
}
