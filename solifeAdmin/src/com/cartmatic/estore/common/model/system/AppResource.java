package com.cartmatic.estore.common.model.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;



import com.cartmatic.estore.common.model.system.base.AppResourceTbl;
import com.cartmatic.estore.core.security.ResourceDetails;

/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class AppResource extends AppResourceTbl implements ResourceDetails{

  	/**
	 * Default Empty Constructor for class Resource
	 */
	public AppResource () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Resource
	 */
	public AppResource (
		 Integer in_appResourceId
		) {
		super (
		  in_appResourceId
		);
	}
	
	
	/**
	 * implement the resourcedetail interface.
	 */
	public GrantedAuthority[] getAuthorities() {
		Set<RoleRes> roleRes = this.getRoleRess();
		List<GrantedAuthority> roles = new ArrayList();
		if (roleRes !=  null)
		for (RoleRes i : roleRes)
		{
			roles.add(i.getAppRole());
		}
		return roles.toArray(new GrantedAuthority[roles.size()]);
		//return (GrantedAuthority[]) appRoles.toArray(new GrantedAuthority[appRoles.size()]);
	}
	
	/**
	 * 得到本资源所属的所有角色
	 * @return
	 */
	public String[] getRoleNames()
	{
		Set<RoleRes> roleRes = this.getRoleRess();
		List<String> roles = new ArrayList();
		if (roleRes !=  null)
		for (RoleRes i : roleRes)
		{
			roles.add(i.getAppRole().getRoleName());
		}
		return roles.toArray(new String[roles.size()]);
	}
	/* implement the resourcedetail interface.
	 * 
	 */
	public String getResString() {
		return getResourceString();
	}
	
	/**
	 * implement the resourcedetail interface.
	 */
	public Short getResType() {
		return getResourceType();
	}
}
