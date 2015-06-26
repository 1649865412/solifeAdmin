
package com.cartmatic.estore.core.security;

import org.springframework.security.core.GrantedAuthority;


public class ResourceMapping {
	// roles
	private GrantedAuthority[] recipients	= null;
	// url path
	private String		resourcePath;
	private String    permission;
	
	public GrantedAuthority[] getRecipients() {
		return recipients;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setRecipients(GrantedAuthority[] recipients) {
		this.recipients = recipients;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

    public String getPermission()
    {
        return permission;
    }

    public void setPermission(String permission)
    {
        this.permission = permission;
    }
	
}