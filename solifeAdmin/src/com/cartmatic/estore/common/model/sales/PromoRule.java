package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.PromoRuleTbl;

/**
 * Model class for PromoRule. Add not database mapped fileds in this class.
 */
public class PromoRule extends PromoRuleTbl {
	public static final int STATE_DOING = 1;  //正在进行中
	public static final int STATE_FUTURE = 2;  //即将推出的
	public static final int STATE_INVALID = 0;  //非激活的
	public static final int STATE_PAST = 3;  //已经过时的
	
	private static final long	serialVersionUID	= 8905364233624009581L;
	public final static String PROMOTION_TYPE = "promotionType";
	public final static String PROMOTION_TYPE_SHOPPINGCARTPROMOTION = "shoppingcartPromotion";
	public final static String PROMOTION_TYPE_CATALOGPROMOTION = "catalogPromotion";
	public final static String PROMOTION_TYPE_COUPONPROMOTION = "couponPromotion";
	
	protected String state;
	protected int startHour;
	protected int endHour;

  	/**
	 * Default Empty Constructor for class PromoRule
	 */
	public PromoRule () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； promoRuleName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPromoRuleName () {
		if (promoRuleId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.name.toString();
	}
	
	/**
	 * Default Key Fields Constructor for class PromoRule
	 */
	public PromoRule (
		 Integer in_promoRuleId
		) {
		super (
		  in_promoRuleId
		);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getStartHour() {
		return startHour;
	}

	public int getEndHour() {
		return endHour;
	}
	
	
	public int getStartHourShow() {
		if(null != this.getStartTime()){
			return this.getStartTime().getHours();
		}else{
			return 0;
		}
	}
	
	public int getEndHourShow() {
		if(null != this.getEndTime()){
			return this.getEndTime().getHours();
		}else{
			return 0;
		}
	}
	

}
