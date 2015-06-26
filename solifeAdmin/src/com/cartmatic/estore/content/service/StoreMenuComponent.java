
package com.cartmatic.estore.content.service;

/**
 * @author fire
 *
 * TODO 
 */
public class StoreMenuComponent {
	/** Holds categoryPath of menu. */
	private String categoryPath;

    /** Holds value of property location. */
	private String location;

    /** Holds value of property name. */
	private String name;

   /** Holds value of property target. */
	private String target;

    /** Holds value of property title. */
	private String title;

    /** Holds value of property toolTip. */
	private String toolTip;


    /** Holds parsed (with variables) url that is used to render a link */
	private String url;
    
	private int level;
    
    /** Holds the menu type,0 is folder 1 is leaf */
	private int type;
    
    private int sortOrder;


    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
    /**
     * @return 
     */
    public String getCategoryPath() {
        return categoryPath;
    }
    /**
     * @param categoryPath 
     */
    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }
 	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public String getTarget() {
	return target;
    }
    public void setTarget(String target) {
	this.target = target;
    }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("menuName="+this.getName());
		return null;
		
	}

}
