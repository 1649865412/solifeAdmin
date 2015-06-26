package com.cartmatic.estoresa.supplier.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.supplier.TbCatPropValueRefer;
import com.cartmatic.estore.supplier.service.TbCatPropValueReferManager;

public class TbCatPropValueReferController extends GenericController<TbCatPropValueRefer> {
    private TbCatPropValueReferManager tbCatPropValueReferManager = null;

    public void setTbCatPropValueReferManager(TbCatPropValueReferManager inMgr) {
        this.tbCatPropValueReferManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(TbCatPropValueRefer entity) {
		return entity.getTbCatPropValueReferName();
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
		mgr = tbCatPropValueReferManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, TbCatPropValueRefer entity, BindException errors) {
		Long tbCatPropValueId=new Long(request.getParameter("tbCatPropValueId"));
		String accessoryName=request.getParameter("accessoryName");
		if(tbCatPropValueId!=entity.getTbCatPropValueId().longValue()){
			String msgKey = "tbCatPropValueRefer.save.params.error";
			errors.reject(msgKey, "save error.");
		}
		if((!errors.hasErrors())&&StringUtils.isBlank(accessoryName)){
			entity.setAccessory(null);
			//String msgKey = "tbCatPropValueRefer.accessory.error.required";
			//errors.reject(msgKey, "accessoryName required.");
		}
		if(StringUtils.isNotBlank(accessoryName)&&!errors.hasErrors()){
			AccessoryGroup accessoryGroup=entity.getTbCatPropRefer().getAccessoryGroup();
			Set<Accessory> accessorys=accessoryGroup.getAccessorys();
			Accessory selected_accessory=null;
			for (Accessory accessory : accessorys) {
				if(accessory.getAccessoryName().equals(accessoryName.trim())){
					selected_accessory=accessory;
					break;
				}
			}
			if(selected_accessory==null){
				String msgKey = "tbCatPropValueRefer.accessory.error.not.found";
				errors.reject(msgKey, "accessory not found.");
			}else{
				entity.setAccessory(selected_accessory);
			}
		}
		//检查该附件项是否已经与其他附件项关联了
		if(!errors.hasErrors()){
			Set<TbCatPropValueRefer> tbCatPropValueRefers=entity.getTbCatPropRefer().getTbCatPropValueRefers();
			for (TbCatPropValueRefer tbCatPropValueRefer : tbCatPropValueRefers) {
				if(tbCatPropValueRefer.getAccessory()!=null&&tbCatPropValueRefer.getAccessory().getAccessoryName().equals(accessoryName.trim())&&tbCatPropValueRefer.getTbCatPropValueReferId().intValue()!=entity.getTbCatPropValueReferId()){
					String msgKey = "tbCatPropValueRefer.accessory.error.has.referenced";
					errors.reject(msgKey, "accessory has referenced.");
					break;
				}
			}
		}
	}

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		TbCatPropValueRefer entity = formBackingObject(request);

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

		/**
		 * 保存后返回相应的提示信息，action=1表示保存更新成功，action=2表示保存更新失败； jFiledErrors为错误提示信息;
		 * 提示信息最好以json方式返回
		 * */
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors.hasErrors()) {
			List<JFieldError> jFiledErrors = new ArrayList<JFieldError>();
			List<ObjectError> ObjectErrors = errors.getAllErrors();
			for (ObjectError objectError : ObjectErrors) {
				JFieldError jFieldError = new JFieldError();
				String message = "";
				if (objectError instanceof FieldError) {
					FieldError fieldError = (FieldError) objectError;
					jFieldError.setObjectName(fieldError.getObjectName());
					jFieldError.setField(fieldError.getField());
					String key = fieldError.getCodes()[3];
					jFieldError.setKey(key);
					if (StringUtils.isEmpty(fieldError.getDefaultMessage())) {
						message = getMessage(key);
					} else {
						message = fieldError.getDefaultMessage();
					}
				} else {
					jFieldError.setField(objectError.getDefaultMessage());
					message = getMessage(objectError.getCodes()[1]);
				}

				jFieldError.setMessage(message);
				jFiledErrors.add(jFieldError);
			}
			ajaxView.setStatus(new Short("2"));
			data.put("jFiledErrors", jFiledErrors);
		}
		return ajaxView;
	}

	
	
}
