/**
 * 
 */

package com.cartmatic.estore.core.event;

/**
 * @author Ryan
 * 
 */
public class ConfigChangedEvent extends ApplicationEvent {

	private static final long	serialVersionUID	= 1L;

	private String				configKey;

	private String				newConfigValue;

	/**
	 * @param source
	 */
	public ConfigChangedEvent(String configKey, String newConfigValue) {
		super(configKey);
		this.configKey = configKey;
		this.newConfigValue = newConfigValue;
	}

	public String getConfigKey() {
		return configKey;
	}

	/**
	 * @return the newConfigValue
	 */
	public String getNewConfigValue() {
		return this.newConfigValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#getSource()
	 */
	@Override
	public Object getSource() {
		// TODO Auto-generated method stub
		return super.getSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		return getConfigKey() + ":" + getNewConfigValue();
	}
}
