/**
 * 
 */
package com.cartmatic.estoresa;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductMainManager;
import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.webapp.util.RequestContext;

/**
 * @author kedou
 * 
 */
public class AdminManageBoardController extends BaseController {
	private ProductMainManager productMainManager;
	private CatalogManager catalogManager;
	private CategoryManager categoryManager;
	private ProductTypeManager productTypeManager;
	
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public void setProductMainManager(ProductMainManager productMainManager) {
		this.productMainManager = productMainManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}

	public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		if(!isAdmin()){
			return null;
		}
		return getModelAndView("/system/adminManageBoard",null);
	}

	private boolean isAdmin(){
		AppUser currentUser=(AppUser)RequestContext.getCurrentUser();	
		return currentUser.isContainRole("admin");
	}
	
	public ModelAndView updateProductType(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(!isAdmin()){
			return null;
		}
		AjaxView ajaxView=new AjaxView(response);
		String catalogCode=request.getParameter("catalogCode");
		String categoryCode=request.getParameter("categoryCode");
		String productTypeName=request.getParameter("productTypeName");
		try {
			Catalog catalog=catalogManager.getByCode(catalogCode);
			if(catalog==null){
				ajaxView.setStatus(new Short("-1"));
				ajaxView.setMsg("目录不存在");
				return ajaxView;
			}
			Category category=categoryManager.getProductCategoryByCode(catalog.getId(), categoryCode);
			if(category==null){
				ajaxView.setStatus(new Short("-1"));
				ajaxView.setMsg("商品分类不存在");
				return ajaxView;
			}
			ProductType newProductType=productTypeManager.getProductTypeByName(productTypeName);
			if(newProductType==null){
				ajaxView.setStatus(new Short("-1"));
				ajaxView.setMsg("产品类型不存在");
				return ajaxView;
			}
			productMainManager.updateProductType(category, newProductType);
			ajaxView.setMsg("["+catalog.getCode()+"]目录的["+category.getCategoryCode()+"]商品分类下的产品已更新到["+productTypeName+"]产品类型");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxView.setStatus(new Short("-500"));
		}
		return ajaxView;
	}
	
	public ModelAndView deleteProduct(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(!isAdmin()){
			return null;
		}
		AjaxView ajaxView=new AjaxView(response);
		String catalogCode=request.getParameter("catalogCode");
		String categoryCode=request.getParameter("categoryCode");
		boolean isIncludeSubCat=ServletRequestUtils.getBooleanParameter(request, "isIncludeSubCat", false);
		try {
			Catalog catalog=catalogManager.getByCode(catalogCode);
			if(catalog==null){
				ajaxView.setStatus(new Short("-1"));
				ajaxView.setMsg("目录不存在");
				return ajaxView;
			}
			Category category=categoryManager.getProductCategoryByCode(catalog.getId(), categoryCode);
			if(category==null){
				ajaxView.setStatus(new Short("-1"));
				ajaxView.setMsg("商品分类不存在");
				return ajaxView;
			}
			
			Integer count=productMainManager.deleteProduct(category, isIncludeSubCat);
			ajaxView.setMsg("["+catalog.getCode()+"]目录的["+category.getCategoryCode()+"]商品分类下的"+count+"个产品已删除.");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxView.setStatus(new Short("-500"));
		}
		return ajaxView;
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
