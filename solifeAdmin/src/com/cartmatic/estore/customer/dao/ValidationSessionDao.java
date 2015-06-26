package com.cartmatic.estore.customer.dao;

import com.cartmatic.estore.common.model.customer.ValidationSession;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for ValidationSession.
 */
public interface ValidationSessionDao extends GenericDao<ValidationSession> {
	 /**
     * get by url and type
     * @param url
     * @param type
     * @return
     */
    public ValidationSession getByUrlType(String url,Short type);
    
    /**
     * check the url is still valid now by email and current datetime.
     * @param email
     * @param url
     * @param type
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