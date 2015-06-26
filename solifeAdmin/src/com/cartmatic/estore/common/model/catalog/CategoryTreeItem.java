package com.cartmatic.estore.common.model.catalog;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 父目录
 * CategoryTreeItem.category只有id
 * @author Administrator
 *
 */
public class CategoryTreeItem extends Category{
	
	private int level;
	
	/**
	 * 当前目录的最大级数
	 */
	private int maxLevel=0;
	
	private Integer itemCount =new Integer(0);
	
	
	/**
	 * 是否存在子目录
	 */
	private boolean isHasChildren;
	
    /**
     * 是否父目录的第一个Item
     */
    private boolean isParentFirstChild;
    
    /**
     * 是否父目录的最后一个Item
     */
    private boolean isParentLastChild;
    
    /**
     * 与下一个相差的级数
     */
    private Integer nextDifference=new Integer(0);
    
    /**
     * 在父目录的index
     */
    private Integer subIndex;
    
    /**
     * 是否商品目录
     */
    private Short isCatalog;
    
    /**
     * 是否伪实体,即是否商品目录/商店
     */
    private Short isFalseEntity;
    

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isHasChildren() {
		return isHasChildren;
	}

	public void setHasChildren(boolean isHasChildren) {
		this.isHasChildren = isHasChildren;
	}

	public boolean isParentFirstChild() {
		return isParentFirstChild;
	}

	public void setParentFirstChild(boolean isParentFirstChild) {
		this.isParentFirstChild = isParentFirstChild;
	}

	public boolean isParentLastChild() {
		return isParentLastChild;
	}

	public void setParentLastChild(boolean isParentLastChild) {
		this.isParentLastChild = isParentLastChild;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", this.getCategoryName()) 
				.append("id", this.getCategoryId()) 
				.append("categoryPath", this.categoryPath) 
				.append("level", this.level) 
				.append("nextDifference", this.nextDifference) 
				.append("code", this.getCategoryCode()) 
				.append("status", this.status) 
				.append("isHasChildren", this.isHasChildren) 
				.append("isParentFirstChild", this.isParentFirstChild) 
				.append("isParentLastChild", this.isParentLastChild)
				.append("isCatalog", this.isCatalog)
				.toString();
	}

	public void setNextDifference(Integer nextDifference) {
		this.nextDifference = nextDifference;
	}

	public Integer getNextDifference() {
		return nextDifference; 
	}

	public Integer getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(Integer subIndex) {
		this.subIndex = subIndex;
	}

	public String getName() {
		return categoryName;
	}
	
	public String getCode() {
		return categoryCode;
	}
	
	public String getPathId(){
		StringBuffer tempPathId=new StringBuffer(".");
		tempPathId.append(getId());
		tempPathId.append(".");
		return tempPathId.toString();
	}

	public void setIsCatalog(Short isCatalog) {
		this.isCatalog = isCatalog;
	}

	public Short getIsCatalog() {
		return isCatalog;
	}

	public Short getIsFalseEntity() {
		return isFalseEntity;
	}

	public void setIsFalseEntity(Short isFalseEntity) {
		this.isFalseEntity = isFalseEntity;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}
	
	
	
}