
package com.cartmatic.estore.core.event;

import java.util.EventObject;

/**
 * Class to be extended by all application events. Abstract as it doesn't make
 * sense for generic events to be published directly.
 * 
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
public abstract class ApplicationEvent extends EventObject {

	/** System time when the event happened */
	private final long	timestamp;

	/**
	 * Create a new ApplicationEvent.
	 * 
	 * @param source
	 *            the component that published the event (never
	 *            <code>null</code>)
	 */
	public ApplicationEvent(Object source) {
		super(source);
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * Return the system time in milliseconds when the event happened.
	 */
	public final long getTimestamp() {
		return timestamp;
	}

}
