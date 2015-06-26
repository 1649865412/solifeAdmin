package com.cartmatic.estoresa.system.web.action.shipping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.Carrier;
import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.service.CarrierManager;
import com.cartmatic.estore.system.service.ShippingMethodManager;

public class ShippingMethodController extends GenericController<ShippingMethod> {
    private static final String	Carrier_LIST	= "carrierList";
    private ShippingMethodManager shippingMethodManager = null;
    
    private CarrierManager carrierManager=null;

    public void setShippingMethodManager(ShippingMethodManager inMgr) {
        this.shippingMethodManager = inMgr;
    }
    
	public void setCarrierManager(CarrierManager carrierManager) {
		this.carrierManager = carrierManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ShippingMethod entity) {
		return entity.getShippingMethodName();
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
		mgr = shippingMethodManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ShippingMethod shippingMethod, BindException errors) {
		String isCod = request.getParameter("isCod");
		if (isCod == null) {
			shippingMethod.setIsCod(new Short("0"));
		} else {
			shippingMethod.setIsCod(new Short("1"));
		}
		
		String name = request.getParameter("shippingMethodName");
		String time = request.getParameter("deliveryTime");
		String detail = request.getParameter("shippingMethodDetail").trim();
		shippingMethod.setShippingMethodName(name);
		shippingMethod.setDeliveryTime(time);
		shippingMethod.setShippingMethodDetail(detail);
	        	
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		ShippingMethod shippingMethod = (ShippingMethod) mv.getModel().get(formModelName);
		if (isEntityNew(request)) {
			shippingMethod.setIsCod(new Short("0"));
			String carrierId = request.getParameter("carrierId");
			if (StringUtils.isNotBlank(carrierId)){
				shippingMethod.setCarrierId(new Integer(carrierId));
			}
		}
		 List<Carrier> carrierList=carrierManager.findActiveCarriers();
		 request.setAttribute(Carrier_LIST,carrierList);
	}

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		List<Carrier> results=new ArrayList<Carrier>();
		List<Carrier> allCarrier=carrierManager.findActiveCarriers();
		if(StringUtils.isNotEmpty(request.getParameter("showingCarrierId"))){
			Carrier carrier=carrierManager.getById(Integer.parseInt(request.getParameter("showingCarrierId")));
			if(carrier!=null) results.add(carrier);
			else{
				results = allCarrier;
			}
		}else{
			results = allCarrier;
		}
		request.setAttribute("allCarrier", allCarrier);

		return new ModelAndView("system/shipping/shippingMethodList",Carrier_LIST, results);
	}
	
	public ModelAndView getListTable(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		List<Carrier> results=new ArrayList<Carrier>();
		if(StringUtils.isNotEmpty(request.getParameter("showingCarrierId"))){
			Carrier carrier=carrierManager.getById(Integer.parseInt(request.getParameter("showingCarrierId")));
			if(carrier!=null) {
				results.add(carrier);
			}
		}
		if(results.size()==0) {
			results = carrierManager.findActiveCarriers();
		}
		return new ModelAndView("system/shipping/include/shippingMethodListTable",Carrier_LIST, results);
	}

	public ModelAndView getShippingMethods(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		try {
			List<Map<String,Object>>data=new ArrayList<Map<String,Object>>();
			ajaxView.setData(data);
			List<ShippingMethod> shippingMethods = shippingMethodManager.getShippingMethodsAllOrder();
			for (ShippingMethod shippingMethod : shippingMethods) {
				Map<String,Object> ct=new HashMap<String, Object>();
				ct.put("shippingMethodId", shippingMethod.getShippingMethodId());
				ct.put("shippingMethodName", shippingMethod.getShippingMethodName());
				data.add(ct);
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			e.printStackTrace();
		}
		return ajaxView;
	}
}
