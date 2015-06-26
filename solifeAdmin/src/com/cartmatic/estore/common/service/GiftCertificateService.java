package com.cartmatic.estore.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.cartmatic.estore.common.model.cart.ShoppingcartItemGc;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.exception.GiftCertificateStateException;

/**
 * 礼券对外模块提供的接口类
 * @author huangwm210 2009-01-05
 * modified by heqingming 2009-01-12
 *
 */
public interface GiftCertificateService {

	/**
	 * 根据shippingcartItemGc创建礼券实体
	 * @param shoppingcartItemGc
	 * @return GiftCertificate
	 */
	public GiftCertificate createGiftCertificate(ShoppingcartItemGc shoppingcartItemGc);
	
	/**
	 * 发送礼券,订单支付完成的时候调用
	 * @param gc
	 * @param orderNo
	 */
	public void doSendGiftCertificate(GiftCertificate giftCertificate, String orderNo);
	
	/**
     * 产生一个只能用于订单的礼券
     * @param salesOrder
     * @param amt
     * @return
     */
    public GiftCertificate createForSalesOrder(SalesOrder salesOrder, BigDecimal amt);
	
	/**
	 * 模拟使用礼券，点击“使用”礼券时候调用的
	 * @param giftCertificateNo 礼券号码
	 * @param decMoney 要扣除的金额
	 * @return BigDecimal 实际扣掉的金额
	 */
	public BigDecimal mockUseGiftCertificate(String giftCertificateNo, BigDecimal decMoney, int shoppingCartItems);
	
	/**
	 * 使用礼券，下订单时调用的
	 * @param giftCertificateNo 礼券号码
	 * @param decMoney 要扣除的金额
	 * @throws GiftCertificateStateException 
	 * 
	 * GiftCertificateStateException会有两个成员变量
	 * giftCertificateNo  是抛出异常的礼券号码
	 * state 是抛出异常对应的错误码 GiftCertificate.STATE_INVALID //无效,礼券号不存在或者其他原因
	 *         					GiftCertificate.STATE_UNACTIVE //无效，因为礼券号未激活
	 *         					GiftCertificate.STATE_UNDEAL //无效，因为礼券号在交易中，暂时不能使用
	 *         					GiftCertificate.STATE_EXPIRE //无效，因为礼券号已经超过可用时间
	 *         					GiftCertificate.STATE_NOT_ENOUGH_REMAINEDAMT //无效，因为余额不足以支付规定的金额 (这个错误状态是最主要的)
	 */
	public void doUseGiftCertificate(String giftCertificateNo, BigDecimal decMoney, int shoppingCartItems) throws GiftCertificateStateException;
	
	
	/**
	 * 根据GiftCertificate查询礼券
	 * @param giftCertificateNo
	 * @return giftCertificate实体,state成员变量会存贮相关状态信息
	 */
	public GiftCertificate QueryGiftCertificateByNo(String giftCertificateNo);
	/**
	 * 检查礼券是否可用
	 * 
	 * @param giftCertificateNo
	 *            礼券编号
	 * @return GiftCertificate.STATE_INVALID //无效,礼券号不存在或者其他原因
	 *         GiftCertificate.STATE_UNACTIVE //无效，因为礼券号未激活
	 *         GiftCertificate.STATE_UNDEAL //无效，因为礼券号在交易中，暂时不能使用
	 *         GiftCertificate.STATE_EXPIRE //无效，因为礼券号已经超过可用时间
	 *         GiftCertificate.STATE_REMAINEDAMT_IS_ZERO //无效，因为余额为零
	 *         GiftCertificate.STATE_AVAILABLE //有效
	 */
	public short checkGiftcertificate(String giftCertificateNo);
	/**
	 * 根据礼券号码获得礼券实体
	 * @param giftCertificateNo
	 * @return
	 */
	public GiftCertificate getGiftCertificateByNo(String giftCertificateNo);
	
	/**
	 * 获得客户已经绑定的礼券号
	 * @param customerId
	 * @return
	 */
	public List<GiftCertificate> getBindedGiftCard(Integer customerId);
}
