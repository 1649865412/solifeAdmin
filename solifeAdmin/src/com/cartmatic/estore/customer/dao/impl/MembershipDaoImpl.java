package com.cartmatic.estore.customer.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.customer.dao.MembershipDao;

/**
 * Dao implementation for Membership.
*/
public class MembershipDaoImpl extends HibernateGenericDaoImpl<Membership> implements MembershipDao {
	
	/**
	 *倒序获取会员等级的membershihp
	 *从高->低.... 
	 * 
	 */
	public List<Membership> getMembershipsOrderLevel(Membership membership) {
		String queryString="";
		List<Membership> memberships = null;
		if(membership.getMembershipId()!=null){
			queryString="from com.cartmatic.estore.common.model.customer.Membership mb where mb.deleted=0 and mb.membershipLevel!=-2 and mb.membershipId!=? order by mb.membershipLevel desc";
			memberships = findByHql(queryString,membership.getMembershipId());
		}else{
			queryString="from com.cartmatic.estore.common.model.customer.Membership mb where mb.deleted=0 and mb.membershipLevel!=-2 order by mb.membershipLevel desc";
			memberships = findByHql(queryString);
		}
		return memberships;
	}
	public List<Membership> getAllByStatus(Short status) {
	    String hql="from com.cartmatic.estore.common.model.customer.Membership mb where mb.status=? and mb.deleted=0 and mb.membershipLevel>0 order by mb.membershipLevel asc";
        return findByHql(hql, status);
    }
	
}
