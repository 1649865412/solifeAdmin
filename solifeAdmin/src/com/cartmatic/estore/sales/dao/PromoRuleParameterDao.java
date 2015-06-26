package com.cartmatic.estore.sales.dao;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for PromoRuleParameter.
 */
public interface PromoRuleParameterDao extends GenericDao<PromoRuleParameter> {
	public void deleteAllByElementId(Integer _elementId);
}