package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.PromoRuleParameterTbl;

/**
 * Model class for PromoRuleParameter. Add not database mapped fileds in this class.
 */
public class PromoRuleParameter extends PromoRuleParameterTbl {
	
	private static final long	serialVersionUID	= -2064344593351536092L;
	public static final Short IS_EXCLUDED_YES = 1; 
	public static final Short IS_EXCLUDED_NO = 1;
	private String promoRuleParameterIdString;
	private String paramTitle;

  	/**
	 * Default Empty Constructor for class PromoRuleParameter
	 */
	public PromoRuleParameter () {
		super();
	}
	
	public PromoRuleParameter(String _id,String _paramName,String _paramValue,String _excludedType){
		this.promoRuleParameterIdString = _id;
		this.paramName = _paramName;
		this.paramValue = _paramValue;
		this.excludedType = _excludedType;
		
		if(this.excludedType != null && !this.excludedType.equals("")){
			this.isExcluded = IS_EXCLUDED_YES;
		}else{
			this.isExcluded = IS_EXCLUDED_NO;
		}
		
	}
	
	public PromoRuleParameter(String _paramName,String _paramValue){
		this.paramName = _paramName;
		this.paramValue = _paramValue;
		this.isExcluded = IS_EXCLUDED_NO;
	}
	
	
	/**
	 * 定义实体的业务名取值； promoRuleParameterName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPromoRuleParameterName () {
		if (promoRuleParameterId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.promoRuleParameterId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class PromoRuleParameter
	 */
	public PromoRuleParameter (
		 Integer in_promoRuleParameterId
		) {
		super (
		  in_promoRuleParameterId
		);
	}

	public String getPromoRuleParameterIdString() {
		return promoRuleParameterIdString;
	}

	public void setPromoRuleParameterIdString(String promoRuleParameterIdString) {
		this.promoRuleParameterIdString = promoRuleParameterIdString;
	}

	public String getParamTitle() {
		return paramTitle;
	}

	public void setParamTitle(String paramTitle) {
		this.paramTitle = paramTitle;
	}
	
	

}
