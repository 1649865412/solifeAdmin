package com.cartmatic.estoresa.catalog.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.ProductAttGroupManager;
import com.cartmatic.estore.catalog.service.ProductRateItemManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.catalog.service.SkuOptionManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.catalog.ProductAttGroup;
import com.cartmatic.estore.common.model.catalog.ProductRateItem;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductTypeController extends GenericController<ProductType> {
	private ProductTypeManager productTypeManager = null;
	private SkuOptionManager skuOptionManager = null;

	public void setProductTypeManager(ProductTypeManager inMgr) {
		this.productTypeManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ProductType entity) {
		return entity.getProductTypeName();
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
		mgr = productTypeManager;
	}

	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		ProductType entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				//更新产品类型关联的属性
				String attGroupNames[] = RequestUtil.getParameterValuesNullSafe(request, "attGroupNames");
				String attGroupIds[] = RequestUtil.getParameterValuesNullSafe(request, "attGroupIds");
				String attGroupItems[] = RequestUtil.getParameterValuesNullSafe(request, "attGroupItems");
				//更新保存产品类型关联SkuOption
				String skuOptionIds[] = RequestUtil.getParameterValuesNullSafe(request, "skuOptionIds");
				//更新保存产品类型关联的评论项
				String rateItemNames[] = RequestUtil.getParameterValuesNullSafe(request, "rateItemNames");
				String rateItemIds[] = RequestUtil.getParameterValuesNullSafe(request, "rateItemIds");
				
				productTypeManager.saveProductTypeAction(entity, attGroupIds, attGroupNames, attGroupItems, skuOptionIds, rateItemNames, rateItemIds);
				String msgKey = (isEntityNew(request)) ? "common.added" : "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(), getEntityName(entity) ));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		ModelAndView mav;
		if (errors.hasErrors()) {
			mav = showForm(request, errors);
		} else if (successView != null) {
			mav = getModelAndView(successView, errors.getModel());
		} else {
			mav = getRedirectToActionView("edit", ((BaseObject) entity).getId().toString());
		}
		return mav;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ProductType entity, BindException errors) {
		ProductType tempProductType = productTypeManager.getProductTypeByName(entity.getProductTypeName());
		if (tempProductType != null && (entity.getProductTypeId() == null || entity.getProductTypeId().intValue() != tempProductType.getProductTypeId().intValue())) {
			String msgKey = "productType.productTypeName.repeated";
			errors.rejectValue("productTypeName", msgKey);
		}
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering ProductTypeController 'onShowForm' method...");
		}
		ProductType productType = (ProductType) mv.getModel().get(formModelName);
		List<Attribute> otherAttributes = productTypeManager.findAttributesExcludeRefProductType(productType.getProductTypeId());
		mv.addObject("otherAttributes", otherAttributes);
		List<SkuOption> otherSkuOptions = skuOptionManager.findActiveSkuOptionsExcludeRefProductType(productType.getProductTypeId());
		mv.addObject("otherSkuOptions", otherSkuOptions);
		List<SkuOption> productTypeSkuOptions = skuOptionManager.findActiveSkuOptionsByProductType(productType.getProductTypeId());
		mv.addObject("productTypeSkuOptions", productTypeSkuOptions);
	}


	
	public void setSkuOptionManager(SkuOptionManager skuOptionManager) {
		this.skuOptionManager = skuOptionManager;
	}

}
