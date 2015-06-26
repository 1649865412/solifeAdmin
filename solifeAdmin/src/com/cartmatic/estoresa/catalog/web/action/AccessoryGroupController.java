package com.cartmatic.estoresa.catalog.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.AccessoryGroupManager;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.catalog.SkuOption;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class AccessoryGroupController extends GenericController<AccessoryGroup> {
    private AccessoryGroupManager accessoryGroupManager = null;

    public void setAccessoryGroupManager(AccessoryGroupManager inMgr) {
        this.accessoryGroupManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(AccessoryGroup entity) {
		return entity.getAccessoryGroupName();
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
		mgr = accessoryGroupManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, AccessoryGroup entity, BindException errors) {
		AccessoryGroup tempAccessoryGroup=accessoryGroupManager.getAccessoryGroupByCode(entity.getGroupCode());
		if(tempAccessoryGroup!=null&&(entity.getAccessoryGroupId()==null||(entity.getAccessoryGroupId().intValue()!=tempAccessoryGroup.getAccessoryGroupId().intValue()))){
			String msgKey = "accessoryGroup.groupCode.repeated";
			errors.rejectValue("groupCode", msgKey);
		}
	}
	
	public ModelAndView refurbishAccessoryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering AccessoryController 'refurbishAccessoryList' method...");
		}
		ModelAndView mv=new ModelAndView("catalog/include/accessoryListContent");
		Integer accessoryGroupId=RequestUtil.getInteger(request,"accessoryGroupId");
		AccessoryGroup accessoryGroup=accessoryGroupManager.getById(accessoryGroupId);
		mv.addObject("accessoryGroup",accessoryGroup);
		return mv;
	}

}
