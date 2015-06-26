
package com.cartmatic.estore.sales.engine;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.cart.ShoppingcartPromotion;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.common.service.ProductService;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.sales.service.PromoRuleManager;
import com.cartmatic.estore.sales.util.PromoDependServicesUtil;
import com.cartmatic.estore.webapp.util.RequestContext;
/**
 * 这是管理更新系统中的促销规则的类,方便ruleEngine获取规则数据，不需要每次都从数据库中读取，若在页面更新规则也必须通知该类。
 * @author CartMatic
 *
 */
public class PRuleManager 
{
	private static final Log logger	= LogFactory.getLog(PRuleManager.class);
	//这两个Vector保存了ruleEngine需要的所有促销规则。
	private static Vector<PRule>	cartPromotionRules		= new Vector<PRule>(); //这个是下面两个vetor的总和
	private static Vector<PRule>	catalogPromotionRules	= new Vector<PRule>();
	private static Vector<PRule>	shoppingcartPromotionRules	= new Vector<PRule>();

	private PromoRuleManager			promoRuleManager;
	private CouponManager				couponManager;
	
	
	
	//temp
	private List<PromoRule> cartPromotionRuleList;
	

	public List<PromoRule> getCartPromotionRuleList() {
		return cartPromotionRuleList;
	}

	public void setCartPromotionRuleList(List<PromoRule> cartPromotionRuleList) {
		this.cartPromotionRuleList = cartPromotionRuleList;
	}

	public void init() {
		refresh();
	}

	
	public void refresh() {
		logger.info("~~~~~refresh the promoRules in system~~~~~~");
		clear();
		
		

		List<PromoRule> rules = promoRuleManager.getAllCatalogPromotionRulesInProcess();
		for (PromoRule rule : rules) {
			PRule prule = new PRule(rule);
			catalogPromotionRules.add(prule);
			cartPromotionRules.add(prule);
		}
		rules = promoRuleManager.getAllCartPromotionRulesInProcess();
		for (PromoRule rule : rules) {
			PRule prule = new PRule(rule);
			shoppingcartPromotionRules.add(prule);
			cartPromotionRules.add(prule);
		}
		
		
		

		logger.debug("there are " + catalogPromotionRules.size()
				+ " rules about catalogPromo");
		logger.debug("there are " + cartPromotionRules.size()
				+ " rules about cartPromo");
	}

	public void addCartPromotionRule(PRule _prule) {
		synchronized (cartPromotionRules) {
			cartPromotionRules.add(_prule);
		}
	}

	public void addAllCartPromotionRules(Collection<PRule> _prules) {
		synchronized (cartPromotionRules) {
			cartPromotionRules.addAll(_prules);
		}
	}

	public void removeCartPromotionRule(PRule _prule) {
		synchronized (cartPromotionRules) {
			cartPromotionRules.remove(_prule);
		}
	}

	public void removeAllCartPromotionRules(Collection<PRule> _prules) {
		synchronized (cartPromotionRules) {
			cartPromotionRules.removeAll(_prules);
		}
	}

	public void clearCartPromotionRules() {
		synchronized (cartPromotionRules) {
			cartPromotionRules.clear();
		}
	}

	public static Vector<PRule> getCartPromotionRules() {
		Vector<PRule> rules = new Vector<PRule>();
		for (PRule rule : cartPromotionRules)
		{
			if (RequestContext.getCurrentStoreCode().equals(rule.getStoreCode()))
				rules.add(rule);
		}
		return rules;
	}
	
	/**
	 * 获得cart中未执行的PRules
	 * @param cart
	 * @return
	 */
	public static Vector<PRule> getPRulesNInCart(Shoppingcart cart) {
		Vector<PRule> rules = new Vector<PRule>();
		Set<ShoppingcartPromotion> promos = cart.getShoppingCartPromotions();
		boolean flag = true;
		for (PRule rule : cartPromotionRules)
		{
			for(ShoppingcartPromotion promo : promos){
				if(promo.getPromoRuleId().equals(rule.getRuleId())){
					flag = false;
					break;
				}
			}
			if (flag && RequestContext.getCurrentStoreCode().equals(rule.getStoreCode())){
				rules.add(rule);
			}
		}
		return rules;
	}

