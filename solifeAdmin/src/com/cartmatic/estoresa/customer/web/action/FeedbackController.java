package com.cartmatic.estoresa.customer.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.model.customer.Feedback;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.customer.service.FeedbackManager;

public class FeedbackController extends GenericController<Feedback> {
    private FeedbackManager feedbackManager = null;

    public void setFeedbackManager(FeedbackManager inMgr) {
        this.feedbackManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Feedback entity) {
		return entity.getFeedbackName();
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
		mgr = feedbackManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Feedback entity, BindException errors) {
	}
	
	
	  /**
     * reply to a feedback handle
     * @param request
     * @param response
     * @return
     */
    public ModelAndView replysAction(HttpServletRequest request,HttpServletResponse response){
    	if (logger.isDebugEnabled()) {
            logger.debug("entering 'FeedbackController replysAction' method...");
        }
    	String feedbackId=request.getParameter("feedbackId");
    	Feedback feedback=null;
    	List<Feedback> replyList=null;
    	if(feedbackId!=null){
    		feedback=mgr.getById(new Integer(feedbackId));
    		SearchCriteria searchCriteria=createSearchCriteria(request,"allReplaysByThreadId");
        	searchCriteria.addParamValue(new Integer(feedbackId));
        	searchCriteria.lockParamValues();
        	replyList=mgr.searchByCriteria(searchCriteria);
    	}
    	request.setAttribute("feedback",feedback);
    	return new ModelAndView("/customer/feedbackReplys","replyList",replyList);
    }
    
    /**
     * close a feedback status ,then this feedback thread can't not reply again.
     * @param request
     * @param response
     * @return
     */
    public ModelAndView closeFeedbackAction(HttpServletRequest request,HttpServletResponse response){
    	String feedbackId=request.getParameter("feedbackId");
    	String redirectView="feedbackReplys.html?feedbackId=" + feedbackId + "&doAction=replysAction" ;
    	Feedback feedback=mgr.getById(new Integer(feedbackId));
    	feedback.setStatus(Feedback.STATUS_CLOSED);//close the feedback.
    	mgr.save(feedback);
    	return new ModelAndView(new RedirectView(redirectView));
    }
    
    /**
     * open a close feedback ,then can reply again.
     * @param request
     * @param response
     * @return
     */
    public ModelAndView reOpenFeedbackAction(HttpServletRequest request,HttpServletResponse response){
    	//TODO 与closeFeedbackAction合并
        if(logger.isDebugEnabled()){
            logger.debug("enter reOpendFeedbackAction....");
        }
        String feedbackId=request.getParameter("feedbackId");
    	String redirectView="feedbackReplys.html?feedbackId=" + feedbackId + "&doAction=replysAction" ;
    	Feedback feedback=mgr.getById(new Integer(feedbackId));
    	feedback.setStatus(Feedback.STATUS_OPENED);//reopen the feedback.
    	mgr.save(feedback);
    	return new ModelAndView(new RedirectView(redirectView));
    }
   
}
