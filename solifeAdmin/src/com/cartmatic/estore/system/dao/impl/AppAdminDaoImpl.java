package com.cartmatic.estore.system.dao.impl;

import org.hibernate.Query;

import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.AppAdminDao;


/**
 * AppAdmin Data Access Object (DAO) implementation.
 * Developer introduced interfaces should be declared here. Won't get overwritten.
*/
public class AppAdminDaoImpl extends HibernateGenericDaoImpl<AppAdmin> implements AppAdminDao {
    /**
     * get all and paging.
     * 
     */
//    public List getAppAdminsAllDesc(PagingBean pb) {
//        String hql="from AppAdmin where deleted=0 ";
//        return find(hql,pb);
//    }
    
    public void removeAppAdmin(Integer appAdminId) {
       String hql="update AppUser set deleted=1 where appuserId=:appuserId";
       Query query=getSession().createQuery(hql);
       query.setInteger("appuserId",appAdminId.intValue());
       query.executeUpdate();
       
    }
}