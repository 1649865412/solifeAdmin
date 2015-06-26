package com.cartmatic.estoresa.order.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.order.OrderReturnSku;
import com.cartmatic.estore.common.model.order.OrderShipment;
import com.cartmatic.estore.common.model.order.OrderSku;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.exception.OutOfStockException;
import com.cartmatic.estore.order.OrderConstants;
import com.cartmatic.estore.order.service.OrderReturnManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class OrderReturnController extends GenericController<OrderReturn> {
	private String receiveReturnView = null;
	
    private OrderReturnManager orderReturnManager = null;
    
    private SalesOrderManager salesOrderManager = null;

    public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}

	public void setOrderReturnManager(OrderReturnManager inMgr) {
        this.orderReturnManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(OrderReturn entity) {
		return entity.getOrderReturnName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = orderReturnManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, OrderReturn entity, BindException errors) {
	}
	
	/**
	 * 创建退货-打开退货窗口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView createReturn(HttpServletRequest request,
			HttpServletResponse response) {
		String orderShipmentId = request.getParameter("orderShipmentId");
		OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(orderShipmentId);
		Set<OrderSku> orderSkus = orderShipment.getOrderSkus();
		for(OrderSku orderSku :orderSkus){
			Set<OrderReturnSku> orderReturnSkus = orderSku.getOrderReturnSkus();
			int returnableQuantity = orderSku.getQuantity();
			if(orderReturnSkus!=null && orderReturnSkus.size()>0){
				for(OrderReturnSku orderReturnSku:orderReturnSkus){
					OrderReturn or = orderReturnSku.getOrderReturn();
					if(or.getStatus().shortValue()!=OrderConstants.RETURN_STATUS_CANCELLED.shortValue())
						returnableQuantity -= orderReturnSku.getQuantity();
				}
			}
			orderSku.setReturnableQuantity(returnableQuantity);
		}
		request.setAttribute("orderSkus", orderSkus);
		request.setAttribute("orderShipment", orderShipment);
		return new ModelAndView("order/createReturn");
	}
	
	public ModelAndView calculateReturnTotal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Map<String, String> jmap = new HashMap<String, String>();
			ajaxView.setData(jmap);
			String lessRestockAmount = request.getParameter("lessRestockAmount");
			BigDecimal shippingCost = null;
			BigDecimal discountAmount = null; 
			BigDecimal shippingTax = null;
			BigDecimal totalBeforeTax = null;
			BigDecimal totalAmount = null;
			BigDecimal itemSubTotal = BigDecimal.ZERO;
			BigDecimal itemTax = BigDecimal.ZERO;
			
			if(!"true".equals(request.getParameter("completeReturn"))){
				String orderShipmentId = request.getParameter("orderShipmentId");
				
				OrderShipment orderShipment = salesOrderManager.getOrderShipmentById(new Integer(orderShipmentId));
				Iterator<OrderSku> i = orderShipment.getOrderSkus().iterator();
				Integer itemToReturnCount = 0;
				while(i.hasNext()){
					OrderSku orderSku = i.next();
					Integer orderSkuId = orderSku.getOrderSkuId();
					//获取页面参数值
					String quantity = request.getParameter("returnQuantity" + orderSkuId);
					if(quantity==null || "0".equals(quantity))
						continue;
					//重新汇总退款
					Integer quantityInteger = Integer.parseInt(quantity);
					itemToReturnCount += quantityInteger;
					BigDecimal itemReturmAmount = OrderReturn.getItemReturmAmount(orderSku.getDiscountPrice(), orderSku.getItemDiscountAmount(), orderSku.getQuantity(), quantityInteger);
					
					itemSubTotal = itemSubTotal.add(itemReturmAmount);
					itemTax = itemTax.add( orderSku.getTax() );
				}
				
				shippingCost = itemToReturnCount==0?BigDecimal.ZERO:orderShipment.getShippingCost();
				discountAmount = itemToReturnCount==0?BigDecimal.ZERO:orderShipment.getDiscountAmount(); 
				shippingTax = itemToReturnCount==0?BigDecimal.ZERO:orderShipment.getShippingTax();
				
				jmap.put("orderShipmentId", orderShipmentId);
			}else{
				String orderReturnId = request.getParameter("orderReturnId");
				OrderReturn orderReturn = orderReturnManager.getById(new Integer(orderReturnId));
				
				itemSubTotal =orderReturn.getItemSubTotal();
				shippingCost = orderReturn.getShippingCost();
				discountAmount = orderReturn.getDiscountAmount();
				shippingTax = orderReturn.getShippingTax();
				itemTax = orderReturn.getItemTax();
			}
			
			totalBeforeTax = itemSubTotal.add(shippingCost).add(discountAmount.negate());
			totalAmount = totalBeforeTax.add(itemTax).add(shippingTax).add(new BigDecimal(lessRestockAmount).negate());
			
			jmap.put("itemSubTotal", itemSubTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			jmap.put("shippingCost", shippingCost.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			jmap.put("discountAmount", discountAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			jmap.put("totalBeforeTax", totalBeforeTax.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			
			jmap.put("itemTax", itemTax.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			jmap.put("shippingTax", shippingTax.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			//只保留小数两位，不进行四舍五入，直接丢弃第三位小数
			jmap.put("returnTotalAmount", totalAmount.setScale(2).toString());
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	/**
	 * 创建退货，生成退货记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeCreateReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = orderReturnManager.createReturn(request.getParameterMap(), appUser);
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 编辑退换货信息-打开编辑窗口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView editReturn(HttpServletRequest request,
			HttpServletResponse response) {
		String orderReturnId = request.getParameter("orderReturnId");
		OrderReturn orderReturn = orderReturnManager.getById(new Integer(orderReturnId));
		
		//计算该退换货下的商品项的可退换数量
		Set<OrderReturnSku> orderReturnSkus = orderReturn.getOrderReturnSkus();
		OrderSku orderSku = null;
		for(OrderReturnSku orderReturnSku:orderReturnSkus){
			orderSku = orderReturnSku.getOrderSku();
			int returnableQuantity = orderSku.getQuantity();
			Set<OrderReturnSku> orderReturnSkusRefOrderSku = orderSku.getOrderReturnSkus();
			if(orderReturnSkus!=null && orderReturnSkus.size()>0){
				for(OrderReturnSku orderReturnSkuWithOrderSku:orderReturnSkusRefOrderSku){
					OrderReturn or = orderReturnSkuWithOrderSku.getOrderReturn();
					//本次退回数量不计入
					if(or.getStatus().shortValue()!=OrderConstants.RETURN_STATUS_CANCELLED.shortValue()
							&&or.getOrderReturnId().intValue()!=orderReturn.getOrderReturnId().intValue())
						returnableQuantity -= orderReturnSku.getQuantity();
				}
			}
			orderReturnSku.setReturnableQuantity(returnableQuantity);
		}
		request.setAttribute("orderReturn", orderReturn);
		request.setAttribute("orderShipmentId", orderSku.getOrderShipmentId());
		return new ModelAndView("order/editReturn");
	}
	
	/**
	 * 编辑退换货信息-保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeEditReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = 0;
			try{
				rtn = orderReturnManager.editReturn(request.getParameterMap(), appUser);
			}catch(org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException e){
				logger.debug(e.getMessage());
				//数据过期
				rtn = -2;
			}
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 取消退换货－流程操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView cancelReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderReturnId = request.getParameter("orderReturnId");
			
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = orderReturnManager.doCancelReturn(orderReturnId, appUser);
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView openCompleteReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String orderReturnId = request.getParameter("orderReturnId");
		OrderReturn orderReturn = orderReturnManager.getById(new Integer(orderReturnId));
		
		request.setAttribute("orderReturn", orderReturn);
		request.setAttribute("completeReturn", true);
		
		return new ModelAndView("order/editReturn");
	}
	
	/**
	 * 完成退换货－流程操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = 0;
			try{
				rtn = orderReturnManager.doCompleteReturn(request.getParameterMap(), appUser);
			}catch(org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException e){
				logger.debug(e.getMessage());
				//数据过期
				rtn = -2;
			}
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	private void printReturnToJson(HttpServletResponse response, int rtn)throws Exception{
		JSONObject jo = new JSONObject();
		jo.put("rtn", rtn);
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		
	}
	
	public ModelAndView createExchange(HttpServletRequest request,
			HttpServletResponse response) {
		return createReturn(request, response);
	}
	
	/**
	 * 创建换货并生成换货订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeCreateExchange(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = 0;
			try{
				rtn = orderReturnManager.createExchange(request.getParameterMap(), appUser, request.getRemoteAddr());
			}catch(OutOfStockException e){
				rtn = -2;
			}
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 收货－打开收货窗口
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView receiveReturn(HttpServletRequest request,
			HttpServletResponse response) {
		String orderReturnId = request.getParameter("orderReturnId");
		OrderReturn orderReturn = orderReturnManager.getById(new Integer(orderReturnId));
		
		request.setAttribute("orderReturn", orderReturn);
		
		return new ModelAndView(receiveReturnView);
	}
	
	/**
	 * 收货,保存收货信息－流程操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView completeReceiveReturn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			AppUser appUser = (AppUser) RequestContext.getCurrentUser();
			int rtn = orderReturnManager.doReceiveReturn(request.getParameterMap(), appUser);
			ajaxView.setStatus(new Short(rtn+""));
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	public String getReceiveReturnView() {
		return receiveReturnView;
	}

	public void setReceiveReturnView(String receiveReturnView) {
		this.receiveReturnView = receiveReturnView;
	}
}
