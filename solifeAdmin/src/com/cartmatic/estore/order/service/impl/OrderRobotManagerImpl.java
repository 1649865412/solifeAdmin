/**
 * 
 */
package com.cartmatic.estore.order.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.service.OrderProcessFlowManager;
import com.cartmatic.estore.order.service.OrderRobotManager;
import com.cartmatic.estore.order.service.SalesOrderManager;

/**
 * @author ryan
 */
public class OrderRobotManagerImpl implements OrderRobotManager {

	protected final transient Log	logger	= LogFactory.getLog(getClass());
	
	private SalesOrderManager salesOrderManager;
	
	private OrderProcessFlowManager orderProcessFlowManager = null;
	
	public void setOrderProcessFlowManager(OrderProcessFlowManager orderProcessFlowManager) {
		this.orderProcessFlowManager = orderProcessFlowManager;
	}

	public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}

	public void doOrderReview() 
	{
		int pageNo = 1;
		//SearchCriteria sc = salesOrderManager.getSearchCriteriaBuilder("awaitingRobotConfirming").
		//					buildSearchCriteria(new MockHttpServletRequest(), 10);
		SearchCriteria sc = SearchCriteria.getHqlPagingInstance("", null, pageNo, 20, null);
		while(true)
		{
			//List<SalesOrder> orders = salesOrderManager.searchByCriteria(sc);
			List<OrderShipment> shipments = salesOrderManager.getOrderShipment4RobotReview(sc);
			if(shipments == null || shipments.size()==0)
				break;
			
			for(OrderShipment orderShipment :shipments)
			{
				int result = orderProcessFlowManager.doConfirmShipmentByRobot(orderShipment);
				if (logger.isDebugEnabled())
				{
					logger.debug("doConfirmShipmentByRobot:"+ result);
				}
//				orderShipment.setHasRobotReviewed(Constants.FLAG_TRUE);
//				SalesOrder salesOrder = orderShipment.getSalesOrder();
//				if(salesOrderManager.hasSimilarOrderShipment(salesOrder, orderShipment, orderShipment.getOrderAddress())){
//					orderProcessFlowManager.doConfirmShipmentByRobot(orderShipment);
//					orderShipment.setIsConfirmedByRobot(Constants.FLAG_TRUE);
//					if(logger.isInfoEnabled())
//						logger.info(">>>>>>>>>>>>>>>OrderShipment#"+orderShipment.getShipmentNo()+" is confirmed by Order Robot!");
//				}else{
//					orderShipment.setIsConfirmedByRobot(Constants.FLAG_FALSE);
//					if(logger.isDebugEnabled())
//						logger.debug(">>>>>>>>>>>>>>>OrderShipment#"+orderShipment.getShipmentNo()+" is not confirmed by Order Robot!");
//				}
//				salesOrderManager.save(orderShipment);
				
			}
			salesOrderManager.flush();
			sc.changePaging(pageNo++, 20);
		}
	}
	
}
