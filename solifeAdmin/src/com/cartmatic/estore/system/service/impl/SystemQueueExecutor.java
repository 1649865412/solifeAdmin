package com.cartmatic.estore.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.content.service.UploadManager;
import com.cartmatic.estore.core.view.MailEngine;
import com.cartmatic.estore.exception.IPTracerException;
import com.cartmatic.estore.order.service.GeoIpCollector;
import com.cartmatic.estore.order.service.SalesOrderGeoipManager;
import com.cartmatic.estore.system.model.EmailQueue;
import com.cartmatic.estore.system.model.GeoIpCollectorQueue;
import com.cartmatic.estore.system.service.SystemQueueManager;

public class SystemQueueExecutor {

	protected final transient Log logger = LogFactory.getLog(getClass());
	
	private int maxExecTimes = 3;
	/**
	 * 重试的时间间隔,单位是秒
	 */
	private int	retryIntervalSec	= 30;
	
	private int numOfQueuePerTime = 10;
	
	private SystemQueueManager systemQueueManager = null;
	
	private MailEngine mailEngine;
	
	private GeoIpCollector geoIpCollector=null;
	
	private SalesOrderGeoipManager salesOrderGeoipManager=null;
	
	private UploadManager uploadManager=null;
	
	
	public void setUploadManager(UploadManager uploadManager) {
		this.uploadManager = uploadManager;
	}

