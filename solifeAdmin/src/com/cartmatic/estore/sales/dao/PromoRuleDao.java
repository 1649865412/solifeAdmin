package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.GenericDao;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.model.PromoRuleSearchCriteria;
/**
 * Dao interface for PromoRule.
 */
public interface PromoRuleDao extends GenericDao<PromoRule> {
	//取得所有当前正在进行中的shoppingcartPromotion类别的促销规则
	public List<PromoRule> getAllCartPromotionRulesInProcess();
	//取得所有当前正在进行中的catalogPromotion类别的促销规则
	public List<PromoRule> getAllCatalogPromotionRulesInProcess();
	//取得所有当前正在进行中的couponPromotion类别的促销规则
	public List<PromoRule> getAllCouponPromotionRulesInProcess();
	//根据具体的coupon获得优惠券类型(即对应的优惠券促销规则)
	public PromoRule getCouponPromotionRule(Coupon _coupon);
	//获得所有正在进行中和将要推出的优惠券类型
	public List<PromoRule> getAllCouponTypesInProcessOrInFuture();
	//后台搜索促销规则列表
	public List<PromoRule>  searchPromotionRules(SearchCriteria _searchCriteria,
			PromoRuleSearchCriteria _promoSearchCriteria);
	//前台获得所有当前正在进行中的促销规则
	public List<PromoRule> getAllPromotionRulesInProcessForFront();
	//前台获得所有即将推出的促销规则	
	public List<PromoRule> getAllPromotionRulesInFutureForFront();
	
	/**
	 * 根据triggerType查询特定用途的优惠劵促销
	 * @param triggerType
	 * @return
	 */
	public PromoRule getSpecialPromotionRule(Short triggerType);
	
}