package com.cartmatic.estore.common.model.customer;

import com.cartmatic.estore.common.model.customer.base.FavoriteTbl;

/**
 * Model class for Favorite. Add not database mapped fileds in this class.
 */
public class Favorite extends FavoriteTbl {

  	/**
	 * Default Empty Constructor for class Favorite
	 */
	public Favorite () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； favoriteName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getFavoriteName () {
		if (favoriteId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.favoriteId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class Favorite
	 */
	public Favorite (
		 Integer in_favoriteId
		) {
		super (
		  in_favoriteId
		);
	}

}
