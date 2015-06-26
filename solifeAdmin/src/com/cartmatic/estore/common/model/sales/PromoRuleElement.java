package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.PromoRuleElementTbl;

/**
 * Model class for PromoRuleElement. Add not database mapped fileds in this class.
 */
public class PromoRuleElement extends PromoRuleElementTbl {
	private static final long	serialVersionUID	= -8869658266006511882L;
	public final static String KIND_OF_ELIGIBILITY = "eligibility";
	public final static String KIND_OF_CONDITION = "condition";
	public final static String KIND_OF_ACTION = "action";
	
	private String promoRuleElementIdString;
  	/**
	 * Default Empty Constructor for class PromoRuleElement
	 */
	public PromoRuleElement () {
		super();
	}
	public PromoRuleElement(String _kind,String _type){
		this.kind = _kind;
		this.type = _type;
	}
	
	
	
	/**
	 * 定义实体的业务名取值； promoRuleElementName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPromoRuleElementName () {
		if (promoRuleElementId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.promoRuleElementId.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class PromoRuleElement
	 */
	public PromoRuleElement (
		 Integer in_promoRuleElementId
		) {
		super (
		  in_promoRuleElementId
		);
	}
	public String getPromoRuleElementIdString() {
		return promoRuleElementIdString;
	}
	public void setPromoRuleElementIdString(String promoRuleElementIdString) {
		this.promoRuleElementIdString = promoRuleElementIdString;
	}
	
	

}