	public void processQueue() {
		List<SystemQueue> items = systemQueueManager.fetchQueueListToProcess(numOfQueuePerTime);
		if (logger.isDebugEnabled())
			logger.debug("ProcessQueue:"+items.size());
		for (int i = 0; i < items.size(); i++) {
			SystemQueue item = items.get(i);
			int sendTimes = item.getExecTimes().intValue();
			item.setExecTimes(new Integer(sendTimes + 1).shortValue());
			// test模式下,不处理队列.
			if (ConfigUtil.getInstance().getSystemMode().equals(Constants.SYSTEM_MOED_TEST))
			{
				item.setTargetEntiy(null);
				item.setQueueStatus(SystemQueue.STATUS_SENT);
				item.appendErrorMsg("Do nothing with test. succefully!");
			}
			else if (SystemQueue.TYPE_EMAIL.equals(item.getQueueType()))
			{
				// send email
				EmailQueue mail = (EmailQueue) item.getTargetEntiy();
				try 
				{	
					mailEngine.sendQueuedEmails(mail.getMimeMessage());				
					//发送成功，清空已封装好的邮件数据，节省存储空间
					item.setTargetEntiy(null);
					item.setQueueStatus(SystemQueue.STATUS_SENT);
					item.appendErrorMsg("Send Email succefully!");
				} catch (Throwable e) {
					logger.error("Error sending a queued email, id: " + item.getSystemQueueId() + " ["+ mail.getMailTos() +"] title: " + item.getTitle() , e);
					if (item.getExecTimes().intValue() >= maxExecTimes) {
						item.setQueueStatus(SystemQueue.STATUS_CANCELLED);
					} else {
						item.setQueueStatus(SystemQueue.STATUS_NOT_SEND);
						item.setNextRetryTime(new Date(System.currentTimeMillis() + retryIntervalSec * 10000 * item.getExecTimes()));
					}
					item.appendErrorMsg("Error sending email: ["+ mail.getMailTos()+"]" + e.getMessage());
				}
			}
			else if (SystemQueue.TYPE_GEOIP.equals(item.getQueueType()))
			{
				//TODO Get the location by IP address.
				GeoIpCollectorQueue geoIpCollectorQueue=(GeoIpCollectorQueue)item.getTargetEntiy();
				try {
					SalesOrderGeoip salesOrderGeoip = geoIpCollector.getGeoIpByIp(geoIpCollectorQueue.getCustomerIp());
					//当系统已存在时，即进行更新操作
					SalesOrderGeoip tempSalesOrderGeoip=salesOrderGeoipManager.getSalesOrderGeoipByOrderNoAndActionType(geoIpCollectorQueue.getOrderNO(), geoIpCollectorQueue.getActionType());
					if(tempSalesOrderGeoip==null){
						salesOrderGeoip.setOrderNo(geoIpCollectorQueue.getOrderNO());
						salesOrderGeoip.setActionType(geoIpCollectorQueue.getActionType());
					}else{
						tempSalesOrderGeoip.setCountry(salesOrderGeoip.getCountry());
						tempSalesOrderGeoip.setState(salesOrderGeoip.getState());
						tempSalesOrderGeoip.setCity(salesOrderGeoip.getCity());
						tempSalesOrderGeoip.setLat(salesOrderGeoip.getLat());
						tempSalesOrderGeoip.setLon(salesOrderGeoip.getLon());
						salesOrderGeoip=tempSalesOrderGeoip;
					} 
					salesOrderGeoipManager.save(salesOrderGeoip);
					item.setQueueStatus(SystemQueue.STATUS_SENT);
					item.appendErrorMsg("IP Tracer succefully!");
				} catch (Exception e) {
					e.printStackTrace();
					
					if (item.getExecTimes().intValue() >= maxExecTimes) {
						item.setQueueStatus(SystemQueue.STATUS_CANCELLED);
					} else {
						item.setQueueStatus(SystemQueue.STATUS_NOT_SEND);
						item.setNextRetryTime(new Date(System.currentTimeMillis() + retryIntervalSec * 1000 * item.getExecTimes()));
					}
					//当IP无法解释时，直接取消，
					if(e instanceof IPTracerException){
						IPTracerException exception=(IPTracerException)e;
						//TYPE_3 IP无法解析，当IP无法解析时，也插入记录，但相关的国家，州，城市，经纬度等为N/A
						if(exception.getType().intValue()==IPTracerException.TYPE_3){
							item.setQueueStatus(SystemQueue.STATUS_CANCELLED);
							SalesOrderGeoip salesOrderGeoip=new SalesOrderGeoip();
							salesOrderGeoip.setOrderNo(geoIpCollectorQueue.getOrderNO());
							salesOrderGeoip.setActionType(geoIpCollectorQueue.getActionType());
							salesOrderGeoip.setCustomerIp(geoIpCollectorQueue.getCustomerIp());
							salesOrderGeoip.setCountry("N/A");
							salesOrderGeoip.setState("N/A");
							salesOrderGeoip.setCity("N/A");
							salesOrderGeoip.setLat("N/A");
							salesOrderGeoipManager.save(salesOrderGeoip);
						}else if(exception.getType().intValue()==IPTracerException.TYPE_0){
							//当登录失败时，推迟继续执行，指定相对较长的时间
							item.setNextRetryTime(new Date(System.currentTimeMillis() + retryIntervalSec * 1000 *10* item.getExecTimes()));
						}
					}
					item.appendErrorMsg("Error IP Tracer: ["+ geoIpCollectorQueue.getCustomerIp()+"]" + e.getMessage());
				}
			}else if (SystemQueue.TYPE_IMAGE_REBUILD.equals(item.getQueueType()))
			{
				try 
				{	
					uploadManager.processImageBatchJob();			
					item.setTargetEntiy(null);
					item.setQueueStatus(SystemQueue.STATUS_SENT);
					item.appendErrorMsg("Start Image Rebuild...");
				} catch (Throwable e) {
					logger.error("Error Image Rebuild, id: " + item.getSystemQueueId() + " title: " + item.getTitle() , e);
					if (item.getExecTimes().intValue() >= maxExecTimes) {
						item.setQueueStatus(SystemQueue.STATUS_CANCELLED);
					} else {
						item.setQueueStatus(SystemQueue.STATUS_NOT_SEND);
						item.setNextRetryTime(new Date(System.currentTimeMillis() + retryIntervalSec * 10000 * item.getExecTimes()));
					}
					item.appendErrorMsg("Error Image Rebuild: "+ e.getMessage());
				}
			}else if (SystemQueue.TYPE_IMAGE_CLEAN.equals(item.getQueueType()))
			{
				try 
				{	
					uploadManager.cleanUnusefulImageJob();	
					item.setTargetEntiy(null);
					item.setQueueStatus(SystemQueue.STATUS_SENT);
					item.appendErrorMsg("Start Clean Unuseful Image...");
				} catch (Throwable e) {
					logger.error("Error Clean Unuseful Image, id: " + item.getSystemQueueId() + " title: " + item.getTitle() , e);
					if (item.getExecTimes().intValue() >= maxExecTimes) {
						item.setQueueStatus(SystemQueue.STATUS_CANCELLED);
					} else {
						item.setQueueStatus(SystemQueue.STATUS_NOT_SEND);
						item.setNextRetryTime(new Date(System.currentTimeMillis() + retryIntervalSec * 10000 * item.getExecTimes()));
					}
					item.appendErrorMsg("Error Clean Unuseful Image: "+ e.getMessage());
				}
			}
			try {
				systemQueueManager.save(item);
			} catch (Throwable e) {
				// 如果save时出错一般就是数据库错误（无法继续处理），直接抛出外面，终止当前处理，会有一些中间状态的垃圾数据（状态还会是sending）
				logger.error("Error updating multiple queue status.",e);
				//还是不要抛Exception,否则定时任务器会停止的.
				//throw new RuntimeException("Error updating multiple queue status.", e);
			}
		}
		systemQueueManager.flush();
	}
	
	public void setSystemQueueManager(SystemQueueManager avalue)
	{
		this.systemQueueManager = avalue;
	}
	
	public void setMailEngine(MailEngine avalue)
	{
		this.mailEngine = avalue;
	}
	
	public void setMaxExecTimes(int avalue)
	{
		maxExecTimes = avalue;
	}
	
	public void setNumOfQueuePerTime(int avalue)
	{
		numOfQueuePerTime = avalue;
	}
	
	public void setRetryIntervalSec(int avalue)
	{
		retryIntervalSec = avalue;
	}

	public void setGeoIpCollector(GeoIpCollector geoIpCollector) {
		this.geoIpCollector = geoIpCollector;
	}

	public void setSalesOrderGeoipManager(
			SalesOrderGeoipManager salesOrderGeoipManager) {
		this.salesOrderGeoipManager = salesOrderGeoipManager;
	}
	
	
	
}
