
package com.cartmatic.estore.common.model.catalog;


public class CatalogVO {	
	
	private Integer catalogId;
	private String catalogName;
	private String path;
	private String pathName;
	private String data;
	private String flag;

	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Integer getCatalogId() {
		return catalogId;
	}
	
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
	
	
	public String getCatalogName() {
		return catalogName;
	}
	
	public void setCatalogName(String nameI18n) {
		this.catalogName = nameI18n;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String toString(){
	    StringBuffer bf=new StringBuffer();
	    bf.append("catalogId="+catalogId+";");
	    bf.append("catalogName="+catalogName+";");
	    bf.append("path="+path+";");
	    bf.append("pathName="+pathName+";");
	    bf.append("data="+data+";");	  
	    bf.append("flag="+flag);	    
	    return bf.toString();
	}
}
