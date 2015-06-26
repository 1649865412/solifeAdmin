package com.cartmatic.estoresa.system.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.Region;
import com.cartmatic.estore.common.model.system.ShippingMethod;
import com.cartmatic.estore.common.model.system.ShippingRate;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.service.RegionManager;
import com.cartmatic.estore.system.service.ShippingMethodManager;
import com.cartmatic.estore.system.service.ShippingRateManager;

public class ShippingRateController extends GenericController<ShippingRate> {
    private ShippingRateManager shippingRateManager = null;
    
    private ShippingMethodManager shippingMethodManager=null;
    
    private RegionManager regionManager=null;

    public void setShippingRateManager(ShippingRateManager inMgr) {
        this.shippingRateManager = inMgr;
    }
    
	public void setShippingMethodManager(ShippingMethodManager shippingMethodManager) {
		this.shippingMethodManager = shippingMethodManager;
	}

	public void setRegionManager(RegionManager regionManager) {
		this.regionManager = regionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(ShippingRate entity) {
		return entity.getShippingRateName();
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
		mgr = shippingRateManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, ShippingRate shippingRate, BindException errors) {
	    BigDecimal basePrice = shippingRate.getBasePrice();
		BigDecimal maxWeight = shippingRate.getMaxWeight();
		BigDecimal maxVolume = shippingRate.getMaxVolume();
		BigDecimal baseWeight = shippingRate.getBaseWeight();
		BigDecimal baseVolume = shippingRate.getBaseVolume();
		BigDecimal weightPerRate = shippingRate.getWeightPerRate();
		BigDecimal volumePerRate = shippingRate.getVolumePerRate();
		BigDecimal volumePerFee = shippingRate.getVolumePerFee();
		BigDecimal itemPerRate = shippingRate.getItemPerRate();
		BigDecimal increaseUnit = shippingRate.getIncreaseUnit();

		try {
			if (basePrice != null) {
				basePrice = basePrice.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setBasePrice(basePrice);
			}
			if (maxWeight != null) {
				maxWeight = maxWeight.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setMaxWeight(maxWeight);
			}
			if (maxVolume != null) {
				maxVolume = maxVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setMaxVolume(maxVolume);
			}
			if (baseWeight != null) {
				baseWeight = baseWeight.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setBaseWeight(baseWeight);
			}
			if (baseVolume != null) {
				baseVolume = baseVolume.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setBaseVolume(baseVolume);
			}
			if (weightPerRate != null) {
				weightPerRate = weightPerRate.setScale(2,BigDecimal.ROUND_HALF_UP);
				shippingRate.setWeightPerRate(weightPerRate);
			}
			if (volumePerRate != null) {
				volumePerRate = volumePerRate.setScale(2,BigDecimal.ROUND_HALF_UP);
				shippingRate.setVolumePerRate(volumePerRate);
			}
			if (volumePerFee != null) {
				volumePerFee = volumePerFee.setScale(2,BigDecimal.ROUND_HALF_UP);
				shippingRate.setVolumePerFee(volumePerFee);
			}
			if (itemPerRate != null) {
				itemPerRate = itemPerRate.setScale(2, BigDecimal.ROUND_HALF_UP);
				shippingRate.setItemPerRate(itemPerRate);
			}
			if (increaseUnit != null) {
				increaseUnit = increaseUnit.setScale(2,BigDecimal.ROUND_HALF_UP);
				shippingRate.setIncreaseUnit(increaseUnit);
			}
		} catch (Exception e) {
		} 
	}
	
	

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		ShippingRate entity = formBackingObject(request);
		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			
			Integer reaionId = entity.getRegionId();
			Integer shippingMethodId = entity.getShippingMethodId();
			Region regionObj = null;
			ShippingMethod shippingMethod = null;
	        if(reaionId!=null && shippingMethodId!=null){
	    		regionObj = regionManager.getById(reaionId);//获取城市名--提示用
	    		shippingMethod = shippingMethodManager.getById(shippingMethodId);
	        }
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				mgr.save(entity);
	            String key = (isEntityNew(request))? "shippingRate.added" : "shippingRate.updated";
	            saveMessage(Message.info(key,  new Object[] {shippingMethod.getShippingMethodName()+" --> "+regionObj.getRegionName()}));
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
	
	

	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model。因为要先保存其名称，否则可能会连I18N数据也已经删除了；另外可以用于出错的时候恢复显示Form页面
		ShippingRate entity = formBackingObject(request);
		BindException errors = null;
		try {
			// 删除并设置信息
			String entityId = request.getParameter(entityIdName);
			if (!StringUtils.isEmpty(entityId)) {
				// 先构造提示信息，否则信息对应的记录可能也会被删除。
				entity = mgr.deleteById(new Integer(entityId));
	            saveMessage(Message.info("shippingRate.deleted",  new Object[] {entity.getShippingMethod().getShippingMethodName()+" --> "+entity.getRegion().getRegionName()}));
			} else {
				saveMessage(Message.info("errors.invalid.delete.id",  entityId));
			}
		} catch (ApplicationException e) {
			// 为了出错的时候可以恢复表单显示，构造errors，但是设置标记跳过绑定和验证
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			handleApplicationException(errors, e);
		}
		
		/**
		 * 保存后返回相应的提示信息，action=1表示保存更新成功，action=2表示保存更新失败； jFiledErrors为错误提示信息;
		 * 提示信息最好以json方式返回
		 * */
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors!=null&&errors.hasErrors()) {
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

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		if (isEntityNew(request)) {
			ShippingRate shippingRate = (ShippingRate) mv.getModel().get(formModelName);
			String shippingMethodId = request.getParameter("shippingMethodId");
			if (StringUtils.isNotBlank(shippingMethodId)) {
				shippingRate.setShippingMethodId(new Integer(shippingMethodId));
			}
			List<ShippingMethod> shippingMethods = shippingMethodManager.findNormalShippingMethods();
			request.setAttribute("shippingMethodList", shippingMethods);
		}
	}
}
