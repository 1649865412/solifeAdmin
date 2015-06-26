package com.cartmatic.estore.common.model.system;

import net.sf.json.JSONObject;

import com.cartmatic.estore.common.model.system.base.AppAdminTbl;

/**
 * Model - Business object
 * This file won't get overwritten.
 */
public class AppAdmin extends AppAdminTbl {

  	/**
	 * Default Empty Constructor for class AppAdmin
	 */
	public AppAdmin () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppAdmin
	 */
	public AppAdmin (
		 Integer in_appAdminId
		) {
		super (
		  in_appAdminId
		);
	}
	
	

	@Override
	public String getAppUserName() {
		if (appuserId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
	        return this.username;
	}

	/**
	 * 构建简单的Json对象主要用于选择器
	 * @return
	 */
	public String getJsonObject(){
		JSONObject jsonSupplier=new JSONObject();
		jsonSupplier.put("appuserId",this.appuserId);
		jsonSupplier.put("username",this.username);
		jsonSupplier.put("email", this.email);
		jsonSupplier.put("firstname", this.firstname);
		jsonSupplier.put("lastname", this.lastname);
		return jsonSupplier.toString();
	}
}
