package com.cartmatic.estore.sales.dao.impl;

import java.util.List;

import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.sales.dao.PromoRuleParameterDao;

/**
 * Dao implementation for PromoRuleParameter.
*/
public class PromoRuleParameterDaoImpl extends HibernateGenericDaoImpl<PromoRuleParameter> implements PromoRuleParameterDao {
	public void deleteAllByElementId(Integer _elementId) {
		List<PromoRuleParameter> params = getHibernateTemplate().find("from PromoRuleParameter param where param.promoRuleElement.promoRuleElementId ="+_elementId);
		getHibernateTemplate().deleteAll(params);
		logger.info(formatMsg("Deleted all parameters of element_id="+_elementId));
	}

}
