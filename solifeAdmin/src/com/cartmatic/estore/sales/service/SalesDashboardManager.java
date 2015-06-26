package com.cartmatic.estore.sales.service;

import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.service.GenericManager;



public interface SalesDashboardManager extends GenericManager<PromoRule>
{
	//取得所有当前正在进行中的的促销规则
	public List<PromoRule> getAllPromotionRulesInProcess(Integer count);
}
