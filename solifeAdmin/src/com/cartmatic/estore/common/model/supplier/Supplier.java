package com.cartmatic.estore.common.model.supplier;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.supplier.base.SupplierTbl;

/**
 * Model class for Supplier. Add not database mapped fileds in this class.
 */
public class Supplier extends SupplierTbl implements Comparable<Supplier>{

  	/**
	 * Default Empty Constructor for class Supplier
	 */
	public Supplier () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Supplier
	 */
	public Supplier (
		 Integer in_supplierId
		) {
		super (
		  in_supplierId
		);
	}

	private Customer supplierAdmin;

	public Customer getSupplierAdmin() {
		return supplierAdmin; 
	}

	public void setSupplierAdmin(Customer supplierAdmin) {
		this.supplierAdmin = supplierAdmin;
	}
	
	/**
	 * 构建简单的Json对象主要用于选择器
	 * @return
	 */
	public String getJsonObject(){
		JSONObject jsonSupplier=new JSONObject();
		jsonSupplier.put("supplierId",this.supplierId);
		jsonSupplier.put("supplierName",this.supplierName);
		jsonSupplier.put("supplierCode", this.supplierCode);
		return jsonSupplier.toString();
	}

	public int compareTo(Supplier supplier) {
		return getSupplierName().compareToIgnoreCase(supplier.getSupplierName());
	}
	
	public List<String[]> getContactList(){
		List<String[]> contactList=new ArrayList<String[]>();
		if(StringUtils.isNotBlank(getContacts())){
			String contact[]=getContacts().split(";");
			for (String string : contact) {
				String subContact[]=string.split(",");
				if(subContact.length<4){
					for (int i = subContact.length; i <4; i++) {
						subContact=(String[])ArrayUtils.add(subContact, "");
					}
				}
				contactList.add(subContact);
			}
		}
		return contactList;
	}
}
