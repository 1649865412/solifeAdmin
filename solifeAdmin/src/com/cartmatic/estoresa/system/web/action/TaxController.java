package com.cartmatic.estoresa.system.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;

import com.cartmatic.estore.common.model.system.Tax;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.system.service.TaxManager;


public class TaxController extends GenericController<Tax> {
    private TaxManager taxManager = null;
    
    public void setTaxManager(TaxManager manager) {
        this.taxManager = manager;
    }

	@Override
	protected String getEntityName(Tax entity) {
		// TODO Auto-generated method stub
		return entity.getTaxName();
	}

	@Override
	protected void onSave(HttpServletRequest request, Tax entity,
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
		mgr = taxManager;
	}
        
}
