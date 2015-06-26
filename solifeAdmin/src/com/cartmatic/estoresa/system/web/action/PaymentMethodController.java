package com.cartmatic.estoresa.system.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.common.model.system.PaymentMethod;
import com.cartmatic.estore.system.service.PaymentMethodManager;
import com.cartmatic.estore.webapp.util.RequestUtil;

public class PaymentMethodController extends GenericController<PaymentMethod> {
    private PaymentMethodManager paymentMethodManager = null;

    public void setPaymentMethodManager(PaymentMethodManager inMgr) {
        this.paymentMethodManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(PaymentMethod entity) {
		return entity.getPaymentMethodName();
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
	
	

	@Override
	public ModelAndView multiSave(HttpServletRequest request,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
            logger.debug("entering 'PaymentMethodController multiSave' method...");
        }

        // get the batch data from post form
    	String[]paymentMethodIds=request.getParameterValues("paymentMethodId");
    	//
    	String[]statuses=request.getParameterValues("ck_status");
        
    	String[]paymentMethodNames=request.getParameterValues("paymentMethodName");
    	
    	String[]paymentMethodDetails=request.getParameterValues("paymentMethodDetail");
    	
    	String[]protocols=request.getParameterValues("protocol");
    	String[]sortOrders=request.getParameterValues("sortOrder");
    	
    	
    	List<PaymentMethod> paymentMethodList=new ArrayList<PaymentMethod>();
    	if(paymentMethodIds.length>0){
    		
	    	for(int i=0;i<paymentMethodIds.length;i++){
	    		
	    		PaymentMethod pm=new PaymentMethod(new Integer(paymentMethodIds[i]));
	    		//set payment new value;
	    		pm.setSortOrder(new Integer(sortOrders[i]));
	    		
	    		pm.setProtocol(protocols[i]);
	    		
	    		pm.setStatus(new Short(statuses[i]));
	    		
	    		pm.setPaymentMethodName(paymentMethodNames[i]);
	    		
	    		pm.setPaymentMethodDetail(paymentMethodDetails[i]);
	    		
	    		paymentMethodList.add(pm);
	    		
	    	}
	    	//update batch
	    	paymentMethodManager.updateBatchPaymentMethods(paymentMethodList);
	    	//show the message for user
	        saveMessage(Message.info("common.updated.multi"));
    	}
    	 //save message
    	return new ModelAndView(new RedirectView("paymentMethod.html"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = paymentMethodManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, PaymentMethod entity, BindException errors) {
	}

	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		 List<PaymentMethod> payMentMethodList = paymentMethodManager.findUnDeletedPaymentMethods();
	     List<PaymentMethod> canAddPayMentMethodList = paymentMethodManager.findDeletedPaymentMethods();
	     request.setAttribute("canAddPayMentMethodList",canAddPayMentMethodList);
		 return new ModelAndView("system/paymentMethodList","paymentMethodList", payMentMethodList);	
	}
	
    /**
     * add one record
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ModelAndView addAction(HttpServletRequest request,HttpServletResponse response)  throws ServletException{
    	Integer paymentMethodId=RequestUtil.getInteger(request,"paymentMethodId");
    	PaymentMethod paymentMethod=paymentMethodManager.getById(paymentMethodId);
    	paymentMethod.setStatus(Constants.STATUS_INACTIVE);
    	paymentMethodManager.save(paymentMethod);
        saveMessage(Message.info("paymentMethod.added",paymentMethod.getPaymentMethodName()));
        return new ModelAndView(new RedirectView("paymentMethod.html"));
    }
	
    
    /**
     * delete one record
     * @param request
     * @param response
     * @return
     */
    public ModelAndView deleteAction(HttpServletRequest request,HttpServletResponse response){
    	if (logger.isDebugEnabled()) {
            logger.debug("entering 'PaymentMethodController deleteAction' method...");
        }
    	Integer paymentMethodId=RequestUtil.getInteger(request,"paymentMethodId");
    	PaymentMethod paymentMethod=paymentMethodManager.getById(paymentMethodId);
    	paymentMethodManager.deleteById(paymentMethod.getPaymentMethodId());
        saveMessage(Message.info("paymentMethod.deleted",paymentMethod.getPaymentMethodName()));
    	return new ModelAndView(new RedirectView("paymentMethod.html"));
    }
    
    

    /**
     * jump to the payment gateway config page
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ModelAndView configAction(HttpServletRequest request, HttpServletResponse response)throws ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering 'configAction' method...");
		}
    	Integer paymentMethodId=RequestUtil.getInteger(request,"paymentMethodId");
    	PaymentMethod paymentMethod=paymentMethodManager.getById(paymentMethodId);
        return new ModelAndView("system/" + paymentMethod.getConfigFile(),"paymentMethod",paymentMethod);
    }
    
    
    public ModelAndView saveConfigAction(HttpServletRequest request,HttpServletResponse response) throws ServletException{

    	Integer paymentMethodId=RequestUtil.getInteger(request,"paymentMethodId");

    	PaymentMethod paymentMethod=paymentMethodManager.getById(paymentMethodId);
    	
    	HashMap<String,String> hashMap=new HashMap<String,String>();

    	Map paramMap=request.getParameterMap();
    	
    	Iterator it=paramMap.entrySet().iterator();
    	
    	while(it.hasNext()){
    		Map.Entry en=(Map.Entry)it.next();
    		
    		String[]value=(String[])en.getValue();
    		
    		hashMap.put(en.getKey().toString(),value[0]);
    	}
    	//test model
    	if(request.getParameter("testModel")!=null){
    		paymentMethod.setTestModel(request.getParameter("testModel"));
    	}
    	paymentMethod.setConfigData(hashMap);
    	paymentMethodManager.save(paymentMethod);
        saveMessage(Message.info("paymentMethod.configData.updated",paymentMethod.getPaymentMethodName()));
    	return getRedirectView("/system/paymentMethod.html?doAction=configAction&paymentMethodId="+paymentMethodId);
    }
	

}
