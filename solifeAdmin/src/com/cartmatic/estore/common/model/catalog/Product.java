package com.cartmatic.estore.common.model.catalog;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.base.ProductTbl;
import com.cartmatic.estore.common.model.system.Store;

/**
 * Model class for Product. Add not database mapped fileds in this class.
 */
public class Product extends ProductTbl implements Cloneable{
	public static final Short STATUS_DRAFT =0;
	public static final Short STATUS_ACTIVE =1;
	public static final Short STATUS_NOTACTIVE =2;
	public static final Short STATUS_DELETED =3;
	public static final Short STATUS_TOBEDELETED =6;
	
	private Integer sortOrder;

  	/**
	 * Default Empty Constructor for class Product
	 */
	public Product () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Product
	 */
	public Product (
		 Integer in_productId
		) {
		super (
		  in_productId
		);
	}

	
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}


	public void setProductStatus(Short productStatus) {
		this.status = productStatus;
	}
	
	/**
	 * 构建简单的Json对象主要用于选择器
	 * @return
	 */
	public JSONObject getJsonObject(){
		JSONObject jsonProduct=new JSONObject();
		jsonProduct.put("productId",this.productId);
		jsonProduct.put("productName",this.productName);
		jsonProduct.put("productCode", this.productCode);
		Map<String,Object> defaultProductSku=new HashMap<String, Object>();
		defaultProductSku.put("productSkuId",this.getDefaultProductSku()==null?"":this.getDefaultProductSku().getProductSkuId());
		defaultProductSku.put("productSkuCode",this.getDefaultProductSku()==null?"":this.getDefaultProductSku().getProductSkuCode());
		defaultProductSku.put("image",this.getDefaultProductSku()==null?"":this.getDefaultProductSku().getImage());
		defaultProductSku.put("price",this.getDefaultProductSku()==null?"":this.getDefaultProductSku().getPrice());
		jsonProduct.put("defaultProductSku", defaultProductSku);
		Map<String,Object> brand=new HashMap<String, Object>();
		brand.put("brandId",this.getBrand()==null?"":this.getBrand().getBrandId());
		brand.put("brandName",this.getBrand()==null?"":this.getBrand().getBrandName());
		jsonProduct.put("brand", brand);
		return jsonProduct;
	}

	/**
	 * 获得该产品的主目录对象。
	 * @return
	 */
	public Category getMainCategory()
	{
		Integer catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
	    Set<ProductCategory> s = this.getProductCategorys();
	    if (s != null){
	    	if(ConfigUtil.getInstance().getIsStoreFront()){
		        for (ProductCategory pc: s){
		            if (catalogId.intValue()==pc.getCategory().getCatalogId()&&Constants.FLAG_TRUE.equals(pc.getIsMainCategory())){
		                return pc.getCategory();
		            }
		        }
		    }else{
		    	for (ProductCategory pc: s){
		            if (Constants.FLAG_TRUE.intValue()!=pc.getCategory().getCatalog().getIsVirtual()&&Constants.FLAG_TRUE.equals(pc.getIsMainCategory())){
		                return pc.getCategory();
		            }
		        }
		    }
	    }
	    return null;
	}
	
	public Category getMainCategory(Integer catalogId)
	{
	    Set<ProductCategory> s = this.getProductCategorys();
	    if (s != null){
	    	for (ProductCategory pc: s){
	            if (catalogId.intValue()==pc.getCategory().getCatalog().getCatalogId()&&Constants.FLAG_TRUE.equals(pc.getIsMainCategory())){
	                return pc.getCategory();
	            }
	        }
	    }
	    return null;
	}

	public Object clone(){
		Product o=null;
        try
        {
            o=(Product)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;

	}
}
