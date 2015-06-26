package com.cartmatic.estore.customer.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.dao.CustomerDao;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.MembershipManager;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.customer.service.ShopPointManager;
import com.cartmatic.estore.customer.service.WishlistManager;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.system.payment.alipay.Md5Encrypt;
import com.cartmatic.estore.system.service.StoreManager;


/**
 * Manager implementation for Customer, responsible for business processing, and communicate between web and persistence layer.
 */
public class CustomerManagerImpl extends GenericManagerImpl<Customer> implements CustomerManager {

	private CustomerDao customerDao = null;
	
	private MembershipManager membershipManager=null;
	
	private ShopPointHistoryManager shopPointHistoryManager=null;
	
	private WishlistManager wishlistManager=null;
	
	private AttributeService attributeService=null;
	
	private ShopPointManager shopPointManager=null;
	
	private MailEngine mailEngine=null;

	private PasswordEncoder passwordEncoder;
	
	private StoreManager storeManager=null;
	
	private CouponManager couponManager = null;
	
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	/**
	 * @param customerDao
	 *            the customerDao to set
	 */
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setMembershipManager(MembershipManager membershipManager) {
		this.membershipManager = membershipManager;
	}

	public void setShopPointHistoryManager(ShopPointHistoryManager shopPointHistoryManager) {
		this.shopPointHistoryManager = shopPointHistoryManager;
	}

