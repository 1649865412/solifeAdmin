package com.cartmatic.estore.common.model.order;

import com.cartmatic.estore.common.model.order.base.TransferRecordTbl;

/**
 * Model class for TransferRecord. Add not database mapped fileds in this class.
 */
public class TransferRecord extends TransferRecordTbl {
	public final static Short TYPE_WESTERN_UNION=new Short("1");
	public final static Short TYPE_WIRE_TRANSFER=new Short("2");
	
  	/**
	 * Default Empty Constructor for class TransferRecord
	 */
	public TransferRecord () {
		super();
	}
	
	/**
	 * 定义实体的业务名取值； transferRecordName
	 * 必须手工完成这个部分，否则编译不通过。
	 */
	public String getTransferRecordName () {
		if (transferRecordId == null)
	        return "";
	    else
			//返回一个指定有业务意义的属性值;
			//如：product的VO就用product.productName
	        return this.transferRecordId+"";
	}
	
	/**
	 * Default Key Fields Constructor for class TransferRecord
	 */
	public TransferRecord (
		 Integer in_transferRecordId
		) {
		super (
		  in_transferRecordId
		);
	}

}
