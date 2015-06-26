/**
 * 
 */

package com.cartmatic.estore.core.event;

/**
 * @author Ryan
 * 
 */
public class RefreshContextEvent extends ApplicationEvent {

	//public final static String	CATEGORY_MENU				= "MENU";
	public final static String	CATEGORY_SERVER_SIDE_CACHE	= "SERVER_SIDE_CACHE";

	/**
	 * 
	 */
	private static final long	serialVersionUID			= -4576150062910890288L;

	private String				refreshCategory;

	/**
	 * @param source
	 */
	public RefreshContextEvent(String category) {
		super(category);
		this.refreshCategory = category;
	}

	/**
	 * @return the refreshCategory
	 */
	public String getRefreshCategory() {
		return this.refreshCategory;
	}

}
