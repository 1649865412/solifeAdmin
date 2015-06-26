/**
 * 
 */
package com.cartmatic.estore.report.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.dao.impl.HibernateDaoSupportExt;
import com.cartmatic.estore.order.OrderConstants;

/**
 * @author Ryan
 * 
 */
@SuppressWarnings("unchecked")
public class SimpleReportDaoImpl extends HibernateDaoSupportExt {
	private final String hql_getProductCount = "select count(*) from Product where deleted=0 and status=1";

	private final String hql_getProductCount_time = "select count(*) from Product where deleted=0 and status=1 and createTime between ? and ?";

	private final String hql_getCategoryCount = "select count(*) from Category where  status=1";

	private final String hql_getCategoryCount_time = "select count(*) from Category where status=1 and createTime between ? and ?";

	private final String hql_getSalesOrderCount = "select count(*) from SalesOrder";

	private final String hql_getSalesOrderCount_time = "select count(*) from SalesOrder where createTime between ? and ?";
	
	private final String hql_getSalesOrderCount_complete_time = "select count(*) from SalesOrder where createTime between ? and ? and orderStatus="+OrderConstants.ORDER_STATUS_COMPLETE;

	private final String hql_getProductStockCount = "select sum(p.stockQty) as stockQtyTotal from Product p where p.deleted=0 and p.status=1";

	private final String hql_getManufacturerCount = "select count(*) from Manufacturer m where m.status=1 and deleted=0";

	private final String hql_getPlacedSalesOrderCount = "select count(*) from SalesOrder s where s.orderStatus="+OrderConstants.ORDER_STATUS_IN_PROGRESS;

	private final String hql_getInProgressSalesOrderCount = "select count(*) from SalesOrder s where s.orderStatus="+OrderConstants.ORDER_STATUS_IN_PROGRESS;

	private final String hql_getCompleteSalesOrderCount = "select count(*) from SalesOrder s where s.orderStatus="+OrderConstants.ORDER_STATUS_COMPLETE;

	private final String hql_getCancelSalesOrderCount = "select count(*) from SalesOrder s where s.orderStatus="+OrderConstants.STATUS_CANCELLED;

	private final String hql_getCompleteSalesOrderTotalMoney = "select sum(s.total) from SalesOrder s where s.orderStatus="+OrderConstants.ORDER_STATUS_COMPLETE;

	private final String hql_getNotProcessedSalesOrders = "select orderNo,createTime,itemsCount,total from SalesOrder so where (so.orderStatus=? or so.orderStatus=? )  and so.paymentStatus=? order by createTime desc";

	private final String hql_getRegisterCustomerCount = "select count(*) from AppUser a where a.status=1 and a.userType=0 and deleted=0";

	private final String hql_getRegisterCustomerCount_time = "select count(*) from AppUser a where createTime between ? and ? and a.status=1 and a.userType=0 and deleted=0 ";

	private final String hql_getAdminCount = "select count(*) from AppUser a where a.status=1 and a.userType=1 and deleted=0";

	private final String hql_getPromotionCount = "select count(*) from Promotion where status=1 and ? BETWEEN starttime and expiretime";

	private final String scale_day = "dayofyear(s.updateTime)";

	private final String scale_week = "week(s.updateTime)";

	private final String scale_month = "month(s.updateTime)";

	private final String hql_getProductMoney = "select sum(stockqty*price) from Product where deleted=0 and status=1";

	private final String hql_getSalesStats = "select min(s.updateTime) as startDate,max(s.updateTime) as endDate,count(distinct s.customer) as customcount,"
			+ "count(distinct s.orderId) as transcount,count(distinct o.orderItemId) as transdtlcount,"
			+ "count(distinct o.product) as goodscount,sum(o.quantity*o.price) as summoney,"
			+ "sum(o.quantity*o.price)-sum(o.quantity*o.discountPrice) as dismoney,sum(o.quantity*o.costPrice) as costmoney"
			+ " from SalesOrder s,OrderItem o where s.orderId=o.salesOrder.orderId and s.updateTime between ? and ?"
			+ " and s.orderStatus = "
			+ OrderConstants.ORDER_STATUS_COMPLETE
			+ " group by ${dayScale} order by ${dayScale} desc";

