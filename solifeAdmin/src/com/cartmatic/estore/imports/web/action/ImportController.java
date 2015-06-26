
package com.cartmatic.estore.imports.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CatalogManager;
import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.imports.model.ImportInfo;
import com.cartmatic.estore.imports.service.ImportManager;
import com.cartmatic.estore.system.service.StoreManager;

public class ImportController extends BaseController {
	private ImportManager	importManager	= null;
	
	private List<String> encodings=null;
	
	private List<String> importTypes=null;
	
	private CatalogManager catalogManager=null;
	
	private StoreManager storeManager=null;

	public void setCatalogManager(CatalogManager catalogManager) {
		this.catalogManager = catalogManager;
	}

	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setImportTypes(List<String> importTypes) {
		this.importTypes = importTypes;
	}

	public void setEncodings(List<String> encodings) {
		this.encodings = encodings;
	}
 
	/** 
	 * 显示导入的数据的相关信息
	 * @see com.cartmatic.estore.core.controller.BaseController#defaultAction(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ModelAndView defaultAction(HttpServletRequest request, HttpServletResponse response) throws ServletException 
	{
		removeNavFromSearchCriteria(request);
		ModelAndView mv=new ModelAndView("import/importIndex");
		ImportInfo importInfo=ImportInfo.getInstance();
		mv.addObject("importInfo", importInfo); 
		return mv;
	}
	
	/**
	 * 进入选择导入类型的客户页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView importType(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		ModelAndView mv=new ModelAndView("import/importType");
		ImportInfo importInfo=ImportInfo.getInstance();
		mv.addObject("importInfo", importInfo);
		mv.addObject("importTypes", importTypes);
		return mv;
	}
	
	
	/**
	 * 进入选择导入类型的客户页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView importTarget(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		ModelAndView mv=new ModelAndView("import/importTarget");
		String importType=request.getParameter("importType");
		if(StringUtils.isEmpty(importType)){
			return getRedirectView("/system/import.html?doAction=importType");
		}
		if(importType.equals("product")||importType.equals("productSku")||importType.equals("category")){
			List<Catalog>catalogList=catalogManager.findByPropertyOrdered("isVirtual", Constants.FLAG_FALSE, "name", true);
			mv.addObject("catalogList", catalogList);
		}else if(importType.equals("customer")){
			List<Store>storeList=storeManager.getAllOrdered("name", true);
			mv.addObject("storeList", storeList);
		}
		mv.addObject("importType", importType);
		return mv;
	}
	
	/**
	 * 进入上传导入文件页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView importUpload(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		ModelAndView mv=new ModelAndView("import/importUpload");
		String importType=request.getParameter("importType");
		if(StringUtils.isEmpty(importType)){
			return getRedirectView("/system/import.html?doAction=importType");
		}
		mv.addObject("importType", importType);
		String catalogId=request.getParameter("catalogId");
		String storeId=request.getParameter("storeId");
		if(StringUtils.isEmpty(catalogId)&&StringUtils.isEmpty(storeId)){
			return getRedirectView("/system/import.html?doAction=importTarget");
		}else{
			if(StringUtils.isNotBlank(catalogId)){
				Catalog catalog=catalogManager.getById(new Integer(catalogId));
				mv.addObject("catalog", catalog);
				mv.addObject("catalogId", catalogId);
			}
			if(StringUtils.isNotBlank(storeId)){
				Store store=storeManager.getById(new Integer(storeId));
				mv.addObject("store", store);
				mv.addObject("storeId", storeId);
			}
		}
		mv.addObject("encodings", encodings);
		return mv;
	}

	/**
	 * 进入导入文件预览页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView previewCsv(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String importFile = request.getParameter("importFile");
		String encoding = request.getParameter("encoding");
		ModelAndView mv = null;
		mv = new ModelAndView("import/importFilePreview");
		/*List<List<String>> result=importManager.previewCsv(importFile,encoding);
		mv.addObject("previewCsvData", result);*/
		return mv;
	}
	
	public ModelAndView checkFile(HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String importFile = request.getParameter("importFile");
		String encoding = request.getParameter("encoding");
		try {
			importFile=URLDecoder.decode(importFile,Constants.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Integer rowCount =0;
		Short flag = 1;
		if (rowCount > 5000000) {
			flag = 0;
		}
		ModelAndView mv = null;
		if (flag == 1) {
			mv = new ModelAndView("import/importFileInfo");
		} else {
			mv = new ModelAndView("import/importUpload");
		}
		mv.addObject("rowCount", rowCount);
		mv.addObject("flag", flag);
		
		String catalogId=request.getParameter("catalogId");
		String storeId=request.getParameter("storeId");
		if(StringUtils.isEmpty(catalogId)&&StringUtils.isEmpty(storeId)){
			return getRedirectView("/system/import.html?doAction=importTarget");
		}else{
			if(StringUtils.isNotBlank(catalogId)){
				Catalog catalog=catalogManager.getById(new Integer(catalogId));
				mv.addObject("catalog", catalog);
				mv.addObject("catalogId", catalogId);
			}
			if(StringUtils.isNotBlank(storeId)){
				Store store=storeManager.getById(new Integer(storeId));
				mv.addObject("store", store);
				mv.addObject("storeId", storeId);
			}
		}
		return mv;
	}
	
	public ModelAndView doImport(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String importType=request.getParameter("importType");
		String importFile=request.getParameter("importFile");
		String encoding=request.getParameter("encoding");
		String catalogId=request.getParameter("catalogId");
		String storeId=request.getParameter("storeId");
		try {
			importFile=URLDecoder.decode(importFile,Constants.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String importMappingConfigName=null;
		if(importType.equals("product")){
			importMappingConfigName="productImportConfig";
		}else if(importType.equals("productSku")){
			importMappingConfigName="productSkuImportConfig";
		}else if(importType.equals("category")){
			importMappingConfigName="categoryImportConfig";
		}
		else if(importType.equals("customer")){
			importMappingConfigName="customerImportConfig";
		}
		Catalog catalog=null;
		if(StringUtils.isNotBlank(catalogId)){
			catalog=catalogManager.getById(new Integer(catalogId));
		}
		Store store=null;
		if(StringUtils.isNotBlank(storeId)){
			store=storeManager.getById(new Integer(storeId));
		}
		if(StringUtils.isNotEmpty(importMappingConfigName)){
			importManager.startImport(importType,importMappingConfigName, importFile, encoding,store,catalog);
		}
		saveMessage(Message.info("import.msg", importType,importFile,encoding));
		return getRedirectView("import.html");
	} 

	
	public ModelAndView stop(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		importManager.stopImport();
		return null;
	}


	public void setImportManager(ImportManager importManager) {
		this.importManager = importManager;
	}
	
	public ModelAndView getImportInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		ImportInfo importInfo=ImportInfo.getInstance();
		ajaxView.setData(importInfo);
		return ajaxView;
	}
	
	public ModelAndView stopImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxView ajaxView=new AjaxView(response);
		importManager.stopImport();
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
