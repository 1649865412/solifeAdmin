
package com.cartmatic.estoresa.sales.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.sales.GiftCertificate;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.sales.service.GiftCertificateManager;

/**
 * 后台礼券controller
 * 
 * @author CartMatic
 * 
 */
public class GiftCertificateController extends
		GenericController<GiftCertificate> {
	private GiftCertificateManager	giftCertificateManager	= null;

	public void setGiftCertificateManager(
			GiftCertificateManager giftCertificateManager) {
		this.giftCertificateManager = giftCertificateManager;
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
		mgr = giftCertificateManager;
	}

	protected void onSave(HttpServletRequest request, GiftCertificate entity,
			BindException errors) {
	}

	protected String getEntityName(GiftCertificate entity) {
		return entity.getGiftCertificateNo();
	}

	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		String state=request.getParameter("state");
		SearchCriteria sc =null;
		if(StringUtils.isNotBlank(state)){
			if (GiftCertificate.SA_STATE_PAST.equals(new Short(state))) {
				sc = createSearchCriteria(request,"defaultState_Past");
				sc.addParamValue(new Date());
			}else{
				sc = createSearchCriteria(request,"defaultState_NotPast");
				sc.addParamValue(new Date());
				sc.addParamValue(new Short(state));
			}
			sc.lockParamValues();
		}else{
			sc = createSearchCriteria(request,"default");
		}
		List<GiftCertificate> results=giftCertificateManager.searchByCriteria(sc);
		return getModelAndView(listView, listModelName, results);
	}

	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		GiftCertificate giftCertificate = (GiftCertificate) mv.getModel().get(
				formModelName);
		if (null == giftCertificate.getGiftCertificateId()) {
			giftCertificate.setStatus(GiftCertificate.STATUS_UNACTIVE);
			setDefaultExpireTime(giftCertificate);
		}
	}

	/**
	 * send email
	 */
	private boolean sendEmail(GiftCertificate giftCertificate) {
		try {
			giftCertificateManager.sendGiftCertificate(giftCertificate, null);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	private void setDefaultExpireTime(GiftCertificate _giftCertificate) {
		GiftCertificate giftCertificate = _giftCertificate;
		giftCertificate.setExpireTime(giftCertificateManager
				.getDefaultGiftCertificateExpireTime());
	}

	private void setGeneratedGiftCertificateNo(GiftCertificate _giftCertificate) {
		GiftCertificate giftCertificate = _giftCertificate;
		giftCertificate.setGiftCertificateNo(giftCertificateManager
				.generateGiftCertificateNo());
	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		GiftCertificate entity = formBackingObject(request);
		entity.setIsSentByEmail(Constants.FLAG_TRUE);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
//				entity.setStatus(GiftCertificate.STATUS_ACTIVE);
				entity.setStatus(GiftCertificate.SA_STATE_UNACTIVE);
				if (null == entity.getGiftCertificateId()) {
					setGeneratedGiftCertificateNo(entity);
					entity.setRemainedAmt(entity.getGiftCertAmt());
				}
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

	public ModelAndView sendEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		GiftCertificate entity = formBackingObject(request);
		try {
			giftCertificateManager.sendGiftCertificate(entity, null);
			String msgKey = "giftCertificate.sendEmail.success";
			saveMessage(Message.info(msgKey, entity.getGiftCertificateNo(),entity.getRecipientEmail()));
		} catch (Exception e) {
			logger.error(e);
			String msgKey = "giftCertificate.sendEmail.error";
			saveMessage(Message.error(msgKey, entity.getGiftCertificateNo(),entity.getRecipientEmail()));
		}
		ModelAndView mav = getRedirectToActionView("edit", ((BaseObject) entity).getId().toString());
		return mav;
	}

	public ModelAndView saveAndSendEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		GiftCertificate entity = formBackingObject(request);
		entity.setIsSentByEmail(Constants.FLAG_TRUE);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				entity.setStatus(GiftCertificate.STATUS_ACTIVE);
				if (null == entity.getGiftCertificateId()) {
					setGeneratedGiftCertificateNo(entity);
					entity.setRemainedAmt(entity.getGiftCertAmt());
				}
				mgr.save(entity);
				String msgKey = (isEntityNew(request)) ? "common.added"	: "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(),getEntityName(entity)));

				giftCertificateManager.sendGiftCertificate(entity, null);
				msgKey = "giftCertificate.sendEmail.success";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(),getEntityName(entity)));
			}
		} catch (Exception e) {
			logger.error(e);
			String msgKey = "giftCertificate.sendEmail.error";
			saveMessage(Message.error(msgKey, getEntityTypeMessage(),getEntityName(entity)));
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

	public ModelAndView active(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		GiftCertificate entity = formBackingObject(request);

		try {
			entity.setStatus(GiftCertificate.STATUS_ACTIVE);
			mgr.save(entity);
			saveMessage(Message.info("giftCertificate.activate.succeed", entity.getGiftCertificateNo()));

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		ModelAndView mav = getRedirectToActionView("edit",
				((BaseObject) entity).getId().toString());
		return mav;
	}

	public ModelAndView forbit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		GiftCertificate entity = formBackingObject(request);
		try {
			entity.setStatus(GiftCertificate.STATUS_UNACTIVE);
			mgr.save(entity);
			saveMessage(Message.info("giftCertificate.forbid.succeed", entity.getGiftCertificateNo()));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		ModelAndView mav = getRedirectToActionView("edit",((BaseObject) entity).getId().toString());
		return mav;
	}

}
