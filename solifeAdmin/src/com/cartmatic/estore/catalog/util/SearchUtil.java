package com.cartmatic.estore.catalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.attribute.Constants;
import com.cartmatic.estore.common.model.catalog.SearchAttributeItem;
import com.cartmatic.estore.common.model.catalog.SearchAttributeModel;

public class SearchUtil {
	
	private static SearchAttributeModel cov(String searchStr){
		SearchAttributeModel SearchAttributeModel=new SearchAttributeModel();
		String temp[]=searchStr.split("#####");
		Integer attributeId=Integer.parseInt(temp[0]);
		SearchAttributeModel.setId(attributeId);
		String tempValue=temp[1];
		String valueStr[]=tempValue.split("@@@@@");
		for (String value : valueStr) {
			String valueD[]=value.split("______");
			SearchAttributeItem searchAttributeItem=new SearchAttributeItem();
			searchAttributeItem.setOperator(valueD[0]);
			searchAttributeItem.setValue(valueD[1]);
			if(valueD.length>2)
				searchAttributeItem.setDataType(Integer.parseInt(valueD[2]));
			if(StringUtils.isNotBlank(searchAttributeItem.getValue())){
				SearchAttributeModel.getAttributes().add(searchAttributeItem);
			}
		}
		if(SearchAttributeModel.getAttributes().size()>0){
			return SearchAttributeModel;
		}else{
			return null;
		}
	}
	
	public static List<SearchAttributeModel> cov(String...searchStr){
		List<SearchAttributeModel> result=new ArrayList<SearchAttributeModel>();
		if(searchStr!=null){
			for (String string : searchStr) {
				SearchAttributeModel SearchAttributeModel=cov(string);
				if(SearchAttributeModel!=null)
					result.add(SearchAttributeModel);
			}
			result=cov(result);
		}
		return result;
	}
	
	private static  List<SearchAttributeModel> cov(List<SearchAttributeModel> SearchAttributeModelList){
		Map<Integer,SearchAttributeModel> temp=new HashMap<Integer, SearchAttributeModel>();
		for (SearchAttributeModel searchAttributeModel : SearchAttributeModelList) {
			SearchAttributeModel temp_SearchAttributeModel =temp.get(searchAttributeModel.getId().intValue());
			if(temp_SearchAttributeModel==null){
				temp.put(searchAttributeModel.getId().intValue(),searchAttributeModel);
			}else{
				temp_SearchAttributeModel.getAttributes().addAll(temp_SearchAttributeModel.getAttributes());
			}
		}

		List<SearchAttributeModel> result=new ArrayList<SearchAttributeModel>();
		Set<Integer> SearchAttributeModelIds=temp.keySet();
		for (Integer SearchAttributeModelId : SearchAttributeModelIds) {
			SearchAttributeModel temp_SearchAttributeModel=temp.get(SearchAttributeModelId);
			result.add(temp_SearchAttributeModel);
		}
		return result;
	}
	
	public static String getAttributeNameByDataType(Integer attributeDataType){
		int type=attributeDataType.intValue();
		if (type == Constants.DATETYPE_INT.intValue()){
			return "intValue";
		}  
			
		else if (type == Constants.DATETYPE_FLOAT.intValue()){
			//decimal
			return "decimalValue";
		}
			
		else if (type == Constants.DATETYPE_BOOLEAN.intValue()){
			//boolean
			return "booleanValue";
		}
			
		else if (type == Constants.DATETYPE_DATE.intValue()){
			//date
			return "dateValue";
			
		}
			
		else if (type == Constants.DATETYPE_LONGTEXT.intValue()){
			//long
			return "longTextValue";
		}
			
		else{
			//shorttext
			return "shortTextValue";
		}
	}
	
