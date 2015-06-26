
package com.cartmatic.estore.core.security;

import org.springframework.security.core.GrantedAuthority;



public interface ResourceDetails {
	/**
	 * 
	 * @return
	 */
	public GrantedAuthority[] getAuthorities();

	/**
	 * resource string such as url.
	 * 
	 * @return
	 */
	public String getResString();

	/**
	 * 
	 * @return
	 */
	public Short getResType();
}