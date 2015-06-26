package com.cartmatic.estore.common.model.supplier;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.supplier.base.SupplierProductTbl;

/**
 * Model class for SupplierProduct. Add not database mapped fileds in this class.
 */
public class SupplierProduct extends SupplierProductTbl {

  	/**
	 * Default Empty Constructor for class SupplierProduct
	 */
	public SupplierProduct () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； supplierProductName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getSupplierProductName () {
		if (supplierProductId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productName;
	}
	
	/**
	 * Default Key Fields Constructor for class SupplierProduct
	 */
	public SupplierProduct (
		 Integer in_supplierProductId
		) {
		super (
		  in_supplierProductId
		);
	}
	
	public List<String[]> getWholesalePriceList(){
		List<String[]> wholesalePriceList=new ArrayList<String[]>();
		if(StringUtils.isNotBlank(getWholesalePrice())){
			String tempPrice[]=getWholesalePrice().split(",");
			for (String string : tempPrice) {
				String subPrice[]=string.split(":");
				try {
					wholesalePriceList.add(new String[]{subPrice[0],subPrice[1]});
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return wholesalePriceList;
	}
	
	public List<String> getMediaUrlList(){
		List<String> mediaUrlList=new ArrayList<String>();
		if(StringUtils.isNotBlank(getMediaUrl())){
			String tempMediaUrls[]=getMediaUrl().split(",");
			for (String tempMediaUrl : tempMediaUrls) {
				mediaUrlList.add(tempMediaUrl);
			}
		}
		return mediaUrlList;
	}

}
