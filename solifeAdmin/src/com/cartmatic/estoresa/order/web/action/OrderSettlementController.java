package com.cartmatic.estoresa.order.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.order.OrderSettlement;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.order.service.OrderSettlementManager;
import com.cartmatic.estore.webapp.util.RequestContext;

public class OrderSettlementController extends GenericController<OrderSettlement> {
    private OrderSettlementManager orderSettlementManager = null;

    public void setOrderSettlementManager(OrderSettlementManager inMgr) {
        this.orderSettlementManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(OrderSettlement entity) {
		return entity.getOrderSettlementName();
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
		mgr = orderSettlementManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, OrderSettlement entity, BindException errors) {
	}
	
	public ModelAndView completeSettlement(HttpServletRequest request,
			HttpServletResponse response){
		AppUser appUser = (AppUser) RequestContext.getCurrentUser();
		String orderSettlementId = request.getParameter("orderSettlementId");
		orderSettlementManager.doCompleteSettlement(orderSettlementId, request.getRemoteAddr(), appUser);
		
		return this.getRedirectToActionView("defaultAction");
	}

}
