package com.cartmatic.estore.imports.handler.customer;

import java.util.Date;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;
import com.opensymphony.oscache.util.StringUtil;

public class RegeditTimeHandler extends ColumnBasicHandler implements
		ColumnHandler {

	public void setProperty(ImportModel importModel, Column column)
			throws Exception {
		Customer customer = (Customer)importModel.getTarget();
		String value = column.getValue();
		if(!StringUtil.isEmpty(value)){
			Date rd = DateUtil.convertStringToDate(value);
			customer.setRegisterTime(rd);
		}
		importModel.setResult("1");
	}

}
