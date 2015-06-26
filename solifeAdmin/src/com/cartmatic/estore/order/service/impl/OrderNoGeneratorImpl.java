/*
 * create date:2006-7-26
 */
package com.cartmatic.estore.order.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.order.dao.SalesOrderDao;
import com.cartmatic.estore.order.service.OrderNoGenerator;

/**
 * @author ChaoMin Yan 2006-7-26 TODO
 */
public class OrderNoGeneratorImpl  implements OrderNoGenerator
{
	private Log log = LogFactory.getLog(OrderNoGeneratorImpl.class);
	private SalesOrderDao salesOrderDao = null;
	private final static Object GenLock = new Object();


	private static String thisDate = "";

	private static int order_seq = -1;
	
	/*
	 * @see com.cartmatic.estore.sales.service.OrderNoGenerator#generateNo()
	 */
	public String generateOrderNo()
	{
		init();  //检查是否需要初始化
		String orderNo = "";
		String strNumber = "";
		int number = getOrder_seq();
		if (number < 10)
		{
			strNumber = "000" + number;
		}
		else if (number < 100)
		{
			strNumber = "00" + number;
		}
		else if (number < 1000)
		{
			strNumber = "0" + number;
		}
		else
		{
			strNumber = String.valueOf(number);
		}
		
		orderNo = getOrderNoPrefix() + DateUtil.fmtTodayToFive() + strNumber + getOrderNoSuffix();

		return orderNo;
	}

	private int getOrder_seq()
	{
		synchronized (GenLock)
		{
			order_seq++;
		}
		return order_seq;
	}

	/*private synchronized boolean isInit()
	{
		if (order_seq == -1)
		{
			return false;
		}
		else
		{
			if (!thisDate.equals(DateUtil.fmtTodayToFive()))
			{
				order_seq = 0;
				thisDate = DateUtil.fmtTodayToFive();
			}
			return true;
		}
	}*/

	private synchronized void init()
	{
		if (order_seq == -1)
		{
			String maxOrderNo = salesOrderDao.getMaxOrderNo();
			if (maxOrderNo == null || maxOrderNo.equals(""))
			{
				thisDate = DateUtil.fmtTodayToFive();
				order_seq = 0;
			}
			else
			{			
				try
				{
					String prefix=getOrderNoPrefix();
					String orderDate = maxOrderNo.substring(prefix.length(), prefix.length()+5);
					thisDate = DateUtil.fmtTodayToFive();
					if (orderDate.equals(thisDate))
					{
						String strMaxNumber = maxOrderNo.substring(prefix.length()+5, prefix.length()+9);
						int maxNumber = Integer.valueOf(strMaxNumber).intValue();
						order_seq = maxNumber;
					}
					else
					{
						order_seq = 0;
					}
				}
				catch (Exception e)			
				{
					order_seq=0;
				}
			}
		}
		else
		{
			if (!thisDate.equals(DateUtil.fmtTodayToFive()))
			{
				order_seq = 0;
				thisDate = DateUtil.fmtTodayToFive();
			}
			
		}		
	}
	private String getOrderNoPrefix(){
		return ConfigUtil.getInstance().getOrderNoPrefix();
	}
	
	private String getOrderNoSuffix(){
		return ConfigUtil.getInstance().getOrderNoSuffix();
	}
	
	public void setSalesOrderDao(SalesOrderDao avalue)
	{
		salesOrderDao = avalue;
	}
	
	public static void main(String[] args){
		String maxOrderNo="S071520008";
		System.out.println(maxOrderNo.substring("S".length(), "S".length() +5));
		System.out.println(maxOrderNo.substring("S".length()+5, "S".length() +5+4));
	}
}
