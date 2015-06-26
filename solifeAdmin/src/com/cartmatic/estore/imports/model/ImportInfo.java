package com.cartmatic.estore.imports.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.util.DateUtil;


public class ImportInfo {
	/**
	 * 导入的文件名
	 */
	private String filename;
	/**
	 * 成功添加数量
	 */
	private int successInsertCount=0;
	/**
	 * 失败添加数量
	 */
	private int failInsertCount=0;
	/**
	 * 成功更新数量
	 */
	private int successUpdateCount=0;
	/**
	 * 失败更新数量
	 */
	private int failUpdateCount=0;
	/**
	 * 忽略数量
	 */
	private int ignoreCount;
	/**
	 * 失败记录,(只记录第几行)
	 */
	private List<Integer> failData=new ArrayList<Integer>();
	/**
	 * 开始导入时间(本次或上次)
	 */
	private Date startImportTime=null;
	/**
	 * 结束导入时间(上次)
	 */
	private Date endImportTime=null;
	
	private static ImportInfo importInfo=null;
	private final static String key="key";
	private final static String key2="key";
	/**
	 * 连续失败次数
	 */
	private int continuouslyFailCount=0;
	
	/**
	 * 导入类型(本次或上一次)
	 */
	private String importType;
	
	/**
	 * 开始导入行
	 */
	private Integer beginRow;
	
	/**
	 * 指定结束行
	 */
	private Integer endRow;
	
	/**
	 * 发出停止导入指令
	 */
	private boolean stop;
	
	/**
	 * 是否正在运行着导入数据
	 */
	private boolean running;
	
	public int getIgnoreCount() {
		return ignoreCount;
	}

	public void setIgnoreCount(int ignoreCount) {
		this.ignoreCount = ignoreCount;
	}

	private ImportInfo(){}
	
	public static ImportInfo getInstance(){
		if(importInfo==null){
			synchronized (key) {
				importInfo=new ImportInfo();
			}
		}
		return importInfo;
	}
	
	public List<Integer> getFailData() {
		return failData;
	}

	public void setFailData(List<Integer> failData) {
		this.failData = failData;
	}

	public Date getStartImportTime() {
		return startImportTime;
	}
	
	public String getStartImportTimeStr() {
		return DateUtil.getDateTime(startImportTime);
	}

	public void setStartImportTime(Date startImportTime) {
		this.startImportTime = startImportTime;
	}

	public Date getEndImportTime() {
		return endImportTime;
	}
	
	public String getEndImportTimeStr() {
		return DateUtil.getDateTime(endImportTime);
	}

	public void setEndImportTime(Date endImportTime) {
		this.endImportTime = endImportTime;
	}

	public synchronized void addSuccessInsertCount(){
		this.successInsertCount++;
		continuouslyFailCount=0;
	}
	
	/**
	 * 初始化导入提示信息
	 */
	public void init(){
		successInsertCount=0;
		failInsertCount=0;
		successUpdateCount=0;
		failUpdateCount=0;
		failData=new ArrayList<Integer>();
		startImportTime=new Date();
		endImportTime=null;
		continuouslyFailCount=0;
		ignoreCount=0;
		running=true;
	}
	
	public void addFailInsertCount(Integer rowNum){
		this.failInsertCount++;
		failData.add(rowNum);
		continuouslyFailCount++;
	}
	
	public void addFailCount(boolean isUpldate,Integer rowNum){
		if(isUpldate){
			failUpdateCount++;
		}else{
			failInsertCount++;
		}
		failData.add(rowNum);
		continuouslyFailCount++;
	}
	
	public void addIgnoreCount(){
		ignoreCount++;
	}
	
	public void addSuccessUpdateCount(){
		this.successUpdateCount++;
		continuouslyFailCount=0;
	}
	
	public void addSuccessCount(boolean isUpldate){
		if(isUpldate){
			successUpdateCount++;
		}else{
			successInsertCount++;
		}
		continuouslyFailCount=0;
	}
	
	public void addFailUpdateCount(Integer rowNum){
		this.failUpdateCount++;
		failData.add(rowNum);
		continuouslyFailCount++;
	}
	
	public int getSuccessInsertCount() {
		return successInsertCount;
	}
	public void setSuccessInsertCount(int successInsertCount) {
		this.successInsertCount = successInsertCount;
	}
	public int getFailInsertCount() {
		return failInsertCount;
	}
	public void setFailInsertCount(int failInsertCount) {
		this.failInsertCount = failInsertCount;
	}
	public int getSuccessUpdateCount() {
		return successUpdateCount;
	}
	public void setSuccessUpdateCount(int successUpdateCount) {
		this.successUpdateCount = successUpdateCount;
	}
	public int getFailUpdateCount() {
		return failUpdateCount;
	}
	public void setFailUpdateCount(int failUpdateCount) {
		this.failUpdateCount = failUpdateCount;
	}
	
	public String toString(){
		return new ToStringBuilder(this).append("startImportTime:",this.startImportTime).append("successInsertCount:",this.successInsertCount).append("failInsertCount:",this.failInsertCount).append("successUpdateCount:",this.successUpdateCount).append("failUpdateCount",this.failUpdateCount).append("failData:",this.failData).toString();
	}

	/*public boolean isCurrentIsUpdate() {
		return currentIsUpdate;
	}*/

//	public void setCurrentIsUpdate(boolean currentIsUpdate) {
//		this.currentIsUpdate = currentIsUpdate;
//	}

	public int getContinuouslyFailCount() {
		return continuouslyFailCount;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public Integer getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
