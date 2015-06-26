
package com.cartmatic.estore.imports.handler.product;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;
import com.cartmatic.estore.imports.util.ImportHelper;
import com.cartmatic.estore.supplier.service.SupplierManager;

public class ProviderHandler extends ColumnBasicHandler implements
		ColumnHandler {
	private Logger logger = Logger.getLogger(ProviderHandler.class);
	private SupplierManager supplierManager=null;

	private Boolean							isName			= true;

	private Boolean							autoAdd			= false;

	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private Integer							maxLength		= null;
	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private LinkedHashMap<String, String>	replaces		= null;
	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private String							toLowerOrUpper	= null;
 
	public void setProperty(ImportModel importModel, Column column) throws Exception {
		Product product = (Product) importModel.getTarget();
		String value = column.getValue();
		Supplier supplier = null;
		if (isName) {
			if (StringUtils.isNotEmpty(value)) {
				supplier = getProviderByName(value);
			}
			if (supplier == null
					&& StringUtils.isNotEmpty(column.getDefaultValue())) {
				supplier = getProviderByName(column.getDefaultValue());
			}
		} else {
			if (StringUtils.isNotEmpty(value)) {
				supplier = supplierManager.getSupplierByCode(value);
			}
			if (supplier == null
					&& StringUtils.isNotEmpty(column.getDefaultValue())) {
				supplier = supplierManager
						.getSupplierByCode(column.getDefaultValue());
			}
		}
		if (supplier == null) {
			if (StringUtils.isNotEmpty(value) && autoAdd) {
				String code=value;
				if (isName) {
					code=ImportHelper.toSubstring(code, maxLength);
					code=ImportHelper.toReplace(code, replaces);
					code=ImportHelper.toLowerOrUpper(code, toLowerOrUpper);
					supplier=supplierManager.getSupplierByCode(code);
				}
				if (supplier == null) {
					//TODO 自动增加失败
					supplier = new Supplier();
					supplier.setSupplierName(value);
					supplier.setSupplierCode(code);
					supplierManager.save(supplier);
					if(logger.isInfoEnabled()){
						logger.info("自动新增供应商："+supplier);
					}
				}
			}
		}
		if (supplier != null) {
			product.setSupplier(supplier);
			importModel.setResult("1");
		}else{
			if(logger.isInfoEnabled()){
				logger.info("没有找到相应的供应商！");
			}
			setDefaultResult(importModel, column);
		}
	}

	private Supplier getProviderByName(String value) {
		List<Supplier> supplierList = supplierManager.getAll();
		if (supplierList != null && supplierList.size() > 0) {
			for (Supplier supplier : supplierList) {
				if (supplier.getSupplierName().equals(value)) {
					return supplier;
				}
			}
		}
		return null;
	}

	public void setIsName(Boolean isName) {
		this.isName = isName;
	}

	public void setAutoAdd(Boolean autoAdd) {
		this.autoAdd = autoAdd;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setReplaces(LinkedHashMap<String, String> replaces) {
		this.replaces = replaces;
	}

	public void setToLowerOrUpper(String toLowerOrUpper) {
		this.toLowerOrUpper = toLowerOrUpper;
	}

	public void setSupplierManager(SupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}
	
	

}
