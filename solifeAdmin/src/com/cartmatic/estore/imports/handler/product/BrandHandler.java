
package com.cartmatic.estore.imports.handler.product;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.service.BrandManager;
import com.cartmatic.estore.common.model.catalog.Brand;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;
import com.cartmatic.estore.imports.util.ImportHelper;

public class BrandHandler extends ColumnBasicHandler implements ColumnHandler {
	private Logger logger = Logger.getLogger(BrandHandler.class);
	private BrandManager brandManager=null;

	private Boolean				isName				= true;

	private Boolean				autoAdd		= false;
	
	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private Integer maxLength=null;
	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private LinkedHashMap<String, String> replaces=null;
	/**
	 * autoAdd,以name为code时,对code的处理
	 */
	private String toLowerOrUpper=null;

	public void setBrandManager(BrandManager brandManager) {
		this.brandManager = brandManager;
	}

	public void setProperty(ImportModel importModel, Column column) throws Exception {
		Product product = (Product) importModel.getTarget();
		String value = column.getValue();
		Brand brand = null;
		if (isName) {
			if (StringUtils.isNotEmpty(value)) {
				brand = getBrandByName(value);
			}
			if (brand == null
					&& StringUtils.isNotEmpty(column.getDefaultValue())) {
				brand = getBrandByName(column.getDefaultValue());
			}
		} else {
			if (StringUtils.isNotEmpty(value)) {
				brand = brandManager.getBrandByCode(value);
			}
			if (brand == null
					&& StringUtils.isNotEmpty(column.getDefaultValue())) {
				brand = brandManager.getBrandByCode(value);
			}
		}
		if (brand == null) {
			if (StringUtils.isNotEmpty(value) && autoAdd) {
				String code=value;
				if (isName) {
					code=ImportHelper.toSubstring(code, maxLength);
					code=ImportHelper.toReplace(code, replaces);
					code=ImportHelper.toLowerOrUpper(code, toLowerOrUpper);
					brand =  brandManager.getBrandByCode(value);
				}
				if (brand == null) {
					brand = new Brand();
					brand.setBrandName(value);
					brand.setBrandCode(code);
					brandManager.save(brand);
					if(logger.isInfoEnabled()){
						logger.info("自动新增品牌："+brand);
					}
				}
			}
		}
		if (brand != null) {
			product.setBrand(brand);
			importModel.setResult("1");
		}else{

			if(logger.isInfoEnabled()){
				logger.info("没有找到相应的品牌！");
			}
			setDefaultResult(importModel, column);
		}
	}

	private Brand getBrandByName(String value) {
		List<Brand> brands = brandManager.findBrandByName(value);
		if (brands != null && brands.size() > 0) {
			for (Brand brand : brands) {
				if (brand.getBrandName().equals(value)) {
					return brand;
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

}