	public void setCartPromotionRules(Vector<PRule> _prules) {
		synchronized (cartPromotionRules) {
			cartPromotionRules = _prules;
		}
	}

	public void addCatalogPromotionRule(PRule _prule) {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules.add(_prule);
		}
	}

	public void addAllCatalogPromotionRules(Collection<PRule> _prules) {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules.addAll(_prules);
		}
	}

	public void removeCatalogPromotionRules(PRule _prule) {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules.remove(_prule);
		}
	}

	public void removeAllCatalogPromotionRules(Collection<PRule> _prules) {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules.removeAll(_prules);
		}
	}

	public void clearCatalogPromotionRules() {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules.clear();
		}
	}

	public static Vector<PRule> getCatalogPromotionRules() {
		Vector<PRule> rules = new Vector<PRule>();
		for (PRule rule : catalogPromotionRules)
		{
			if (RequestContext.getCurrentStoreCode().equals(rule.getStoreCode()))
				rules.add(rule);
		}
		return rules;
	}

	public void setCatalogPromotionRules(Vector<PRule> _prules) {
		synchronized (catalogPromotionRules) {
			catalogPromotionRules = _prules;
		}
	}
	
	public Vector<PRule> getCouponPromotionRules(Coupon _coupon) {
		Vector<PRule> prules = new Vector<PRule>();
		prules.add(new PRule(promoRuleManager.getCouponPromotionRule(couponManager
				.getCouponByNo(_coupon.getCouponNo()))));
		logger.debug("there are " + prules.size() + " rules about couponPromo");
		return prules;
	}
	
	/**
	 * 获取cart中并未存在的优惠劵促销
	 * @param _coupon
	 * @param _cart
	 * @return
	 */
	public Vector<PRule> getPRuleNInCart(Coupon _coupon, Shoppingcart _cart){
		Vector<PRule> prules = new Vector<PRule>();
		PromoRule rule = promoRuleManager.getCouponPromotionRule(couponManager
				.getCouponByNo(_coupon.getCouponNo()));
		boolean flag = true;
		for(ShoppingcartPromotion promo : _cart.getShoppingCartPromotions()){
			if(promo.getPromoRuleId().equals(rule.getId())){
				flag = false;
				break;
			}
		}
		if(flag){
			prules.add(new PRule(rule));
		}
		logger.debug("there are " + prules.size() + " rules about couponPromo");
		return prules;
	}
	
/////
	public void addShoppingcartPromotionRule(PRule _prule) {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules.add(_prule);
		}
	}

	public void addAllShoppingcartPromotionRules(Collection<PRule> _prules) {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules.addAll(_prules);
		}
	}

	public void removeShoppingcartPromotionRules(PRule _prule) {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules.remove(_prule);
		}
	}

	public void removeAllShoppingcartPromotionRules(Collection<PRule> _prules) {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules.removeAll(_prules);
		}
	}

	public void clearShoppingcartPromotionRules() {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules.clear();
		}
	}

	public static Vector<PRule> getShoppingcartPromotionRules() {
		Vector<PRule> rules = new Vector<PRule>();
		for (PRule rule : shoppingcartPromotionRules)
		{
			if (RequestContext.getCurrentStoreCode().equals(rule.getStoreCode()))
				rules.add(rule);
		}
		return rules;		
	}

	public void setShoppingcartPromotionRules(Vector<PRule> _prules) {
		synchronized (shoppingcartPromotionRules) {
			shoppingcartPromotionRules = _prules;
		}
	}
	
	

	public void clear() {
		clearCartPromotionRules();
		clearCatalogPromotionRules();
		clearShoppingcartPromotionRules();
	}

	

	public void setPromoRuleManager(PromoRuleManager avalue) {
		this.promoRuleManager = avalue;
	}

	public void setCouponManager(CouponManager avalue) {
		this.couponManager = avalue;
	}

	public void setProductService(ProductService _productService) {
		PromoDependServicesUtil.setProductService(_productService);
	}

	public void setOrderService(OrderService _orderService) {
		PromoDependServicesUtil.setOrderService(_orderService);
	}

	//@Override
	//public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	//	doRefresh();
	//}

}
