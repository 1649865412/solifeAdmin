package com.cartmatic.estore.imports.handler;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class ImportClause {
	private LinkedHashMap<String, String>clauses;
	private Logger logger = Logger.getLogger(ImportClause.class);
	public boolean check(Map<String,String>rowDataMap){
		boolean falg=true;
		if(clauses!=null&&clauses.size()>0){
			Set<String> headers=clauses.keySet();
			for (String header : headers) {
				String clause=clauses.get(header);
				String value=rowDataMap.get(header);
				if(!checkValue(value, clause)){
					logger.warn("本条数据不满足导入条件，忽略处理。header:["+header+"]，clause：["+clause+"]，value:["+value+"]，RowData:"+rowDataMap);
					return false;
				}
			}
		}
		return  falg;
	}
	
	public boolean checkValue(String value,String clause){
		value=value.trim();
		String type=clause.substring(0, clause.indexOf("@")).toLowerCase();
		clause=clause.substring(type.length()+1);
		String op=clause.substring(0,clause.indexOf("@"));
		String cValue=clause.substring(op.length()+1);
		if(type.equals("string")){
			if(op.equals("=")){
				return cValue.equals(value);
			}else if(op.equals("!=")){
				return !cValue.equals(value);
			}else if(op.equals("indexof")){
				return value.indexOf(cValue)>0;
			}else if(op.equals("startswith")){
				return value.startsWith(cValue);
			}else if(op.equals("endswith")){
				return value.endsWith(cValue);
			}
		}else if(type.equals("integer")){
			try {
				Integer iValue=new Integer(value);
				Integer ciValue=new Integer(cValue);
				if(op.equals("=")){
					return iValue.intValue()==ciValue.intValue();
				}else if(op.equals("<")){
					return iValue.intValue()<ciValue.intValue();
				}else if(op.equals("<=")){
					return iValue.intValue()<=ciValue.intValue();
				}else if(op.equals(">")){
					return iValue.intValue()>ciValue.intValue();
				}else if(op.equals(">=")){
					return iValue.intValue()>=ciValue.intValue();
				}else if(op.equals("!=")){
					return iValue.intValue()!=ciValue.intValue();
				}
			} catch (Exception e) {
				return false;
			}
		}else if(type.equals("bigdecimal")){
			try {
				BigDecimal iValue=new BigDecimal(value);
				BigDecimal ciValue=new BigDecimal(cValue);
				if(op.equals("=")){
					return iValue.doubleValue()==ciValue.doubleValue();
				}else if(op.equals("<")){
					return iValue.doubleValue()<ciValue.doubleValue();
				}else if(op.equals("<=")){
					return iValue.doubleValue()<=ciValue.doubleValue();
				}else if(op.equals(">")){
					return iValue.doubleValue()>ciValue.doubleValue();
				}else if(op.equals(">=")){
					return iValue.doubleValue()>=ciValue.doubleValue();
				}else if(op.equals("!=")){
					return iValue.doubleValue()!=ciValue.doubleValue();
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	public LinkedHashMap<String, String> getClauses() {
		return clauses;
	}

	public void setClauses(LinkedHashMap<String, String> clauses) {
		this.clauses = clauses;
	}
	public static void main(String[] args) {
		new ImportClause().checkValue("", "int@>=@0123");
	}

}
