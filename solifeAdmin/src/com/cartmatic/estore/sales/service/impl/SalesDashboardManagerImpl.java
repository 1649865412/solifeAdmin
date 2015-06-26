
package com.cartmatic.estore.sales.service.impl;

import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.dao.SalesDashboardDao;
import com.cartmatic.estore.sales.service.SalesDashboardManager;

public class SalesDashboardManagerImpl extends GenericManagerImpl<PromoRule>
		implements SalesDashboardManager {
	private SalesDashboardDao	salesDashboardDao;

	public void setSalesDashboardDao(SalesDashboardDao salesDashboardDao) {
		this.salesDashboardDao = salesDashboardDao;
	}

	protected void initManager() {
		dao = salesDashboardDao; 
	}

	protected void onDelete(PromoRule entity) {

	}

	protected void onSave(PromoRule entity) {

	}

	public List<PromoRule> getAllPromotionRulesInProcess(Integer count) {
		return salesDashboardDao.getAllPromotionRulesInProcess(count);
	}
}
