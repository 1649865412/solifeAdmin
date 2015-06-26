package com.cartmatic.estore.imports.model;

import java.util.HashMap;
import java.util.Map;

import com.cartmatic.estore.common.model.catalog.Catalog;
import com.cartmatic.estore.common.model.system.Store;

/**
 * 当前处理导入的对象的Model类，
 * @author Administrator
 *
 */
public class ImportModel {
	/**
	 * 当前导入的目标商店
	 */
	private Store store;
	/**
	 * 当前导入的目标目录
	 */
	private Catalog catalog;
	/**
	 * 清除当前数据处理信息
	 */
	public void clear(){
		result=null;
		msg=null;
		update=false;
	}
	/**
	 * 导入对象
	 */
	private Object target;
	
	/**
	 * 当前数据处理结果
	 * result-1表示处理失败，该条数据无法处理；0表示无值或无法处理，可以忽略该信息的设置；1表示设置该数据成功
	 */
	private String	result;
	
	/**
	 * 当前数据处理返回信息
	 */
	private String msg;
	
	
	/**
	 * 当前处理的是否更新操作
	 */
	private boolean update;
	
	
	public boolean isUpdate() {
		return update;
	}


	public void setUpdate(boolean update) {
		this.update = update;
	}
	private Map<String,Object> importTargetData=new HashMap<String, Object>();


	public Object getTarget() {
		return target;
	}


	public void setTarget(Object target) {
		this.target = target;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public Map<String, Object> getImportTargetData() {
		return importTargetData;
	}


	public void setImportTargetData(Map<String, Object> importTargetData) {
		this.importTargetData = importTargetData;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Store getStore() {
		return store;
	}


	public void setStore(Store store) {
		this.store = store;
	}


	public Catalog getCatalog() {
		return catalog;
	}


	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	
	
}
