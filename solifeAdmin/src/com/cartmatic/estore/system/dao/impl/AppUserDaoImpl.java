package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.system.dao.AppUserDao;

/**
 * Dao implementation for AppUser.
*/
public class AppUserDaoImpl extends HibernateGenericDaoImpl<AppUser> implements AppUserDao,UserDetailsService {
	
	 public boolean isCustomerExist(String username) {
	      List list = findByHql(" from AppUser where username=? and userType=0", username);
	      return list.size() > 0;
	 }
	    
	 public boolean isAdminExist(String username) {
	      List list = findByHql(" from AppUser where username=? and userType=1", username);
	      return list.size() > 0;
	 }
	 
	
	
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
		try {
			return (UserDetails) getHibernateTemplate().executeWithNativeSession(new HibernateCallback<AppUser>() {
				public AppUser doInHibernate(Session session) {
					String hql="";
                	
                	if(username.indexOf("@")!=-1){
                		hql="from AppUser where email=?";
                	}else{
                		hql="from AppUser where username=?";
                		Short userType=AppUser.USER_TYPE_ADMIN;
                		if (ContextUtil.isStoreFront())
                		{
                			userType = AppUser.USER_TYPE_CUSTOMER;
                		}
                    	hql+=" and userType="+userType;
                	}                        	
                    Query queryObject = session.createQuery(hql);
                    SessionFactoryUtils.applyTransactionTimeout(queryObject, session.getSessionFactory());
                    queryObject.setParameter(0, username);

                    AppUser user = (AppUser) queryObject.uniqueResult();
                    if (user != null) {
                        Hibernate.initialize(user.getUserRoles());
                    }
                    return user;
				}
			});
		} catch (Exception e) {
			throw new UsernameNotFoundException("user '" + username + "' not found...", e);
		}
		// try {
		// HibernateTemplate tmpl = getHibernateTemplate();
		// tmpl.setFlushMode();
		// AppUser user = getUserByName(username);
		// initialize(user.getUserRoles());
		// return user;
		// } catch (ObjectRetrievalFailureException e) {
		// throw new UsernameNotFoundException("user '" + username
		// + "' not found...");
		// }
	}

	

	/* 
	 * TODO 本方法目前只是前台登录时使用，应移到customerDao
	 */
	public AppUser getUserByName(String username) {
    	//boolean isFrontStore=ContextUtil.isStoreFront();
        String hql="";
    	if(username.indexOf("@")!=-1){
        	hql="from AppUser where email=?";
        }else{
        	hql="from AppUser where username=?";
        }
    	Short userType=AppUser.USER_TYPE_ADMIN;
		if (ContextUtil.isStoreFront())
		{
			userType = AppUser.USER_TYPE_CUSTOMER;
		}
    	//Short userType=isFrontStore?AppUser.USER_TYPE_CUSTOMER:AppUser.USER_TYPE_ADMIN;
    	hql+=" and userType="+userType;
    	AppUser user = (AppUser) findUnique(hql,username);

        return user;
	}

	public List<AppUser> getAllAppAdmin() {
		String hql="from AppUser au where au.deleted!=1 and au.userType = 1 and au.appuserId>0";
		return findByHql(hql);
	}
	
}
