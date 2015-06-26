package com.cartmatic.estore.system.service;

import java.util.List;

import com.cartmatic.estore.common.model.system.Currency;
import com.cartmatic.estore.core.service.GenericManager;

/**
 * Manager interface for Currency, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface CurrencyManager extends GenericManager<Currency> {

	public List<Currency> loadEnableCurrency();
}
