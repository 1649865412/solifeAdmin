/**
 * 
 */
package com.cartmatic.estore.order.vo;

import java.io.Serializable;
import java.util.Date;

import com.cartmatic.estore.common.util.DateUtil;

/**
 * @author pengzhirong
 * 
 * 订单搜索过滤字段类
 *
 */
public class OrderFilter implements Serializable {

	/** 订单编号*/
	private String orderNo;
	/** 会员ID*/
	private Integer customerId;
	private String customerFirstname;
	
	/** 地址表的电话号码*/
	private String phoneNumber;
	
	private String customerEmail;
	
	/**排序字段*/
	private String orderByField;
	
	private String postalcode;
	
	private Short orderStatus;
	
	private Short paymentStatus;
	
	/**
	 * 表示非指定的支付状态
	 */
	private Short notPaymentStatus;
	
	private Short shipmentStatus;
	
	private String rmaNo;
	
	private Short isOnHold;
	
	private Short isHoldByCustomer;
	
	private Short isConfirmedByRobot;
	
	private boolean underSearchView;
	
	/**下单时间段 - 起始*/
	private Date startTime;
	/**下单时间段 - 终止*/
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getStartTimeString(){
		if(startTime!=null)
			return DateUtil.getDate(startTime);
		return null;
	}
	
	public String getEndTimeString(){
		if(endTime!=null)
			return DateUtil.getDate(endTime);
		return null;
	}
	
	public boolean isUnderSearchView() {
		return underSearchView;
	}

	public void setUnderSearchView(boolean underSearchView) {
		this.underSearchView = underSearchView;
	}

	public Short getIsConfirmedByRobot() {
		return isConfirmedByRobot;
	}

	public void setIsConfirmedByRobot(Short isConfirmedByRobot) {
		this.isConfirmedByRobot = isConfirmedByRobot;
	}

	public Short getIsOnHold() {
		return isOnHold;
	}

	public void setIsOnHold(Short isOnHold) {
		this.isOnHold = isOnHold;
	}

	public Short getIsHoldByCustomer() {
		return isHoldByCustomer;
	}

	public void setIsHoldByCustomer(Short isHoldByCustomer) {
		this.isHoldByCustomer = isHoldByCustomer;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstname() {
		return customerFirstname;
	}

	public void setCustomerFirstname(String customerFirstname) {
		this.customerFirstname = customerFirstname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Short paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Short getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(Short shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public Short getNotPaymentStatus() {
		return notPaymentStatus;
	}

	public void setNotPaymentStatus(Short notPaymentStatus) {
		this.notPaymentStatus = notPaymentStatus;
	}

	public String getRmaNo() {
		return rmaNo;
	}

	public void setRmaNo(String rmaNo) {
		this.rmaNo = rmaNo;
	}

}
