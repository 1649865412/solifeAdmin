package com.cartmatic.estore.order.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderAudit;
import com.cartmatic.estore.common.model.order.OrderPayment;
import com.cartmatic.estore.common.model.order.OrderPromotion;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.vo.OrderFilter;

/**
 * 订单基础接口，主要处理显示与编辑
 * 
 * @author pengzhirong
 *
 */
public interface SalesOrderManager extends GenericManager<SalesOrder> {
		
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId);
	
	/**
	 * 通过会员Email与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(String orderNo, String email);
	
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId);
	
	/**
	 * 获取所有订单的发货项，按shipmentNo排序
	 * @param salesOrderId
	 * @return
	 */
	public List<OrderShipment> getAllShipmentsByOrderId(Serializable salesOrderId);
	

	/**
	 * 统计待备货的发货项
	 * @return
	 */
	public Integer countShipments4Picking();
	
	/**
	 * 获取待备货的发货项
	 * @return
	 */
	public List<OrderShipment> getShipments4Picking();
	
	/**
	 *  获取指定订单下的未确认备货的发货项的ID与编码
	 * @param salesOrderId
	 * @param orderShipmentId 不包含在内的发货项ID
	 * @return
	 */
	public Map<Integer, String> getShipments4MoveItemById(Serializable salesOrderId, Serializable orderShipmentId);
	
	/**
	 * 添加商品到指定的发货项中
	 * @param orderShipmentId
	 * @param productSkuId
	 * @param curUser
	 * @return 1 - 成功
	 * 		   0 - 参数异常
	 * 		  -1 - 业务异常，订单未被当前用户锁住或被暂停处理或发货项已确订可备货
	 * 		  -2 - 业务异常，该产品可售数量（包括可预订数量）为零，不可销售！
	 */
	public int addItem(Serializable orderShipmentId, Serializable productSkuId, AppUser curUser) throws OutOfStockException;
	
	/**
	 * 获取订单并加锁
	 * @param salesOrderId
	 * @return
	 */
	public SalesOrder doGetAndLockById(Serializable salesOrderId);
	
	/**
	 * 获取订单并设置加锁权限数据
	 * @param salesOrderId
	 * @return
	 */
	public SalesOrder getForViewById(Serializable salesOrderId);
	
	/**
	 * 保存发货项
	 * @param orderShipment
	 */
	public void save(OrderShipment orderShipment);
	
	/**
	 * 保存订单地址
	 * @param orderAddress
	 */
	public void save(OrderAddress orderAddress);
	
	/**
	 * 保存订单商品项
	 * @param orderSku
	 */
	public void save(OrderSku orderSku);
	
	/**
	 * 保存订单备注审计
	 * @param orderAudit
	 */
	public void save(OrderAudit orderAudit);
	
	public void save(OrderPayment orderPayment);
	
	public void save(OrderPromotion orderPromotion);
	
	public OrderShipment getOrderShipmentById(Serializable orderShipmentId);
	
	public OrderAddress getOrderAddressById(Serializable orderAddressId);
	
	public OrderSku getOrderSkuById(Serializable orderSkuId);
	
	public OrderShipment getOrderShipmentByShipmentNo(Serializable shipmentNo);
	
	/**
	 * 移动商品
	 * @param orderSkuId
	 * @param toOrderShipmentId
	 * @param quantity
	 * @return 商品(sku)最终移动到的发货项
	 */
	public OrderShipment doMoveItem(Serializable orderSkuId, Serializable toOrderShipmentId, Integer quantity);
	
	/**
	 *	移除发货项中的商品
	 * @param orderSkuId
	 */
	public void doRemoveItem(Serializable orderSkuId);
	
	/**
	 * 管理员添加备注
	 * @param salesOrderId
	 * @param detail
	 */
	public void addNote(Serializable salesOrderId, String detail);
	
	/**
	 * 编辑备注
	 * @param orderAuditId
	 * @param detail
	 */
	public void editNote(Serializable orderAuditId, String detail);
	
	
	/**
	 * 保存订单所有可编辑的信息
	 * @param params 页面表单参数
	 * @param out_exceptionMsg 业务异常信息
	 * @return 1 成功
	 * 		  -1 异常
	 * 	  	  -2 某商品可销售(包括预订)量不能满足新增订购数量
	 */
	public int saveAll(Map params, StringBuilder out_exceptionMsg) throws OutOfStockException;
	
	/**
	 * 解锁订单
	 * @param salesOrder
	 */
	public void unlock(SalesOrder salesOrder);
	
	/**
	 * 返回指定会员的订单数
	 * @param userId
	 * @return
	 */
	public long countCustomerOrder(Serializable userId);
	
	/**
	 * 获取最近的支付记录
	 * @param salesOrderId
	 * @return
	 */
	public OrderPayment getLatestOrderPayment(Serializable salesOrderId);
	
	/**
	 * 搜索提示功能，返回指定前缀的前N条记录
	 * @param prefix
	 * @param pageSize
	 * @return
	 */
	public List<String> autoCompleteShipmentNo(String prefix, Integer pageSize);
	
	/**
	 * 获取指定商品的在等待库存分配的订单SKU
	 * @param productSkuId
	 * @return
	 */
	public List<OrderSku> getOrderSkuAwaitingInventoryByProductSkuId(Integer productSkuId);
	
	/**
	 * 获取最大的订单号
	 * @return
	 */
	public String getMaxOrderNo();
	
	/**
	 * 发送订单通知邮件
	 * @param mailType 邮件类型 {@link OrderConstants}
	 * @param salesOrder
	 */
	public void sendNotificationEmail(Short mailType,SalesOrder salesOrder);

	/**
	 * 发送发货通知邮件
	 * @param salesOrder
	 * @param orderShipment 发货项
	 */
	public void sendShipmentNotificationEmail(SalesOrder salesOrder,OrderShipment orderShipment);
	
	/**
	 * 获取订单，用于智能审核
	 * @param sc
	 * @return
	 */
	public List<OrderShipment> getOrderShipment4RobotReview(SearchCriteria sc);
	
	/**
	 * 是否有类同的发货项
	 * @param salesOrder
	 * @param orderShipment
	 * @param shippingAddress
	 * @return
	 */
	//public boolean hasSimilarOrderShipment(SalesOrder salesOrder, OrderShipment orderShipment, OrderAddress shippingAddress);
	
	/**
	 * 计算发货项税费与运输费，当前只计算商品税与运输费
	 * @param orderShipment
	 * @param evict 供controller调用，仅起计算作用
	 */
	public void caculateTaxesAndShippingCost(OrderShipment orderShipment, boolean evict);
	
	public SalesOrder getSalesOrderByOrderNo(String orderNo);
	
	/**
	 * 列出指定会员的所有订单
	 * @param userId 前台会员ID
	 * @return
	 */
	public List<SalesOrder> getSalesOrderByUserId(Serializable userId);
	
	/**
	 * 根据订单号列出找出相应的订单
	 * @param orderNos
	 * @return
	 */
	public List<SalesOrder> getSalesOrder(List<String> orderNos);
	
	/**
	 * 保存更新物流跟踪号,运输成本
	 * @param orderShipmentId
	 * @param trackingNo
	 * @param shippingCostPaid
	 * @return
	 * @throws Exception
	 */
	public OrderShipment updateTrackingNoAndCostPaid(Integer orderShipmentId,String trackingNo,BigDecimal shippingCostPaid) throws Exception;
	
	/**
	 * 为订单调整库存(分配库存).完全支付时触发的库存调整，正常情况下只增加分配。（减少库存分配的是调整时立刻执行）
	 * @param salesOrder
	 * @throws OutOfStockException
	 */
	public void doAllocateQty4Order(SalesOrder salesOrder) throws OutOfStockException;
	
	/**
	 * 取消过期订单
	 */
	public void cancelExpireSalesOrder();
	
	/**
	 * 某用户在某段时间区间内已支付的订单
	 * @param userId
	 * @param paymentStatus
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<SalesOrder> getSalesOrder(Integer userId, Serializable paymentStatus, Date begin, Date end);
}
