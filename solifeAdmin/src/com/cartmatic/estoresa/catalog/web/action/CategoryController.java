
package com.cartmatic.estoresa.catalog.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.webapp.util.RequestUtil;

public abstract class CategoryController<T> extends GenericController<T> {

	protected CategoryManager	categoryManager	= null;

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
	

	/* 
	 * 保存目录判断目录Code是否重复
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.BindException)
	 */
	//@Override
	protected void onSave(HttpServletRequest request, Category entity,
			BindException errors) {
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
		// TODO Auto-generated method stub
		return null;
	}

	
	/* 
	 * 新增目录要先指定其父目录
	 * @see com.cartmatic.estore.core.controller.BaseController#onShowForm(javax.servlet.http.HttpServletRequest, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		if (logger.isDebugEnabled()) {
			logger
					.debug("entering CategoryController 'onShowForm' method...");
		}
	}
	
	
	/**
	 * 按categoryIds的顺序更新目录sortOrder
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView saveCategorySortOrder(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer[] categoryIds = RequestUtil.getIntegerArrayNullSafe(request,"categoryIds");
			categoryManager.saveCategorySortOrder(categoryIds);
			ajaxView.setData(categoryIds);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
}