/*
 * Created on Dec 5, 2006
 *
 */
package com.cartmatic.estore.report.model;

import java.util.List;

/**
 * @author Ryan
 *  
 */
public class ReportParameter {

    /**
     * Common report parameters used. Begin with a _. Refer to report template.
     */
    public final static String REPORT_SQL = "_REPORT_SQL";

    public final static String ORDER_BY_SQL = "_ORDER_BY_SQL";

    public final static String LOCALE_CODE = "_LOCALE_CODE";

    public final static String CTX_PATH = "_CTX_PATH";

    public final static String REPORT_FORMAT = "_REPORT_FORMAT";

    public final static String GROUP_BY_COL = "_GROUP_BY_COL";

    public final static String ORDER_BY_COL = "_ORDER_BY_COL";

    public static String JASPER_REPORT_PATH = "/report/JasperReport/";
    
    public static String JASPER_REPORT_JRXML_PATH = "/report/jrxml/";

    private String name = null;

    private String description = null;

    private boolean isForPrompting = true;

    private String defaultExp = null;

    private String inputType = "";//private String inputType = "String";
    
    private String dataType="String";

    private List optionList = null;//for select,mutibox
    
    private boolean nullAllow=true;//for select
    
    private String sqlStr;

    public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public void setDefaultExp(String defaultExp) {
        this.defaultExp = defaultExp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public void setForPrompting(boolean isForPrompting) {
        this.isForPrompting = isForPrompting;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultExp() {
        return defaultExp;
    }

    public String getDescription() {
        return description;
    }

    public String getInputType() {
        return inputType;
    }

    public boolean isForPrompting() {
        return isForPrompting;
    }

    public String getName() {
        return name;
    }

    public String getHelpTips() {
        StringBuffer buf = new StringBuffer();
        buf.append((description == null || "".equals(description)) ? ""
                : "Description: " + description + " ");
        buf.append((defaultExp == null || "".equals(defaultExp)) ? ""
                : "Default: " + defaultExp);

        return buf.toString();
    }

    public List getOptionList() {
        return optionList;
    }

    public void setOptionList(List optionList) {
        this.optionList = optionList;
    }

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isNullAllow() {
		return nullAllow;
	}
	
	public boolean getNullAllow() {
		return nullAllow;
	}

	public void setNullAllow(boolean nullAllow) {
		this.nullAllow = nullAllow;
	}
}
