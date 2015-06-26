package com.cartmatic.estore.common.model.catalog;

import java.util.ArrayList;
import java.util.List;

public class SearchAttributeModel {
	private Integer id;
	private List<SearchAttributeItem> attributes=new ArrayList<SearchAttributeItem>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<SearchAttributeItem> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<SearchAttributeItem> attributes) {
		this.attributes = attributes;
	}
	
	
}
