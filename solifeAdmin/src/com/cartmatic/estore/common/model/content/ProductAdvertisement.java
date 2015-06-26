package com.cartmatic.estore.common.model.content;

import com.cartmatic.estore.common.model.content.base.ProductAdvertisementTbl;

/**
 * Model class for ProductAdvertisement. Add not database mapped fileds in this class.
 */
public class ProductAdvertisement extends ProductAdvertisementTbl {
	public String categoryPathName;
	public String getCategoryPathName() {
		return categoryPathName;
	}

	public void setCategoryPathName(String categoryPathName) {
		this.categoryPathName = categoryPathName;
	}
	
  	/**
	 * Default Empty Constructor for class ProductAdvertisement
	 */
	public ProductAdvertisement () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productAdvertisementName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductAdvertisementName () {
		if (productAdvertisementId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productAdvertisementId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class ProductAdvertisement
	 */
	public ProductAdvertisement (
		 Integer in_productAdvertisementId
		) {
		super (
		  in_productAdvertisementId
		);
	}

}
