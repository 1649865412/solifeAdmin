package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.CatalogTbl;

/**
 * Model class for Catalog. Add not database mapped fileds in this class.
 */
public class Catalog extends CatalogTbl {

  	/**
	 * Default Empty Constructor for class Catalog
	 */
	public Catalog () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； catalogName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getCatalogName () {
		if (catalogId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.name;
	}
	
	/**
	 * Default Key Fields Constructor for class Catalog
	 */
	public Catalog (
		 Integer in_catalogId
		) {
		super (
		  in_catalogId
		);
	}
}