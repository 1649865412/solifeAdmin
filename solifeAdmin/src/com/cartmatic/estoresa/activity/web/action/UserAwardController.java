package com.cartmatic.estoresa.activity.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.activity.service.AwardService;
import com.cartmatic.estore.activity.service.UserAwardService;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.activity.UserAward;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.view.AjaxView;

public class UserAwardController extends GenericController<UserAward>
{
	
	private UserAwardService userAwardService;
	
	private AwardService awardService;
	
	@Override
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		//根据search参数来设置filter
		SearchCriteria sc =null;
		if(sc==null){
			sc = createSearchCriteria(request,"default");
		}
		List<UserAward> results = userAwardService.searchByCriteria(sc);
		
		request.setAttribute("awardList", this.awardService.getAllOrdered("level", true));
		
		return getModelAndView(listView, listModelName, results);
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
					userAwardService.deleteById(Integer.parseInt(id));
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
			UserAward ua = userAwardService.getById(new Integer(entityId));
			String entityName = getEntityName(ua);
			userAwardService.deleteById(Integer.parseInt(entityId));
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
	protected String getEntityName(UserAward entity) {
		// TODO Auto-generated method stub
		return entity.getFirstKeyColumnName();
	}

	@Override
	protected void onSave(HttpServletRequest request, UserAward entity, BindException errors) {
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
		mgr = this.userAwardService;
	}

	public UserAwardService getUserAwardService() {
		return userAwardService;
	}

	public void setUserAwardService(UserAwardService userAwardService) {
		this.userAwardService = userAwardService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}


}
