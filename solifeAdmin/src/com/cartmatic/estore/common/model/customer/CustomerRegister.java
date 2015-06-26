package com.cartmatic.estore.common.model.customer;
/**
 * 
 * @author chenshangxuan
 *
 * the vo for the customer to register 
 */
public class CustomerRegister {

    private String username="";
    private String password="";
    private String rePassword="";
    
    private String email="";
    private String reEmail="";
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getReEmail() {
        return reEmail;
    }
    public void setReEmail(String reEmail) {
        this.reEmail = reEmail;
    }
    public String getRePassword() {
        return rePassword;
    }
    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
