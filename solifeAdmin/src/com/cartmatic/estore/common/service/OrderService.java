/**
 * 
 */
package com.cartmatic.estore.common.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.PaymentHistory;
import com.cartmatic.estore.exception.OutOfStockException;

/**
 * 订单服务接品类
 * @author pengzhirong
 */
public interface OrderService {
	
	/**
	 * 返回指定会员的订单数是否为零
	 * 
	 * @param userId
	 * @return true - 为零
	 * 		   false - 不为零
	 */
	public boolean isCustomerOrderNumZero(Serializable userId);
	
	/**
	 * 下订单
	 * @param billingAddress 帐单发票地址 可为null（全为礼券的订单）
	 * @param shippingAddress 运输地址 可为null（全为礼券的订单）
	 * @param salesOrder 订单基本信息
	 * @param orderShipment 发货项
	 * @param orderSkus 订单商品项集合
	 * @param orderPromotions 订单商品项应用的促销集合  可为null
	 * @param orderPayments 礼券或积分支付  可为null
	 * @param note 备注 可为null
	 * @throws OutOfStockException 
	 */
	public void doPlaceOrder(OrderAddress billingAddress, OrderAddress shippingAddress, 
			SalesOrder salesOrder, OrderShipment orderShipment, 
			Set<OrderSku> orderSkus, Set<OrderPromotion> orderPromotions, Set<OrderPayment> orderPayments, String note) throws OutOfStockException;
	
	/**
	 * 为预订订单分配库存，产品入库时调用
	 * @param productSkuId
	 * @return
	 */
	public void doAllocation4PreOrBackOrder(Integer productSkuId);
	
	/**
	 * 列出指定会员的所有订单
	 * @param userId 前台会员ID
	 * @return
	 */
	public List<SalesOrder> getSalesOrderByUserId(Serializable userId);
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId);
	
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId);
	
	public SalesOrder getSalesOrder(String orderNo, String customerEmail);
	
	public SalesOrder getSalesOrderByOrderNo(String orderNo);
	/**
	 * 前台 申请取消订单
	 * @param salesOrderId 订单ID
	 * @param reasonType 取消原因类型
	 * @param note 前台会员申请取消时所填备注信息
	 * @param userId 前台会员ID
	 */
	public void doCancelOrder(Integer storeId,Serializable salesOrderId, Short reasonType, String note, Serializable userId);
	
	/**
	 * 前台 支付回调接口，在线支付成功时调用
	 * @param orderNo 订单编号
	 * @param paymentAmount 支付金额
	 * @param paymentMethodName 支付网关的名称
	 * @param ipAddress 用户的IP地址，注意这个字段最长64位
	 */
	public void doCreatePayment(Serializable orderNo, BigDecimal paymentAmount, String paymentMethodName, String ipAddress);
	
	/**
	 * 发送支付成功的email给客户
	 * @param ph 支付历史vo
	 * @param salesOrder 订单vo
	 */
	public void sendPaymentResult(PaymentHistory ph, String orderNo);
	
	/**
	 * 发送支付成功的email给商店客服
	 * @param ph 支付历史vo
	 * @param salesOrder 订单vo
	 */
	public void sendPaymentResultToStore(PaymentHistory ph, String orderNo);
	
	/**
	 * 对已经下单的订单,进行使用[订单专用礼券]和[普通礼券]
	 * 
	 * @param 成功的为true,否则为false
	 */
	public boolean doUseSalesOrderGiftCertificate(SalesOrder salesOrder, String gcNo, String ipAddress);
	
	
	/**
	 * 确认是否全部已分配
	 * @param orderSku
	 */
	public void updateStatusForReallocated(OrderSku orderSku);
}
