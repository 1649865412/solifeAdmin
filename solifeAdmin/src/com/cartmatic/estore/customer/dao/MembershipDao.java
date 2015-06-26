package com.cartmatic.estore.customer.dao;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Membership.
 */
public interface MembershipDao extends GenericDao<Membership> {
	/**
	 *倒序获取会员等级的membershihp
	 *从高->低.... 
	 */
	public List<Membership> getMembershipsOrderLevel(Membership membership);
	
	public List<Membership> getAllByStatus(Short status);
	
}