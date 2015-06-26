/*
 * Created on Dec 5, 2006
 *
 */
package com.cartmatic.estore.report.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ryan
 *
 */
public interface JasperReportManager {
    public List getReportParamList(String reportName);
    
    public List getReportList();
    
    public boolean compileJasperReport(File sourceReportFile);
    
    public boolean uploadJasperReport(InputStream in,String fileName);
    
    public boolean deleteJasperReport(String fileName);
}
