
package com.cartmatic.estoresa.sales.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.sales.service.RecommendedTypeManager;
/**
 * 后台关联推荐类型管理controller
 * @author CartMatic
 *
 */
public class RecommendedTypeController extends GenericController<RecommendedType> {
	private RecommendedTypeManager	recommendedTypeManager						= null;

	public void setRecommendedTypeManager(RecommendedTypeManager recommendedTypeManager) {
		this.recommendedTypeManager = recommendedTypeManager;
	}

	protected String getEntityName(RecommendedType entity) {
		return entity.getRecommendTitle();
	}
	
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		throw new RuntimeException("Not implemented yet!");
	}

	protected void initController() throws Exception {
		mgr = recommendedTypeManager;
	}

	protected void onSave(HttpServletRequest request, RecommendedType entity,
			BindException errors) {
	}

	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		

	}
	
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		RecommendedType entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				if(entity.getRecommendedTypeId() == null){
					entity.setIsSystem(Constants.FLAG_TRUE);
				}
				mgr.save(entity);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(),getEntityName(entity)));
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

}