	private final String hql_getHotSalesProduct = "select p.productId,p.productCode,p.productName,"
			+ "sum(o.quantity) as quantity,sum(o.quantity*o.price) as summoney"
			+ " from SalesOrder s,OrderItem o,Product p "
			+ "where s.orderId=o.salesOrder.orderId and o.product.productId=p.productId and s.updateTime between ? and ? "
			+ " and s.orderStatus = " + OrderConstants.ORDER_STATUS_COMPLETE
			+ " group by p.productId,p.productCode,p.productName order by sum(o.quantity) desc";

	private final String hql_getHotSalesCategory = "select c.categoryCode,c.categoryName,"
			+ "sum(o.quantity) as quantity,sum(o.quantity*o.price) as summoney"
			+ " from SalesOrder s,OrderItem o,ProductCategory pc,Category c "
			+ " where s.orderId=o.salesOrder.orderId and o.product.productId=pc.product.productId and pc.isMainCategory=1 and pc.category.categoryId=c.categoryId and s.updateTime between ? and ? "
			+ " and s.orderStatus = " + OrderConstants.ORDER_STATUS_COMPLETE
			+ " group by c.categoryId,c.categoryCode,c.categoryName order by sum(o.quantity) desc";

	private final String hql_getHotSalesManufacturer = "select m.manufacturerCode,m.manufacturerName,"
			+ "sum(o.quantity) as quantity,sum(o.quantity*o.price) as summoney"
			+ " from SalesOrder s,OrderItem o,Product p,Manufacturer m"
			+ " where s.orderId=o.salesOrder.orderId and o.product.productId=p.productId and p.manufacturer.manufacturerId=m.manufacturerId and s.updateTime between ? and ?"
			+ " and s.orderStatus = " + OrderConstants.ORDER_STATUS_COMPLETE
			+ " group by m.manufacturerId order by sum(o.quantity) desc";

	private final String hql_getNoStockProduct = "select productId,productCode,productName,stockQty,stockMinQuantity "
			+ "from Product	where deleted=0 and status=1 and (stockQty<=0 or (stockQty< stockMinQuantity and stockMinQuantity is not null))  order by stockMinQuantity";

	public Map getStoreStatsTimedModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execUniqueResultHql(model, "productCount1", hql_getProductCount_time, getStartAndEndTimeOfToday());
		execUniqueResultHql(model, "productCount2", hql_getProductCount_time, getStartAndEndTimeOfThisWeek());
		execUniqueResultHql(model, "productCount3", hql_getProductCount_time, getStartAndEndTimeOfThisMonth());
		execUniqueResultHql(model, "productCount4", hql_getProductCount_time, getStartAndEndTimeOfThisQuarter());
		execUniqueResultHql(model, "productCount5", hql_getProductCount_time, getStartAndEndTimeOfThisYear());
		execUniqueResultHql(model, "productCount6", hql_getProductCount_time, getStartAndEndTimeOfAllTime());

		execUniqueResultHql(model, "categoryCount1", hql_getCategoryCount_time, getStartAndEndTimeOfToday());
		execUniqueResultHql(model, "categoryCount2", hql_getCategoryCount_time, getStartAndEndTimeOfThisWeek());
		execUniqueResultHql(model, "categoryCount3", hql_getCategoryCount_time, getStartAndEndTimeOfThisMonth());
		execUniqueResultHql(model, "categoryCount4", hql_getCategoryCount_time, getStartAndEndTimeOfThisQuarter());
		execUniqueResultHql(model, "categoryCount5", hql_getCategoryCount_time, getStartAndEndTimeOfThisYear());
		execUniqueResultHql(model, "categoryCount6", hql_getCategoryCount_time, getStartAndEndTimeOfAllTime());

		execUniqueResultHql(model, "soCount1", hql_getSalesOrderCount_time, getStartAndEndTimeOfToday());
		execUniqueResultHql(model, "soCount2", hql_getSalesOrderCount_time, getStartAndEndTimeOfThisWeek());
		execUniqueResultHql(model, "soCount3", hql_getSalesOrderCount_time, getStartAndEndTimeOfThisMonth());
		execUniqueResultHql(model, "soCount4", hql_getSalesOrderCount_time, getStartAndEndTimeOfThisQuarter());
		execUniqueResultHql(model, "soCount5", hql_getSalesOrderCount_time, getStartAndEndTimeOfThisYear());
		execUniqueResultHql(model, "soCount6", hql_getSalesOrderCount_time, getStartAndEndTimeOfAllTime());

