package com.cartmatic.estore.order.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.OrderAddress;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.OrderShipmentDao;

/**
 * Dao implementation for OrderShipment.
*/
public class OrderShipmentDaoImpl extends HibernateGenericDaoImpl<OrderShipment> implements OrderShipmentDao {
	
	/**
	 * 返回最新的orderShipment
	 * @param salesOrderId
	 * @return
	 */
	public List<OrderSku> getRecentShipments(int limit)
	{
		String hql = "from OrderSku s order by s.orderSkuId desc";
		return this.findByHqlPaged(hql,0,limit, null);
	}
	
	public List<OrderShipment> getAllShipmentsByOrderId(Integer salesOrderId){
		String hql = "from OrderShipment os where os.salesOrder.salesOrderId=? order by shipmentNo";
		return this.findByHql(hql, salesOrderId);
	}
	
	public Integer countShipments4Picking(){
		String hql = "select count(os.orderShipmentId) from OrderShipment os, SalesOrder so where os.salesOrder.salesOrderId=so.salesOrderId and os.status=? and so.isOnHold!=?";
		return new Integer(this.findUnique(hql, OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE,Constants.FLAG_TRUE).toString());
	}
	
	/**
	 * 获取待备货的发货项
	 * @return
	 */
	public List<OrderShipment> getShipments4Picking(){
		String hql = "select os, so.customerFirstname, so.customerLastname from OrderShipment os, SalesOrder so where os.salesOrder.salesOrderId=so.salesOrderId and os.status=? and so.isOnHold!=?";
		List list = this.findByHql(hql, OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE, Constants.FLAG_TRUE);
		List<OrderShipment> orderShipments = new ArrayList<OrderShipment>();
		if(list!=null)
			for(int i=0; i<list.size(); i++){
				Object[] array = (Object[])list.get(i);
				OrderShipment orderShipment = (OrderShipment)array[0];
				orderShipment.setCustomerFirstname((String)array[1]);
				orderShipment.setCustomerLastname((String)array[2]);
				orderShipments.add(orderShipment);
			}
		 
		return orderShipments;
	}
	
	public Map<Integer, String> getForMoveItemByOrderId(Integer salesOrderId, Integer orderShipmentId){
		String hql = "select os from OrderShipment os where os.salesOrder.salesOrderId=? and os.status<=? and os.orderShipmentId!=? order by os.shipmentNo";
		List<OrderShipment> rst = this.findByHql(hql, salesOrderId, OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED, orderShipmentId);
		Map<Integer, String> moveTo = new HashMap<Integer, String>();
		if(rst!=null)
			for(OrderShipment temp: rst){
				if(temp.hasPhysicalSku())
					moveTo.put(temp.getOrderShipmentId(), temp.getShipmentNo());
			}
		return moveTo;
	}
	
	/**
	 * 返回指定订单下的下一个发货项编码
	 * @param salesOrderId
	 * @return
	 */
	public String getNextShipmentNo(Integer salesOrderId){
		String hql = "select os.shipmentNo from OrderShipment os where os.salesOrder.salesOrderId=? order by os.shipmentNo desc";
		List rst = this.findByHql(hql, salesOrderId);
		String shipmentNo = (String)rst.get(0);
		String suffixNum = shipmentNo.substring(shipmentNo.length()-2, shipmentNo.length());
		String numStart = "-";
		String parseNum = "";
		if(suffixNum.startsWith("0")){
			numStart +="0"; 
			parseNum = suffixNum.substring(1);
		}
		else
		{
			parseNum = suffixNum;
		}
		
		return shipmentNo.replaceAll("-"+suffixNum, numStart+(Integer.parseInt(parseNum)+1));
		
	}
	
	/**
	 * 搜索提示功能，返回指定前缀的前N条记录
	 * @param prefix
	 * @param pageSize
	 * @return
	 */
	public List<String> autoCompleteShipmentNo(String prefix, Integer pageSize){
		if(pageSize==null) pageSize = 8;
		String hql = "select os.shipmentNo from OrderShipment os, SalesOrder so where os.shipmentNo like ? and os.status=? and os.salesOrder.salesOrderId=so.salesOrderId and so.isOnHold!=?";
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance(hql.toString(), new Object[]{"%"+prefix, OrderConstants.SHIPMENT_STATUS_PICKING, Constants.FLAG_TRUE}, 1, pageSize, null);
		return this.searchByCriteria(sc);
	}
	
	/**
	 * 获取订单，用于智能审核
	 * @param sc
	 * @return
	 */
	public List<OrderShipment> getOrderShipment4RobotReview(SearchCriteria sc){
		//N分钟前
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -ConfigUtil.getInstance().getOrderRobotReviewDelayMinutes());
		Date dt1 = cal.getTime();
		
		String hql = "select os from SalesOrder so, OrderShipment os where so.salesOrderId=os.salesOrder.salesOrderId and so.paymentStatus=? and so.isOnHold=0 and so.isLocked=0 and os.status=? and so.createTime<?";
		           // select so from SalesOrder so, OrderShipment os where so.salesOrderId=os.salesOrder.salesOrderId and so.paymentStatus=30 and os.status=20 and so.isOnHold = 0 order by so.salesOrderId desc
		return this.find(hql, sc.getStartIdx(), sc.getFetchSize(), OrderConstants.PAYMENT_STATUS_PAID, OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED, dt1);
	}
	
	/**
	 * 是否有类同的发货项，对比email与地址
	 * @param salesOrder
	 * @param orderShipment
	 * @param shippingAddress
	 * @return
	 */
	public boolean hasSimilarOrderShipment(SalesOrder salesOrder, OrderShipment orderShipment, OrderAddress shippingAddress){
		if(shippingAddress==null) return false;
		String hql = "select os from SalesOrder so, OrderShipment os, OrderAddress oa where so.salesOrderId=os.salesOrder.salesOrderId and os.orderAddress.orderAddressId is not null and os.orderAddress.orderAddressId=oa.orderAddressId" +
				" and os.shipmentNo!=? and so.customerEmail=? and so.orderStatus=? and oa.country=? and oa.state=? and oa.city=? and oa.address1=? and oa.address2=?";
		Long countNum = this.countByHql(hql, orderShipment.getShipmentNo(), salesOrder.getCustomerEmail(), OrderConstants.ORDER_STATUS_COMPLETE,
				shippingAddress.getCountry(), 
				shippingAddress.getState(),
				shippingAddress.getCity(),
				shippingAddress.getAddress1(),
				shippingAddress.getAddress2());
		
		if(countNum!=null && countNum.longValue()>0)
			return true;
		
		return false;
	}
}
