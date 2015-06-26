package com.cartmatic.estore.sales.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.PromoRuleElementDao;

/**
 * Dao implementation for PromoRuleElement.
*/
public class PromoRuleElementDaoImpl extends HibernateGenericDaoImpl<PromoRuleElement> implements PromoRuleElementDao {
	public void deleteAllByRuleId(Integer _ruleId){
		List<PromoRuleElement> elements = getHibernateTemplate().find("from PromoRuleElement element where element.promoRuleParameter.promoRuleParameterId ="+_ruleId);
		getHibernateTemplate().deleteAll(elements);
		logger.info(formatMsg("Deleted all elements of rule_id="+_ruleId));
	}
}