	public static String[] getOperatorAndValue(String operatorMark,String value){
		String[] result=new String[2];
		String operator = " = ?";// default
		operatorMark=operatorMark.toUpperCase();
		if ("LIKE".equals(operatorMark)) {
			operator = " like ?";
		} else if ("NOT".equals(operatorMark)) {
			operator = " <> ?";
		} else if ("GTE".equals(operatorMark)) {
			operator = " >= ?";
		} else if ("STE".equals(operatorMark)) {
			operator = " <= ?";
		} else if ("GT".equals(operatorMark)) {
			operator = " > ?";
		} else if ("LTE".equals(operatorMark)) {
			operator = " <= ?";
		} else if ("LT".equals(operatorMark)) {
			operator = " < ?";
		}
		result[0]=operator;
		result[1]=value;
		return result;
	}
	
	public static List<SearchAttributeModel> covbyMap(Map<String,String[]> param){
		List<SearchAttributeModel> result=new ArrayList<SearchAttributeModel>();
		String proAttrIds[]=param.get("paid");
		String proAttrOperators[]=param.get("pao");
		String proAttrValues[]=param.get("pav");
		String proAttrDataTypes[]=param.get("padt");
		if(proAttrIds!=null&&proAttrOperators!=null&&proAttrValues!=null){
			Map<Integer,SearchAttributeModel> tempResult=new HashMap<Integer, SearchAttributeModel>();
			for (int i = 0; i < proAttrIds.length; i++) {
				if(StringUtils.isBlank(proAttrIds[i]))
					continue;
				Integer id=Integer.parseInt(proAttrIds[i]);
				String operator=proAttrOperators[i];
				String value=proAttrValues[i];
				SearchAttributeItem searchAttributeItem=new SearchAttributeItem();
				searchAttributeItem.setOperator(operator);
				searchAttributeItem.setValue(value);
				if(i<proAttrDataTypes.length){
					Integer dataType=Integer.parseInt(proAttrDataTypes[i]);
					searchAttributeItem.setDataType(dataType);
				}
				if(StringUtils.isNotBlank(searchAttributeItem.getValue())){
					SearchAttributeModel searchAttributeModel=tempResult.get(id);
					if(searchAttributeModel==null){
						searchAttributeModel=new SearchAttributeModel();
						searchAttributeModel.setId(id);
						tempResult.put(id, searchAttributeModel);
					}
					searchAttributeModel.getAttributes().add(searchAttributeItem);
				}
			}
			Set<Integer> SearchAttributeModelIds=tempResult.keySet();
			for (Integer SearchAttributeModelId : SearchAttributeModelIds) {
				SearchAttributeModel temp_SearchAttributeModel=tempResult.get(SearchAttributeModelId);
				result.add(temp_SearchAttributeModel);
			}
		}
		return result;
	}
	
	
	public static List<String> getValue(Integer id,String operator,List<SearchAttributeModel> result){
		List<String>values=new ArrayList<String>();
		for (SearchAttributeModel searchAttributeModel : result) { 
			if(searchAttributeModel.getId().intValue()==id){
				List<SearchAttributeItem>searchAttributeItemList=searchAttributeModel.getAttributes();
				for (SearchAttributeItem searchAttributeItem : searchAttributeItemList) {
					if(searchAttributeItem.getOperator().equals(operator)){
						values.add(searchAttributeItem.getValue());
					}
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			values.add("");
		}
		return values;
	}
	
	public static void main(String[] args) {
//		String searchStr[]={"1#####Operator______value1______DataType@@@@@Operator______value2______DataType@@@@@Operator______value2______DataType...","2#####Operator______value1______DataType@@@@@Operator______value2______DataType@@@@@Operator______value2______DataType..."};
		String searchStr[]={"1#####EQ______value1______10@@@@@EQ______value2______10@@@@@NOT______value2______10","2#####EQ______value1______10@@@@@EQ______value2______10@@@@@EQ______value2______10"};
		List<SearchAttributeModel> result=SearchUtil.cov(searchStr);
		for (SearchAttributeModel searchAttributeModel : result) {
			System.out.println(searchAttributeModel.getId()+"______"+searchAttributeModel.getAttributes());
		}
	}
	
}
