package com.cartmatic.estore.sales.dao;

import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.dao.GenericDao;

public interface SalesDashboardDao extends GenericDao<PromoRule>{
	//取得所有当前正在进行中的的促销规则
	public List<PromoRule> getAllPromotionRulesInProcess(Integer count);
}
