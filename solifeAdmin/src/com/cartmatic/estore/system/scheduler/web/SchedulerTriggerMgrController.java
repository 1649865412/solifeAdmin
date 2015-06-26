package com.cartmatic.estore.system.scheduler.web;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.SchedulerUtil;

public class SchedulerTriggerMgrController extends BaseController
{
	private Scheduler scheduler = null;
	
	private String defaultView = "system/schedulerTriggerFrom";
	
	
	public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException
	{
		removeNavFromSearchCriteria(req);
		ModelAndView mv = new ModelAndView(defaultView);
		Trigger trigger = getTrigger(req);
		if (trigger instanceof CronTrigger)
		{
			mv.addObject("triggerType", "CronTrigger");
		}
		else if (trigger instanceof SimpleTrigger)
		{
			mv.addObject("triggerType", "SimpleTrigger");
		}
		mv.addObject("trigger", trigger);
		return mv;
	}
	
	/**
	 * save the trigger.
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView save(HttpServletRequest req, HttpServletResponse resp)
	{
		Trigger trigger = getTrigger(req);
		if (trigger instanceof CronTrigger)
		{
			return saveCronTrigger(req, (CronTrigger) trigger);
		}
		else if (trigger instanceof SimpleTrigger)
		{
			return saveSimpleTrigger(req, (SimpleTrigger) trigger);
		}
		return null;
	}
	
	private ModelAndView saveCronTrigger(HttpServletRequest req, CronTrigger trigger) 
	{
		String cronExpression = req.getParameter("cronExpression");
		
		ModelAndView mv = new ModelAndView(defaultView);
		mv.addObject("trigger", trigger);
		mv.addObject("triggerType", "CronTrigger");
		try
		{
			trigger.setCronExpression(cronExpression);
		}
		catch(java.text.ParseException e)
		{
			saveMessage(Message.error("scheduler.error.expression"));
			return mv;
		}
		if(!reSchedulerJob(trigger))
		{
			saveMessage(Message.error("scheduler.error.reScheduler"));
			return mv;
		}
		try
		{
			SchedulerUtil.updateCronTriggerConfig(trigger.getName(), cronExpression);
			saveMessage(Message.info("scheduler.updateConfig.succeed",trigger.getName()));
		}
		catch(Exception e)
		{
			saveMessage(Message.error("scheduler.error.updateConfig"));
			logger.error("Can't not update XML File.",e);
			return mv;
		}
		return getRedirectView("/system/scheduler_trigger.html?trigger="+trigger.getName()+"&group="+trigger.getGroup());
	}
	
	private ModelAndView saveSimpleTrigger(HttpServletRequest req, SimpleTrigger trigger) 
	{
		long repeatInterval = 0;
		
		ModelAndView mv = new ModelAndView(defaultView);
		mv.addObject("triggerType", "SimpleTrigger");
		mv.addObject("trigger", trigger);
		try
		{
			repeatInterval = Long.parseLong(req.getParameter("repeatInterval"));
		}
		catch(NumberFormatException e)
		{
			saveMessage(Message.error("scheduler.error.invalidParam"));
			return mv;
		}
				
		trigger.setRepeatInterval(repeatInterval);			
		if(!reSchedulerJob(trigger))
		{
			saveMessage(Message.error("scheduler.error.reScheduler"));
			return mv;
		}		
		try
		{
			SchedulerUtil.updateSimpleTriggerConfig(trigger.getName(), repeatInterval);
			saveMessage(Message.info("scheduler.updateConfig.succeed",trigger.getName()));
		}
		catch(Exception e)
		{
			saveMessage(Message.error("scheduler.error.updateConfig"));
			logger.error("Can't not update XML File.",e);
			return mv;
		}
		return getRedirectView("/system/scheduler_trigger.html?trigger="+trigger.getName()+"&group="+trigger.getGroup());
	}
	
	private boolean reSchedulerJob(Trigger trigger)
	{
		boolean rs = true;
		try
		{	
			int status = scheduler.getTriggerState(trigger.getName(), trigger.getGroup());
			scheduler.unscheduleJob(trigger.getName(), trigger.getGroup());
			scheduler.scheduleJob(trigger);
			if (status == 0)
			{
				scheduler.resumeTrigger(trigger.getName(), trigger.getGroup());
			}
		}
		catch(SchedulerException e)
		{
			rs = false;
			logger.error("Can't not reset trigger["+ trigger.getName() +"]",e);
		}
		return rs;
	}
	
	private Trigger getTrigger(HttpServletRequest req)
	{
		String trigger = req.getParameter("trigger");
		String group = req.getParameter("group");
		Trigger rs = null;
		try
		{
			rs = scheduler.getTrigger(trigger, group);
		}
		catch (SchedulerException e)
		{
			logger.error("Get trigger fail.",e);
		}
		//处理描述文字
		String des = rs.getDescription();
		while(des.endsWith(SchedulerUtil.PAUSED_FLAG))
		{
			des = des.substring(0, des.indexOf(SchedulerUtil.PAUSED_FLAG));
		}
		rs.setDescription(des);
		return rs;
	}
	
	public void setScheduler(Scheduler avalue)
	{
		scheduler = avalue;
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
