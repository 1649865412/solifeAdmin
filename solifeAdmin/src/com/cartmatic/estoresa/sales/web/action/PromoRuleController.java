
package com.cartmatic.estoresa.sales.web.action;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.BrandManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.catalog.service.ProductSkuManager;
import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.sales.Coupon;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.sales.engine.PRuleManager;
import com.cartmatic.estore.sales.model.EmailModel;
import com.cartmatic.estore.sales.model.PromoRuleSearchCriteria;
import com.cartmatic.estore.sales.service.CouponManager;
import com.cartmatic.estore.sales.service.PromoRuleElementManager;
import com.cartmatic.estore.sales.service.PromoRuleManager;
import com.cartmatic.estore.sales.service.PromoRuleParameterManager;
import com.cartmatic.estore.sales.util.PromoRuleUtil;
import com.cartmatic.estore.system.service.ShippingMethodManager;
import com.cartmatic.estore.system.service.StoreManager;
import com.cartmatic.estore.webapp.event.RefreshPromoEvent;
import com.cartmatic.estore.webapp.util.RequestUtil;

/**
 * 促销controller
 * @author CartMatic
 * 
 */

public class PromoRuleController extends GenericController<PromoRule> {
	private PromoRuleManager			promoRuleManager				= null;
	private PromoRuleElementManager		promoRuleElementManager			= null;
	private PromoRuleParameterManager	promoRuleParameterManager		= null;
	private ProductManager				productManager					= null;
	private ProductSkuManager			productSkuManager				= null;
	private CategoryManager				categoryManager					= null;
	private BrandManager				brandManager					= null;
	private ShippingMethodManager		shippingMethodManager			= null;
	private CouponManager				couponManager					= null;
	private PRuleManager				pruleManager					= null;
	private StoreManager storeManager = null;
	
	private final String				PREFIX_OF_ELEMENT_ID_IN_DB		= "o";
	private final String				PREFIX_OF_PARAMETER_ID_IN_PAGE	= "p";

	public void setPromoRuleManager(PromoRuleManager promoRuleManager) {
		this.promoRuleManager = promoRuleManager;
	}

	public void setPromoRuleElementManager(
			PromoRuleElementManager promoRuleElementManager) {
		this.promoRuleElementManager = promoRuleElementManager;
	}

