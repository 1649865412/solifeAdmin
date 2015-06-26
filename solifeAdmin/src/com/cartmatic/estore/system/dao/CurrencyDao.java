package com.cartmatic.estore.system.dao;

import java.util.List;

import com.cartmatic.estore.common.model.system.Currency;
import com.cartmatic.estore.core.dao.GenericDao;
/**
 * Dao interface for Currency.
 */
public interface CurrencyDao extends GenericDao<Currency> {
	public List<Currency> loadEnableCurrency();
}