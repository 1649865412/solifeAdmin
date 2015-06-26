package com.cartmatic.estore.customer.service;

import com.cartmatic.estore.common.model.customer.ValidationSession;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for ValidationSession, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface ValidationSessionManager extends GenericManager<ValidationSession> {
	 /**
     * get by url and type
     * @param url
     * @param type
     * @return
     */
    public ValidationSession getByUrlType(String url,Short type);
    
    /**
     * if the email is not exsit in the app_user table ,then return null;
     * else generate a url record for this email's owner to reset his password,and return this new vo;
     * @param email
     * @return
     */
    public ValidationSession doGenerateUrlByEmail(String email,Short type);
    
    
    /**
     * check the url is validat by email, url and current datetime. 
     * @param email
     * @param url
     * @return
     */
    public boolean isUrlValid(String email,String url,Short type);
    
    /**
     * check this url is still validate
     * @param url
     * @param type
     * @return
     */
    public boolean isUrlValid(String url,Short type);
}