	public void setPromoRuleParameterManager(
			PromoRuleParameterManager promoRuleParameterManager) {
		this.promoRuleParameterManager = promoRuleParameterManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setProductSkuManager(ProductSkuManager productSkuManager) {
		this.productSkuManager = productSkuManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setShippingMethodManager(
			ShippingMethodManager shippingMethodManager) {
		this.shippingMethodManager = shippingMethodManager;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

	public void setPruleManager(PRuleManager pruleManager) {
		this.pruleManager = pruleManager;
	}
	
	public void setStoreManager(StoreManager avalue)
	{
		this.storeManager = avalue;
	}

	protected String getEntityName(PromoRule entity) {
		return entity.getPromoRuleName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		throw new RuntimeException("Not implemented yet!");
	}

	protected void initController() throws Exception {
		mgr = promoRuleManager;
	}

	protected void onSave(HttpServletRequest request, PromoRule entity,
			BindException errors) {
	}

	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		PromoRule rule = (PromoRule) mv.getModel().get(formModelName);
		// -----------------set rule's promotionType-------------------//
		setPromotionType(request, rule);
		// -----------------set rule's parameters title----------------//
		setTitle(rule);
		// -----------------set rule's state---------------------------//
		setState(rule);
		// -----------------inject elementList(including parameters) to
		// page---------------------//
		mv.addObject("elementList", rule.getPromoRuleElements());
		
		
		mv.addObject("stores",storeManager.getAllActiveStores());
	}

	/**
	 * 保存单个记录的数据，并可以处理应用级的错误信息。在formBackingObject读数据的时候已经加锁，所以可以保证事务和版本控制。子类需要实现onSave。
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		
		PromoRule entity = formBackingRule(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);

			logger.debug("hasErrors====" + errors.hasErrors());

			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				setCreateTime(request, entity);
				setStartHourAndEndHour(request, entity);
				if (entity.getStoreId() != null)
				{
					Store store = storeManager.getById(entity.getStoreId());
					entity.setStore(store);
					entity.setCatalog(store.getCatalog());
				}
				mgr.save(entity);

				List<String> updateFieldList = handlerElementsAndParams(request, entity);

				setState(entity);

				/**
				 * --------------------- 回传JSON给页面----------------------*
				 */
				Map<String, Object> data = new HashMap<String, Object>();
				ajaxView.setData(data);
				data.put("promoRuleId", entity.getPromoRuleId());
				data.put("promoRuleName", entity.getPromoRuleName());
				data.put("version", entity.getVersion());
				data.put("state", entity.getState());
				data.put("availableCount", entity.getAvailableCount());
				data.put("createTime", DateUtil.getDateTime(entity
						.getCreateTime()));
				data.put("updateTime", DateUtil.getDateTime(entity
						.getUpdateTime()));
				data.put("createTimeShow", DateUtil.getDateTime(entity
						.getCreateTime()));
				data.put("updateTimeShow", DateUtil.getDateTime(entity
						.getUpdateTime()));
				data.put("createBy", entity.getCreateBy());
				data.put("updateBy", entity.getUpdateBy());
				data.put("updateFields", updateFieldList);
				
				// refresh pruleManager 必须这个先refresh
				mgr.evict(entity); // 必须先把entity这个对象从缓存中清除出去，从而及时释放它占用的内存。否则在后面再读取列表的时候，这个对象的关联集合不会加载
				refreshPruleManager();
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				ajaxView.setMsg(getMessage(msgKey, getEntityTypeMessage(), getEntityName(entity)));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
		}
		return ajaxView;
	}

	public ModelAndView saveAndNext(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PromoRule entity = formBackingRule(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			logger.error("hasErrors====" + errors.hasErrors());
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				setCreateTime(request, entity);
				setStartHourAndEndHour(request, entity);
				mgr.save(entity);

				handlerElementsAndParams(request, entity);

				// refresh pruleManager 必须这个先refresh
				mgr.evict(entity); // 必须先把entity这个对象从缓存中清除出去，从而及时释放它占用的内存。否则在后面再读取列表的时候，这个对象的关联集合不会加载
				refreshPruleManager();
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
			mav = getRedirectToActionView("edit", ((BaseObject) entity).getId()
					.toString());
		}
		// 只有在成功（不出错）的时候才会转到下个工作件，否则直接用save传回来的mav。
		if (request.getAttribute("HAS_ERRORS") == null) {
			if (isEntityNew(request)) {

				return getRedirectToActionView("add");
			} else {

				return nextItem(request, response);
			}
		}

		return mav;
	}

