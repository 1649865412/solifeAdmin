package com.cartmatic.estore.common.model.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.cartmatic.estore.common.model.system.base.AppRoleTbl;

/**
 * Model class for AppRole. Add not database mapped fileds in this class.
 */
public class AppRole extends AppRoleTbl implements GrantedAuthority{
	private List<AppAdmin> thisAppAdminList;

	public List<AppAdmin> getThisAppAdminList() {
		return thisAppAdminList;
	}

	public void setThisAppAdminList(List<AppAdmin> thisAppAdminList) {
		this.thisAppAdminList = thisAppAdminList;
	}

	public static String ORDER_CANCEL_ROLE="订单取消员";
	
  	/**
	 * Default Empty Constructor for class AppRole
	 */
	public AppRole () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； appRoleName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getAppRoleName () {
		if (appRoleId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.roleName;
	}
	
	/**
	 * Default Key Fields Constructor for class AppRole
	 */
	public AppRole (
		 Integer in_appRoleId
		) {
		super (
		  in_appRoleId
		);
	}

	@Override
	public String getAuthority() {
		return getRoleName();
	}

	public Set<AppResource> getAppResources()
    {
    	Set<RoleRes> rs = this.getRoleRess();
    	Set<AppResource> result = new HashSet<AppResource>();
    	if (rs != null)
    	{
    		for (RoleRes roleRes : rs)
    		{
    			result.add(roleRes.getAppResource());
    		}
    	}
    	return result;
    }
    
    public Set<String> getResources()
    {
    	Set<RoleRes> rs = this.getRoleRess();
    	Set<String> result = new HashSet<String>();
    	if (rs != null)
    	{
    		for (RoleRes roleRes : rs)
    		{
    			result.add(roleRes.getAppResource().getResourceString());
    		}
    	}
    	return result;
    }
}
