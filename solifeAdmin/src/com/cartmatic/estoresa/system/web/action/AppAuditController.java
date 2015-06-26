package com.cartmatic.estoresa.system.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.system.AppAudit;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.system.service.AppAuditManager;

public class AppAuditController extends GenericController<AppAudit> {
    private AppAuditManager appAuditManager = null;

    public void setAppAuditManager(AppAuditManager inMgr) {
        this.appAuditManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(AppAudit entity) {
		return entity.getAppAuditName();
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
		mgr = appAuditManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, AppAudit entity, BindException errors) {
	}

	@Override
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) {
		SearchCriteria sc = null;
		if(StringUtils.isNotBlank(request.getParameter("userName"))){
			String userName=request.getParameter("userName").trim();
			sc = createSearchCriteria(request,"byUserName");
			sc.addParamValue("%"+userName+"%");
			sc.addParamValue("%"+userName+"%");
			sc.addParamValue("%"+userName+"%");
		}else{
			sc = createSearchCriteria(request);
		}
		List<AppAudit> results = searchByCriteria(sc);
		return getModelAndView(listView, listModelName, results);
	}
	
	private String getUsers(String name){
    	StringBuffer buf = new StringBuffer("0");
    	List<Integer> userIds=appAuditManager.findUserIdsbyName(name);
    	for (Integer userId : userIds) {
    		buf.append(","+userId);
		}
    	return buf.toString();
    }
	
	

}