	public void setWishlistManager(WishlistManager wishlistManager) {
		this.wishlistManager = wishlistManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setShopPointManager(ShopPointManager shopPointManager) {
		this.shopPointManager = shopPointManager;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public void setPasswordEncoder(PasswordEncoder avalue)
    {
    	passwordEncoder = avalue;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = customerDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(Customer entity) {
		
	}
	
	@Override
	protected void delete(Customer entity) {
		long uuid= UUID.randomUUID().getLeastSignificantBits();
        uuid=Math.abs(uuid);
		entity.setEmail(uuid+"");
		entity.setUsername(uuid+"");
		entity.setDeleted(Constants.FLAG_TRUE);
		this.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(Customer entity) {

	}

	
	public void saveCustomer(Customer customer, List<AttributeValue> customerAddreibuteValueList) {
		boolean isNewCustomer=customer.getAppuserId() == null;
		if(StringUtils.isNotBlank(customer.getNewPassword())){
			customer.setPassword(passwordEncoder.encodePassword(customer.getNewPassword(), null));
		}
		if (isNewCustomer) {
			//设置基本属性
			customer.setRegisterTime(new Date());
			customer.setUserType(AppUser.USER_TYPE_CUSTOMER);
			customer.setIsLocked(AppUser.CONST_UNLOCKED);
			customer.setDeleted(Constants.MARK_NOT_DELETED);
			if (customer.getMembershipId() == null) {// add default	// membership id.
				Membership membership = membershipManager.getBaseMembership();
				if (membership != null)
					customer.setMembershipId(membership.getMembershipId());
			}
			save(customer);
			shopPointHistoryManager.saveNewShopPointHistory(CustomerConstants.ShopPoint_Type_Registration, customer);
			wishlistManager.addDefaultWishlist(customer);
		}else{
			save(customer);
		}
		
    	//保存自定义属性值
		if (customerAddreibuteValueList != null)
			attributeService.saveOrUpdateAttributeValue(customerAddreibuteValueList, customer);
	}

	/*private void copyCustomerField(Customer source, Customer dest) {
		dest.setTitle(source.getTitle());
        dest.setFirstname(source.getFirstname());
        dest.setLastname(source.getLastname());
        dest.setEmail(source.getEmail());
        
        dest.setTelephone(source.getTelephone());
        dest.setFax(source.getFax());
        dest.setZip(source.getZip());
        
        dest.setPasswordHint(source.getPasswordHint());
        dest.setPasswordHintAnswer(source.getPasswordHintAnswer());
        dest.setCustomerPosition(source.getCustomerPosition());
        
        dest.setMembershipId(source.getMembershipId());
        dest.setNote(source.getNote());
	}*/

	

	public void upgradeMembershipLevelJob() {

		PagingBean pagingBean = new PagingBean();
		
		
		int currentPage = 1;
		int itemsPerPage = 20;
		
		pagingBean.setItemsPerPage(itemsPerPage);
		List<ShopPoint> shopPointList = new ArrayList<ShopPoint>();
		List<Membership> memberShipList = new ArrayList<Membership>();
		memberShipList = membershipManager.getAllMembershipsOrderByUpgradeShopPointDesc();
		
		do {
			pagingBean.setCurrentPage(currentPage);
			shopPointList = shopPointManager.findShopPointListOderbyGainedTotalDesc(pagingBean);
			
			for (ShopPoint shopPoint : shopPointList) {
				Customer customer = shopPoint.getCustomer();
				int gainedTotal = shopPoint.getGainedTotal();
				
				Membership perMembership = membershipManager.getById(customer.getMembershipId());
				
				if (memberShipList != null && memberShipList.size() > 0) {
					for (Membership memberShip : memberShipList) {
						int upgradeShopPoint = memberShip.getUpgradeShopPoint();
						// 向高一级别等级升级、如果是匿名顾客的话就不用比较了.或者如果shopPoint为-1表示不支持自动升级
						if (memberShip.getUpgradeShopPoint().compareTo(perMembership.getUpgradeShopPoint())<=0 
								|| memberShip.getMembershipLevel().intValue() == -2
								|| upgradeShopPoint == -1) {
							continue;
						}
						// 会员等级不等于之前的会员等级(相同的会员等级就不需要升级了)
						if (customer.getMembershipId().intValue() == memberShip
								.getMembershipId().intValue()) {
							break;
						}

						// 如果获得总积分>=升级积分,获取MembershipId更新客户的会员等级.
						if (gainedTotal >= upgradeShopPoint) {
							customer.setMembershipId(memberShip
									.getMembershipId());
							dao.save(customer);

							Map<String, Object> map = new HashMap<String, Object>();
							map.put("upgradeShopPoint", upgradeShopPoint);
							map.put("perMembershipName", perMembership
									.getMembershipName());
							map.put("aftMembershipName", memberShip
									.getMembershipName());
							map.put("userName", customer.getFullName());
							map.put("newMembership", memberShip);
							mailEngine.sendSimpleTemplateMail(
									"/customer/upgradeMembershipLevel.vm".intern(), map,
									null, null, customer.getEmail());
							break;
						}
					}
				}

			}
			this.dao.flush();
			currentPage++;
		} while (itemsPerPage == shopPointList.size());
	}

	public Long getCustomerCountsByMembershipId(Integer membershipId) {
		return customerDao.getCustomerCountsByMembershipId(membershipId);
	}

	public String getVirtualLoginUrl(Integer customerId,Integer storeId) {
		Customer customer=customerDao.getById(new Integer(customerId));
		String url="";
		if(customer!=null){
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			long time=calendar.getTimeInMillis();
			String sid=customer.getPassword()+time;
			sid=Md5Encrypt.md5(sid);
			sid=customerId+"_"+sid;
			
			Store store=storeManager.getById(storeId);
//			store=customer.getStore();
			
			url=store.getSiteUrl()+"/virtualLogin.html?sid="+sid;
		}
		return url;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		return customerDao.getCustomerByEmail(email);
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		return customerDao.getCustomerByUsername(username);
	}

	@Override
	public void addCoupon(Integer couponTypeId, Integer customerId) {
		// TODO Auto-generated method stub
		List<Coupon> couponList = couponManager.createCoupons(couponTypeId, 1, 1, null, 1);
		if(couponList.size() > 0){
			Customer customer  = this.customerDao.getById(customerId);
			customer.setUserCoupons(new HashSet<Coupon>(couponList));
			Coupon coupon = couponList.get(0);
			coupon.setIsSent((short)1);
			coupon.setSentEmail(customer.getEmail());
			this.couponManager.save(coupon);
			this.customerDao.save(customer);
		}
	}

	public CouponManager getCouponManager() {
		return couponManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

}
