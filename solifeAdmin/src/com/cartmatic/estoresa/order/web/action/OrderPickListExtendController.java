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

/**
 * 
 * @author pengzhirong
 *
 */
public class OrderPickListExtendController extends GenericController<OrderPickList> {
    private OrderPickListManager orderPickListManager = null;
    
    //private SalesOrderManager salesOrderManager = null;
    
    //private OrderProcessFlowManager orderProcessFlowManager = null;
    
    //private CompanyInfoManager companyInfoManager = null;
    
	

	public void setOrderPickListManager(OrderPickListManager inMgr) {
        this.orderPickListManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(OrderPickList entity) {
		return entity.getOrderPickListName();
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
		mgr = orderPickListManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, OrderPickList entity, BindException errors) {
	}
	
	
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		String createBy =request.getParameter("COL@u.username@String@LIKE");
		if(StringUtils.isBlank(createBy)){
			return super.defaultAction(request, response);
		}else{
			SearchCriteria sc = createSearchCriteria(request,"selectByUser");
			List results = searchByCriteria(sc);
			return getModelAndView(listView, listModelName, results);
		}
	}	
	
} 



