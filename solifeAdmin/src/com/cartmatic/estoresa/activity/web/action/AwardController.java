package com.cartmatic.estoresa.activity.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.activity.service.AwardService;
import com.cartmatic.estore.common.model.activity.Award;
import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;

public class AwardController extends GenericController<Award>
{
	
	private AwardService awardService;
	
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Award entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
//				entity.setStatus(GiftCertificate.STATUS_ACTIVE);
				if(entity.getCreateTime() == null){
					entity.setCreateTime(new Date(System.currentTimeMillis()));
				}
				entity.setUpdateTime(new Date(System.currentTimeMillis()));
				mgr.save(entity);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(), getEntityName(entity)));
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
	
	/**
	 * 简化GenericController的multiDelete，ajax处理，返回空数据
	 * 
	 */
	@Override
	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		try {
			String[] ids = request.getParameterValues("multiIds");
			if (ids != null && ids.length > 0) {
				List<Integer> uaIds = new ArrayList<Integer>();
				for (String id : ids) {
					awardService.deleteById(Integer.parseInt(id));
					uaIds.add(Integer.valueOf(id));
				}
				String message = getMessage("common.deleted.multi", new Object[]{ getEntityTypeMessage(), ids.length });

				ajaxView.setMsg(message);
				ajaxView.setStatus(new Short("1"));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		
		return this.defaultAction(request, response);
	}

	/**
	 * 简化GenericController的delete，ajax处理，返回空数据
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#delete(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String entityId = request.getParameter(entityIdName);
			Award ua = awardService.getById(new Integer(entityId));
			String entityName = getEntityName(ua);
			awardService.deleteById(entityId);
			String message = getMessage("common.deleted", new Object[] {getEntityTypeMessage(), entityName });
			ajaxView.setMsg(message);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	@Override
	protected String getEntityName(Award entity) {
		// TODO Auto-generated method stub
		return entity.getFirstKeyColumnName();
	}

	@Override
	protected void onSave(HttpServletRequest request, Award entity, BindException errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		mgr = awardService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

}
