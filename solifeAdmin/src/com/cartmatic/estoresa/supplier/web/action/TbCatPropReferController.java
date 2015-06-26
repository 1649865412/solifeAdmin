package com.cartmatic.estoresa.supplier.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import com.cartmatic.estore.catalog.service.AccessoryGroupManager;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.supplier.TbCatPropRefer;
import com.cartmatic.estore.supplier.service.TbCatPropReferManager;

public class TbCatPropReferController extends GenericController<TbCatPropRefer> {
    private TbCatPropReferManager tbCatPropReferManager = null;
    
    private AccessoryGroupManager accessoryGroupManager=null;

    public void setAccessoryGroupManager(AccessoryGroupManager accessoryGroupManager) {
		this.accessoryGroupManager = accessoryGroupManager;
	}

	public void setTbCatPropReferManager(TbCatPropReferManager inMgr) {
        this.tbCatPropReferManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(TbCatPropRefer entity) {
		return entity.getTbCatPropReferName();
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
		mgr = tbCatPropReferManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, TbCatPropRefer entity, BindException errors) {
		String accessoryGroupCode=request.getParameter("accessoryGroupCode");
		if(StringUtils.isNotBlank(accessoryGroupCode)){
			AccessoryGroup accessoryGroup=accessoryGroupManager.getAccessoryGroupByCode(accessoryGroupCode);
			if(accessoryGroup!=null){
				entity.setAccessoryGroup(accessoryGroup);
			}else{
				String msgKey = "tbCatPropRefer.accessoryGroupId.is.null";
				errors.rejectValue("accessoryGroupId", msgKey);
			}
		}else{
			entity.setAccessoryGroup(null);
		}
		if(!errors.hasErrors()){
			TbCatPropRefer tbCatPropRefer2=tbCatPropReferManager.getTbCatPropReferByAccGroupCode(accessoryGroupCode);
			if (tbCatPropRefer2 != null&& (entity.getId() == null || tbCatPropRefer2.getId().intValue() != entity.getId().intValue())) {
				String msgKey = "tbCatPropRefer.accessoryGroupId.refer.repeated";
				errors.rejectValue("accessoryGroupId", msgKey,new Object[]{tbCatPropRefer2.getTbCatPropName()},"");
			}
		}
	}

}
