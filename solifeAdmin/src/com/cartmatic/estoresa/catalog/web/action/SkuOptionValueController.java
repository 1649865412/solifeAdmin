package com.cartmatic.estoresa.catalog.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.SkuOptionValueManager;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;

public class SkuOptionValueController extends GenericController<SkuOptionValue> {
    private SkuOptionValueManager skuOptionValueManager = null;

    public void setSkuOptionValueManager(SkuOptionValueManager inMgr) {
        this.skuOptionValueManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SkuOptionValue entity) {
		return entity.getSkuOptionValueName();
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
		mgr = skuOptionValueManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SkuOptionValue entity, BindException errors) {
	}

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		SkuOptionValue entity = formBackingObject(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				mgr.save(entity);
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
	
	

}
