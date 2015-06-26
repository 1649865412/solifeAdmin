package com.cartmatic.estore.catalog.model;

import java.io.Serializable;

import com.cartmatic.estore.common.model.attribute.Attribute;

public class BulkField {
	public static final Short OBJ_TYPE_PRODUCT =1;
	public static final Short OBJ_TYPE_SKU =2;
	public static final Short OBJ_TYPE_PROD_ATTR =3;
	public BulkField(Short objType,String name,Serializable id){
		this.objType=objType;
		this.name=name;
		if(objType==BulkField.OBJ_TYPE_PRODUCT.intValue()){
			this.inputName=name+"_p_"+id;
		}else if(objType==BulkField.OBJ_TYPE_SKU.intValue()){
			this.inputName=name+"_s_"+id;
		}else if(objType==BulkField.OBJ_TYPE_PROD_ATTR.intValue()){
			this.inputName=name+"_pav_"+id;
		}
	}
	/**
	 * 批修改属性的对象
	 * 1为产品，
	 * 2为sku,
	 * 3为自定义属性
	 */
	private Short objType;
	/**
	 * 批修改的属性名（跟product，sku等属性同名）
	 */
	private String name;
	/**
	 * html 的输入框name
	 */
	private String inputName;
	/**
	 * 属性值
	 */
	private String value;
	
	/**
	 * 修改自定义属性
	 */
	private Attribute attribute;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	
}
