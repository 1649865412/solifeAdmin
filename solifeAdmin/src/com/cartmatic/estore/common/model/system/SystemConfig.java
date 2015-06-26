package com.cartmatic.estore.common.model.system;

import com.cartmatic.estore.common.model.system.base.SystemConfigTbl;

/**
 * Model class for SystemConfig. Add not database mapped fileds in this class.
 */
public class SystemConfig extends SystemConfigTbl implements Auditable,
com.cartmatic.estore.core.model.SystemConfig {
	private String	oldValue;
  	/**
	 * Default Empty Constructor for class SystemConfig
	 */
	public SystemConfig () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； systemConfigName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSystemConfigName () {
		if (systemConfigId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.configKey;
	}
	
	/**
	 * Default Key Fields Constructor for class SystemConfig
	 */
	public SystemConfig (
		 Integer in_systemConfigId
		) {
		super (
		  in_systemConfigId
		);
	}

	@Override
	public AuditInfo getAuditInfo(String methodName) {
		AuditInfo auditInfo = new AuditInfo();
		// Optional, Set entity type/name, default is null. $[...] is a i18n
		// mark.
		auditInfo.setEntityType("$[menu.viewSystemConfigs]");
		// Optional, Set the partial url after ?, so can form the full url which
		// can be clicked by admin. Leading & or ?
		// is not required.
		auditInfo.setSupplementUrl("");
		// Optional, but recommended, Set detailed entity info to be audit,
		// default is the entity.toString().
		StringBuilder sb = new StringBuilder();
		if (oldValue != null) {
			sb.append("{change [").append(getConfigKey()).append("] from [")
					.append(oldValue).append("] to [").append(configValue)
					.append("]}");
		} else {
			sb.append("{change [").append(getConfigKey()).append(
					"] settings only.}");
		}
		auditInfo.setEntityAuditInfo(sb.toString());
		// Optional, Add one methodName - actionName mapping, can add multiple
		// times. default is methodName.
		auditInfo.addActionNameMappings("saveSystemConfig", "Update");
		return auditInfo;
	}
	
	public boolean getIsValueChanged() {
		return oldValue != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.common.model.system.base.SystemConfigTbl#setConfigValue(java.lang.String)
	 */
	@Override
	public void setConfigValue(String aValue) {
		if (aValue != null && !aValue.equals(configValue)) {
			oldValue = configValue;
			super.setConfigValue(aValue);
		}
	}
}