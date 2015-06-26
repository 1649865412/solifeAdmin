package com.cartmatic.estore.common.model.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.base.AppUserTbl;
import com.cartmatic.estore.core.model.LabelValue;

/**
 * Model class for AppUser. Add not database mapped fileds in this class.
 */
public class AppUser extends AppUserTbl implements UserDetails,com.cartmatic.estore.core.model.AppUser {
	//public static final Short	CONST_DELETED		= new Short("1");
	// lock status
	public static final Short	CONST_LOCKED		= new Short("1");

	// deleted status
	//public static final Short	CONST_UNDELETED		= new Short("0");
	public static final Short	CONST_UNLOCKED		= new Short("0");

	public static final String	CUSTOMER_ROLE		= "customer";
	/* add below tow feilds to validate change password form */
	public static final String	UNGRANTED_ROLE		= "UNGRANTED_ROLE";
	public static final Short	USER_TYPE_ADMIN		= new Short((short) 1);

	// user type
	public static final Short	USER_TYPE_CUSTOMER	= new Short((short) 0);
	public static final Short	USER_TYPE_PARTNER	= new Short((short) 2);

	private String				confirmPassword		= null;

	private String				newPassword			= null;

  	/**
	 * Default Empty Constructor for class AppUser
	 */
	public AppUser () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； appUserName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAppUserName () {
		if (appuserId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.email;
	}
	
	/**
	 * Default Key Fields Constructor for class AppUser
	 */
	public AppUser (
		 Integer in_appuserId
		) {
		super (
		  in_appuserId
		);
	}
	
	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 */
	public void addRole(AppRole role) {
		getUserRoles().add(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		// if has no role, then set a ungrantedRole to current user.
		if (getUserType().intValue() == 0) {// customer
			result.add(new GrantedAuthorityImpl(AppUser.CUSTOMER_ROLE));
			return result;
		}
		if (userRoles.size() == 0) {
			result.add(new GrantedAuthorityImpl(AppUser.UNGRANTED_ROLE));
			return result;
		}
		//result.add(new GrantedAuthorityImpl(AppUser.UNGRANTED_ROLE));
		return userRoles;
		
		//return (GrantedAuthority[]) userRoles
		//		.toArray(new GrantedAuthority[userRoles.size()]);
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * Returns the full name.
	 */
	public String getFullName() {
		if (firstname == null && lastname == null) {
			return username;
		} else {
			return (firstname == null ? "" : firstname) +  (lastname == null ? "" : " "+lastname);
		}
	}

	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Convert user roles to LabelValue objects for convenience.
	 */
	public List<LabelValue> getRoleList() {
		List<LabelValue> roles = new ArrayList<LabelValue>();

		if (this.userRoles != null) {
			for (Iterator it = userRoles.iterator(); it.hasNext();) {
				AppRole role = (AppRole) it.next();

				// convert the user's roles to LabelValue Objects
				roles
						.add(new LabelValue(role.getRoleName(), role
								.getRoleName()));
			}
		}

		return roles;
	}

	public String getRoleString() {
		if (userRoles.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer("");
		Iterator it = userRoles.iterator();

		if (it.hasNext()) {
			sb.append(((AppRole) it.next()).getRoleName());
		}
		while (it.hasNext()) {
			sb.append(",").append(((AppRole) it.next()).getRoleName());
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return isAccountNonLocked();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return !CONST_LOCKED.equals(isLocked);
	}

	/**
	 * check the current user contain the admin role
	 * 
	 * @return
	 */
	public boolean isContainAdminRole() {
		return isContainRole("admin");
	}

	public boolean isContainRole(String role) {
		GrantedAuthority[] grantedAuthorities = (GrantedAuthority[]) userRoles
				.toArray(new GrantedAuthority[userRoles.size()]);
		boolean isContain = false;
		for (int i = 0; i < grantedAuthorities.length; i++) {
			if (role.equals(grantedAuthorities[i].getAuthority())) {
				isContain = true;
				break;
			}
		}
		return isContain;
	}

	public boolean isContainRoles(String[] roles) {
		boolean isContain = false;
		for (int cnt = 0; cnt < roles.length; cnt++) {
			if (isContainRole(roles[cnt])) {
				isContain = true;
				break;
			}
		}
		return isContain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return isAccountNonLocked();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return Constants.STATUS_ACTIVE.equals(status)
				&& deleted.equals(Constants.MARK_NOT_DELETED);
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
