package com.cartmatic.estore.imports.handler.customer;

import java.util.List;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.service.CustomerService;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;

public class MembershipHandler extends ColumnBasicHandler implements
		ColumnHandler {

	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public void setProperty(ImportModel importModel, Column column)
			throws Exception {
		Customer customer = (Customer)importModel.getTarget();
		String value = column.getValue();
		List<Membership> mbs = customerService.getMembershipByName(value);
        if(mbs.size()>=1){//假如有多条同名的会员等级，则取第一条
        	Membership sb = mbs.get(0);
        	customer.setMembership(sb);
        }
        else{
        	//TODO 如果没有对应的会员级别,应该报错.
//        	Membership sb = new Membership();
//        	sb.setMembershipName(value);
//        	sb.setMembershipDetail("");
//        	sb.setDeleted(Short.valueOf("0").shortValue());
//        	sb.setVersion(0);
//        	Membership temp = customerService.getHightLevelMembership(sb);//因为系统内置默认的等级，所以，此处不会出现null
//        	sb.setMembershipLevel(temp.getMembershipLevel()+1);
//        	customerService.saveMembership(sb);
//        	customer.setMembership(sb);
        }
        importModel.setResult("1");
	}

}
