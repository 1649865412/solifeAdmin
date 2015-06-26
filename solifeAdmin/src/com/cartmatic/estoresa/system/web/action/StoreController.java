package com.cartmatic.estoresa.system.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.system.service.PaymentMethodManager;
import com.cartmatic.estore.system.service.ShippingMethodManager;
import com.cartmatic.estore.system.service.StoreManager;

public class StoreController extends GenericController<Store> {
    private StoreManager storeManager = null;
    private CatalogManager catalogManager=null;
    private PaymentMethodManager paymentMethodManager;
    private ShippingMethodManager shippingMethodManager;
    
    public void setShippingMethodManager(ShippingMethodManager avalue)
    {
    	this.shippingMethodManager = avalue;
    }
    public void setPaymentMethodManager(PaymentMethodManager avalue)
    {
    	this.paymentMethodManager = avalue;
    }
    public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public void setStoreManager(StoreManager inMgr) {
        this.storeManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Store entity) {
		return entity.getStoreName();
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
		mgr = storeManager;
	}

	/*
	 * 
	 * 
	 * setup shipping and payment
	 */
	@Override
	protected void onSave(HttpServletRequest request, Store entity, BindException errors) 
	{
		String[] paymentMethodIds = request.getParameterValues("paymentMethodIds");
		String[] shippingMethodIds = request.getParameterValues("shippingMethodIds");
		entity.getPaymentMethods().clear();
		entity.getShippingMethods().clear();
		if(paymentMethodIds!=null){
			for (String id: paymentMethodIds)
			{
				entity.getPaymentMethods().add(paymentMethodManager.getById(new Integer(id)));
			}
		}
		
		if(shippingMethodIds!=null){
			for (String id: shippingMethodIds)
			{
				entity.getShippingMethods().add(shippingMethodManager.getById(new Integer(id)));
			}
		}
		
		if(entity.getPaymentMethods()==null||entity.getPaymentMethods().size()==0){
			errors.reject("store.paymentMethod.required");
		}
		if(entity.getShippingMethods()==null||entity.getShippingMethods().size()==0){
			errors.reject("store.shippingMethod.required");
		}
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		List<Catalog> catalogList=catalogManager.getAll();
		request.setAttribute("catalogList", catalogList);
		request.setAttribute("paymentMethodList", paymentMethodManager.findAllActivePaymentMethods());
		request.setAttribute("shippingMethodList", shippingMethodManager.getAll());
		super.onShowForm(request, mv);
	}

}