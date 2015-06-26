package com.cartmatic.estore.customer.dao.impl;

import java.util.Date;

import com.cartmatic.estore.common.model.customer.ValidationSession;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.ValidationSessionDao;

/**
 * Dao implementation for ValidationSession.
*/
public class ValidationSessionDaoImpl extends HibernateGenericDaoImpl<ValidationSession> implements ValidationSessionDao {

	public ValidationSession getByUrlType(String url, Short type) {
		String hql="from ValidationSession vo where vo.url=? and vo.vsType=? and vo.expiredDate>?";
		ValidationSession validationSession=(ValidationSession)findUnique(hql, new Object[]{url,type,new Date()});
        return validationSession;
	}

	public boolean isUrlValid(String email, String url, Short type) {
		String hql="select count(*) from ValidationSession vo where vo.expiredDate>? and vo.email=? and vo.url=? and vo.vsType=?";
        Date date=new Date();
        Object []params=new Object[]{date,email,url,type};
        Long value = (Long) findUnique(hql, params);
        
        if(value.intValue() > 0)
            return true;
        else
            return false;
	}

	public boolean isUrlValid(String url, Short type) {
		String hql = "select count(*) from ValidationSession vo where vo.expiredDate>? and vo.url=? and vo.vsType=?";
		Date date = new Date();
		Object[] params = new Object[] { date, url, type };
		Long value = (Long) findUnique(hql, params);
		logger.debug("isUrlValid: value=====>" + value);
		if (value.intValue() > 0)
			return true;
		else
			return false;
	}

}