		execUniqueResultHql(model, "soCompleteCount1", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfToday());
		execUniqueResultHql(model, "soCompleteCount2", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfThisWeek());
		execUniqueResultHql(model, "soCompleteCount3", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfThisMonth());
		execUniqueResultHql(model, "soCompleteCount4", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfThisQuarter());
		execUniqueResultHql(model, "soCompleteCount5", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfThisYear());
		execUniqueResultHql(model, "soCompleteCount6", hql_getSalesOrderCount_complete_time,
				getStartAndEndTimeOfAllTime());

		execUniqueResultHql(model, "customerCount1", hql_getRegisterCustomerCount_time, getStartAndEndTimeOfToday());
		execUniqueResultHql(model, "customerCount2", hql_getRegisterCustomerCount_time, getStartAndEndTimeOfThisWeek());
		execUniqueResultHql(model, "customerCount3", hql_getRegisterCustomerCount_time, getStartAndEndTimeOfThisMonth());
		execUniqueResultHql(model, "customerCount4", hql_getRegisterCustomerCount_time,
				getStartAndEndTimeOfThisQuarter());
		execUniqueResultHql(model, "customerCount5", hql_getRegisterCustomerCount_time, getStartAndEndTimeOfThisYear());
		execUniqueResultHql(model, "customerCount6", hql_getRegisterCustomerCount_time, getStartAndEndTimeOfAllTime());

		// TODO:订单、库存等因为设计的原因，还没有办法按状态、时间来进行这种统计。目前主要支持能用createtime统计的数据，因为createtime是稳定的，而updateTime不稳定。库存数目和金额都是随时变化的，除非有详细的入库出库记录。订单处理也是类似。
		// execUniqueResultHql(model, "categoryCount", hql_getCategoryCount);
		// execUniqueResultHql(model, "attributeCount", hql_getProductCount);
		//
		// execUniqueResultHql(model, "sumStockProductAmt", hql_getProductMoney);
		// execUniqueResultHql(model, "sumStockProductQty", hql_getProductStockCount);
		// execUniqueResultHql(model, "manufacturerCount", hql_getManufacturerCount);

