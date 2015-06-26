package com.cartmatic.estore.common.model.system;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.system.base.TaxRateTbl;

/**
 * Model class for TaxRate. Add not database mapped fileds in this class.
 */
public class TaxRate extends TaxRateTbl {
  	/**
	 * Default Empty Constructor for class TaxRate
	 */
	public TaxRate () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； taxRateName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTaxRateName () {
		if (taxRateId == null)
	        return "";
	    else{
	    	//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	    	/*String name="";
	    	if(this.getProductType()!=null){
	    		name=this.getProductType().getProductTypeName();
	    	}
	    	if(this.getTax()!=null){
	    		if(StringUtils.isNotBlank(name)){
	    			name+=" >> ";
	    		}
	    		name+=this.getTax().getTaxName();
	    	}
	    	if(this.getRegion()!=null){
	    		if(StringUtils.isNotBlank(name)){
	    			name+=" >> ";
	    		}
	    		name+=this.getRegion().getRegionName();
	    	}
	    	if(this.getRateValue()!=null){
	    		if(StringUtils.isNotBlank(name)){
	    			name+=" ";
	    		}
	    		name+=this.getRateValue()+"%";
	    	}
	        return name;*/
	        return this.getRegionId()+"";
	    }
	}
	
	/**
	 * Default Key Fields Constructor for class TaxRate
	 */
	public TaxRate (
		 Integer in_taxRateId
		) {
		super (
		  in_taxRateId
		);
	}
}
