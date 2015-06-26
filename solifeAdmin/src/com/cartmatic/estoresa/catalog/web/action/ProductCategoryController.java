
package com.cartmatic.estoresa.catalog.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.catalog.service.ProductCategoryManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.helper.AttributeUtil;
import com.cartmatic.estore.common.helper.JFieldErrorUtil;
import com.cartmatic.estore.common.model.attribute.Attribute;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.service.AttributeService;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.JFieldError;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class ProductCategoryController extends CategoryController<Category> {
	private ProductCategoryManager	productCategoryManager	= null;
	private AttributeService attributeService=null;
	private ProductMainManager productMainManager=null;
	private ProductManager productManager=null;
	private CatalogManager catalogManager=null;
	
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public void setProductCategoryManager(ProductCategoryManager inMgr) {
		this.productCategoryManager = inMgr;
	}
  
	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	@Override
	protected void initController() throws Exception {
		mgr = this.categoryManager;
	}
	
	@Override
	public  String getEntityName(Category entity) {
		return entity.getClass().getSimpleName();
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
		Category tempCategory = categoryManager.getProductCategoryByCode(parentCategory.getCatalogId(),entity.getCategoryCode());
		if (tempCategory != null
				&& (entity.getCategoryId() == null || entity.getCategoryId()
						.intValue() != tempCategory.getCategoryId().intValue())) {
			String msgKey = "category.categoryCode.repeated";
			errors.rejectValue("categoryCode", msgKey);
		}
		if(entity.getCategoryId()==null&&entity.getIsCreateSubLinkedCategory()!=null&&entity.getIsCreateSubLinkedCategory().intValue()==Constants.FLAG_TRUE){
			Integer linkedCategoryId=ServletRequestUtils.getIntParameter(request,"linkedCategoryId", 0);
			if(linkedCategoryId>0){
				Category linkedCategory=categoryManager.getById(linkedCategoryId);
				List<Category>linkedCategoryAllChildren=linkedCategory.getAllChildren();
				int count=0;
				String msg="";
				for (Category linkedCategoryChild : linkedCategoryAllChildren)
				{
					tempCategory = categoryManager.getProductCategoryByCode(parentCategory.getCatalogId(),linkedCategoryChild.getCategoryCode());
					if(tempCategory!=null){
						count++;
						msg+="<br/>["+parentCategory.getCatalog().getCode()+"]"+tempCategory.getCategoryPathName()+"=="+"["+linkedCategoryChild.getCatalog().getCode()+"]"+linkedCategoryChild.getCategoryPathName();
					}
					if(count>=5){
						break;
					}
				}
				if(count>0){
					msg=getMessage("category.linkedCategory.code.repeated")+msg;
					errors.rejectValue("linkedCategory", msg);
				}
			}
		}
	}

	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			// 取得Form对应的Model
			Category entity = formBackingObject(request);
			boolean isNew=entity.getId()==null;
			BindException errors = null;
			try {
				ServletRequestDataBinder binder = bindAndValidate(request, entity);
				errors = new BindException(binder.getBindingResult());
				// 传给子类的实现，后者可以继续验证和加入错误到errors
				entity.setCategoryType(Constants.CATEGORY_TYPE_CATALOG);
				onSave(request, entity, errors);
				if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
					// 保存、更新自定义属性
					List<AttributeValue> attributeValues = AttributeUtil.getInstance().getAttributeFromRequest(request);
					Integer oldLinkedCategoryId=categoryManager.getLinkedCategoryId(entity.getCategoryId());
					entity.setOldLinkedCategoryId(oldLinkedCategoryId);
					categoryManager.saveCategoryAction(entity,attributeValues);
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
				data.put("cId", entity.getCatalogId());
				data.put("id", entity.getId());
				data.put("isVirtual", entity.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue());
				data.put("iconSkin", entity.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
				//data.put("icon", entity.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"/StoreAdmin/images/icon/category_linked.png":"/StoreAdmin/images/icon/category.png");
				
				
				if(isNew&&entity.getIsCreateSubLinkedCategory()!=null&&entity.getIsCreateSubLinkedCategory().intValue()==Constants.FLAG_TRUE){
					List<Category> subCategoryList=entity.getAllChildren();
					JSONArray subTreeItemJsonList=new JSONArray();
					for (Category categoryTreeItem : subCategoryList){
						JSONObject item=new JSONObject();
						item.put("name", categoryTreeItem.getCategoryName());
						item.put("code", categoryTreeItem.getCategoryCode());
						item.put("categoryPath", categoryTreeItem.getCategoryPath());
						item.put("isLinkcategory", categoryTreeItem.getLinkedCategory()!=null);
						item.put("id", categoryTreeItem.getId());
						item.put("pId", categoryTreeItem.getParentId()==null?0:categoryTreeItem.getParentId());
						item.put("cId", categoryTreeItem.getCatalog().getCatalogId());
						item.put("isVirtual", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue());
						item.put("iconSkin", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
						subTreeItemJsonList.add(item);
					}
					data.put("subTreeItemJsonList", subTreeItemJsonList);
				}
				
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
		List<Attribute>attributeList=attributeService.getAttributes(new Short(com.cartmatic.estore.attribute.Constants.MODULE_CATEGORY.toString()));
		mv.addObject("attributeList", attributeList);
		
		if (RequestUtil.getInteger(request, "categoryId") == null) {
			Integer parentId = RequestUtil.getInteger(request,"parentId");
			Category parentCategory = categoryManager.getById(parentId);
			mv.addObject("parentCategory", parentCategory);
			mv.addObject("catalog", parentCategory.getCatalog());
		}else{
			Category category = (Category) mv.getModel().get(formModelName);
			Category parentCategory = category.getCategory();
			mv.addObject("parentCategory", parentCategory);
			mv.addObject("catalog", category.getCatalog());
		}
	}



	@Override
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer categoryId = RequestUtil.getInteger(request, "categoryId");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("categoryId", categoryId);
			Category category=categoryManager.getById(categoryId);
			if (categoryManager.isContainSubCategoriesOrProducts(categoryId)) {
				ajaxView.setStatus(new Short("2"));
				ajaxView.setMsg(getMessage("category.remove.notEmpty",category.getCategoryName()));
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
	
	public ModelAndView addExistingProduct(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer productId=ServletRequestUtils.getIntParameter(request,"productId", 0);
			Integer categoryId=ServletRequestUtils.getIntParameter(request, "categoryId", 0);
			if(productId>0&&categoryId>0){
				Category category=categoryManager.getById(categoryId);
				Product product=productManager.getById(productId);
				Short result= productMainManager.addExistingProductToCate(category, product);
				if(result.intValue()==1){
					ajaxView.setStatus(result);
					ajaxView.setMsg(getMessage("category.add.existingProduct.success",category.getCategoryName(),product.getProductName()));
				}else{
					ajaxView.setStatus(result);
					ajaxView.setMsg(getMessage("category.existing.product",category.getCategoryName(),product.getProductName()));
				}
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 移除映射产品
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView removeExistingProduct(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer productIds[]=RequestUtil.getIntegerArrayNullSafe(request,"multiIds");
			Integer categoryId=ServletRequestUtils.getIntParameter(request, "categoryId", 0);
			if(productIds.length>0&&categoryId>0){
				Category category=categoryManager.getById(categoryId);
				if(productIds.length==1){
					Product product=productManager.getById(productIds[0]);
					Short result= productMainManager.remmoveExistingProduct4Cate(category, product);
					if(result.intValue()==1){
						ajaxView.setStatus(result);
						ajaxView.setMsg(getMessage("category.remove.existingProduct.success",category.getCategoryName(),product.getProductName()));
					}else{
						ajaxView.setStatus(result);
						ajaxView.setMsg(getMessage("category.notExisting.product",category.getCategoryName(),product.getProductName()));
					}
				}else{
					List<String> successProductCodeList=new ArrayList<String>();
					List<String> errorProductCodeList=new ArrayList<String>();
					for (Integer productId : productIds){
						Product product=productManager.getById(productId);
						Short result= productMainManager.remmoveExistingProduct4Cate(category, product);
						if(result.intValue()==1){
							successProductCodeList.add(product.getProductCode());
						}else{
							errorProductCodeList.add(product.getProductCode());
						}
					}
					if(errorProductCodeList.size()>0){
						ajaxView.setStatus(new Short("2"));
						ajaxView.setMsg(getMessage("category.remove.existingProducts.success2",category.getCategoryName(),successProductCodeList.size(),errorProductCodeList.size(),Arrays.toString(errorProductCodeList.toArray(new String[]{}))));
					}else{
						ajaxView.setStatus(new Short("1"));
						ajaxView.setMsg(getMessage("category.remove.existingProducts.success",category.getCategoryName(),successProductCodeList.size()));
					}
				}
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	public ModelAndView checkCategoryCodeRepeated(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			Integer catalogId=ServletRequestUtils.getIntParameter(request, "catalogId", 0);
			Integer linkedCategoryId=ServletRequestUtils.getIntParameter(request,"linkedCategoryId", 0);
			if(linkedCategoryId>0&&catalogId>0){
				Catalog catalog=catalogManager.getById(catalogId);
				Category linkedCategory=categoryManager.getById(linkedCategoryId);
				List<Category>linkedCategoryAllChildren=linkedCategory.getAllChildren();
				int count=0;
				String msg="";
				for (Category linkedCategoryChild : linkedCategoryAllChildren)
				{
					Category tempCategory = categoryManager.getProductCategoryByCode(catalog.getCatalogId(),linkedCategoryChild.getCategoryCode());
					if(tempCategory!=null){
						count++;
						msg+="<br/>["+catalog.getCode()+"]"+tempCategory.getCategoryPathName()+"=="+"["+linkedCategoryChild.getCatalog().getCode()+"]"+linkedCategoryChild.getCategoryPathName();
					}
					if(count>=5){
						break;
					}
				}
				if(count>0){
					msg=getMessage("category.linkedCategory.code.repeated")+msg;
					ajaxView.setMsg(msg);
					ajaxView.setStatus(new Short("2"));
				}
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
}
