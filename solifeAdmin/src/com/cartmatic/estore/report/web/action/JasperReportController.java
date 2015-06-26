/*
 * Created on Dec 1, 2006
 *  
 */
package com.cartmatic.estore.report.web.action;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.report.model.ReportParameter;
import com.cartmatic.estore.report.service.JasperReportManager;

/**
 * @author Ryan
 *  
 */
public class JasperReportController extends BaseController {

    private String reportPath = ReportParameter.JASPER_REPORT_PATH;
    private MultipartResolver multipartResolver=null;
    
    public void setMultipartResolver(MultipartResolver multipartResolver) {
		this.multipartResolver = multipartResolver;
	}

	/*
     * List available reports
     * 
     * @see com.cartmatic.estore.webapp.action.BaseListController#defaultAction(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ModelAndView defaultAction(HttpServletRequest request,
            HttpServletResponse response) throws ServletException {
    	removeNavFromSearchCriteria(request);
        List reportList = jasperReportManager.getReportList();
        request.setAttribute("reportList", reportList);
        return new ModelAndView("report/jasperReport");
    }

    /**
     * Generate requested report
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ModelAndView generateReport(HttpServletRequest request,
            HttpServletResponse response) throws ServletException {
        try {
            return getReportModelAndView(request, response);
        } catch (Exception e) {
            saveMessage(Message.error("report.error.generate"));
            return new ModelAndView("report/jasperReport");
        }
    }

    protected ModelAndView getReportModelAndView(HttpServletRequest request,
            HttpServletResponse response) {
        /**
         * 
         * Make sure the required parameters are specified, or else return a
         * user friendly page to report the error.
         * 
         */
    	logger.debug("enter getReportModelAndView......");
        String reportName = request.getParameter("reportName");
        if (StringUtils.isEmpty(reportName)) {
            saveMessage(Message.error("report.error.name.required"));
            return new ModelAndView("report/jasperReport");
        }

        /**
         * 
         * Parse and set report format
         * 
         */
        String format = request.getParameter(ReportParameter.REPORT_FORMAT);
        if (format != null) {
            format = format.toLowerCase();
        }
        if ("excel".equals(format)) {
            format = "xls";
        } else if (!"pdf".equals(format) && !"xls".equals(format)
                && !"csv".equals(format) && !"html".equals(format)) {
            format = "html";
        }

        logger.info("Generating report: " + reportName + " with format: "
                + format);

        JasperReportsMultiFormatView jasperView = getJasperReportView(reportName);

        jasperView.setExporterParameters(getExporterParameters(request,format));

        Map model = getModel(request, reportName, format);