	/**
	 * 产生优惠券
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView generateCoupon(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		PromoRule entity = formBackingRule(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			if (!errors.hasErrors()) {
				Integer couponStyle = new Integer(request
						.getParameter("couponStyle")).intValue();
				int quantity = new Integer(request.getParameter("quantity"))
						.intValue();
				String prefix = request.getParameter("prefix").toLowerCase();
				if (couponStyle == 3) //指定的优惠券号
				{
					couponManager.createCoupon(entity.getPromoRuleId(), entity
						.getAvailableCount(), request.getParameter("speCouponNo"));
				}
				else
				{
					couponManager.createCoupons(entity.getPromoRuleId(), entity
						.getAvailableCount(), couponStyle, prefix, quantity);
				}
			}
		} catch (ApplicationException e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			handleApplicationException(errors, e);
		}
		return ajaxView;
	}

	/**
	 * 
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model。因为要先保存其名称，否则可能会连I18N数据也已经删除了；另外可以用于出错的时候恢复显示Form页面
		PromoRule entity = formBackingObject(request);
		try {
			// 删除并设置信息
			String entityId = request.getParameter(entityIdName);
			if (!StringUtils.isEmpty(entityId)) {
				// 先构造提示信息，否则信息对应的记录可能也会被删除。
				String entityName = getEntityName(entity);
				entity = mgr.deleteById(new Integer(entityId));
				saveMessage(Message.info("common.deleted", new Object[] {getEntityTypeMessage(), entityName }));
				// refresh pruleManager 必须这个先refresh
				mgr.evict(entity); // 必须先把entity这个对象从缓存中清除出去，从而及时释放它占用的内存。否则在后面再读取列表的时候，这个对象的关联集合不会加载
				refreshPruleManager();
			} else {
				saveMessage(Message.error("errors.invalid.delete.id", new Object[] {getEntityTypeMessage(), entityId }));
			}
		} catch (ApplicationException e) {
			// 为了出错的时候可以恢复表单显示，构造errors，但是设置标记跳过绑定和验证
			request.setAttribute("SUPPRESS_BINDING", Boolean.TRUE);
			request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
			BindException errors = null;
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			handleApplicationException(errors, e);
			return showForm(request, errors);
		}

		return getModelAndView(cancelFormView, null);
	}

	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		String[] ids = request.getParameterValues("multiIds");
		if (ids != null && ids.length > 0) {
			mgr.deleteAllByIds(ids);
			saveMessage(Message.info("common.deleted.multi", new Object[] {getEntityTypeMessage(), ids.length }));
			// refresh pruleManager 必须这个先refresh
			refreshPruleManager();
		}
		return getRedirectToActionView("search");
	}

	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) 
	{
		SearchCriteria sc = createSearchCriteria(request);
		List<PromoRule> results;
		if (RequestUtil.getParameterNullSafe(request, "newSearch").equals("1")) {
			sc.changePaging(1, sc.getPageSize());
			PromoRuleSearchCriteria promoRuleSearchCriteria = new PromoRuleSearchCriteria();
			BaseBinder binder = new BaseBinder();
			binder.bind(request, promoRuleSearchCriteria,"promoRuleSearchCriteria");
			results = promoRuleManager.searchPromotionRules(sc, promoRuleSearchCriteria);
		} else {
			results = searchByCriteria(sc);
		}

		for (PromoRule rule : results) {
			setState(rule);
		}
		ModelAndView mv = getModelAndView(listView, listModelName, results);
		mv.addObject("stores",storeManager.getAllActiveStores());
		return mv;
	}

	private void setState(PromoRule rule) {
		if (rule.getPromoRuleId() != null) {
			promoRuleManager.setState(rule);
		} else {
			rule.setState(null);
		}
	}

	private void setStartHourAndEndHour(HttpServletRequest request,
			PromoRule _rule) {
		PromoRule rule = _rule;
		Calendar c = Calendar.getInstance();
		if (null != rule.getStartTime()) {
			c.setTime(rule.getStartTime());
			c.set(Calendar.HOUR_OF_DAY, rule.getStartHour());
			rule.setStartTime(c.getTime());
		}
		if (null != rule.getEndTime()) {
			c.setTime(rule.getEndTime());
			c.set(Calendar.HOUR_OF_DAY, rule.getEndHour());
			rule.setEndTime(c.getTime());
		}
	}

	private void setPromotionType(HttpServletRequest request, PromoRule _rule) {
		PromoRule rule = _rule;
		String promotionType = RequestUtil.getParameterNullSafe(request, PromoRule.PROMOTION_TYPE);
		String storeId = RequestUtil.getParameterNullSafe(request, "storeId");
		if (rule.getId() == null) {
			rule.setPromoType(promotionType);
			rule.setStore(storeManager.getById(new Integer(storeId)));
		}
	}

	private void setTitle(PromoRule _rule) {
		PromoRule rule = _rule;
		for (PromoRuleElement element : rule.getPromoRuleElements()) {
			for (PromoRuleParameter param : element.getPromoRuleParameters()) {
				if (param.getParamName().equals("PRODUCT")
						|| param.getParamName().equals("PRODUCT_EXCLUDED")) {
					Product product = null;
					try {
						product = productManager.loadById(new Integer(param
								.getParamValue()));
						param.setParamTitle(product.getProductName());
					} catch (Exception e) {
						param.setParamTitle("!");

					}
				}
				if (param.getParamName().equals("CATEGORY")
						|| param.getParamName().equals("CATEGORY_EXCLUDED")) {
					Category category = null;
					try {
						category = categoryManager.loadById(new Integer(param
								.getParamValue()));
						param.setParamTitle(category.getCategoryName());
					} catch (Exception e) {

						param.setParamTitle("!");
					}

				}
				if (param.getParamName().equals("SKU")
						|| param.getParamName().equals("SKU_EXCLUDED")) {
					ProductSku sku = null;
					try {
						sku = productSkuManager.loadById(new Integer(param
								.getParamValue()));
						param.setParamTitle(sku.getProductSkuName());
					} catch (Exception e) {

						param.setParamTitle("!");
					}
				}
				if (param.getParamName().equals("BRAND")) {
					Brand brand = null;
					try {
						brand = brandManager.loadById(new Integer(param
								.getParamValue()));
						param.setParamTitle(brand.getBrandName());
					} catch (Exception e) {
						param.setParamTitle("!");

					}
				}
				if (param.getParamName().equals("SHIPPING_METHOD")) {
					ShippingMethod shippingMethod = null;
					try {
						shippingMethod = shippingMethodManager.getById(new Integer(param.getParamValue()));
						param.setParamTitle(shippingMethod.getShippingMethodName());
					} catch (Exception e) {
						param.setParamTitle("!");

					}
				}

			}
		}
	}

	private void setCreateTime(HttpServletRequest request, PromoRule _rule) {
		PromoRule rule = _rule;
		String createTimeString = RequestUtil.getParameterNullSafe(request,
				"createTime");
		if (!createTimeString.equals("")) {
			try {
				rule.setCreateTime(DateUtil.convertStringToDate(DateUtil
						.getDateTimePattern(), createTimeString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			rule.setCreateTime(null);
		}

	}

	private List<String> handlerElementsAndParams(HttpServletRequest request,
			PromoRule entity) {
		List<String> updateFieldList = new ArrayList<String>();
		/**
		 * -------------------- 处理element->更新或者新增-----------------------*
		 */

