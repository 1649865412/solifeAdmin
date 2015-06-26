
package com.cartmatic.estoresa.attribute.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.attribute.service.AttributeManager;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;

public class AttributeController extends GenericController<Attribute> {
	private AttributeManager	attributeManager	= null;
    
	public void setAttributeManager(AttributeManager inMgr) {
		this.attributeManager = inMgr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Attribute entity) {
		
		return entity.getAttributeName();
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
		String[] multiIds = request.getParameterValues("attrIds");
		Map<Integer, Map<String , Object>> map = new HashMap<Integer, Map<String, Object>>();
		if(multiIds != null&&multiIds.length != 0){
		  for(int i=0;i<multiIds.length;i++){
			Integer id = Integer.valueOf(multiIds[i]);
			String b = request.getParameter(multiIds[i]);
			Map<String, Object> status = new HashMap<String, Object>();
			status.put("status", b.equals("1")?true:false);
			map.put(id, status);
		  }
		}
	    return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = attributeManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Attribute entity,
			BindException errors) {
	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Attribute attr = formBackingObject(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, attr);
			errors = new BindException(binder.getBindingResult());
			
			//onSave(request, attr, errors);
			if (!errors.hasErrors()) {
				if(request.getParameter("from")!=null&&request.getParameter("from").equals("new")){
					String code = attr.getAttributeCode();
					Attribute testA = attributeManager.getAttribute(code);
					if(testA!=null){
						request.setAttribute("HAS_ERRORS", true);
						request.setAttribute("attributeCode_repeat", true);
						return edit(request, response);
					}
				}
				attributeManager.saveOrUpdateAttribute(attr);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, new Object[] {getEntityTypeMessage(), getEntityName(attr) }));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}
		
		ModelAndView mav;
		if (errors.hasErrors()) {
			mav = showForm(request, errors);
		} else if (successView != null) {
			mav = search(request, response);
		} else {
			mav = getRedirectToActionView("edit", ((BaseObject) attr).getId()
					.toString());
		}
		return mav;
	}
	
	/**
	 * 批量修改属性值
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView bulkUpdateAttrValue(HttpServletRequest request, HttpServletResponse response)
	{
		String attId = request.getParameter("attributeId");
		String fromValue = request.getParameter("fromValue");
		String toValue = request.getParameter("toValue");
		if (StringUtils.isBlank(fromValue) || StringUtils.isBlank(toValue))
		{
			saveMessage(Message.error("attribute.error.value.blank"));
		}
		else if(toValue.equals(fromValue)){
			saveMessage(Message.error("attribute.error.value.equals"));
		}
		else
		{
			Attribute obj = attributeManager.loadById(new Integer(attId));
			int result = attributeManager.bulkUpdateAttrValue(fromValue, toValue, obj);
			saveMessage(Message.info("attribute.bulkUpdate.attrValue.success",result));
		}
		return getRedirectToActionView("edit", attId);
	}
	
	public ModelAndView getAttribute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		String code=request.getParameter("code");
		if(StringUtils.isNotBlank(code)){
			Attribute attr = attributeManager.getAttribute(code);
			if(attr!=null){
				Map<String, Object>attribute=new HashMap<String, Object>();
				attribute.put("attributeId", attr.getAttributeId());
				attribute.put("attributeName", attr.getAttributeName());
				attribute.put("attributeCode", attr.getAttributeCode());
				ajaxView.setData(attribute);
			}
		}
		return ajaxView;
	}

}
