package com.cartmatic.estoresa.order.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.order.service.OrderPickListManager;

public class OrderShipmentController extends GenericController<OrderPickList>{
	private OrderPickListManager orderPickListManager = null;
	
	
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {

		SearchCriteria sc = createSearchCriteria(request,"orderShipment");
		List results = searchByCriteria(sc);
		return getModelAndView(listView, "orderShipmentList", results);
	}
	
	
	public void setOrderPickListManager(OrderPickListManager inMgr) {
        this.orderPickListManager = inMgr;
    }



	@Override
	protected String getEntityName(OrderPickList entity) {
		// TODO Auto-generated method stub
		return entity.getCreateTime().toString();
	}



	@Override
	protected void onSave(HttpServletRequest request, OrderPickList entity,
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
		mgr = orderPickListManager;
	}
}
