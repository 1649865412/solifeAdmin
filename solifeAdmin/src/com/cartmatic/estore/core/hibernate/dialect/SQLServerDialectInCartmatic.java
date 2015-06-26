/*
 * create 2006-11-8
 * 
 * 
 */

package com.cartmatic.estore.core.hibernate.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

/**
 * @author Ryan
 * 
 * 
 */
public class SQLServerDialectInCartmatic extends SQLServerDialect {
	public SQLServerDialectInCartmatic() {
		super();
		registerFunction("dayofyear", new SQLFunctionTemplate(
				Hibernate.INTEGER, "datepart(dy, ?1)"));
		registerFunction("week", new SQLFunctionTemplate(Hibernate.INTEGER,
				"datepart(wk, ?1)"));
	}
}