		return model;
	}

	public Map getStoreStatsModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execUniqueResultHql(model, "productCount", hql_getProductCount);
		execUniqueResultHql(model, "categoryCount", hql_getCategoryCount);
		execUniqueResultHql(model, "attributeCount", hql_getProductCount);
		execUniqueResultHql(model, "promotionCount", hql_getPromotionCount, DateUtil.getNow());

		execUniqueResultHql(model, "customerCount", hql_getRegisterCustomerCount);
		execUniqueResultHql(model, "adminCount", hql_getAdminCount);

		execUniqueResultHql(model, "sumStockProductAmt", hql_getProductMoney);
		execUniqueResultHql(model, "sumStockProductQty", hql_getProductStockCount);
		execUniqueResultHql(model, "manufacturerCount", hql_getManufacturerCount);

		execUniqueResultHql(model, "soCount", hql_getSalesOrderCount);
		execUniqueResultHql(model, "placedSoCount", hql_getPlacedSalesOrderCount);
		execUniqueResultHql(model, "processingSoCount", hql_getInProgressSalesOrderCount);
		execUniqueResultHql(model, "cancelledSoCount", hql_getCancelSalesOrderCount);
		execUniqueResultHql(model, "completedSoCount", hql_getCompleteSalesOrderCount);
		execUniqueResultHql(model, "sumCompletedSoAmt", hql_getCompleteSalesOrderTotalMoney);

		return model;
	}

	public Map getNoStockProductModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execListResultHql(model, "noStockProduct", hql_getNoStockProduct);

		return model;
	}

	public Map getHotSalesProductModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execListResultHql(model, "hotSalesProduct", hql_getHotSalesProduct, getTimeParams(params));

		return model;
	}

	public Map getHotSalesCategoryModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execListResultHql(model, "hotSalesCategory", hql_getHotSalesCategory, getTimeParams(params));

		return model;
	}

	public Map getHotSalesManufacturerModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execListResultHql(model, "hotSalesCategory", hql_getHotSalesManufacturer, getTimeParams(params));

		return model;
	}

	public Map getSalesStatsModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();
		String scale = (String) params.get("prm_scale");

		String hql = replaceHqlGroupByScaleOption(hql_getSalesStats, scale);
		execListResultHql(model, "salesStats", hql, getTimeParams(params));

		return model;
	}

	public Map getNotProcessedOrdersModel(Map params) {
		Map<String, Object> model = new HashMap<String, Object>();

		execListResultHql(model, "salesOrderList", hql_getNotProcessedSalesOrders,
				OrderConstants.ORDER_STATUS_IN_PROGRESS,OrderConstants.ORDER_STATUS_IN_PROGRESS,OrderConstants.PAYMENT_STATUS_PAID);

		return model;
	}

	private String replaceHqlGroupByScaleOption(String in_hql, String scale) {
		String hql = in_hql;
		if ("day".equals(scale)) {
			hql = hql.replace("${dayScale}", scale_day);
		} else if ("week".equals(scale)) {
			hql = hql.replace("${dayScale}", scale_week);
		} else {// month,default
			hql = hql.replace("${dayScale}", scale_month);
		}

		return hql;

	}

	private Object[] getTimeParams(Map params) {
		String timePeriodCode = (String) params.get("prm_timePeriod");
		int iTimePeriodCode = 0;

		if (timePeriodCode != null) {
			iTimePeriodCode = Integer.parseInt(timePeriodCode);
		}

		Object[] startAndEndTime = null;
		switch (iTimePeriodCode) {
		case 1:
			startAndEndTime = getStartAndEndTimeOfToday();
			break;
		case 2:
			startAndEndTime = getStartAndEndTimeOfThisWeek();
			break;
		case 3:
			startAndEndTime = getStartAndEndTimeOfThisMonth();
			break;
		case 4:
			startAndEndTime = getStartAndEndTimeOfThisQuarter();
			break;
		case 5:
			startAndEndTime = getStartAndEndTimeOfThisYear();
			break;
		case 6:
			startAndEndTime = getStartAndEndTimeOfAllTime();
			break;

		default:
			startAndEndTime = getStartAndEndTimeOfThisMonth();
			break;
		}

		return startAndEndTime;
	}

	private Object[] getStartAndEndTimeOfThisYear() {
		return new Date[] { DateUtil.getStartOfThisYear(), DateUtil.getEndOfThisYear() };
	}

	private Object[] getStartAndEndTimeOfThisQuarter() {
		return new Date[] { DateUtil.getStartOfThisQuarter(), DateUtil.getEndOfThisQuarter() };
	}

	private Object[] getStartAndEndTimeOfThisMonth() {
		return new Date[] { DateUtil.getStartOfThisMonth(), DateUtil.getEndOfThisMonth() };
	}

	private Object[] getStartAndEndTimeOfThisWeek() {
		return new Date[] { DateUtil.getStartOfThisWeek(), DateUtil.getEndOfThisWeek() };
	}

	private Object[] getStartAndEndTimeOfToday() {
		return new Date[] { DateUtil.getStartOfThisDay(), DateUtil.getEndOfThisDay() };
	}

	private Object[] getStartAndEndTimeOfAllTime() {
		return new Date[] { DateUtil.getStartOfAllTime(), DateUtil.getEndOfAllTime() };
	}

	private void execUniqueResultHql(Map<String, Object> model, String attrName, String hql, Object... values) {
		model.put(attrName, findUnique(hql, values));
	}

	/**
	 * List里面可以是VO或Map。最多取5个，暂时不打算支持配置显示多少条。
	 * 
	 * @param model
	 * @param attrName
	 * @param hql
	 * @param values
	 */
	private void execListResultHql(Map<String, Object> model, String attrName, String hql, Object... values) {
		model.put(attrName, find(hql, 0, 5, values));
	}
}