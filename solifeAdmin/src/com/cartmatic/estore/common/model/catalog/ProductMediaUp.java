package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductMediaTbl;
import com.cartmatic.estore.common.model.catalog.base.ProductMediaUpTbl;

/**
 * Model class for ProductMedia. Add not database mapped fileds in this class.
 */
public class ProductMediaUp extends ProductMediaUpTbl {
	private String iconImage;
  	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	/**
	 * Default Empty Constructor for class ProductMedia
	 */
	public ProductMediaUp () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productMediaName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductMediaName () {
		if (productMediaUpId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productMediaUpId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductMediaUp (
		 Integer in_productMediaId
		) {
		super (
		  in_productMediaId
		);
	}

}
