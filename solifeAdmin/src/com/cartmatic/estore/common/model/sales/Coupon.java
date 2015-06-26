package com.cartmatic.estore.common.model.sales;

import com.cartmatic.estore.common.model.sales.base.CouponTbl;

/**
 * Model class for Coupon. Add not database mapped fileds in this class.
 */
public class Coupon extends CouponTbl {
	
	
	private static final long	serialVersionUID	= 1834095859162747940L;
	
	public final static short STATE_INVALID = 0;   //无效,不存在该优惠券号码或其他原因
	public final static short STATE_INVALID_NOT_ACTIVE = -1; //无效，因为优惠券未激活
	public final static short STATE_INVALID_IS_END = -2; //无效，因为优惠券的使用期限已过
	public final static short STATE_INVALID_NOT_START = -3; //无效，因为优惠券未到优惠时间
	public final static short STATE_INVALID_REMAINEDTIMES_IS_ZERO = -4; //无效，因为优惠券剩余使用次数为0
	public final static short STATE_VALID = 1;    //有效
	
	public final static short STATE_SAME_PRULE = 5 ;		//当前输入的优惠劵跟原先的优惠劵属于同一促销信息
	
	private short state;
  	/**
	 * Default Empty Constructor for class Coupon
	 */
	public Coupon () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； couponName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getCouponName () {
		if (couponId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.couponNo;
	}
	
	/**
	 * Default Key Fields Constructor for class Coupon
	 */
	public Coupon (
		 Integer in_couponId
		) {
		super (
		  in_couponId
		);
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}
	
	

}
