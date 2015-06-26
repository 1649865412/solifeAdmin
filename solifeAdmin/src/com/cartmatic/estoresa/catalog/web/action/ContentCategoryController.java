
package com.cartmatic.estoresa.catalog.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ContentCategoryController extends CategoryController<Category> {
	private ContentManager	contentManager	= null;

	public void setContentManager(ContentManager inMgr) {
		this.contentManager = inMgr;
	}
  
	@Override
	protected void initController() throws Exception {
		mgr = this.categoryManager;
	}
	
	@Override
	public  String getEntityName(Category entity) {
		return entity.getClass().getSimpleName();
	}
	
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		if (RequestUtil.isSelectorUri(request))
			return categorySelector(request, response);
		
		//加载目录树数据
		List<CategoryTreeItem> contentTreeItems = categoryManager.getContentTreeItemList();
		request.setAttribute("categoryTreeItems", contentTreeItems);
		JSONArray treeItemJsonList=new JSONArray();
		for (CategoryTreeItem categoryTreeItem : contentTreeItems){
			JSONObject item=new JSONObject();
			item.put("name", categoryTreeItem.getCategoryName());
			item.put("code", categoryTreeItem.getCategoryCode());
			item.put("categoryPath", categoryTreeItem.getCategoryPath());
			item.put("isLinkCategory", categoryTreeItem.getIsLinkCategory()==Constants.FLAG_TRUE.shortValue());
			item.put("id", categoryTreeItem.getId());
			item.put("pId", categoryTreeItem.getParentId()==null?0:categoryTreeItem.getParentId());
			item.put("sId", categoryTreeItem.getStore().getStoreId());
			
			//node 图标指定
			if(categoryTreeItem.getIsFalseEntity()==Constants.FLAG_TRUE.intValue()){
				item.put("iconSkin", "catalog");
			}else{
				item.put("iconSkin", categoryTreeItem.getIsLinkCategory()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
			}
			
			treeItemJsonList.add(item);
		}
		request.setAttribute("treeItemJsonList", treeItemJsonList);
		
		// 清空导航新框架信息删
		removeNavFromSearchCriteria(request);
		// 下面这两个属性如果是全局搜索的时候有用
		request.setAttribute("globalSearch", RequestUtil.getParameterNullSafe(request, "globalSearch"));
		request.setAttribute("contentTitle", RequestUtil.getParameterNullSafe(request, "gsContentTitle"));
		return new ModelAndView("catalog/contentCategoryList");
	}

	/**
	 * 产品目录选择器
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView categorySelector(HttpServletRequest request,
			HttpServletResponse response) {
		/*List<CategoryTreeItem> categoryTreeItems = categoryManager.getCategoryTreeItems();
		request.setAttribute("categoryTreeItems", categoryTreeItems);*/
		if(request.getParameter("test")!=null){
			return new ModelAndView("catalog/categorySelector2");
		}else{
			return new ModelAndView("catalog/categorySelector");
		}
		
	}

	
	@Override
	protected void onSave(HttpServletRequest request, Category entity,BindException errors) {
		//检查目录是否重复
		Category parentCategory=null;
		if(entity.getCategoryId()==null){
			parentCategory=categoryManager.getById(entity.getCategory().getCategoryId());
		}else{
			parentCategory=entity.getCategory();
		}
		Category tempCategory = categoryManager.getContentCategoryByCode(parentCategory.getStoreId(),entity.getCategoryCode());
		if (tempCategory != null
				&& (entity.getCategoryId() == null || entity.getCategoryId()
						.intValue() != tempCategory.getCategoryId().intValue())) {
			String msgKey = "category.categoryCode.repeated";
			errors.rejectValue("categoryCode", msgKey);
		}
	}

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			// 取得Form对应的Model
			Category entity = formBackingObject(request);
			BindException errors = null;
			try {
				ServletRequestDataBinder binder = bindAndValidate(request, entity);
				errors = new BindException(binder.getBindingResult());
				// 传给子类的实现，后者可以继续验证和加入错误到errors
				entity.setCategoryType(Constants.CATEGORY_TYPE_CONTENT);
				onSave(request, entity, errors);
				if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
					categoryManager.saveCategoryAction(entity,null);
				}
			} catch (ApplicationException e) {
				handleApplicationException(errors, e);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			if (errors.hasErrors()) {
				List<JFieldError> jFiledErrors = JFieldErrorUtil.getFiledErrors(errors);
				ajaxView.setStatus(new Short("2"));
				data.put("jFiledErrors", jFiledErrors);
			} else {
				ajaxView.setStatus(new Short("1"));
				data.put("name", entity.getCategoryName());
				data.put("code", entity.getCategoryCode());
				data.put("sId", entity.getStoreId());
				data.put("id", entity.getId());
				data.put("isLinkCategory", entity.getIsLinkCategory().intValue()==Constants.FLAG_TRUE);
				data.put("iconSkin", entity.getIsLinkCategory()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
				
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				ajaxView.setMsg(getMessage(msgKey, new Object[] {getEntityTypeMessage(), entity.getCategoryName()}));
			}
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering ProductCategoryController 'onShowForm' method...");
		}
		if (RequestUtil.getInteger(request, "categoryId") == null) {
			Integer parentId = RequestUtil.getInteger(request,"parentId");
			Category parentCategory = categoryManager.getById(parentId);
			mv.addObject("parentCategory", parentCategory);
			mv.addObject("store", parentCategory.getStore());
		}else{
			Category category = (Category) mv.getModel().get(formModelName);
			Category parentCategory = category.getCategory();
			mv.addObject("parentCategory", parentCategory);
			mv.addObject("store", category.getStore());
		}
	}



	/**
	 * 检查指定目录是否存在子目录或存在直属产品
	 * @param categoryId
	 * @return
	 */
	private boolean isContainSubCategoriesOrProducts(Integer categoryId) {
		Integer subCount = categoryManager.countSubCategoryById(categoryId);
		if (subCount == null || subCount.intValue() == 0)
			subCount = contentManager
					.getContentCountByCategory(categoryId);
		if (subCount != null && subCount.intValue() > 0)
			return true;
		return false;
	}

	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer categoryId = RequestUtil.getInteger(request, "categoryId");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("categoryId", categoryId);
			Category category=categoryManager.getById(categoryId);
			if (isContainSubCategoriesOrProducts(categoryId)) {
				ajaxView.setStatus(new Short("2"));
				ajaxView.setMsg(getMessage("category.remove.notEmpty2",category.getCategoryName()));
			} else {
				categoryManager.deleteById(categoryId);
				ajaxView.setStatus(new Short("1"));
				ajaxView.setMsg(getMessage("category.deleted",category.getCategoryName()));
			}
			ajaxView.setData(data);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
}
