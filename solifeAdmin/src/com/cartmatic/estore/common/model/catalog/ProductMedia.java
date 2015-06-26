package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.ProductMediaTbl;

/**
 * Model class for ProductMedia. Add not database mapped fileds in this class.
 */
public class ProductMedia extends ProductMediaTbl {
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
	public ProductMedia () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productMediaName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductMediaName () {
		if (productMediaId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productMediaId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class ProductMedia
	 */
	public ProductMedia (
		 Integer in_productMediaId
		) {
		super (
		  in_productMediaId
		);
	}

}
