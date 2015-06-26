package com.cartmatic.estore.report.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.util.StringUtil;
import com.cartmatic.estore.report.model.ReportParameter;
import com.cartmatic.estore.report.service.JasperReportManager;

/**
 * @author Ryan
 *  
 */
public class JasperReportManagerImpl implements JasperReportManager {

    private final static Collection filteredParameters = new ArrayList();
    private final Log	logger	= LogFactory.getLog(getClass());
/*    static {
        //TODO, define all in ...
        filteredParameters.add(ReportParameter.REPORT_SQL);
        filteredParameters.add(ReportParameter.CTX_PATH);
        filteredParameters.add(ReportParameter.LOCALE_CODE);
        filteredParameters.add(ReportParameter.ORDER_BY_SQL);
        filteredParameters.add(ReportParameter.REPORT_FORMAT);
    }*/

    /**
     * Get all report parameters that user can specify, filtering system defined
     * and a few parameters used by our application contract. Will compile
     * report first when necessary.
     * 
     * @param reportName
     * @return
     */
    public List getReportParamList(String reportName) {
        List paramList = new ArrayList();

        JasperReport report = null;

        try {
            Resource reportFile = ContextUtil.getSpringContext().getResource(
                    ReportParameter.JASPER_REPORT_PATH + reportName + ".jasper");

            report = (JasperReport) JRLoader.loadObject(reportFile.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error in parsing report parameters, report file not found or something unexpected occured.",
                    e);
        }

        JRParameter[] params = report.getParameters();
        for (int i = 0; i < params.length; i++) {
        	String paraDesc=params[i].getDescription();
        	if (!params[i].isSystemDefined() && !filteredParameters.contains(params[i].getName())) {
        		ReportParameter reportParam = null;
        		if(StringUtils.isNotEmpty(paraDesc)&&paraDesc.startsWith("hidden")){
                    String[] p1 = StringUtil.toArrayByDel(paraDesc, "#");
                    if(p1.length<2||p1[1]==null||!p1[1].equals("sqlstr"))
        			    continue;
                    else{
                    	reportParam = new ReportParameter();
                    	reportParam.setInputType("sqlstr");
                    	reportParam.setSqlStr(p1[2]);
                    	reportParam.setName(params[i].getName());
                    	reportParam.setDefaultExp(params[i].getDefaultValueExpression() == null ? ""
                                : params[i].getDefaultValueExpression().getText());
                    	paramList.add(reportParam);
                    	continue;
                    }
        		}
        		if(params[i].getName().startsWith("_")) continue;
        		reportParam = new ReportParameter();
                reportParam.setName(params[i].getName());
                reportParam.setDescription(paraDesc);
                reportParam.setForPrompting(params[i].isForPrompting());
                reportParam.setDefaultExp(params[i].getDefaultValueExpression() == null ? ""
                        : params[i].getDefaultValueExpression().getText());
                if ("java.lang.Boolean".equals(params[i].getValueClassName())) {
                    reportParam.setInputType("checkbox");
                } else if ("java.util.Date".equals(params[i].getValueClassName())
                        ||"java.sql.Timestamp".equals(params[i].getValueClassName())|| params[i].getName().startsWith("DATE_")) {
                    reportParam.setInputType("date");
                } else if (StringUtils.isNotEmpty(paraDesc)&& paraDesc.startsWith("options")) {
                    int index1 = paraDesc.indexOf("#{");
                    int index2 = paraDesc.indexOf("}", index1+3);
                    String strOptions = paraDesc.substring(index1+2, index2);
                    String[] options = StringUtil.toArrayByDel(strOptions, "~");
                    List optionList = new ArrayList();
                    for (int j = 0; j < options.length; j++) {
                        String[] nameAndValue = null;
                        if (options[j].indexOf("|") > 0) {
                            nameAndValue = StringUtil.toArrayByDel(options[j],"|");
                        } else {
                            nameAndValue = new String[] { options[j],options[j] };
                        }
                        optionList.add(nameAndValue);
                    }
                    if(paraDesc.startsWith("options#mutibox"))
                    	reportParam.setInputType("mutibox");
                    else if(paraDesc.startsWith("options#select")){
                    	reportParam.setInputType("select");
                    	if(paraDesc.lastIndexOf("isNullAllow=false")!=-1) reportParam.setNullAllow(false);
                    }
                    int typeIndex=paraDesc.lastIndexOf('#', index1-1);
                    if(typeIndex+1<index1-2){
                    	reportParam.setDataType(paraDesc.substring(typeIndex+1,index1));
                    }
                    reportParam.setOptionList(optionList);
                }
                else{
                	if("java.lang.Integer".equals(params[i].getValueClassName())){
                		reportParam.setDataType("Integer");
                	}
                    reportParam.setInputType("input");
                }
                paramList.add(reportParam);
            }
        }
        
        return paramList;
    }

