package com.cartmatic.estore.common.model.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.base.SalesOrderTbl;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * Model class for SalesOrder. Add not database mapped fileds in this class.
 */
public class SalesOrder extends SalesOrderTbl {

  	/**
	 * Default Empty Constructor for class SalesOrder
	 */
	public SalesOrder () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； salesOrderName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSalesOrderName () {
		if (salesOrderId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.orderNo;
	}
	
	/**
	 * Default Key Fields Constructor for class SalesOrder
	 */
	public SalesOrder (
		 Integer in_salesOrderId
		) {
		super (
		  in_salesOrderId
		);
	}
	
	public void addOrderAudit(OrderAudit orderAudit){
		this.getOrderAudits().add(orderAudit);
	}
	
    /**当前加锁订单的管理员用户名*/
    private String lockedByUserName = null;
    
    /** 是否被当前用户锁住*/
    private boolean lockedByMyself = false;
    
	public boolean isLockedByMyself() {
		return lockedByMyself;
	}

	public void setLockedByMyself(boolean lockedByMyself) {
		this.lockedByMyself = lockedByMyself;
	}

	public String getLockedByUserName() {
		return lockedByUserName;
	}

	public void setLockedByUserName(String lockedByUserName) {
		this.lockedByUserName = lockedByUserName;
	}
	
	/**
	 * 换货订单中用到，更改时请注意
	 * @param isExchangeOrder
	 * @param orderStatus
	 * @param paymentStatus
	 * @param totalAmount
	 * @return
	 */
	public SalesOrder clone(Short isExchangeOrder, Short orderStatus, Short paymentStatus, BigDecimal totalAmount){
		SalesOrder so = new SalesOrder();
		so.setInvoiceTitle(this.getInvoiceTitle());
		so.setBillingAddressId(null);
		so.setCreateTime(new Date());
		so.setCustomerId(this.getCustomerId());
		so.setCustomerEmail(this.getCustomerEmail());
		so.setCustomerFirstname(this.getCustomerFirstname());
		so.setCustomerLastname(this.getCustomerLastname());
		so.setCustomerTitle(this.getCustomerTitle());
		so.setHasInvoice(this.getHasInvoice());
		so.setIpAddress("");
		so.setIsCod(this.getIsCod());
		so.setIsExchangeOrder(isExchangeOrder);
		so.setOrderStatus(orderStatus);
		so.setPaymentStatus(paymentStatus);
		so.setTotalAmount(totalAmount);
		so.setIsOnHold(Constants.FLAG_FALSE);
		so.setIsLocked(Constants.FLAG_FALSE);
		return so;
	}
	
	public boolean isExchangeOrder(){
		if(this.getIsExchangeOrder()!=null && this.getIsExchangeOrder().shortValue()==Constants.FLAG_TRUE)
			return true;
		return false;
	}
	/**换货订单的原订单ID*/
	protected Integer originalOrderId;
	
	public Integer getOriginalOrderId() {
		return this.getOriginalOrder()==null?null:this.getOriginalOrder().getOriginalOrderId();
	}

	public void setOriginalOrderId(Integer originalOrderId) {
		if (originalOrderId==null) {
			originalOrder = null;
	    } else {
	    	originalOrder = new SalesOrder(originalOrderId);
	    	originalOrder.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
	}
	
	private SalesOrder originalOrder;

	public SalesOrder getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(SalesOrder originalOrder) {
		this.originalOrder = originalOrder;
	}
	
	public boolean isCod(){
		if(this.getIsCod()==null || this.getIsCod().shortValue()==Constants.FLAG_FALSE.shortValue() || this.getIsCod().shortValue()==Constants.PAY_TRANSFER.shortValue()) 
			return false;
		
		return true;
	}
	
	public void updatePaymentStatus(){
		if(this.getPaidAmount().compareTo(BigDecimal.ZERO)==1){
			int rtn = this.getPaidAmount().compareTo(this.getTotalAmount());
			if(rtn==-1)
				this.setPaymentStatus(OrderConstants.PAYMENT_STATUS_PARTIALLY_PAID);
			else
				this.setPaymentStatus(OrderConstants.PAYMENT_STATUS_PAID);
		}
	}
	
	public void updateTotalAmount(){
		Set<OrderShipment> orderShipments = this.getOrderShipments();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for(OrderShipment orderShipment :orderShipments){
			if(orderShipment.getStatus().shortValue()!=OrderConstants.STATUS_CANCELLED.shortValue())
				totalAmount = totalAmount.add(orderShipment.getTotalAfterTax());
		}
		this.setTotalAmount(totalAmount);
	}
	
	public BigDecimal getActualBalance(BigDecimal curBalance){
		BigDecimal actualBalance = curBalance;
		Set<OrderShipment> orderShipments = this.getOrderShipments();
		for(OrderShipment orderShipment: orderShipments){
			if(orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE.shortValue()
					|| orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_PICKING.shortValue())
				actualBalance = actualBalance.add(orderShipment.getTotalAfterTax().negate());
		}
		
		return actualBalance;
	}
	
	public void updateOrderStatusByShipments(){
		Set<OrderShipment> oss = this.getOrderShipments();
		int total = oss.size();
		int completeNum = 0;
		int cancelledNum = 0;
		for(OrderShipment orderShipment: oss){
			if(orderShipment.getStatus().shortValue()==OrderConstants.SHIPMENT_STATUS_SHIPPED.shortValue()){
				completeNum++;
			}else if(orderShipment.getStatus().shortValue()==OrderConstants.STATUS_CANCELLED.shortValue()){
				cancelledNum++;
			}
		}
		if(completeNum>0 && completeNum+cancelledNum<total)
			this.setOrderStatus(OrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED);
		else if(completeNum>0 && completeNum+cancelledNum==total)
			this.setOrderStatus(OrderConstants.ORDER_STATUS_COMPLETE);
		else if(cancelledNum==total)
			this.setOrderStatus(OrderConstants.STATUS_CANCELLED);
	}
	
	//=========================================业务操作规则=================================================================
	/**
	 * 是否显示“支付”跳转链接
	 */
	public boolean getShowPaymentLink(){
		if( Constants.FLAG_TRUE.compareTo(this.getIsCod())==0)
			return false;
		
		if(this.getOrderStatus().compareTo(OrderConstants.ORDER_STATUS_AWAITING_RETURN)==0
				|| OrderConstants.STATUS_CANCELLED.compareTo(this.getOrderStatus())==0 
				|| OrderConstants.PAYMENT_STATUS_PAID.compareTo(this.getPaymentStatus())==0)
			return false;
		
		return true;
	}
	
	
	/**
	 * 是否可以申请取消订单
	 * @return true 允许申请取消订单
	 * 		   false 不允许
	 */
	public boolean getCancelOrderPermission4Customer(){
		if(this.getIsOnHold().shortValue()==Constants.FLAG_TRUE.shortValue()
				&&this.getIsHoldByCustomer().shortValue()==Constants.FLAG_TRUE.shortValue())
			return false;
		
		if(!(this.orderStatus.shortValue()==OrderConstants.ORDER_STATUS_IN_PROGRESS.shortValue()))
			return false;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -ConfigUtil.getInstance().getOrderRobotReviewDelayMinutes());
		if (cal.getTimeInMillis() > createTime.getTime())
		{
			return false;
		}
		
		/* 不需要判断ordershipemnt，只要判断时间就可以。
		Set<OrderShipment> oss = this.getOrderShipments();
		for(OrderShipment orderShipment: oss){
			//待备货状态前的流程状态,即未”确认可备货“前
			if(orderShipment.getStatus().shortValue() > OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED.shortValue())
				return false;
		}*/		
		return true;
	}
	
	/**
	 * 后台管理员是否有取消订单的权限
	 * @return
	 */
	public boolean getCancelOrderPermission(){
		if(this.getIsOnHold().shortValue()==Constants.FLAG_TRUE.shortValue()
				&&this.getIsHoldByCustomer().shortValue()==Constants.FLAG_TRUE.shortValue())
			return true;
		
		if(this.isExchangeOrder() && this.orderStatus.shortValue()==OrderConstants.ORDER_STATUS_AWAITING_RETURN.shortValue())
			return true;
		
		if(!(this.isLockedByMyself() && this.orderStatus.shortValue()==OrderConstants.ORDER_STATUS_IN_PROGRESS.shortValue()))
			return false;
		/* 就算是备货中,也可以取消订单.只是流程会复杂一点. 2009/07/24 Ryan Liu
		Set<OrderShipment> oss = this.getOrderShipments();
		for(OrderShipment orderShipment: oss){
			//备货中状态及其后的流程状态下（不包含已取消状态）不可取消订单
			if(orderShipment.getStatus().shortValue() >= OrderConstants.SHIPMENT_STATUS_PICKING.shortValue()
					&& orderShipment.getStatus().shortValue() != OrderConstants.STATUS_CANCELLED.shortValue())
				return false;
		}
		*/
		return true;
	}
	
	public boolean getHoldOrderPermission(){
		if(!this.isLockedByMyself() || isOnHold.shortValue()==Constants.FLAG_TRUE.shortValue() 
				|| orderStatus.shortValue()==OrderConstants.ORDER_STATUS_COMPLETE.shortValue()
				|| orderStatus.shortValue()==OrderConstants.STATUS_CANCELLED.shortValue()
				|| orderStatus.shortValue()==OrderConstants.ORDER_STATUS_AWAITING_RETURN.shortValue())
			return false;
		
		return true;
	}
	
	public boolean getResumeOrderPermission(){
		if(isOnHold.shortValue()!=Constants.FLAG_TRUE.shortValue() || orderStatus.shortValue()==OrderConstants.ORDER_STATUS_COMPLETE.shortValue())
			return false;
		
		return true;
	}
	
	public boolean getLockOrderPermission(){
		if(this.getIsOnHold().shortValue()==Constants.FLAG_TRUE.shortValue()
				|| this.getIsLocked().shortValue()==Constants.FLAG_TRUE.shortValue()
				|| orderStatus.shortValue()==OrderConstants.ORDER_STATUS_COMPLETE.shortValue()
				|| orderStatus.shortValue()==OrderConstants.STATUS_CANCELLED.shortValue())
			return false;
		
		return true;
	}
	
	
	public boolean getUnlockOrderPermission() {
		if(this.getIsLocked().shortValue()==Constants.FLAG_TRUE.shortValue() ){
			//当前用户加的锁或超级管理员角色
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			if(appUser.getAppuserId().intValue()==this.getLockedBy().intValue() || appUser.isContainAdminRole())
				return true;
		}
		
		return false;
	}


	/**
	 * 产品成本
	 * @return
	 */
	public BigDecimal getTotalProductCost() {
		BigDecimal totalProductCost = BigDecimal.ZERO;
		Set<OrderShipment> orderShipments=getOrderShipments();
		for (OrderShipment orderShipment : orderShipments) {
			Set<OrderSku> orderSkus=orderShipment.getOrderSkus();
			for (OrderSku orderSku : orderSkus) {
				// 注意：因为历史问题，产品成本价需要乘与0.8才是真正的成本。
				// 每个产品的成本
				if (orderSku.getCostPrice() != null)
				{
					BigDecimal costPrecent=new BigDecimal(ConfigUtil.getInstance().getPrecentOfProductCost()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal itemCost =orderSku.getCostPrice().multiply(new BigDecimal(orderSku.getQuantity())).multiply(costPrecent);
					totalProductCost = totalProductCost.add(itemCost);
				}
			}
		}
		return totalProductCost.setScale(2, BigDecimal.ROUND_HALF_UP);
	}


	/**
	 * 实际支付的运输成本
	 */
	public BigDecimal getTotalShippingCost() {
		BigDecimal totalShippingCost = BigDecimal.ZERO;
		Set<OrderShipment> orderShipments=getOrderShipments();
		for (OrderShipment orderShipment : orderShipments) {
			if (orderShipment.getShippingCostPaid() != null)
				totalShippingCost = totalShippingCost.add(orderShipment.getShippingCostPaid());
		}
		return totalShippingCost;
	}
	
	/**
	 * 应该支付的金额
	 */
	public BigDecimal getShouldPay()
	{
		return totalAmount.subtract(paidAmount);
	}
	
	/**
	 * 获得所有的订单消息
	 * @return
	 */
	public int getSumOfAllMsgs()
	{
		if (this.getOrderMessages() == null)
			return 0;
		return this.getOrderMessages().size();
	}
	
	/**
	 * 管理员所有未读的最新消息
	 * @return
	 */
	public int getSumAdminNewMsgs()
	{
		if (this.getOrderMessages() == null)
			return 0;
		Set<OrderMessage> msgs = this.getOrderMessages();
		int sum = 0;
		for (OrderMessage obj: msgs)
		{
			//来自客户的信息,所以customerId = createBy
			if (Constants.STATUS_NOT_PUBLISHED.equals(obj.getStatus())
					&& obj.getCustomerId().equals(obj.getCreateBy()))
			{
				sum ++;
			}
		}
		return sum;
	}
	
	/**
	 * 客户所有未读的最新消息
	 * @return
	 */
	public int getSumCustomerNewMsgs()
	{
		if (this.getOrderMessages() == null)
			return 0;
		Set<OrderMessage> msgs = this.getOrderMessages();
		int sum = 0;
		for (OrderMessage obj: msgs)
		{
			if (Constants.STATUS_NOT_PUBLISHED.equals(obj.getStatus())
					&& !obj.getCustomerId().equals(obj.getCreateBy()))
			{
				sum ++;
			}
		}
		return sum;
	}
}
