/*
 * create 2006-11-8
 * 
 * 
 */

package com.cartmatic.estore.core.hibernate.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle9Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

/**
 * @author Ryan
 * 
 * 
 */
public class OracleDialectInCartmatic extends Oracle9Dialect {
	public OracleDialectInCartmatic() {
		super();
		registerFunction("dayofyear", new SQLFunctionTemplate(
				Hibernate.INTEGER, "TO_CHAR(?1,'DDD')"));
		registerFunction("month", new SQLFunctionTemplate(Hibernate.INTEGER,
				"TO_CHAR(?1,'MM')"));
		registerFunction("week", new SQLFunctionTemplate(Hibernate.INTEGER,
				"TO_CHAR(?1,'WW')"));
	}
}
