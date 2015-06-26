package com.cartmatic.estore.order.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.dao.SalesOrderDao;

/**
 * Dao implementation for SalesOrder.
*/
public class SalesOrderDaoImpl extends HibernateGenericDaoImpl<SalesOrder> implements SalesOrderDao {

	public String getMaxOrderNo() {
		String orderNoPrefix = ConfigUtil.getInstance().getOrderNoPrefix();
		String orderNoSuffix = ConfigUtil.getInstance().getOrderNoSuffix();
		String hql="select so.orderNo from SalesOrder so where orderNo like ? order by so.salesOrderId desc".intern();
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance(hql.toString(), new Object[]{orderNoPrefix+"%"+orderNoSuffix}, 1, 1, null);
		List<String> list = this.searchByCriteria(sc);
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
	
	
	/**
	 * 通过会员ID与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,String orderNo, Serializable userId){
		return (SalesOrder)this.findUnique("select so from SalesOrder so where so.store.storeId=? and so.orderNo=? and so.customer.appuserId=?", storeId,orderNo, new Integer(userId.toString()));
	}
	
	/**
	 * 通过会员email与订单编号获取订单
	 * @param orderNo 订单编号
	 * @param email 前台会员Email
	 * @return
	 */
	public SalesOrder getSalesOrder(String orderNo, String email)
	{
		return (SalesOrder)this.findUnique("select so from SalesOrder so where so.orderNo=? and so.customerEmail=?", orderNo, email);
	}
	/**
	 * 通过会员ID与订单ID获取订单
	 * @param salesOrderId 订单ID
	 * @param userId 前台会员ID
	 * @return
	 */
	public SalesOrder getSalesOrder(Integer storeId,Serializable salesOrderId, Serializable userId){
		return (SalesOrder)this.findUnique("select so from SalesOrder so where so.store.storeId=? and so.salesOrderId=? and so.customer.appuserId=?", storeId,new Integer(salesOrderId.toString()), new Integer(userId.toString()));
	}
	
	public long countCustomerOrder(Integer userId, boolean complete){
		String hql = "from SalesOrder so where so.customer.appuserId=?";
		List params = new ArrayList();
		params.add(userId);
		if(complete){
			hql += " and so.orderStatus=?";
			params.add(OrderConstants.ORDER_STATUS_COMPLETE);
		}
		return this.countByHql(hql, params.toArray());
	}
	
	public void clearAll(){
		this.clear();
	}
	
	/**
	 * 后台主页订单统计
	 * @return 统计项key-value对
	 */
	public Map<String, Long> count4OrderDashboard(){
		Map<String, Long> map = new HashMap<String, Long>();
		//处理中订单
		Long countInProgress = this.countByHql("select count(*) from SalesOrder so where so.orderStatus=?", OrderConstants.ORDER_STATUS_IN_PROGRESS);
		map.put("countInProgress", countInProgress);
		
		//部分发货的订单
		Long countPartiallyShipped = this.countByHql("select count(*) from SalesOrder so where so.orderStatus=?", OrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED);
		map.put("countPartiallyShipped", countPartiallyShipped);
		
		//完成的订单
		Long countComplete = this.countByHql("select count(*) from SalesOrder so where so.orderStatus=?", OrderConstants.ORDER_STATUS_COMPLETE);
		map.put("countComplete", countComplete);
		
		//等待原货退回的订单
		Long countAwaitingReturn = this.countByHql("select count(*) from SalesOrder so where so.orderStatus=?", OrderConstants.ORDER_STATUS_AWAITING_RETURN);
		map.put("countAwaitingReturn", countAwaitingReturn);
		
		//暂停处理的订单
		Long countOnHold = this.countByHql("select count(*) from SalesOrder so where so.isOnHold=?", Constants.FLAG_TRUE);
		map.put("countOnHold", countOnHold);
		
		//已取消的订单
		Long countCancelled = this.countByHql("select count(*) from SalesOrder so where so.orderStatus=?", OrderConstants.STATUS_CANCELLED);
		map.put("countCancelled", countCancelled);
		
		
		
		//缺货的发货项
		Long countAwaitingInventory = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_AWAITING_INVENTORY);
		map.put("countAwaitingInventory", countAwaitingInventory);
		
		//库存已分配的发货项
		Long countInventoryAssigned = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_INVENTORY_ASSIGNED);
		map.put("countInventoryAssigned", countInventoryAssigned);
		
		//等待支付的发货项
		Long countAwaitingPayment = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_AWAITING_PAYMENT);
		map.put("countAwaitingPayment", countAwaitingPayment);
		
		//待备货的发货项
		Long countPickingAvailable = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_PICKING_AVAILABLE);
		map.put("countPickingAvailable", countPickingAvailable);
		
		//备货中的发货项
		Long countPicking = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_PICKING);
		map.put("countPicking", countPicking);
		
		//已发货的发货项
		Long countShipped = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.SHIPMENT_STATUS_SHIPPED);
		map.put("countShipped", countShipped);
		
		//已取消的发货项
		Long countShipmentCancelled = this.countByHql("select count(*) from OrderShipment os where os.status=?", OrderConstants.STATUS_CANCELLED);
		map.put("countShipmentCancelled", countShipmentCancelled);
		
		return map;
	}

	@SuppressWarnings("unchecked")
	public List<SalesOrder> getSalesOrder(List<String> orderNos) {
		if(orderNos==null || orderNos.size()==0)
			return null;
		StringBuffer sb=new StringBuffer();
		sb.append("from SalesOrder so where ");
		int index=0;
		int length=orderNos.size();
		for(Iterator it=orderNos.iterator();it.hasNext();){
			index++;
			String orderNo=it.next().toString();
			sb.append("so.orderNo ='" + orderNo + "' ");
			if(index<length){
				sb.append(" or ");
			}
		}
		sb.append(" order by orderNo desc");
		return findByHql(sb.toString());
	}


	public List<SalesOrder> findExpiredSalesOrder(Integer expireDate) {
		expireDate=Math.abs(expireDate);
		Calendar date=Calendar.getInstance(); 
		date.add(Calendar.DAY_OF_YEAR,expireDate*-1);
		String selectHql="from SalesOrder so where so.orderStatus=? and so.paymentStatus=? and so.createTime<?";
		List<SalesOrder> salesOrderList=findByHql(selectHql,new Object[]{ OrderConstants.ORDER_STATUS_IN_PROGRESS,OrderConstants.PAYMENT_STATUS_UNPAID,date.getTime()});
		return salesOrderList;
	}


	@Override
	public long countCreatedCustomer4Time(Date beginDate, Date endDate) {
		Long result = this.countByHql("select count(*) from SalesOrder so where so.createTime>=? and so.createTime<=? ", beginDate,endDate);
		return result;
	}


	@Override
	public List<SalesOrder> getSalesOrder(Integer userId, Serializable paymentStatus, Date begin, Date end) {
		// TODO Auto-generated method stub
		String hql = "from SalesOrder so where so.customer.appuserId=? and so.paymentStatus=? and so.updateTime>=? and so.updateTime<=?";
		List<SalesOrder> list = findByHql(hql,new Object[]{ userId,paymentStatus,begin,end });
		return list;
	}
	
}
