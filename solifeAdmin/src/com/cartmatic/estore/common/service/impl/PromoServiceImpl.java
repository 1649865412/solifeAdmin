
package com.cartmatic.estore.common.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.service.PromoService;
import com.cartmatic.estore.common.util.PromoUtil;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.engine.PRule;
import com.cartmatic.estore.sales.engine.PRuleEngine;
import com.cartmatic.estore.sales.engine.PRuleManager;
import com.cartmatic.estore.sales.model.EmailModel;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.sales.service.PromoRuleManager;
import com.cartmatic.estore.system.service.SystemConfigManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class PromoServiceImpl implements PromoService {
	private static final Log	logger	= LogFactory.getLog(PromoService.class);
//	private static final String	SEPERATOR_BEW_SHIPPINGINFO			= ",";
//	private static final String	SEPERATOR_BEW_LEVEL_AND_DISCOUNT	= "-";
//	private static final String	FLAG_DISCOUNT_PERCENT				= "%";
//	private static final String	FLAG_DISCOUNT_AMOUNT				= "$";
	private PromoRuleManager	promoRuleManager;
	private PRuleManager		pruleManager;
	private CouponManager		couponManager;
	private SystemConfigManager	systemConfigManager;
	//private ShoppingcartItemManager shoppingcartItemManager;

	public void setPromoRuleManager(PromoRuleManager promoRuleManager) {
		this.promoRuleManager = promoRuleManager;
	}

	public void setPruleManager(PRuleManager pruleManager) {
		this.pruleManager = pruleManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public SystemConfigManager getSystemConfigManager() {
		return systemConfigManager;
	}

	public void setSystemConfigManager(SystemConfigManager systemConfigManager) {
		this.systemConfigManager = systemConfigManager;
	}
	
//	public void setShoppingcartItemManager(
//			ShoppingcartItemManager shoppingcartItemManager) {
//		this.shoppingcartItemManager = shoppingcartItemManager;
//	}

	public Shoppingcart appendPromoInfo(Shoppingcart _cart, AppUser _customer) {
		logger.debug("promoService:appendPromoInfo shoppingcart");
		Shoppingcart cart = _cart;
		PRuleEngine engine;
		if (cart.getIsUsedCoupon() == null
				|| cart.getIsUsedCoupon() != Shoppingcart.ISUSECOUPON_YES) {
			engine = new PRuleEngine(pruleManager.getCartPromotionRules());
		} else {
			Coupon coupon = new Coupon();
			coupon.setCouponNo(cart.getUsedCouponNo());
			Vector<PRule> couponPromotionRules = new Vector<PRule>();
			
			
			if (ConfigUtil.getInstance().getIsAllowSystemRulesWhenUseCoupon()) 
			{
				logger.debug("system config-IsAllowSystemRulesWhenUseCoupon = true");
				couponPromotionRules.addAll(pruleManager.getCartPromotionRules());
			}
			else
			{
				logger.debug("system config-IsAllowSystemRulesWhenUseCoupon = false");
			}
			if (checkCoupon(coupon.getCouponNo()) == Coupon.STATE_VALID) {
				couponPromotionRules.addAll(pruleManager
						.getCouponPromotionRules(coupon));
			}
			engine = new PRuleEngine(couponPromotionRules);
		}
		try {
			cart = engine.run(cart, _customer);
			//将coupon信息加到promotion中
			for(ShoppingcartPromotion promo: cart.getShoppingCartPromotions()){
				if(PromoRule.PROMOTION_TYPE_COUPONPROMOTION.equals(promo.getType().trim())){
					promo.setIsUsedCoupon(Constants.FLAG_TRUE);
					promo.setUsedCouponNo(cart.getUsedCouponNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	/**
	 * 添加未关联购物车外的折扣信息
	 * @param _cart
	 * @param _customer
	 * @return
	 */
	public Shoppingcart appendPromoNInCart(Shoppingcart _cart, AppUser _customer){
		logger.debug("promoService:appendPromoInfo shoppingcart");
		Shoppingcart cart = _cart;
		PRuleEngine engine;
		if (cart.getIsUsedCoupon() == null
				|| cart.getIsUsedCoupon() != Shoppingcart.ISUSECOUPON_YES) {
			engine = new PRuleEngine(pruleManager.getPRulesNInCart(cart));
		} else {
			Coupon coupon = new Coupon();
			coupon.setCouponNo(cart.getUsedCouponNo());
			Vector<PRule> couponPromotionRules = new Vector<PRule>();
			
			
			if (ConfigUtil.getInstance().getIsAllowSystemRulesWhenUseCoupon()) 
			{
				logger.debug("system config-IsAllowSystemRulesWhenUseCoupon = true");
				couponPromotionRules.addAll(pruleManager.getPRulesNInCart(cart));
			}
			else
			{
				logger.debug("system config-IsAllowSystemRulesWhenUseCoupon = false");
			}
			if (checkCoupon(coupon.getCouponNo()) == Coupon.STATE_VALID) {
				couponPromotionRules.addAll(pruleManager.getPRuleNInCart(coupon, _cart));
			}
			engine = new PRuleEngine(couponPromotionRules);
		}
		try {
			cart = engine.run(cart, _customer);
			//将coupon信息加到promotion中
			for(ShoppingcartPromotion promo: cart.getShoppingCartPromotions()){
				if(PromoRule.PROMOTION_TYPE_COUPONPROMOTION.equals(promo.getType().trim())){
					promo.setIsUsedCoupon(Constants.FLAG_TRUE);
					promo.setUsedCouponNo(cart.getUsedCouponNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}
	
	

	public ProductSku appendPromoInfo(ProductSku _sku) {
		logger.debug("promoService:appendPromoInfo sku");
		ProductSku sku = _sku;
		PRuleEngine engine = new PRuleEngine(pruleManager
				.getCatalogPromotionRules());
		try {
			sku = engine.run(sku);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sku;
	}
	

	public Collection<ProductSku> appendPromoInfo(Collection<ProductSku> _list) {
		Collection<ProductSku> list = _list;
		PRuleEngine engine = new PRuleEngine(pruleManager.getCatalogPromotionRules());
		try 
		{
			list = engine.run(list);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

//	private Map<Integer, BigDecimal[]> parseShippingDiscountInfo(
//			String _shippingDiscountInfo) {
//		Map<Integer, BigDecimal[]> infos = null;
//		if (_shippingDiscountInfo != null
//				&& !_shippingDiscountInfo.trim().equals("")) {
//			infos = new HashMap<Integer, BigDecimal[]>();
//			String[] items = _shippingDiscountInfo.substring(1).split(
//					SEPERATOR_BEW_SHIPPINGINFO);
//			for (String item : items) {
//				String[] ld = item.split(SEPERATOR_BEW_LEVEL_AND_DISCOUNT);
//				if (infos.containsKey(new Integer(ld[0]))) {
//					BigDecimal[] discounts = infos.get(new Integer(ld[0]));
//					if (ld[1].indexOf(FLAG_DISCOUNT_PERCENT) != -1) {
//						discounts[0] = discounts[0].add(new BigDecimal(ld[1]
//								.substring(1)));
//					} else {
//						discounts[1] = discounts[1].add(new BigDecimal(ld[1]
//								.substring(1)));
//					}
//				} else {
//					BigDecimal[] discounts = new BigDecimal[2];
//					if (ld[1].indexOf(FLAG_DISCOUNT_PERCENT) != -1) {
//						discounts[0] = new BigDecimal(ld[1].substring(1));
//						discounts[1] = new BigDecimal("0");
//					} else {
//						discounts[0] = new BigDecimal("0");
//						discounts[1] = new BigDecimal(ld[1].substring(1));
//					}
//					infos.put(new Integer(ld[0]), discounts);
//				}
//			}
//		}
//		return infos;
//	}

	public BigDecimal getShippingFee(String _shippingDiscountInfo,
			Integer _shippingMethodId, BigDecimal _shippingFee) {
		Map<Integer, BigDecimal[]> infos = PromoUtil.parseShippingDiscountInfo(_shippingDiscountInfo);
		if (null != infos) {
			if (infos.containsKey(_shippingMethodId)) {
				BigDecimal[] discounts = infos.get(_shippingMethodId);
				BigDecimal shippingFeeDiscountAmount = new BigDecimal("0");
				if (discounts[0].compareTo(new BigDecimal("0")) > 0) {
					shippingFeeDiscountAmount = _shippingFee
							.multiply(discounts[0]);
				}
				shippingFeeDiscountAmount = shippingFeeDiscountAmount
						.add(discounts[1]);
				if (shippingFeeDiscountAmount.compareTo(_shippingFee) > 0) {
					return BigDecimal.ZERO;
				} else {
					return _shippingFee.add(shippingFeeDiscountAmount.negate());
				}
			}
		}
		return _shippingFee;

	}
	
	public Coupon QueryCouponByNo(String couponNo){
		Coupon coupon = couponManager.getCouponByNo(couponNo);
		short state = getState(coupon);
		if(Coupon.STATE_INVALID == state){
			coupon = new Coupon();
		}
		coupon.setState(state);
		return coupon;
	}

	public short checkCoupon(String couponNo) {
		Coupon coupon = couponManager.getCouponByNo(couponNo);
		return getState(coupon);
		
	}

	private short getState(Coupon coupon) {
		
		if (null == coupon) {
			return Coupon.STATE_INVALID;
		}
		//Does this coupon belong to current store?
		if (coupon.getPromoRule().getStore() == null ||
				!RequestContext.getCurrentStoreCode().equals(coupon.getPromoRule().getStore().getCode()))
		{
			return Coupon.STATE_INVALID;
		}
		if (coupon.getRemainedTimes().compareTo(0) <= 0) {
			return Coupon.STATE_INVALID_REMAINEDTIMES_IS_ZERO;
		}
		PromoRule couponType = coupon.getPromoRule();
		promoRuleManager.setState(couponType);
		int state = Integer.parseInt(couponType.getState());
		if (PromoRule.STATE_INVALID == state) {
			return Coupon.STATE_INVALID_NOT_ACTIVE;
		} else if (PromoRule.STATE_FUTURE == state) {
			return Coupon.STATE_INVALID_NOT_START;
		} else if (PromoRule.STATE_PAST == state) {
			return Coupon.STATE_INVALID_IS_END;
		}
		return Coupon.STATE_VALID;
	}

	public short useCoupon(String couponNo) {
		logger.info("check coupon " + couponNo + " before use");
		short status = checkCoupon(couponNo);

		if (Coupon.STATE_VALID == status) {
			logger.info("use coupon " + couponNo);
			Coupon coupon = couponManager.getCouponByNo(couponNo);
			coupon.setRemainedTimes(coupon.getRemainedTimes() - 1);
			couponManager.save(coupon);
		}
		return status;
	}

	public PromoRule getCouponType(Integer gainedCouponTypeId) {
		if (null != gainedCouponTypeId) {
			return promoRuleManager.getById(gainedCouponTypeId);
		} else {
			return null;
		}
	}
	
	public Collection<ProductSku> getPromoInfoUsedInProductModule(ProductSku sku){
		logger.debug("promoService:mockAppendPromoInfo sku");
		Collection<ProductSku> skus = null;
		final Vector<PRule> cartPromotionRules = pruleManager.getCartPromotionRules();
		PRuleEngine engine = new PRuleEngine(cartPromotionRules);
		try {
			skus = engine.mockRun(sku);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skus;
		
	}
	
	public void doSendCoupon(Integer couponTypeId, EmailModel emailModel){
		Coupon coupon = couponManager.getIdleCoupon(couponTypeId);
		if(null == coupon){
			//产生一张类型为数字，无前缀的优惠券
			PromoRule promoRule = promoRuleManager.getById(couponTypeId);
			if(promoRule == null){
				//促销规则已删掉
				return;
			}
			coupon = couponManager.createCoupon(couponTypeId, promoRule.getAvailableCount(), 1, "");
			coupon.setPromoRule(promoRule);
		}
		couponManager.doSendCoupon(coupon, emailModel);
	}
	
}
