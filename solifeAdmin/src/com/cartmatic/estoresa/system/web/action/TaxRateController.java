package com.cartmatic.estoresa.system.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.catalog.service.ProductTypeManager;
import com.cartmatic.estore.common.model.catalog.ProductType;
import com.cartmatic.estore.common.model.system.Tax;
import com.cartmatic.estore.common.model.system.TaxRate;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.system.service.TaxManager;
import com.cartmatic.estore.system.service.TaxRateManager;

public class TaxRateController extends GenericController<TaxRate> {
	private static final String	ProductType_LIST	= "productTypeList";
	
    private TaxRateManager taxRateManager = null;
    
    private ProductTypeManager productTypeManager=null;
    
    private TaxManager taxManager=null;

    public void setTaxRateManager(TaxRateManager inMgr) {
        this.taxRateManager = inMgr;
    }

	public void setProductTypeManager(ProductTypeManager productTypeManager) {
		this.productTypeManager = productTypeManager;
	}
	
	public void setTaxManager(TaxManager taxManager) {
		this.taxManager = taxManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(TaxRate entity) {
		return entity.getTaxRateName();
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
		mgr = taxRateManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, TaxRate taxRate, BindException errors) {
		if (isEntityNew(request) && !this.check(taxRate)) {
			errors.rejectValue("formula", "taxRate.updatedFailed");
		}
	}
	
	private boolean check(TaxRate taxRate) {
		boolean result = true;
		List<TaxRate> exists = taxRateManager.findByTaxProductTypeRegion(taxRate.getTaxId(), taxRate.getProductTypeId(), taxRate.getRegionId());
		if (exists.size() > 0)
			result = false;
		return result;

	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		List<ProductType> productTypeList = productTypeManager.findActiveProductTypes();
		request.setAttribute(ProductType_LIST, productTypeList);
		
		//TODO 未完成
		List<Tax> taxs=taxManager.getAll();
		request.setAttribute("taxs", taxs);
	}
}
