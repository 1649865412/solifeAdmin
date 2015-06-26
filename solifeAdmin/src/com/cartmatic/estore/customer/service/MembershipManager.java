package com.cartmatic.estore.customer.service;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Membership, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface MembershipManager extends GenericManager<Membership> {
	/**
	 * 检查当前会员级别是否存在
	 * @param membership
	 * @return
	 */
	public boolean isExistLevel(Membership membership);
	
	
	/**
	 * 检查会员升级的积分是否已经存在
	 * @param membership
	 * @return
	 */
	public boolean isExistUpgradePoint(Membership membership);
	
	public boolean isBigToFront(Membership membership);
	
	/**
	 *倒序获取会员等级的membershihp
	 *从高->低.... 
	 * 
	 */
	public List<Membership> getMembershipsOrderLevel(Membership membership);
	
	/**
	 * 获取默认会员等级
	 * @param membershipLevel
	 * @return
	 */
	public Membership getBaseMembership();
	
	/**
	 * 根据会员名获取会员等级
	 * @param name
	 * @return
	 */
	public List<Membership> getMembershipByName(String name);
	
	/**
	 * 获取匿名会员等级
	 * @return
	 */
	public Membership getAnonymousMembership();
	
	
	/**
	 * 获取会员级别，按升级点数倒序排列
	 * @return
	 */
	public List<Membership> getAllMembershipsOrderByUpgradeShopPointDesc();
	
    /**
     * 获取所有激活的会员等级
     * @return
     */
    public List<Membership> getAllActiveMemberships();
    
    /**
     * 根据level查询会员级别
     * @param membershipLevel
     * @return
     */
	public Membership getMembershipByLevel(Integer membershipLevel) ;
    
}