		for (String elementString : RequestUtil.getParameterValuesNullSafe(
				request, "elementString")) {
			PromoRuleElement element = PromoRuleUtil.getElement(elementString);
			List<PromoRuleParameter> paras = PromoRuleUtil
					.getParameters(elementString);
			if (element.getPromoRuleElementIdString().contains(
					PREFIX_OF_ELEMENT_ID_IN_DB)) {
				// 该element是更新的

				// 设回正确的id in database
				element.setPromoRuleElementId(Integer.parseInt(element
						.getPromoRuleElementIdString().substring(1)));
				for (PromoRuleParameter para : paras) {
					para.setPromoRuleElementId(element.getPromoRuleElementId());
					if (para.getPromoRuleParameterIdString().contains(
							PREFIX_OF_PARAMETER_ID_IN_PAGE)) {
						// 该parameter是新增的
						para.setPromoRuleParameterId(null);
					} else {
						// 该parameter是更新的
						para.setPromoRuleParameterId(Integer.parseInt(para
								.getPromoRuleParameterIdString()));
					}
					try {
						promoRuleParameterManager.save(para);
						promoRuleParameterManager.evict(para); // 释放内存，重要
					} catch (Exception e) {
						logger.error("~~~~~~~~~");
						logger.error("idInPage ="
								+ para.getPromoRuleParameterIdString());
						logger.error("idInDB ="
								+ para.getPromoRuleParameterId());
						logger.error("name =" + para.getParamName());
						logger.error("value =" + para.getParamValue());
						e.printStackTrace();
					}
					if (para.getPromoRuleParameterIdString().contains(
							PREFIX_OF_PARAMETER_ID_IN_PAGE)) {
						// 该parameter是新增的
						updateFieldList.add(PromoRuleUtil
								.getUpdateFormatString(para
										.getPromoRuleParameterIdString(), para
										.getPromoRuleParameterId().toString()));
					}
				}

			} else {
				// 该element是新增的
				element.setPromoRuleId(entity.getPromoRuleId());
				promoRuleElementManager.save(element);
				promoRuleElementManager.evict(element); // 释放内存，重要
				updateFieldList.add(PromoRuleUtil.getUpdateFormatString(element
						.getPromoRuleElementIdString(), element
						.getPromoRuleElementId().toString()));

				// 新增parameters
				for (PromoRuleParameter para : paras) {
					para.setPromoRuleElementId(element.getPromoRuleElementId());
					para.setPromoRuleParameterId(null);
					promoRuleParameterManager.save(para);
					promoRuleParameterManager.evict(para); // 释放内存，重要
					updateFieldList.add(PromoRuleUtil.getUpdateFormatString(
							para.getPromoRuleParameterIdString(), para
									.getPromoRuleParameterId().toString()));
				}
			}
		}
		/**
		 * -------------------- 处理parameter->删除-----------------------*
		 */
		for (String deleteParam : RequestUtil.getParameterValuesNullSafe(
				request, "deleteParam")) {
			promoRuleParameterManager.deleteById(Integer.parseInt(deleteParam));
		}
		/**
		 * --------------------- 处理element->删除----------------------*
		 */
		for (String deleteElement : RequestUtil.getParameterValuesNullSafe(
				request, "deleteElement")) {
			promoRuleElementManager.deleteById(Integer.parseInt(deleteElement
					.substring(1)));

		}

