package com.cartmatic.estore.customer.service.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.dao.MembershipDao;
import com.cartmatic.estore.customer.service.MembershipManager;


/**
 * Manager implementation for Membership, responsible for business processing, and communicate between web and persistence layer.
 */
public class MembershipManagerImpl extends GenericManagerImpl<Membership> implements MembershipManager {

	private MembershipDao membershipDao = null;

	/**
	 * @param membershipDao
	 *            the membershipDao to set
	 */
	public void setMembershipDao(MembershipDao membershipDao) {
		this.membershipDao = membershipDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = membershipDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Membership entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Membership entity) {

	}

	public boolean isExistLevel(Membership membership) {
		List<Membership>membershipList=findByProperty("membershipLevel", membership.getMembershipLevel());
		if(membershipList.size()==0){
			return false; 
		}else if((membership.getMembershipId()==null&&membershipList.size()>0)||membershipList.size()>1){
			return true;
		}else{
			return membership.getMembershipId().intValue()!=membershipList.get(0).getMembershipId().intValue();
		}
	}

	public boolean isExistUpgradePoint(Membership membership) {
		List<Membership>membershipList=findByProperty("upgradeShopPoint", membership.getMembershipLevel());
		if(membershipList.size()==0){
			return false;
		}else if((membership.getMembershipId()==null&&membershipList.size()>0)||membershipList.size()>1){
			return true;
		}else{
			return membership.getMembershipId().intValue()!=membershipList.get(0).getMembershipId().intValue();
		}
	}

	public boolean isBigToFront(Membership membership){
		List<Membership> memberships = membershipDao.getMembershipsOrderLevel(membership);
		//如果只有0条记录满足,就不用比较了,直接返回true;
		if(memberships.size()==0){
			return true;
		}
		if(membership.getUpgradeShopPoint()!=null && membership.getUpgradeShopPoint()==-1){
			return true;
		}
		for(int i=0;i<memberships.size();i++){
			Membership _membership=memberships.get(i);
			if(_membership.getUpgradeShopPoint()==null){
				return true;
			}
			if(membership.getMembershipLevel()>_membership.getMembershipLevel()){
				if(i==0){
					if(membership.getUpgradeShopPoint()>_membership.getUpgradeShopPoint())
						return true;					
				}else{
					Membership premembership=memberships.get(i-1);
					if(membership.getUpgradeShopPoint()>_membership.getUpgradeShopPoint() && 
							membership.getUpgradeShopPoint()<premembership.getUpgradeShopPoint())
						return true;
				}
				break;
			}
		}
		/**如果在修改状态下所有满足的记录的会员等级和升级积分都比修改的记录大的状态,此时要保证这种状态,只需比较最后一条
		 **如:修改状态下只有1-n条记录9->900,8->800,7->700  当前修改记录4->400
		*/
		if(membership.getMembershipId()!=null){
			Membership _membership=memberships.get(memberships.size()-1);
			if(_membership.getUpgradeShopPoint()==null){
				return true;
			}
			if(membership.getMembershipLevel()<_membership.getMembershipLevel() &&
					membership.getUpgradeShopPoint() < _membership.getUpgradeShopPoint())
			{
				return true;
			}
		}
		return false;
	}

	public List<Membership> getMembershipsOrderLevel(Membership membership) {
		return membershipDao.getMembershipsOrderLevel(membership);
	}

	public Membership getMembershipByLevel(Integer membershipLevel) {
		return membershipDao.findUniqueByProperty("membershipLevel", membershipLevel);
	}
	
	public Membership getBaseMembership(){ 
		return getMembershipByLevel(CustomerConstants.MEMBERSHIP_LEVEL_BASE);
	}

	public List<Membership> getMembershipByName(String membershipName) {
		List<Membership> membershipList=findByProperty("membershipName", membershipName);
		return membershipList;
	}

	public Membership getAnonymousMembership() {
		return getMembershipByLevel(CustomerConstants.MEMBERSHIP_LEVEL_ANONYMOUS);
	}

	public List<Membership> getAllMembershipsOrderByUpgradeShopPointDesc() {
		return membershipDao.getAllOrdered("upgradeShopPoint", false);
	}

	public List<Membership> getAllActiveMemberships() {
		return membershipDao.getAllByStatus(Constants.STATUS_ACTIVE);
	}
	
}
