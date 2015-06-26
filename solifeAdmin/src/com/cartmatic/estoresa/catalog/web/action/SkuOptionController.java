package com.cartmatic.estoresa.catalog.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.SkuOptionManager;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class SkuOptionController extends GenericController<SkuOption> {
    private SkuOptionManager skuOptionManager = null;

    public void setSkuOptionManager(SkuOptionManager inMgr) {
        this.skuOptionManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SkuOption entity) {
		return entity.getSkuOptionName();
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
		mgr = skuOptionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SkuOption entity, BindException errors) {
		SkuOption tempSkuOption=skuOptionManager.getSkuOptionByCode(entity.getSkuOptionCode());
		if(tempSkuOption!=null&&(entity.getSkuOptionId()==null||(entity.getSkuOptionId().intValue()!=tempSkuOption.getSkuOptionId().intValue()))){
			String msgKey = "skuOption.skuOptionCode.repeated";
			errors.rejectValue("skuOptionCode", msgKey);
		}
	}
	public ModelAndView refurbishSkuOptionValueList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering ProductController 'refurbishSkuTab' method...");
		}
		ModelAndView mv=new ModelAndView("catalog/include/skuOptionValueListContent");
		Integer skuOptionId=RequestUtil.getInteger(request,"skuOptionId");
		SkuOption skuOption=skuOptionManager.getById(skuOptionId);
		mv.addObject("skuOption",skuOption);
		return mv;
	}
}
