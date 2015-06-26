package com.cartmatic.estore.customer.service.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.core.model.PagingBean;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.customer.dao.ShopPointDao;
import com.cartmatic.estore.customer.service.ShopPointManager;


/**
 * Manager implementation for ShopPoint, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShopPointManagerImpl extends GenericManagerImpl<ShopPoint> implements ShopPointManager {

	private ShopPointDao shopPointDao = null;

	/**
	 * @param shopPointDao
	 *            the shopPointDao to set
	 */
	public void setShopPointDao(ShopPointDao shopPointDao) {
		this.shopPointDao = shopPointDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = shopPointDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(ShopPoint entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(ShopPoint entity) {

	}

	public void saveChangeTotal(Customer customer, Integer totalPoint) {
		ShopPoint sp=dao.findUniqueByProperty("customer", customer);
		if(sp==null){//if the shop point not found  for current user, new a shop point object.
			sp=new ShopPoint();
			sp.setCustomer(customer);
			sp.setGainedTotal(new Integer(0));
			sp.setTotal(new Integer(0));
			sp.setUsedTotal(new Integer(0));
		}
		sp.setTotal(sp.getTotal().intValue()+totalPoint);
		
		if(totalPoint>0){
			sp.setGainedTotal(sp.getGainedTotal()+totalPoint);
		}else{
			sp.setUsedTotal(sp.getUsedTotal()+(-totalPoint));
		}
		
		sp.setUpdateTime(new Date());
		
		dao.save(sp);
		//TODO 破坏性检查数据
		if(sp.getTotal()<0){
			throw new RuntimeException("数据错误,操作后积分为负数!");
		}
	}

	public ShopPoint getByCustomerId(Integer customerId) {
		return shopPointDao.getByCustomerId(customerId);
	}

	public List<ShopPoint> findShopPointListOderbyGainedTotalDesc(PagingBean pagingBean) {
		return shopPointDao.findShopPointListOderbyGainedTotalDesc(pagingBean);
	}

}
