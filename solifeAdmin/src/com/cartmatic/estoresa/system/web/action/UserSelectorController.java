
package com.cartmatic.estoresa.system.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.system.AppAdmin;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.system.service.AppUserManager;

public class UserSelectorController extends GenericController<AppUser> {
	private AppUserManager appUserManager=null;

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		if(request.getRequestURI().indexOf("userSelectorDataList.html")!=-1){
			return getData(request, response);
		}
		return new ModelAndView("system/userSelector");
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView getData(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria searchCriteria = createSearchCriteria(request,"userSelector");
		String userType=request.getParameter("userType");
		if(StringUtils.isBlank(userType))
			userType="0";
		searchCriteria.addParamValue(new Short(userType));
    	searchCriteria.lockParamValues();
		List results = searchByCriteria(searchCriteria);
		for (Object object : results) {
			AppUser appUser=(AppUser)object;
			if(appUser.getUserType().intValue()==0){
				Customer customer=(Customer)appUser;
				customer.getJsonObject();
			}else{
				AppAdmin appAdmin=(AppAdmin)appUser;
			}
		}
		request.setAttribute("userList", results);
		request.setAttribute("pagingId",request.getParameter("pagingId"));
		return new ModelAndView("system/include/userSelectorDataList");
	}

	@Override
	protected String getEntityName(AppUser entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onSave(HttpServletRequest request, AppUser entity,BindException errors) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		mgr = appUserManager;
	}
}
