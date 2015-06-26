
package com.cartmatic.estoresa.content.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.common.helper.AttributeUtil;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.content.model.ContentSearchCriteria;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.textsearch.SearchConstants;
import com.cartmatic.estore.webapp.event.IndexNotifyEvent;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ContentController extends GenericController<Content> {
	private ContentManager	contentManager	= null;
	private CategoryManager	categoryManager	= null;
	private AttributeService attributeService=null;
	
	public void setContentManager(ContentManager contentManager) {
		this.contentManager = contentManager;
	}
	
	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Content entity) {
		return entity.getContentTitle();
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
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = this.contentManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController#onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Content content,BindException errors) {
		if(content.getStoreId()==null){
			Category category=categoryManager.getById(content.getCategoryId());
			content.setStore(category.getStore());
		}
		if(StringUtils.isNotBlank(content.getContentCode())){
			Content tempContent=contentManager.getContentByCode(content.getStoreId(),content.getContentCode());
			if(tempContent!=null&&(content.getContentId()==null||tempContent.getContentId().intValue()!=content.getContentId().intValue())){
				String msgKey = "content.contentCode.repeated";
				errors.rejectValue("contentCode", msgKey);
			}
		}
		
	}

	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		return null;
	}

	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria searchCriteria = createSearchCriteria(request);
		ContentSearchCriteria contentSearchCriteria = new ContentSearchCriteria();
		BaseBinder binder = new BaseBinder();
		binder.bind(request, contentSearchCriteria,"contentSearchCriteria");
		request.setAttribute("contentSearchCriteria", contentSearchCriteria);
		List<Content> results = contentManager.searchContents(searchCriteria,contentSearchCriteria);
		
		
		/*List<Content> results = null;
		String newSearch = RequestUtil.getParameterNullSafe(request,"newSearch");
		if (newSearch.equals("1")) {
			// ----------------- 根据关键词搜---------------------------//
			ContentSearchCriteria contentSearchCriteria = new ContentSearchCriteria();
			searchCriteria.changePaging(1, searchCriteria.getPageSize());
			BaseBinder binder = new BaseBinder();
			binder
					.bind(request, contentSearchCriteria,
							"contentSearchCriteria");
			contentSearchCriteria.setCategoryId(null); // 目录id不能成为搜索条件
			results = contentManager.searchContents(searchCriteria,contentSearchCriteria);
		} else if (newSearch.equals("2")) {
			// ----------------- 根据目录id搜---------------------------//
			String categoryId = RequestUtil.getParameterNullSafe(request,
					"categoryId");
			ContentSearchCriteria contentSearchCriteria = new ContentSearchCriteria();
			searchCriteria.changePaging(1, searchCriteria.getPageSize());
			Category category = categoryManager
					.getById(new Integer(categoryId));
			request.setAttribute("category", category);
			if (category.getIsLinkCategory() == null
					|| category.getIsLinkCategory() == Category.ISLINKCATEGORY_NO) {
				BaseBinder binder = new BaseBinder();
				binder.bind(request, contentSearchCriteria,
						"contentSearchCriteria");
				contentSearchCriteria.setContentTitle(null); // 标题不能成为搜索条件
				// add more here
				results = contentManager.searchContents(searchCriteria,contentSearchCriteria);
			}
		} else {
			// ----------------- 不是新的搜索---------------------------//
			results = searchByCriteria(searchCriteria);

		}*/
		if (null != results) {
			for (Content content : results) {
				setState(content);
			}
		}
		return getModelAndView("/content/include/contentListBody", listModelName, results);
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		// 取得要删除的记录的id的列表。注意：选择框的id要是"multiIds"。
		try {
			String[] ids = request.getParameterValues("multiIds");
			if (ids != null && ids.length > 0) {
				List<Integer> productIds=new ArrayList<Integer>();
				for (String id : ids) {
					productIds.add(Integer.valueOf(id));
				}
				mgr.deleteAllByIds(ids);
				//设置索引
				IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_CONTENT, SearchConstants.UPDATE_TYPE.DEL); 
				event.setIds(ids);
				ContextUtil.getInstance().fireApplicationEvent(event);
				String message = getMessage("common.deleted.multi", new Object[]{ getEntityTypeMessage(), ids.length });
				//更新索引
				CatalogHelper.getInstance().indexNotifyDeleteEvent(productIds.toArray(new Integer[]{}));
				ajaxView.setMsg(message);
				ajaxView.setStatus(new Short("1"));
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		
		return ajaxView;
	}

	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String entityId = request.getParameter(entityIdName);
			Content content=contentManager.getById(new Integer(entityId));
			String entityName = getEntityName(content);
			mgr.deleteById(Integer.valueOf(entityId));
			//设置索引
			IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_CONTENT, SearchConstants.UPDATE_TYPE.DEL); 
			event.setIds(new String[]{entityId});
			ContextUtil.getInstance().fireApplicationEvent(event);
			String message = getMessage("common.deleted", new Object[] {getEntityTypeMessage(), entityName });
			ajaxView.setMsg(message);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}

	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		Content entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				// 保存、更新自定义属性
				List<AttributeValue> attributeValues = AttributeUtil.getInstance().getAttributeFromRequest(request);
				contentManager.saveContentAction(entity, attributeValues);
				//mgr.save(entity);
				setState(entity);
				//设置索引
				IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_CONTENT, SearchConstants.UPDATE_TYPE.UPDATE); 
				event.addId(entity.getContentId());
				ContextUtil.getInstance().fireApplicationEvent(event);
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		/**
		 * --------------------- 回传JSON给页面----------------------*
		 */
		AjaxView ajaxView=new AjaxView(response);
		Map<String, Object> data = new HashMap<String, Object>();
		ajaxView.setData(data);
		if (errors.hasErrors()) {
			List<JFieldError> jFiledErrors = JFieldErrorUtil.getFiledErrors(errors);
			ajaxView.setStatus(new Short("2"));
			data.put("jFiledErrors", jFiledErrors);
		} else {
			String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
			ajaxView.setMsg(getMessage(msgKey, new Object[] {getEntityTypeMessage(), entity.getContentCode()}));
			ajaxView.setStatus(new Short("1"));
			data.put("contentId", entity.getContentId());
			data.put("contentCode", entity.getContentCode());
			data.put("state", entity.getState());
			data.put("createTime", DateUtil.getDateTime(entity.getCreateTime()));
			data.put("updateTime", DateUtil.getDateTime(entity.getUpdateTime()));
			
			//显示前台访问地址
			String localRedirectUrl=CatalogHelper.getInstance().getContentUrl(entity);
			data.put("viewUrl", ConfigUtil.getInstance().getStore().getSiteUrl()+localRedirectUrl);
			data.put("localRedirectUrl", localRedirectUrl);
		}
		return ajaxView;
	}

	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Content content = (Content) mv.getModel().get(formModelName);
		String categoryId = RequestUtil.getParameterNullSafe(request, "categoryId");
		setState(content);
		if (!categoryId.equals("")) 
		{
			Category category = categoryManager.getById(new Integer(categoryId));
			content.setCategory(category);
		}
		
		if(content.getId()!=null)
		{
			String localRedirectUrl=CatalogHelper.getInstance().getContentUrl(content);
			request.setAttribute("viewUrl",ConfigUtil.getInstance().getStore().getSiteUrl()+localRedirectUrl);
			request.setAttribute("localRedirectUrl", localRedirectUrl);
		}
		List<Attribute> attributeList = attributeService.getAttributes(new Short(com.cartmatic.estore.attribute.Constants.MODULE_CONTENT.toString()));
		request.setAttribute("attributeList", attributeList);
		//mv.addObject("attributeList", attributeList);
	}

	public ModelAndView edit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		Content entity = formBackingObject(request);

		BindException errors = null;
		// Bind without validation, to allow for prepopulating a form, and for
		// convenient error evaluation in views (on both first attempt and
		// resubmit).
		// 会使用formModelName的名称进行绑定
		request.setAttribute("SUPPRESS_VALIDATION", Boolean.TRUE);
		ServletRequestDataBinder binder = bindAndValidate(request, entity);
		errors = new BindException(binder.getBindingResult());
		// this.savedNavAndSearchCriteria(request, sc,
		// getMessage(listModelName+".heading"));
		return showForm(request, errors);
	}

	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Content entity = formBackingObject(request);
		BindException errors = null;
		// 会使用formModelName的名称进行绑定
		request.setAttribute("SUPPRESS_VALIDATION", Boolean.FALSE);
		ServletRequestDataBinder binder = bindAndValidate(request, entity);
		errors = new BindException(binder.getBindingResult());

		return showForm(request, errors);
		// return edit(request, response);
	}

	private void setState(Content content) {
		if (content.getContentId() != null) {
			contentManager.setState(content);
		} else {
			content.setState(null);
		}
	}
}
