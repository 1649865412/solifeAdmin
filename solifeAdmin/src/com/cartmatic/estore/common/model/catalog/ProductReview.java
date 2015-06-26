package com.cartmatic.estore.common.model.catalog;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.catalog.base.ProductReviewTbl;

/**
 * Model class for ProductReview. Add not database mapped fileds in this class.
 */
public class ProductReview extends ProductReviewTbl {
	/**
	 * 临时保存原状态
	 */
	private Short origStatus;
  	public void setOrigStatus(Short origStatus) {
		this.origStatus = origStatus;
	}

	/**
	 * Default Empty Constructor for class ProductReview
	 */
	public ProductReview () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； productReviewName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getProductReviewName () {
		if (productReviewId == null){
			return "";
		}else if(StringUtils.isNotBlank(this.subject)){
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.subject;
		}else {
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.productReviewId.toString()+"";
		}
	}
	
	/**
	 * Default Key Fields Constructor for class ProductReview
	 */
	public ProductReview (
		 Integer in_productReviewId
		) {
		super (
		  in_productReviewId
		);
	}

	public Short getOrigStatus() {
		return origStatus;
	}

}
