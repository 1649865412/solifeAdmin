/**
 * 
 */
package com.cartmatic.estore.order;

/**
 * @author pengzhirong
 *
 */
public class OrderConstants {
	
	/**订单状态*/
	public final static Short ORDER_STATUS_IN_PROGRESS = new Short("10");
	public final static Short ORDER_STATUS_PARTIALLY_SHIPPED = new Short("20");
	public final static Short ORDER_STATUS_COMPLETE = new Short("30");
	public final static Short ORDER_STATUS_AWAITING_RETURN = new Short("40");
	
	/**此状态值不会出现在数据库中，只在页面处理时用到，暂停处理由SalesOrder.isOnHold决定*/
	public final static Short STATUS_ON_HOLD = new Short("70");
	public final static Short STATUS_CANCELLED = new Short("80");
	
	
	/**发货项状态*/
	public final static Short SHIPMENT_STATUS_AWAITING_INVENTORY = new Short("10");
	public final static Short SHIPMENT_STATUS_INVENTORY_ASSIGNED = new Short("20");
	public final static Short SHIPMENT_STATUS_AWAITING_PAYMENT = new Short("30");
	public final static Short SHIPMENT_STATUS_PICKING_AVAILABLE = new Short("40");
	public final static Short SHIPMENT_STATUS_PICKING = new Short("50");
	public final static Short SHIPMENT_STATUS_SHIPPED = new Short("60");
	
	/**支付状态*/
	public final static Short PAYMENT_STATUS_UNPAID=new Short("10");
	public final static Short PAYMENT_STATUS_PARTIALLY_PAID=new Short("20");
	public final static Short PAYMENT_STATUS_PAID=new Short("30");
	
	/** 支付事务类型－在线支付*/
	public final static Short TRANSACTION_TYPE_ONLINE=new Short("1");
	/** 支付事务类型－现金支付*/
	public final static Short TRANSACTION_TYPE_CASH=new Short("2");
	/** 支付事务类型－邮局汇款*/
	public final static Short TRANSACTION_TYPE_POSTAL=new Short("3");
	/** 支付事务类型－购物积分支付*/
	public final static Short TRANSACTION_TYPE_SHOP_POINT=new Short("4");
	/** 支付事务类型－礼券支付*/
	public final static Short TRANSACTION_TYPE_GIFT_CERT=new Short("5");
	/** 支付事务类型－发货扣款*/
	public final static Short TRANSACTION_TYPE_SHIP=new Short("6");
	/** 支付事务类型－退货退款*/
	public final static Short TRANSACTION_TYPE_RETURN=new Short("7");
	/** 支付事务类型－货到付款*/
	public final static Short TRANSACTION_TYPE_COD=new Short("8");
	
	/**退换货原因*/
	public final static Short RETURN_REASON_FAULTY = new Short("10");
	public final static Short RETURN_REASON_UNWANTED_GIFT = new Short("20");
	public final static Short RETURN_REASON_INCORRECT_ITEM = new Short("30");
	public final static Short RETURN_REASON_OTHERS = new Short("40");
	
	/**退换货状态*/
	public final static Short RETURN_STATUS_AWAITING_RETURN = new Short("10");
	public final static Short RETURN_STATUS_AWAITING_COMPLETION = new Short("20");
	public final static Short RETURN_STATUS_COMPLETE = new Short("30");
	public final static Short RETURN_STATUS_CANCELLED = new Short("40");
	
	/**接收到的货物状态－完好无损*/
	public final static Short RECEIVED_STATUS_PERFECT = new Short("0");
	/**接收到的货物状态－有损坏*/
	public final static Short RECEIVED_STATUS_DEFECTIVE = new Short("1");
	
	/** 会员添加，申请取消、退换货*/
	public final static String AUDIT_TANSACTION_TYPE_CUSTOMER = "CUSTOMER";
	
	/** 客服代表或管理员*/
	public final static String AUDIT_TANSACTION_TYPE_ADMIN = "ADMINISTRATOR";
	
	/** 系统产生，审计*/
	public final static String AUDIT_TANSACTION_TYPE_SYS = "SYSTEM";
	
	public final static String SYS_ADDED_BY = "System";
	
	/** 退货类型－退货*/
	public final static Short RETURN_TYPE_RETURN = new Short("0");
	/** 退货类型－换货*/
	public final static Short RETURN_TYPE_EXCHANGE = new Short("1");
	
	/** 订单通知邮件类型-下订单*/
	public final static short MAIL_TYPE_PLACE_ORDER = 1;
	
	/** 订单通知邮件类型-发货*/
	public final static short MAIL_TYPE_SHIPPING = 2;
	
	/** 订单通知邮件类型-重新确认*/
	public final static short MAIL_TYPE_RECONFIRM = 3;
	
	public final static Short SALESORDER_SETTLEMENT_STATUS_NOT=new Short((short)0);
	public final static Short SALESORDER_SETTLEMENT_STATUS_DONE=new Short((short)1);
	
	/**订单取消类型定义**/
	/**
	 * 已在别处购买
	 */
	public final static short ORDER_CANCELTYPE_BOUGHT=1;	
	/**
	 * 价格太贵
	 */
	public final static short ORDER_CANCELTYPE_PRICEHIGHT=2;	
	/**
	 * 有缺货商品
	 */
	public final static short ORDER_CANCELTYPE_OUTOFSTOCK=3;	
	/**
	 * 等待太久
	 */
    public final static short ORDER_CANCELTYPE_WAIT=4;    
    /**
     * 错误重复下单
     */
    public final static short ORDER_CANCELTYPE_REPEAT=5;    
    /**
     * 其他原因
     */
    public final static short ORDER_CANCELTYPE_OTHER=6;
}
