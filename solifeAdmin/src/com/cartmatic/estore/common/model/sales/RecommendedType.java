package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.RecommendedTypeTbl;

/**
 * 
 * @author CartMatic
 *
 */
public class RecommendedType extends RecommendedTypeTbl 
{
  	
	private static final long	serialVersionUID	= -4407865081119844175L;
	public static final Short AUTOEVAL_NO = 0;  //否
	public static final Short AUTOEVAL_YES = 1;  //是
	public static final Short AUTOEVAL_CANNOT = 2;  //不能
	/**
	 * Default Empty Constructor for class RecommendedType
	 */
	public RecommendedType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RecommendedType
	 */
	public RecommendedType (
		 Integer in_recommendedTypeId
		) {
		super (
		  in_recommendedTypeId
		);
	}

}