    /**
     * Get all reports in the report folder. TODO, add description support. Now
     * can just support report name. Can sync with database and add version
     * control, now only use file system.
     * 
     * @return
     */
    public List getReportList() {
        List reportList = new ArrayList();

        File reportFolder = null;
        try {
            reportFolder = ContextUtil.getSpringContext().getResource(
                    ReportParameter.JASPER_REPORT_PATH).getFile();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error occured when listing report files, check your deployment and access right.",
                    e);
        }

        File[] reportFiles = reportFolder.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".jasper");
            }

        });

        try {
            for (int i = 0; i < reportFiles.length; i++) {
            	String jasperFileName=ReportParameter.JASPER_REPORT_PATH+ reportFiles[i].getName();
            	InputStream jasperFile=ContextUtil.getSpringContext().getResource(jasperFileName).getInputStream();
                JasperReport report = (JasperReport) JRLoader.loadObject(jasperFile);
                String[] reportInfo = new String[] {
                        reportFiles[i].getName().substring(0, reportFiles[i].getName().lastIndexOf(".")),
                        report.getName() };
                if (!reportList.contains(reportInfo)) {
                    reportList.add(reportInfo);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(
                    "Error in parsing report parameters, report file not found or something unexpected occured.",
                    e);
        }

        return reportList;
    }

    public boolean compileJasperReport(File sourceReportFile) {
        String fileName = sourceReportFile.getName().substring(0,sourceReportFile.getName().lastIndexOf("."));
        
        File compiledReportFile = new File(sourceReportFile.getParent(),fileName + ".jasper");
        try {
        	compiledReportFile=ContextUtil.getSpringContext().getResource(ReportParameter.JASPER_REPORT_PATH+fileName + ".jasper").getFile();
		} catch (IOException e) {
			 throw new RuntimeException(
	                    "Error occured when listing report files, check your deployment and access right.",e);
		}

        boolean compiled = false;
        //if (!compiledReportFile.exists() || compiledReportFile.lastModified() < sourceReportFile.lastModified()) {
            try {
                JasperCompileManager.compileReportToFile( sourceReportFile.getPath(), compiledReportFile.getPath());
                compiledReportFile.setLastModified(sourceReportFile.lastModified() + 1);
                compiled = true;
            } catch (JRException e) {
                throw new RuntimeException("Compile report file error:"
                        + sourceReportFile.getPath(), e);
            }
        //}

        return compiled;
    }


	public boolean uploadJasperReport(InputStream in, String fileName) {
		boolean result = false;
		logger.debug("enter......");
		logger.debug(fileName);
		try{
			 OutputStream bos =
	            new FileOutputStream(fileName);
	        int bytesRead = 0;
	        byte[] buffer = new byte[8192];
           
	        while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
	            bos.write(buffer, 0, bytesRead);
	        }
	        bos.flush();
	        bos.close();
	        result = true;

		}catch(Exception e){
			logger.debug(e.toString());
			result = false;
		}
		 
		return false;
	}

	public boolean deleteJasperReport(String fileName) {
		boolean result = false;
		logger.debug("enter deleteTJasperReport......"+fileName);
		try{
			FileUtil.delFile(fileName+".jasper");
			FileUtil.delFile(fileName+".jrxml");
			result = true;
		}catch(Exception e){
			result = false;
			logger.debug("Delete jasperreport file is failed...."+e.toString());
		}
		return result;
	}
}

