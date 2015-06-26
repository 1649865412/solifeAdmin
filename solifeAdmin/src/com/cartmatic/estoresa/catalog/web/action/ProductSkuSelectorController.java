package com.cartmatic.estoresa.catalog.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.BrandManager;
import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.core.controller.BaseBinder;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;

public class ProductSkuSelectorController extends GenericController<Product>{

	private ProductManager	productManager	= null;
	private BrandManager	brandManager	= null;
	private CategoryManager	categoryManager	= null;
	private CatalogManager catalogManager=null;
	
	
	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		//当URI为productSkuSelectorDataList.html时，表示查找列表数据
		if(request.getRequestURI().indexOf("productSkuSelectorDataList.html")!=-1){
			return getData(request, response);
		}
		
		//获取所有Catalog
		List<Catalog>catalogList=catalogManager.getAllOrdered("name", true);
		Integer catalogId=ServletRequestUtils.getIntParameter(request, "catalogId", 0);
		Integer virtual=ServletRequestUtils.getIntParameter(request, "virtual", 0);
		if(catalogId>0||virtual>0){
			List<Catalog>tempPatalogList=new ArrayList<Catalog>();
			for (Catalog catalog : catalogList){
				if(catalogId>0){
					if(catalog.getId().intValue()==catalogId){
						tempPatalogList.add(catalog);
						break;
					}
				}else if(virtual>0){
					if(catalog.getIsVirtual()==Constants.FLAG_TRUE.intValue()&&virtual==1){
						tempPatalogList.add(catalog);
					}else if(catalog.getIsVirtual()==Constants.FLAG_FALSE.intValue()&&virtual==2){
						tempPatalogList.add(catalog);
					}
				}
			}
			catalogList=tempPatalogList;
		}
		request.setAttribute("catalogList", catalogList);
		// 获取所有品牌
		List<Brand> brands = brandManager.findAllBrands();
		request.setAttribute("brands", brands);
		return new ModelAndView("catalog/productSkuSelector");
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView getData(HttpServletRequest request,HttpServletResponse response) {
		SearchCriteria searchCriteria = createSearchCriteria(request);
		ProductSearchCriteria productSearchCriteria = new ProductSearchCriteria();
		BaseBinder binder = new BaseBinder();
		binder.bind(request, productSearchCriteria,"productSearchCriteria");
		request.getParameter("productKind");
		productSearchCriteria.setProductStatus("1,2");
		searchCriteria=productManager.getProductSkuSearchCriteria(searchCriteria, productSearchCriteria);
		List results = searchByCriteria(searchCriteria);
		request.setAttribute("productSearchCriteria", productSearchCriteria);
		request.setAttribute("productSkuList", results);
		request.setAttribute("pagingId",request.getParameter("pagingId"));
		return new ModelAndView("catalog/include/productSkuSelectorDataList");
	}
	
	@Override
	protected String getEntityName(Product entity) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onSave(HttpServletRequest request, Product entity,
			BindException errors) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void initController() throws Exception {
		mgr = productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
