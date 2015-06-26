package com.cartmatic.estore.common.model.system;

import java.io.Serializable;

/**
 * 
 * @author chenshangxuan
 *
 * for the  user to modify password
 */
public class ModifyPasswordModel implements Serializable {
    private Integer appuserId;
    private String oldPassword="";
    private String newPassword="";
    private String confirmPassword="";
    
    public Integer getAppuserId() {
        return appuserId;
    }
    public void setAppuserId(Integer appuserId) {
        this.appuserId = appuserId;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
   
}
