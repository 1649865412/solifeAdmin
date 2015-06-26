package com.cartmatic.estore.content.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * User: fire
 * Date: 2007-7-13
 * Time: 11:04:43
 */
public class ColumnComponent implements Serializable {
    private String categoryPath;
    private String name;
    private String url;
    private int level;
    private int type;
    private String code;
    private Integer categoryId;
    private int isActive;
    private int isLinkCategory;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getIsLinkCategory() {
		return isLinkCategory;
	}

	public void setIsLinkCategory(int isLinkCategory) {
		this.isLinkCategory = isLinkCategory;
	}

	public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String toString() {
        return "ColumnComponent{" +
                "name='" + name + '\'' +
                "categoryPath="+categoryPath+'\''+
                ", url='" + url + '\'' +
                ", level=" + level +
                ", type=" + type +
                ", categoryId=" + categoryId +
                '}';
    }
}
