package com.cartmatic.estore.imports.handler.customer;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;
import com.opensymphony.oscache.util.StringUtil;

public class LastNameHandler extends ColumnBasicHandler implements
		ColumnHandler {

	public void setProperty(ImportModel importModel, Column column)
			throws Exception {
		Customer customer = (Customer)importModel.getTarget();
		String value = column.getValue();
		if(StringUtil.isEmpty(value)){
			importModel.setResult("1");
			return;
		}
		String[] nn = value.split(" ");
		if(nn.length>1)
		    customer.setLastname(nn[1]);
		else
			customer.setLastname("");
		importModel.setResult("1");
	}

}
