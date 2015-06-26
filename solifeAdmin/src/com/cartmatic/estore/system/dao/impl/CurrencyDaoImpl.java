package com.cartmatic.estore.system.dao.impl;

import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.system.Currency;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.system.dao.CurrencyDao;

/**
 * Dao implementation for Currency.
*/
public class CurrencyDaoImpl extends HibernateGenericDaoImpl<Currency> implements CurrencyDao {
	public List<Currency> loadEnableCurrency()
	{
		return this.findByHql("from Currency where status=? order by sortOrder asc", Constants.STATUS_ACTIVE);
	}
}