        return new ModelAndView(jasperView, model);
    }

    /**
     * Get default JasperReportView and set common parameters
     * 
     * @return
     */
    protected JasperReportsMultiFormatView getJasperReportView(String reportName) {
        JasperReportsMultiFormatView jasperView = new JasperReportsMultiFormatView();

        /**
         * common parameters for all reports
         */
        jasperView.setJdbcDataSource(dataSource);
        jasperView.setServletContext(ContextUtil.getServletContext());
        jasperView.setFormatKey(ReportParameter.REPORT_FORMAT);

        jasperView.setUrl(reportPath + reportName + ".jasper");

        Properties props = new Properties();
        props.put("pdf", "attachment;filename=" + reportName + ".pdf");
        props.put("csv", "attachment;filename=" + reportName + ".csv");
        props.put("xls", "attachment;filename=" + reportName + ".xls");
        jasperView.setContentDispositionMappings(props);

        /**
         * will trigger initApplicationContext
         */
        jasperView.setApplicationContext(ContextUtil.getSpringContext());
        return jasperView;
    }

    /**
     * Get default data model and set common parameters
     * 
     * @return
     */
    protected Map getModel(HttpServletRequest request, String reportName, String format) {
        Map model = new HashMap();
        /**
         * Bean datasource for JasperReport, will be converted to JRDataSource
         * when not null
         * <P>
         * TODO auto detect manager and call it to generate report data, and set
         * jasperView.setReportDataKey("beanData");
         */
        //Collection beanData = null;
        //model.put("beanData", beanData);
        /**
         * Export request parameters into model
         */
        List paramList = jasperReportManager.getReportParamList(reportName);
        for (Iterator iter = paramList.iterator(); iter.hasNext();) {
            ReportParameter param = (ReportParameter) iter.next();
            String paramValue = request.getParameter(param.getName());
            if ("checkbox".equals(param.getInputType())) {
                model.put(param.getName(),"true".equals(paramValue) ? Boolean.TRUE: Boolean.FALSE);
            }
            else if("sqlstr".equals(param.getInputType())){
            	continue;//因为sqlstr可能会使用其他的产品，当所有参数都组装完成之后，才给sqlstr类型赋值
            }
            else if (StringUtils.isNotEmpty(paramValue)) {
            	if("mutibox".equals(param.getInputType())) {
            		StringBuffer options=new StringBuffer();
            		String[] mutiOptons=request.getParameterValues(param.getName());
            		if("Integer".equalsIgnoreCase(param.getDataType())){
            			for (int i = 0; i < mutiOptons.length; i++) {
            				options.append(",").append(mutiOptons[i]);
						}
            		}else{
            			for (int i = 0; i < mutiOptons.length; i++) {
            				options.append(",'").append(mutiOptons[i]).append("'");
						}
            		}
            		if(options.length()>0){
            			options.deleteCharAt(0);
            			if("String".equalsIgnoreCase(param.getDataType())){
            				options.insert(0, '"').append('"');
            			}
            			model.put(param.getName(),options.toString());
            		}
            	}else if ("date".equals(param.getInputType())) {
                    try {
                        Date date = DateUtil.convertStringToDate(paramValue);
                        if (param.getName().toLowerCase().indexOf("start") != -1) {
                            date = DateUtil.getStartOfThisDay(date);
                        }
                        if (param.getName().toLowerCase().indexOf("end") != -1) {
                            date = DateUtil.getEndOfThisDay(date);
                        }
                        model.put(param.getName(), new Timestamp(date.getTime()));
                    } catch (Exception e) {
                        logger.error("Report date parse error!", e);
                    }
                }else if("input_int".equals(param.getInputType())){
                	model.put(param.getName(), new java.lang.Integer(paramValue));
                }
                else {
                	if("Integer".equalsIgnoreCase(param.getDataType()))
                		model.put(param.getName(), new java.lang.Integer(paramValue));
                	else{
                		model.put(param.getName(), paramValue);
                	}
                }
            }
        }
        for (Iterator iter = paramList.iterator(); iter.hasNext();) {
        	ReportParameter param = (ReportParameter) iter.next();
        	if("sqlstr".equals(param.getInputType())){
        		String sql = param.getSqlStr();
        		boolean point = true;
        		if(StringUtils.contains(sql, "param{")){
        			String[] params = StringUtils.substringsBetween(sql, "param{", "}");
        			en:for(String p:params){
        				if(model.get(p)!=null){
        					String pValue = (String) model.get(p);
        					sql = StringUtils.replace(sql, "param{"+p+"}", pValue);
        				}
        				else{
        					point = false;
        					break en;
        				}
        			}
        			if(point)//假如报表中sql语句中自定义参数没有按照param{paramName}的形式去定义参数值，则使用默认的值表达式
        			    model.put(param.getName(), sql);
        			else
        				model.put(param.getName(), param.getDefaultExp());
        		}
        		else{
        			model.put(param.getName(), param.getDefaultExp());
        		}
        	}
        }

        /**
         * Set common parameters
         */
        model.put(ReportParameter.REPORT_FORMAT, format);
        model.put(ReportParameter.LOCALE_CODE,
                request.getAttribute("CUR_LOCALE_CODE"));
        model.put(ReportParameter.CTX_PATH, ConfigUtil.getInstance().getStoreAdminSiteUrl());

        /**
         * Disable pagination for Excel/CSV format.
         */
        if ("cvs".equals(format) || "xls".equals(format)|| "xml".equals(format)) {
            model.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }

        /**
         * Grouping and ordering handling
         */
        String orderBySql = "";
        String orderByCol = request.getParameter(ReportParameter.ORDER_BY_COL);
        if ("cvs".equals(format) || "xls".equals(format)
                || "xml".equals(format)) {
            model.put(ReportParameter.GROUP_BY_COL, "");
            //Keep user selected ordering
        } else {
            String groupByCol = request.getParameter(ReportParameter.GROUP_BY_COL);
            if (groupByCol != null && !"".equals(groupByCol)) {
                orderBySql = " order by " + groupByCol;
            }
        }
        if (orderByCol != null && !"".equals(orderByCol)) {
            if (orderBySql != null) {
                if (orderBySql.indexOf(orderByCol) == -1) {
                    orderBySql += "," + orderByCol;
                }
            } else {
                orderBySql = " order by " + orderByCol;
            }

        }
        model.put(ReportParameter.ORDER_BY_SQL, orderBySql);

        return model;
    }

    public void setReportPath(String reportPath) {
        Assert.notNull(reportPath);
        this.reportPath = reportPath;
        if (!reportPath.startsWith("/")) {
            reportPath = "/" + reportPath;
        }
        if (!reportPath.endsWith("/")) {
            reportPath = reportPath + "/";
        }

        ReportParameter.JASPER_REPORT_PATH = reportPath;
    }

    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private JasperReportManager jasperReportManager;

    public void setJasperReportManager(JasperReportManager jasperReportManager) {
        this.jasperReportManager = jasperReportManager;
    }

    /**
     * Show parameters of requested report
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ModelAndView showReportParams(HttpServletRequest request,
            HttpServletResponse response) throws ServletException {
        String reportName = request.getParameter("reportName");
        logger.debug("enter showReportParams.....");

        if (StringUtils.isEmpty(reportName)) {
            saveMessage(Message.error("report.error.name.required"));
        } else {
            List reportParamList = jasperReportManager.getReportParamList(reportName);
            request.setAttribute("reportParamList", reportParamList);

        }

        return new ModelAndView("report/showReportParams");
    }

    protected Map getExporterParameters(HttpServletRequest request,
            String format) {
        /**
         * Exporter specific settings
         */
        Map exporterParameters = new HashMap();
        if ("html".equals(format)) {
        	exporterParameters.put(JRHtmlExporterParameter.IMAGES_URI,request.getContextPath() + "/report/tmpimg/");
            exporterParameters.put(JRHtmlExporterParameter.IMAGES_DIR_NAME,request.getSession().getServletContext().getRealPath("/report/tmpimg/"));;
            exporterParameters.put(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.TRUE);
            
            
//            exporterParameters.put(JRHtmlExporterParameter.IMAGES_URI,request.getContextPath() + "/report/tmpimg/aaa.gif");
//            exporterParameters.put(JRHtmlExporterParameter.IMAGES_DIR_NAME,request.getSession().getServletContext().getRealPath("/report/tmpimg/"));;
//            exporterParameters.put(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.TRUE);
//            exporterParameters.put(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
           /* Map imagesMap = new HashMap(); 
            request.getSession().setAttribute("IMAGES_MAP", imagesMap);
            exporterParameters.put(JRHtmlExporterParameter.IMAGES_MAP, imagesMap); 
            exporterParameters.put(JRHtmlExporterParameter.IMAGES_URI, "image.jsp?image=");*/
        } else if ("xls".equals(format)) {
            exporterParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
            exporterParameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
            exporterParameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);            
            exporterParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
            exporterParameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        }
        return exporterParameters;
    }

    public ModelAndView compileJasperReports(HttpServletRequest request,
            HttpServletResponse response) throws ServletException {
    	request.getParameter("doAction");
        File reportFolder = null;
        try {
            reportFolder = ContextUtil.getSpringContext().getResource(
                    ReportParameter.JASPER_REPORT_JRXML_PATH).getFile();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error occured when listing report files, check your deployment and access right.",
                    e);
        }

        File[] reportSources = reportFolder.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".jrxml");
            }

        });

        for (int i = 0; i < reportSources.length; i++) {
            try {
                logger.info("Compiling (only when necessary) report file:"
                        + reportSources[i].getPath());
                if (jasperReportManager.compileJasperReport(reportSources[i])) {
                    logger.info("Successfully compiled report: "
                            + reportSources[i].getPath());
                    saveMessage(Message.info("report.compile.jasper.succeed",reportSources[i]));
                }
            } catch (Exception e) {
                logger.error("Compile report file error:"  + reportSources[i].getPath(), e);
                saveMessage(Message.error("report.error.compile.jasper",reportSources[i]));
            }

        }
        return defaultAction(request, response);
    }
    
    public ModelAndView uploadReport(HttpServletRequest request,
    		HttpServletResponse response)throws ServletException{
    	
    	 MultipartHttpServletRequest fileRequest =  null;
    	 try{
    		 if(multipartResolver.isMultipart(request)){
    			 fileRequest=multipartResolver.resolveMultipart(request);
    		 }
    		 CommonsMultipartFile file = (CommonsMultipartFile)fileRequest.getFile("file");
        	 String fileName = file.getOriginalFilename();
        	 String filePath = null;
        	 File reportFolder = null;
        	 
             try {
                 reportFolder = ContextUtil.getSpringContext().getResource(
                         ReportParameter.JASPER_REPORT_JRXML_PATH).getFile();
             } catch (IOException e) {
                 throw new RuntimeException(
                         "Error occured when listing report files, check your deployment and access right.",
                         e);
             }
        	if(reportFolder.isDirectory()){
        		filePath = reportFolder.getPath();
        		if(!filePath.endsWith("/")){
        			filePath += "/";
        	
        		}
        	}else{
        		return defaultAction(request,response);
        	}
        	fileName = filePath+fileName; 
        	
        	try{
        		this.jasperReportManager.uploadJasperReport(file.getInputStream(),fileName);
        	}catch(Exception e){
        		logger.debug("jasperreport exception ....."+e.toString());
        	}
        	
        	this.compileJasperReports(request,response);
        	return defaultAction(request,response);
    		 
    	 }catch (Exception e) {
    		 logger.debug("It is not the multipart/form-data request. something wrong happen:" + e.getMessage());
    		 return defaultAction(request, response);
    	 }finally {
			if (fileRequest instanceof MultipartHttpServletRequest) {
				multipartResolver.cleanupMultipart(fileRequest);
			}
		 }
    			
    	 
    }
    
    public ModelAndView deleteReport(HttpServletRequest request,
    		HttpServletResponse response)throws ServletException{
    	File reportFolder = null;
    	if(logger.isDebugEnabled()){
    		logger.debug("enter deleteReport.......");
    	}
   	 	
        try {
            reportFolder = ContextUtil.getSpringContext().getResource(
                    ReportParameter.JASPER_REPORT_PATH).getFile();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error occured when listing report files, check your deployment and access right.",
                    e);
        }
    	
        String fileName = request.getParameter("reportFileName");
        if(fileName == null || "".equals(fileName) ){
        	return defaultAction(request,response);
        }
        String filePath = reportFolder.getPath();
        if(!filePath.endsWith("/"))
        	filePath += "/";
        filePath += fileName;
        //filePath += ".jasper";
        this.jasperReportManager.deleteJasperReport(filePath);

    	return defaultAction(request,response);
    	
    }

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
