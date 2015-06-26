
package com.cartmatic.estore.common.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.core.model.AppUser;
import com.cartmatic.estore.sales.model.EmailModel;
/**
 * 促销模块接口模块
 * @author CartMatic
 *
 */
public interface PromoService {

	/**
	 * 添加关于shoppingcart折扣信息
	 * 
	 * @param _cart
	 *            折扣前的shoppingcart
	 * @param _customer
	 *            消费者
	 * @return Shoppingcart 折扣后的shoppingcart
	 */
	public Shoppingcart appendPromoInfo(Shoppingcart _cart, AppUser _customer);
	
	/**
	 * 添加未关联购物车外的折扣信息
	 * @param _cart
	 * @param _customer
	 * @return
	 */
	public Shoppingcart appendPromoNInCart(Shoppingcart _cart, AppUser _customer);

	/**
	 * 添加关于sku折扣信息
	 * 
	 * @param _sku
	 *            折扣前的sku
	 * @return ProductSku 折扣后的sku
	 */
	public ProductSku appendPromoInfo(ProductSku _sku);
	
	
	/**
	 * 添加关于sku折扣信息
	 * 
	 * @param Collection
	 *            <ProductSku> 折扣前的sku collection
	 * @return Collection<ProductSku> 折扣后的sku collection
	 */
	public Collection<ProductSku> appendPromoInfo(Collection<ProductSku> _list);

	/**
	 * 得到折扣后的运费(对指定shippingMethodId的运输方式打折)
	 * 
	 * @param _shippingDiscountInfo
	 *            折扣后的运费信息，从shoppingcart.getShippingDiscountInfo()方法获得，一般先通过appendPromoInfo方法计算，该方法会对shoppingcart注入运费的折扣信息。
	 * @param _shippingMethodId
	 *            运输方式Id 对应表Shipping_method
	 * @param _shippingFee
	 *            折扣前的运费
	 * @return 折扣后的运费
	 */
	public BigDecimal getShippingFee(String _shippingDiscountInfo,
			Integer _shippingMethodId, BigDecimal _shippingFee);
	
	/**
	 * 根据couponNo查询优惠券
	 * @param couponNo
	 * @return coupon实体,state成员变量会存贮相关状态信息
	 */
	public Coupon QueryCouponByNo(String couponNo);

	/**
	 * 检查优惠券是否可用
	 * 
	 * @param couponNo
	 *            优惠券编号
	 * @return Coupon.STATE_INVALID //无效,不存在该优惠券号码或其他原因
	 *         Coupon.STATE_INVALID_NOT_ACTIVE //无效，因为优惠券未激活
	 *         Coupon.STATE_INVALID_IS_END //无效，因为优惠券的使用期限已过
	 *         Coupon.STATE_INVALID_NOT_START //无效，因为优惠券未到优惠时间
	 *         Coupon.STATE_INVALID_REMAINEDTIMES_IS_ZERO //无效，因为优惠券剩余使用次数为0
	 *         Coupon.STATE_VALID //有效
	 */
	public short checkCoupon(String couponNo);

	/**
	 * 使用优惠券
	 * 
	 * @param couponNo
	 *            优惠券编号
	 * @return 返回值同checkCoupon(String couponNo)，请参考
	 */
	public short useCoupon(String couponNo);
	
	/**
	 * 根据购物车赠送的优惠券类型Id获得优惠券类型实体
	 * @param gainedCouponTypeId
	 * @return PromoRule
	 */
	public PromoRule getCouponType(Integer gainedCouponTypeId);
	/**
	 * 获得关于SKU的促销信息
	 * @param sku
	 * @return Collection<ProductSku>
	 */
	public Collection<ProductSku> getPromoInfoUsedInProductModule(ProductSku sku);
	/**
	 * 订单支付完成,发送优惠券
	 * @param couponTypeId  对应的优惠券促销Id
	 * @param emailModel 邮件model
	 */
	public void doSendCoupon(Integer couponTypeId, EmailModel emailModel);

}
