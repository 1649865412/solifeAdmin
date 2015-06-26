
package com.cartmatic.estore.imports.handler.sku;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cartmatic.estore.catalog.service.SkuOptionValueManager;
import com.cartmatic.estore.common.model.catalog.SkuOptionValue;
import com.cartmatic.estore.imports.handler.ColumnHandler;
import com.cartmatic.estore.imports.handler.basic.ColumnBasicHandler;
import com.cartmatic.estore.imports.model.Column;
import com.cartmatic.estore.imports.model.ImportModel;


public class SkuOptionHandler extends ColumnBasicHandler implements
		ColumnHandler {
	/**
	 * 分隔符，如"=",机身颜色=黑色,颜色_图片=星月银
	 */
	private String delimiter;
	private boolean isName=true;
    private SkuOptionValueManager skuOptionValueManager = null;
	private Logger logger = Logger.getLogger(SkuOptionHandler.class);
	

	public void setProperty(ImportModel importModel,Column column) throws Exception {
		List<String> tempSkuOptionValues=column.getValues();
		Set<Integer>skuOptionValueIds=new HashSet<Integer>();
		for (String tempSkuOptionValue : tempSkuOptionValues) {
			tempSkuOptionValue=tempSkuOptionValue.trim();
			if(StringUtils.isEmpty(tempSkuOptionValue))continue;
			if(tempSkuOptionValue.indexOf(delimiter)==-1){
				logger.warn("Sku value 格式错误");
				importModel.setResult("-1");
				return ;
			}
			String skuOptionValue_name_value[]=tempSkuOptionValue.split(delimiter);
			SkuOptionValue skuOptionValue=getSkuOptionValue(tempSkuOptionValue, skuOptionValue_name_value[0],skuOptionValue_name_value[1]);
			if(skuOptionValue==null){
				importModel.setResult("-1");
			}else{
				skuOptionValueIds.add(skuOptionValue.getId());
			}
		}
		importModel.getImportTargetData().put("skuOptionValueIds", skuOptionValueIds);
		importModel.setResult("1");
	}
	
	private SkuOptionValue getSkuOptionValue(String skuOptionValue,String name,String value){
		List<SkuOptionValue>skuOptionValueList=null;
		if(isName){
			skuOptionValueList=skuOptionValueManager.findSkuOptionValueByOptionNameAndValueName(name, value);
		}else{
			skuOptionValueList=skuOptionValueManager.findSkuOptionValueByOptionCodeAndValueName(name, value);
		}
		if(skuOptionValueList.size()==0){
			logger.warn("指定的Sku选项'"+skuOptionValue+"'，系统不存在相应的SkuOptionValue");
			return null;
		}else if(skuOptionValueList.size()>1){
			logger.warn("指定的Sku选项'"+skuOptionValue+"'，系统存在相应多个的SkuOptionValue,该数据不作处理");
			return null;
		}else{
			return skuOptionValueList.get(0);
		}
	}

	public void setSkuOptionValueManager(SkuOptionValueManager skuOptionValueManager) {
		this.skuOptionValueManager = skuOptionValueManager;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public boolean getIsName() {
		return isName;
	}

	public void setIsName(boolean isName) {
		this.isName = isName;
	}


}
