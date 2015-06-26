package com.cartmatic.estore.common.model.catalog;

import com.cartmatic.estore.common.model.catalog.base.SkuOptionValueTbl;

/**
 * Model class for SkuOptionValue. Add not database mapped fileds in this class.
 */
public class SkuOptionValue extends SkuOptionValueTbl implements Comparable<SkuOptionValue>{
	/**
	 * 辅助确定ProductSku选项
	 */
	private boolean skuSelected=false;
	
	/**
	 * 辅助记录保存sku
	 */
	private ProductSku sku;

  	/**
	 * Default Empty Constructor for class SkuOptionValue
	 */
	public SkuOptionValue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SkuOptionValue
	 */
	public SkuOptionValue (
		 Integer in_skuOptionValueId
		) {
		super (
		  in_skuOptionValueId
		);
	}

	public boolean isSkuSelected() {
		return skuSelected;
	}

	public void setSkuSelected(boolean skuSelected) {
		this.skuSelected = skuSelected;
	}

	@Override
	public int compareTo(SkuOptionValue o) {
		if(this.sortOrder!=null&&o.sortOrder!=null){
			return this.sortOrder.compareTo(o.sortOrder);
		}else if(this.sortOrder==null&&o.sortOrder!=null){
			return -1;
		}else if(this.sortOrder!=null&&o.sortOrder==null){
			return 1;
		}else{
			return this.getId().compareTo(o.getId());
		}
	}

	public ProductSku getSku() {
		return sku;
	}

	public void setSku(ProductSku sku) {
		this.sku = sku;
	}
}