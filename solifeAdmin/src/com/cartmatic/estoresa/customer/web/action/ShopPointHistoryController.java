package com.cartmatic.estoresa.customer.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.ShopPoint;
import com.cartmatic.estore.common.model.customer.ShopPointHistory;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.customer.service.ShopPointManager;

public class ShopPointHistoryController extends GenericController<ShopPointHistory> {
    private ShopPointHistoryManager shopPointHistoryManager = null;
    
    private ShopPointManager shopPointManager=null;
    
    private CustomerManager customerManager=null;
    
    public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setShopPointManager(ShopPointManager shopPointManager) {
		this.shopPointManager = shopPointManager;
	}

	public void setShopPointHistoryManager(ShopPointHistoryManager inMgr) {
        this.shopPointHistoryManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ShopPointHistory entity) {
		return entity.getShopPointHistoryName();
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
		mgr = shopPointHistoryManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ShopPointHistory entity, BindException errors) {
		if(entity.getShopPointHistoryId()!=null){
			String msgKey = "shopPointHistory.update.error";
			errors.rejectValue("amount", msgKey);
		}else{
			Integer amount=entity.getAmount();
			if(amount==null||amount==0){
				String msgKey = "shopPointHistory.amount.error.zero";
				errors.rejectValue("amount", msgKey);
			}
		}
	}
	
	
	
	@Override
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		ShopPointHistory entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				shopPointHistoryManager.saveShopPointHistoryAndUpdateTotal(entity);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, new Object[] {getEntityTypeMessage(), getEntityName(entity)}));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors.hasErrors()) {
			List<JFieldError> jFiledErrors = JFieldErrorUtil.getFiledErrors(errors);
			ajaxView.setStatus(new Short("2"));
			data.put("jFiledErrors", jFiledErrors);
		}  else {
			String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
			ajaxView.setMsg(getMessage(msgKey, new Object[] {getEntityTypeMessage(), getEntityName(entity)}));
			ajaxView.setStatus(new Short("1"));
		}
		return ajaxView;
	}

	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria sc = createSearchCriteria(request);
		Integer customerId=ServletRequestUtils.getIntParameter(request,"customerId",0);
		sc.addParamValue(customerId);
		sc.lockParamValues();
		List results = searchByCriteria(sc);
		ShopPoint shopPoint=shopPointManager.getByCustomerId(customerId);
		request.setAttribute("shopPoint",shopPoint);
		return getModelAndView(listView, listModelName, results);
	}
	
	
	
	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Integer customerId=ServletRequestUtils.getIntParameter(request,"customerId",0);
		Customer customer=customerManager.getById(customerId);
		mv.addObject("customer", customer);
		super.onShowForm(request, mv);
	}

	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return edit(request, response);
	}
}
