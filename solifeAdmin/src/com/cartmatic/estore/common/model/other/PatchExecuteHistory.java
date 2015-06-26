package com.cartmatic.estore.common.model.other;

import com.cartmatic.estore.common.model.other.base.PatchExecuteHistoryTbl;

/**
 * Model class for PatchExecuteHistory. Add not database mapped fileds in this class.
 */
public class PatchExecuteHistory extends PatchExecuteHistoryTbl {

  	/**
	 * Default Empty Constructor for class PatchExecuteHistory
	 */
	public PatchExecuteHistory () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； patchExecuteHistoryName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getPatchExecuteHistoryName () {
		if (patchExecuteHistoryId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.fileName;
	}
	
	/**
	 * Default Key Fields Constructor for class PatchExecuteHistory
	 */
	public PatchExecuteHistory (
		 Integer in_patchExecuteHistoryId
		) {
		super (
		  in_patchExecuteHistoryId
		);
	}

}
