/*
 * Created on Jan 16, 2008
 * 
 */

package com.cartmatic.estore.core.model;

public interface SystemConfig {
	public String getCategory();

	public String getConfigKey();

	public Short getConfigType();

	public String getConfigValue();

	public Short getDataType();

	public Short getIsSystem();

	public String getOptions();

	public void setCategory(String category);

	public void setConfigKey(String configKey);

	public void setConfigType(Short configType);

	public void setConfigValue(String configValue);

	public void setDataType(Short dataType);

	public void setIsSystem(Short isSystem);

	public void setOptions(String options);

}
