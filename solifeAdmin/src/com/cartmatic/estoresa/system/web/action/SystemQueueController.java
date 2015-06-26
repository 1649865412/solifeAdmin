package com.cartmatic.estoresa.system.web.action;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.order.SalesOrderGeoip;
import com.cartmatic.estore.common.model.system.SystemQueue;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.system.model.GeoIpCollectorQueue;
import com.cartmatic.estore.system.service.SystemQueueManager;

public class SystemQueueController extends GenericController<SystemQueue> {
    private SystemQueueManager systemQueueManager = null;

    public void setSystemQueueManager(SystemQueueManager inMgr) {
        this.systemQueueManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SystemQueue entity) {
		return entity.getSystemQueueName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = systemQueueManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SystemQueue entity, BindException errors) {
	}

	
	public ModelAndView tracerPlaceOrderIp(HttpServletRequest request,HttpServletResponse response) {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String orderNo=request.getParameter("orderNo");
			String ipAddress=request.getParameter("ipAddress");
			GeoIpCollectorQueue geoIpCollectorQueue=new GeoIpCollectorQueue();
			geoIpCollectorQueue.setOrderNO(orderNo);
			geoIpCollectorQueue.setCustomerIp(ipAddress);
			geoIpCollectorQueue.setActionType(SalesOrderGeoip.PLACE_ORDER);
			SystemQueue queue = new SystemQueue();
			queue.setTitle(ipAddress); 
			queue.setQueueType(SystemQueue.TYPE_GEOIP);
			queue.setTargetEntiy(geoIpCollectorQueue);
			queue.setNextRetryTime(new Date());
			systemQueueManager.save(queue);
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	/**
	 * 重建图片
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView imageRebuild(HttpServletRequest request,HttpServletResponse response) {
		SystemQueue queue = new SystemQueue();
		queue.setTitle("Image Rebuild");
		queue.setQueueType(SystemQueue.TYPE_IMAGE_REBUILD);
		queue.setTargetEntiy(null);
		queue.setNextRetryTime(new Date());
		systemQueueManager.save(queue);
		saveMessage(Message.info("systemQueue.image.rebuild"));
		return getRedirectView("/system/systemQueue.html");
	}
	
	/**
	 * 清理没有引用的图片
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView cleanUnusefulImage(HttpServletRequest request,HttpServletResponse response) {
		SystemQueue queue = new SystemQueue();
		queue.setTitle("Clean Unuseful Image");
		queue.setQueueType(SystemQueue.TYPE_IMAGE_CLEAN);
		queue.setTargetEntiy(null);
		queue.setNextRetryTime(new Date());
		systemQueueManager.save(queue);
		saveMessage(Message.info("systemQueue.image.clean"));
		return getRedirectView("/system/systemQueue.html");
	}

}
