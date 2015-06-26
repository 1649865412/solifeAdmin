package com.cartmatic.estore.customer.service.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.CustomerConstants;
import com.cartmatic.estore.customer.dao.ShopPointHistoryDao;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.customer.service.ShopPointManager;


/**
 * Manager implementation for ShopPointHistory, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShopPointHistoryManagerImpl extends GenericManagerImpl<ShopPointHistory> implements ShopPointHistoryManager {
	private ShopPointManager shopPointManager=null;	
	private ShopPointHistoryDao shopPointHistoryDao = null;

	/**
	 * @param shopPointHistoryDao
	 *            the shopPointHistoryDao to set
	 */
	public void setShopPointHistoryDao(ShopPointHistoryDao shopPointHistoryDao) {
		this.shopPointHistoryDao = shopPointHistoryDao;
	}

	public void setShopPointManager(ShopPointManager shopPointManager) {
		this.shopPointManager = shopPointManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shopPointHistoryDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ShopPointHistory entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ShopPointHistory entity) {

	}

	public void saveNewShopPointHistory(Short shopPointType, Customer customer) {
		// TODO Auto-generated method stub
		saveNewShopPointHistory(shopPointType, customer, null,null);
	}

	private void saveNewShopPointHistory(Short shopPointType,Customer customer,Integer shopPointTotal,String description){
		
		ShopPointHistory sph=new ShopPointHistory();
		sph.setCustomer(customer);
		sph.setShopPointType(shopPointType);
		sph.setDescription(description);
		int total=0;		
		if(CustomerConstants.ShopPoint_Type_Registration.equals(shopPointType)){			
			total = ConfigUtil.getInstance().getRegistrationShopPointAmount();			
			sph.setAmount(total);			
			if(description==null){
				sph.setDescription("customer registration.");
			}			
		}else if(CustomerConstants.ShopPoint_Type_Login.equals(shopPointType)){			
			total=ConfigUtil.getInstance().getLoginShopPointAmount();			
			sph.setAmount(total);			
			if(description==null){
				sph.setDescription("customer login.");
			}			
		}else if(CustomerConstants.ShopPoint_Type_Shopping.equals(shopPointType)){			
			sph.setAmount(shopPointTotal);			
			if(description==null){
				sph.setDescription("shopping.");
			}			
		}/*else if(Constants.ShopPoint_Type_Feedback.equals(shopPointType)){			
			sph.setAmount(ConfigUtil.getInstance().getFeedbackShopPointAmount());			
			if(description==null){
				sph.setDescription("donate the shop point for useful feedback or suggestion.");
			}			
		}*/else if(CustomerConstants.ShopPoint_Type_ProductComment.equals(shopPointType)){			
			sph.setAmount(shopPointTotal);			
			if(description==null){
				sph.setDescription("product comment.");
			}
		}else if(CustomerConstants.ShopPoint_Type_Buy_Product.equals(shopPointType)){				
			sph.setAmount(shopPointTotal);			
			if(description==null){
				sph.setDescription("buy product by shop point.");
			}			
		}else if(CustomerConstants.ShopPoint_Type_Introduce.equals(shopPointType)){
			//TODO
		}else if (CustomerConstants.ShopPoint_Type_Cancel_Order.equals(shopPointType)){
			sph.setAmount(shopPointTotal);
			if(description==null){
				sph.setDescription("return shop point when cancel order");
			}
		}
		else {//others
			total=shopPointTotal;
			sph.setAmount(total);			
			sph.setDescription("others.");
		}		
		saveShopPointHistoryAndUpdateTotal(sph);
	}
	
	public void saveShopPointHistoryAndUpdateTotal(ShopPointHistory shopPointHistory) {
		if(shopPointHistory.getAmount().intValue() !=0) {
			save(shopPointHistory);
			//update this customer 's shop point
			shopPointManager.saveChangeTotal(shopPointHistory.getCustomer(),shopPointHistory.getAmount());
		}
	}

	public void saveNewShopPointHistory(Short shopPointType, Customer customer, Integer shopPointTotal) {
		saveNewShopPointHistory(shopPointType, customer, shopPointTotal,null);
	}

	public boolean getIsLoginToday(Integer customerId) {
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date startDate=cal.getTime();
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		Date endDate=cal.getTime();
		
		List list=shopPointHistoryDao.getByCustomerIdAndDateAndType(customerId, startDate, endDate,CustomerConstants.ShopPoint_Type_Login);
		
		if(list.size()>0){
			return true;
		}
		return false;
	}

	/*public List<ShopPointHistory> getAllByCustomerId(Integer customerId, PagingBean pb) {
		return shopPointHistoryDao.getAllByCustomerId(customerId, pb);
	}*/
}
