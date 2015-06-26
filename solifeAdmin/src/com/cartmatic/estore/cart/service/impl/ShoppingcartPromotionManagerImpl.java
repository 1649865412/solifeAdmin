package com.cartmatic.estore.cart.service.impl;

import com.cartmatic.estore.cart.dao.ShoppingcartPromotionDao;
import com.cartmatic.estore.cart.service.ShoppingcartPromotionManager;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;


/**
 * Manager implementation for ShoppingcartPromotion, responsible for business processing, and communicate between web and persistence layer.
 */
public class ShoppingcartPromotionManagerImpl extends GenericManagerImpl<ShoppingcartPromotion> implements ShoppingcartPromotionManager {

	private ShoppingcartPromotionDao shoppingcartPromotionDao ;
	
	@Override
	protected void initManager() {
		// TODO Auto-generated method stub
		this.dao = this.shoppingcartPromotionDao;
	}

	@Override
	protected void onDelete(ShoppingcartPromotion entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSave(ShoppingcartPromotion entity) {
		// TODO Auto-generated method stub
		
	}

	public ShoppingcartPromotionDao getShoppingcartPromotionDao() {
		return shoppingcartPromotionDao;
	}

	public void setShoppingcartPromotionDao(ShoppingcartPromotionDao shoppingcartPromotionDao) {
		this.shoppingcartPromotionDao = shoppingcartPromotionDao;
	}
}
