package com.cartmatic.estore.common.model.catalog;

import java.util.List;

public class CategoryMenu {
	private List<CategoryTreeItem> categoryTreeList;
	
	/**
	 * 指定目录的实际级数。
	 */
	private Integer displayLevel;

	public List<CategoryTreeItem> getCategoryTreeList() {
		return categoryTreeList;
	}

	public void setCategoryTreeList(List<CategoryTreeItem> categoryTreeList) {
		this.categoryTreeList = categoryTreeList;
	}

	public Integer getDisplayLevel() {
		return displayLevel;
	}

	public void setDisplayLevel(Integer displayLevel) {
		this.displayLevel = displayLevel;
	} 
}
