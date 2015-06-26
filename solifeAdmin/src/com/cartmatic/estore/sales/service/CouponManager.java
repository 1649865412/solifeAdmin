package com.cartmatic.estore.sales.service;

import java.util.List;

import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.sales.model.EmailModel;

/**
 * Manager interface for Coupon, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface CouponManager extends GenericManager<Coupon> {
	/**
	 * 产生多个优惠券号码
	 * @param _couponType 优惠券促销规则ID
	 * @param _availableCount 可使用次数
	 * @param _quantity 数量
	 * @param _couponStyle 产生类型 数字1，英文2
	 * @param _prefix 前缀
	 */
	public List<Coupon> createCoupons(Integer _couponType, Integer _availableCount,
			int _couponStyle, String _prefix,int _quantity);
	/**
	 * 产生一个优惠券号码
	 * @param _couponType 优惠券促销规则ID
	 * @param _availableCount 可使用次数
	 * @param _quantity 数量
	 * @param _couponStyle 产生类型 数字1，英文2
	 * @param _prefix 前缀
	 */
	public Coupon createCoupon(Integer _couponType, Integer _availableCount,
			int _couponStyle, String _prefix) ;
	
	/**
	 * 产生一个优惠券,使用指定的优惠券号
	 * @param couponType 优惠券促销规则ID
	 * @param availableCount 可使用次数
	 * @param couponNo 指定的优惠券号
	 * @return
	 */
	public Coupon createCoupon(Integer couponType, Integer availableCount, String couponNo);
	/**
	 * 是否存在该优惠券号码
	 * @param couponNo
	 * @return boolean
	 */
	public boolean existCoupon(String couponNo) ;
	/**
	 * 根据优惠券号码获得优惠券实体
	 * @param couponNo
	 * @return
	 */
	public Coupon getCouponByNo(String couponNo) ;
	/**
	 * 搜索优惠券
	 * @param _searchCriteria
	 * @param _promoRule 促销规则
	 * @return List
	 */
	public List searchCoupons(SearchCriteria _searchCriteria, String _promoRule);
	/**
	 * 发送赠送优惠券邮件
	 * @param coupon 优惠券
	 * @param emailModel
	 */
	public void doSendCoupon(Coupon coupon, EmailModel emailModel);
	/**
	 * 获得一张已有的未发送的优惠券
	 * @param couponTypeId
	 * @return coupon 优惠券  如果没有返回null
	 */
	public Coupon getIdleCoupon(Integer couponTypeId);
}
