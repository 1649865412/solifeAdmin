
package com.cartmatic.estoresa.catalog.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.ProductReviewManager;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.ProductReview;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.customer.service.ShopPointHistoryManager;
import com.cartmatic.estore.webapp.util.RequestContext;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductReviewController extends GenericController<ProductReview> {
	private ProductReviewManager	productReviewManager	= null;

	public void setProductReviewManager(ProductReviewManager inMgr) {
		this.productReviewManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ProductReview entity) {
		return entity.getProductReviewName();
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
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = productReviewManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ProductReview entity,
			BindException errors) {

	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		ProductReview entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				productReviewManager.save(entity);
				/*if (!entity.getReviewUserId().equals(new Integer("-2"))) {
					ShopPointHistory shopPointHistory = new ShopPointHistory();
					shopPointHistory
							.setShopPointType(ShopPointHistory.SHOPPOINT_TYPE_PRODUCTREVIEW);
					shopPointHistory.setAmount(entity.getGivenPoint());
					shopPointHistory.setCustomerId(entity.getReviewUserId());
					shopPointHistory.setDescription("product review");
					shopPointHistoryManager.saveShopPointHistoryAndUpdateTotal(shopPointHistory);
				}*/
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, new Object[] {getEntityTypeMessage(), getEntityName(entity) }));
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
		return mav;
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		int[] productReviewGivenPoints = ConfigUtil.getInstance()
				.getProductReviewGivenPoints();
		mv.addObject("productReviewGivenPoints", productReviewGivenPoints);
	}

	/*
	 * 重复本方法是为了较准确保存原来的状态
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected ProductReview formBackingObject(HttpServletRequest request) {
		ProductReview productReview = super.formBackingObject(request);
		productReview.setOrigStatus(productReview.getStatus());
		return productReview;
	}

	public ModelAndView listReply(HttpServletRequest request,
			HttpServletResponse response) {
		Integer productReviewId = RequestUtil.getInteger(request,
				"productReviewId");
		List<ProductReview> reviewReplyList = productReviewManager
				.findReplyListByReview(productReviewId);
		ModelAndView mv = new ModelAndView("catalog/productReviewReplyList");
		mv.addObject("reviewReplyList", reviewReplyList);
		return mv;
	}

	public ModelAndView saveReply(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("enter ProductReviewController saveReply");
		AjaxView ajaxView=new AjaxView(response);
		ProductReview productReviewReply = new ProductReview();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(productReviewReply, "productReview");
		binder.bind(request);
		productReviewReply.setReviewUser((AppUser) RequestContext
				.getCurrentUser());
		productReviewReply.setRemoteIp(request.getRemoteAddr());
		productReviewReply.setStatus(Constants.STATUS_ACTIVE);
		productReviewManager.saveReviewReply(productReviewReply);
		// TODO setHasReply是否有必要
		// ProductReview
		// productReview=productReviewManager.getById(productReviewReply.getReviewId());
		// productReview.setHasReply(Short.valueOf("1"));
		// productReviewManager.save(productReview);

		return ajaxView;
	}

	public ModelAndView deleteReply(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		String entityId = request.getParameter(entityIdName);
		productReviewManager.deleteReviewReplyById(new Integer(entityId));
		return ajaxView;
	}

	public ModelAndView multiActive(HttpServletRequest request,HttpServletResponse response) {
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		String[] ids = request.getParameterValues("multiIds");
		if (ids != null && ids.length > 0) {
			Integer activeCount=productReviewManager.doActiveAllByIds(ids);
			saveMessage(Message.info("productReviewList.active.multi", getEntityTypeMessage(), activeCount));
		}
		return getRedirectToActionView("search");
	}

}
