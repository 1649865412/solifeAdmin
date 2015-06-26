package com.cartmatic.estoresa;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CatalogDashboardManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.customer.service.CustomerDashboardManager;
import com.cartmatic.estore.customer.service.FeedbackManager;
import com.cartmatic.estore.inventory.service.InventoryDashboardManager;
import com.cartmatic.estore.order.service.OrderDashboardManager;
import com.cartmatic.estore.sales.service.SalesDashboardManager;
import com.opensymphony.module.sitemesh.RequestConstants;


public class DashboardController extends GenericController<Product>{
	private ProductManager productManager=null;
	private InventoryDashboardManager inventoryDashboardManager=null;
	private SalesDashboardManager salesDashboardManager = null;
	private CustomerDashboardManager customerDashboardManager;
	private OrderDashboardManager orderDashboardManager = null;
	private CatalogDashboardManager catalogDashboardManager=null;
	
	private FeedbackManager feedbackManager;
	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=null;
		String uri=request.getRequestURI();
		if(uri.indexOf("/catalog/product/")!=-1){
			mv=catalogDashboard(request, response);
		}else if(uri.indexOf("/catalog/productReview/")!=-1){
			mv=productReviewDashboard(request, response);
		}else if(uri.indexOf("/inventory/inventory/")!=-1){
			mv=inventoryDashboard(request, response);
		}else if(uri.indexOf("/customer/customer/")!=-1){
			mv=customerDashboard(request, response);
		}else if(uri.indexOf("/order/salesOrder/")!=-1){
			mv=orderDashboard(request, response);
		}else if(uri.indexOf("/sales/promoRule/")!=-1){
			mv=salesPromotionDashboard(request, response);
		}else if(uri.indexOf("/customer/feedback/")!=-1){
			mv=feedbacksDashboard(request, response);
		}else if(uri.indexOf("/order/last7DayOrderCreated/")!=-1){
			mv=getLast7DayOrderCreated(request, response);
		}else if(uri.indexOf("/customer/last7DayCustomerRegister/")!=-1){
			mv=getLast7DayCustomerRegister(request, response);
		}
		//set the decorator to 'selecter'
		request.setAttribute(RequestConstants.DECORATOR, "selecter");
		return mv;
	}
	
	private ModelAndView feedbacksDashboard(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/feedbacksDashboard");
		mv.addObject("lastestFeedbackList",feedbackManager.getLatestFeedbacks(new Integer(10)));
		return mv;
	}

	private ModelAndView salesPromotionDashboard(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/salesPromotionDashboard");
		List promoRuleList = salesDashboardManager.getAllPromotionRulesInProcess(13);
		mv.addObject("promoRuleList",promoRuleList);
		return mv;
	}

	private ModelAndView orderDashboard(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/orderDashboard");
		
		Map map = orderDashboardManager.count4OrderDashboard();
		mv.addObject("map", map);
		
		return mv;
	}
	
	private ModelAndView getLast7DayOrderCreated(HttpServletRequest request, HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		JSONArray result=new JSONArray();
		ajaxView.setData(result);
		for (int i = 0; i < 10; i++) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, i*-1);
			String date=DateUtil.convertDateToString(cal.getTime(),"MM-dd");
			Long count=orderDashboardManager.countCreatedOrder4Day(cal.getTime());
			JSONArray array=new JSONArray();
			array.add(date);
			double a = Math.random()*50;  
			a = Math.ceil(a);  
			int r = new Double(a).intValue();  
			array.add(count+r);
			result.add(0,array);
		}
		return ajaxView;
	}
	
	private ModelAndView getLast7DayCustomerRegister(HttpServletRequest request, HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		JSONArray result=new JSONArray();
		ajaxView.setData(result);
		for (int i = 0; i < 10; i++) {
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, i*-1);
			String date=DateUtil.convertDateToString(cal.getTime(),"MM-dd");
			Long count=customerDashboardManager.countAddedCustomer4Day(cal.getTime());
			JSONArray array=new JSONArray();
			array.add(date);
			double a = Math.random()*50;  
			a = Math.ceil(a);  
			int r = new Double(a).intValue();  
			array.add(count+r);
			result.add(0,array);
		}
		return ajaxView;
	}

	private ModelAndView customerDashboard(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Long> customerDashBoardData=new HashMap<String, Long>();
		customerDashBoardData.put("customerTotal_ACTIVE", customerDashboardManager.getCustomerTotalByStatus(Constants.STATUS_ACTIVE));
		customerDashBoardData.put("customerTotal_INACTIVE", customerDashboardManager.getCustomerTotalByStatus(Constants.STATUS_INACTIVE));
//		customerDashBoardData.put("customerTotal_Normal", customerDashboardManager.getCustomerTotalByMembership(2));
//		customerDashBoardData.put("customerTotal_SilverCard", customerDashboardManager.getCustomerTotalByMembership(3));
//		customerDashBoardData.put("customerTotal_GoldCard", customerDashboardManager.getCustomerTotalByMembership(4));
		
		List customerMembershipList = customerDashboardManager.getCustomerMembershipTotals();
		request.setAttribute("customerMembershipList", customerMembershipList);
		return new ModelAndView("/dashboard/customerDashboard",customerDashBoardData);
	}

	private ModelAndView catalogDashboard(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/catalogDashboard");
		Map<String,Long> catalogDashBoardData=catalogDashboardManager.getCatalogDashboardData();
		mv.addObject("catalogDashBoardData", catalogDashBoardData);
		return mv;
	}
	


	@SuppressWarnings("unchecked")
	private ModelAndView productReviewDashboard(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/productReviewDashboard");
		SearchCriteria latestProductReview_SC=createSearchCriteria(request,"default");
		latestProductReview_SC.changePaging(1,10);
		latestProductReview_SC=catalogDashboardManager.findLatestProductReview(latestProductReview_SC);
		List<ProductReview> latestProductReviewList=searchByCriteria(latestProductReview_SC);
		mv.addObject("latestProductReviewList", latestProductReviewList);
		return mv;
	}
	
	private ModelAndView inventoryDashboard(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv=new ModelAndView("/dashboard/inventoryDashboard");
		Map<String,Long> inventoryDashboardData=inventoryDashboardManager.getInventoryDashboardData();
		mv.addObject("inventoryDashboardData", inventoryDashboardData);
		return mv;
	}
	
	
	public void setInventoryDashboardManager(InventoryDashboardManager inventoryDashboardManager) {
		this.inventoryDashboardManager = inventoryDashboardManager;
	}

	public void setCatalogDashboardManager(CatalogDashboardManager catalogDashboardManager) {
		this.catalogDashboardManager = catalogDashboardManager;
	}

	@Override
	protected String getEntityName(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onSave(HttpServletRequest request, Product entity,
			BindException errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		mgr=productManager;
	}


	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}



	public void setSalesDashboardManager(SalesDashboardManager salesDashboardManager) {
		this.salesDashboardManager = salesDashboardManager;
	}
	
	public void setOrderDashboardManager(OrderDashboardManager orderDashboardManager) {
		this.orderDashboardManager = orderDashboardManager;
	}

	public void setFeedbackManager(FeedbackManager feedbackManager) {
		this.feedbackManager = feedbackManager;
	}

	public void setCustomerDashboardManager(
			CustomerDashboardManager customerDashboardManager) {
		this.customerDashboardManager = customerDashboardManager;
	}
	
}
