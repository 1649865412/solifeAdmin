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
import com.cartmatic.estore.catalog.service.BrandManager;
import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.supplier.service.SupplierManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class CatalogController extends GenericController<Catalog> {
    private CatalogManager catalogManager = null;
    
    private CategoryManager categoryManager=null;
    
    private BrandManager brandManager=null;
    
    private SupplierManager supplierManager=null;
    
    private ProductTypeManager productTypeManager=null;

    public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setCatalogManager(CatalogManager inMgr) {
        this.catalogManager = inMgr;
    }

	
	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Catalog entity) {
		return entity.getCatalogName();
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
		mgr = catalogManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Catalog entity, BindException errors) {
		Catalog tempCatalog=catalogManager.getByCode(entity.getCode());
		if (tempCatalog != null
				&& (entity.getId() == null || entity.getId().intValue() != tempCatalog.getId().intValue())) {
			String msgKey = "catalog.code.repeated";
			errors.rejectValue("code", msgKey);
		}
		if(entity.getIsVirtual().intValue()==Constants.FLAG_FALSE&&(entity.getAvailabilityRule()==null||entity.getAvailabilityRule().intValue()<1)){
			String msgKey = "catalog.availabilityRule.required";
			errors.rejectValue("availabilityRule", msgKey);
		}
	}

	@Override
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			// 取得Form对应的Model
			Catalog entity = formBackingObject(request);
			BindException errors = null;
			try {
				ServletRequestDataBinder binder = bindAndValidate(request, entity);
				errors = new BindException(binder.getBindingResult());
				// 传给子类的实现，后者可以继续验证和加入错误到errors
				onSave(request, entity, errors);
				if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
					// 保存、更新自定义属性
					catalogManager.save(entity);
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
				data.put("name", entity.getName());
				data.put("code", entity.getCode());
				data.put("cId", entity.getCatalogId());
				data.put("id", entity.getCategory().getId());
				data.put("isVirtual", entity.getIsVirtual()==Constants.FLAG_TRUE.shortValue());
				data.put("icon", entity.getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"../images/icon/catalog_virtual.png":"../images/icon/catalog.png");
				
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				ajaxView.setMsg(getMessage(msgKey, new Object[] {getEntityTypeMessage(), entity.getName()}));
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
	public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse response) {
		// 获取所有品牌（搜索表单）
		List<Brand> brands = brandManager.findAllBrands();
		request.setAttribute("brands", brands);
		// 获取所有供应商（搜索表单）
		List<Supplier> suppliers = supplierManager.getAllByDefaultSortBy();
		request.setAttribute("suppliers", suppliers);
		// 获取所有产品类型，（搜索表单）
		List<ProductType> productTypes = productTypeManager.getAll();
		request.setAttribute("productTypes", productTypes);
		// 清空导航新框架信息删
		removeNavFromSearchCriteria(request);
		
		//加载目录树数据
		List<CategoryTreeItem> catalogTreeItems = categoryManager.getCatalogTreeItemList();
		
		request.setAttribute("categoryTreeItems", catalogTreeItems);
		
		JSONArray catalogTreeItemJsonList=new JSONArray();
		for (CategoryTreeItem categoryTreeItem : catalogTreeItems){
			JSONObject item=new JSONObject();
			item.put("name", categoryTreeItem.getCategoryName());
			item.put("code", categoryTreeItem.getCategoryCode());
			item.put("categoryPath", categoryTreeItem.getCategoryPath());
			item.put("isLinkcategory", categoryTreeItem.getLinkedCategory()!=null);
			item.put("id", categoryTreeItem.getId());
			item.put("pId", categoryTreeItem.getParentId()==null?0:categoryTreeItem.getParentId());
			item.put("cId", categoryTreeItem.getCatalog().getCatalogId());
			item.put("isVirtual", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue());
			
			//node 图标指定
			/*if(categoryTreeItem.getIsCatalog()==Constants.FLAG_TRUE.intValue()){
				item.put("icon", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"../images/icon/catalog_virtual.png":"../images/icon/catalog.png");
			}else{
				item.put("icon", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"../images/icon/category_linked.png":"../images/icon/category.png");
			}*/
			if(categoryTreeItem.getIsCatalog()==Constants.FLAG_TRUE.intValue()){
				item.put("iconSkin", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"catalog_virtual":"catalog");
			}else{
				item.put("iconSkin", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
			}
			catalogTreeItemJsonList.add(item);
		}
		request.setAttribute("catalogTreeItemJsonList", catalogTreeItemJsonList);
		removeNavFromSearchCriteria(request);
		return new ModelAndView("catalog/catalogList");
	}
	
	
	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer catalogId = RequestUtil.getInteger(request, "catalogId");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("catalogId", catalogId);
			Catalog catalog=catalogManager.getById(catalogId);
			Integer result=catalogManager.deleteCatalog(catalogId);
			if (result.intValue()==2) {
				ajaxView.setStatus(new Short("2"));
				ajaxView.setMsg(getMessage("category.remove.notEmpty",catalog.getName()));
			} else if(result.intValue()==1) {
				ajaxView.setStatus(new Short("1"));
				ajaxView.setMsg(getMessage("category.deleted",catalog.getName()));
			}else{
				ajaxView.setStatus(new Short("-500"));
				ajaxView.setMsg(getMessage("system.error.msg"));
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