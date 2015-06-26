
package com.cartmatic.estoresa.supplier.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.supplier.service.SupplierManager;

public class SupplierSelectorController extends GenericController<Supplier> {
	private SupplierManager supplierManager=null;
	
	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView defaultAction(HttpServletRequest request,
			HttpServletResponse response) {
		if(request.getRequestURI().indexOf("supplierSelectorDataList.html")!=-1){
			return getData(request, response);
		}
		return new ModelAndView("supplier/supplierSelector");
	}
	
	@SuppressWarnings("unchecked")
	private ModelAndView getData(HttpServletRequest request,
			HttpServletResponse response) {
		SearchCriteria searchCriteria = createSearchCriteria(request);
		List results = searchByCriteria(searchCriteria);
		request.setAttribute("supplierList", results);
		request.setAttribute("pagingId",request.getParameter("pagingId"));
		return new ModelAndView("supplier/include/supplierSelectorDataList");
	}

	@Override
	protected String getEntityName(Supplier entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onSave(HttpServletRequest request, Supplier entity,
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
		mgr = supplierManager;
	}


}