		return updateFieldList;
	}

	/**
	 * email发生优惠劵
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView sendCoupon(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		BindException errors = null;
		EmailModel emailModel = new EmailModel();
		try {
			ServletRequestDataBinder binder = bindAndValidate(request,emailModel);
			errors = new BindException(binder.getBindingResult());
			if (!errors.hasErrors()) {
				String couponNo = RequestUtil.getParameterNullSafe(request, "couponNo");
				Coupon coupon = couponManager.getCouponByNo(couponNo);
				couponManager.doSendCoupon(coupon, emailModel);
			}
		} catch (ApplicationException e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			handleApplicationException(errors, e);
		}
		return ajaxView;
	}

	private PromoRule formBackingRule(HttpServletRequest request) {
		markIsFormAction(request);
		String entityId = request.getParameter(entityIdName);
		PromoRule entity = null;

		if (!StringUtils.isEmpty(entityId)) {
			try {
				entity = entityClass.newInstance();
				entity.setPromoRuleId(new Integer(entityId));
			} catch (Exception ex) {
				logger.info(formatMsg("Error retrieving entity [%1 : %2]. So assume it is new.",entityClassName, entityId, ex));
				saveMessage(Message.error("errors.invalid.edit.id", entityId));
			}
		}
		if (entity == null) {
			try {
				entity = entityClass.newInstance();
				// TODO, move to showForm will be better?
				request.setAttribute(NEW_ENTITY_MARKER, Boolean.TRUE);
			} catch (Throwable e) {// should not happen
				new RuntimeException("Unexpected error!", e);
			}
		}
		request.setAttribute("entityClassName", entityClassName);
		return entity;
	}

	private void refreshPruleManager() {
		// 马上刷新 后台
		pruleManager.refresh();
		// 定时刷新 前后台
		ContextUtil.getInstance().fireApplicationEvent(
				new RefreshPromoEvent("RefreshPromoManager"));
	}
	
	public ModelAndView getCouponTypes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		List<Map<String,Object>>data=new ArrayList<Map<String,Object>>();
		ajaxView.setData(data);
		List<PromoRule> couponTypes = promoRuleManager.getAllCouponTypesInProcessOrInFuture();
		for (PromoRule promoRule : couponTypes) {
			Map<String,Object> ct=new HashMap<String, Object>();
			ct.put("couponTypeId", promoRule.getPromoRuleId());
			ct.put("couponTypeName", promoRule.getName());
			data.add(ct);
		}
		return  ajaxView;
	}
}
