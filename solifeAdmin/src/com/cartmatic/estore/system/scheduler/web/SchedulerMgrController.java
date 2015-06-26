package com.cartmatic.estore.system.scheduler.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.SchedulerUtil;

public class SchedulerMgrController extends BaseController
{

	private String defaultView = "system/schedulerMgr";
	
	private Scheduler scheduler = null;

	public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException
	{
		removeNavFromSearchCriteria(req);
		return getBaseMv();
	}
	
	public ModelAndView run(HttpServletRequest req, HttpServletResponse resp)
	{
		boolean hasError = false;
		try
		{
			SchedulerUtil.startupScheduler(scheduler);
		}
		catch(SchedulerException e)
		{
			logger.error("Can't start scheduler.",e);
			hasError = true;
		}
		if (hasError)
		{
			saveMessage(Message.error("scheduler.error.start"));
		}else{
			saveMessage(Message.info("scheduler.msg.start"));
		}
		return getRedirectView(successView);
	}
	
	public ModelAndView stop(HttpServletRequest req, HttpServletResponse resp)
	{
		boolean hasError = false;
		try
		{
			scheduler.standby();
		}
		catch(SchedulerException e)
		{
			logger.error("Can't shutdown scheduler.",e);
			hasError = true;
		}
		if (hasError)
		{
			saveMessage(Message.error("scheduler.error.shutdown"));
		}else{
			saveMessage(Message.info("scheduler.msg.shutdown"));
		}
		return getRedirectView(successView);
	}
	
	public ModelAndView pausedTrigger(HttpServletRequest req, HttpServletResponse resp)
	{
		String triggerName = req.getParameter("trigger");
		String groupName = req.getParameter("group");
		boolean hasError = false;
		try
		{
			scheduler.pauseTrigger(triggerName, groupName);
			SchedulerUtil.pausedTriggerInConfig(triggerName);
		}
		catch(Exception e)
		{
			logger.error("Can't shutdown scheduler.",e);
			hasError = true;
		}
		if (hasError)
		{
			saveMessage(Message.error("scheduler.error.paused",triggerName));
		}else{
			saveMessage(Message.info("scheduler.paused.succeed",triggerName));
		}
		return getRedirectView(successView);
	}
	
	public ModelAndView resumeTrigger(HttpServletRequest req, HttpServletResponse resp)
	{
		String triggerName = req.getParameter("trigger");
		String groupName = req.getParameter("group");
		boolean hasError = false;
		try
		{
			scheduler.resumeTrigger(triggerName, groupName);
			SchedulerUtil.unpausedTriggerInConfig(triggerName);
		}
		catch(Exception e)
		{
			logger.error("Can't shutdown scheduler.",e);
			hasError = true;
		}
		if (hasError)
		{
			saveMessage(Message.error("scheduler.error.resume",triggerName));
		}else{
			saveMessage(Message.info("scheduler.startup.succeed",triggerName));
		}
		return getRedirectView(successView);
	}

	private ModelAndView getBaseMv()
	{
		ModelAndView mv = new ModelAndView(defaultView);;
		mv.addObject("scheduler", scheduler);
		
		String[] triggerGroups;
		String[] triggersInGroup;
		try
		{
			if (scheduler.isInStandbyMode())
			{
				return mv;
			}
			triggerGroups = scheduler.getTriggerGroupNames();
			int groupSize = triggerGroups.length;
			mv.addObject("groupSize", groupSize);
			for (int i = 0; i < triggerGroups.length; i++)
			{
				mv.addObject("groupName"+i, triggerGroups[i]);
				triggersInGroup = scheduler.getTriggerNames(triggerGroups[i]);
				
				List triggers = new ArrayList();
				for (int j = 0; j < triggersInGroup.length; j++)
				{
					TriggerModel tmodel = new TriggerModel();
					Trigger trigger = scheduler.getTrigger(triggersInGroup[j], triggerGroups[i]);
					//添加描述，去掉原来在trigger中的描述的 pasused_flag
					String des = trigger.getDescription();
					if (des != null)
					{
						while(des.endsWith(SchedulerUtil.PAUSED_FLAG))
						{
							des = des.substring(0, des.indexOf(SchedulerUtil.PAUSED_FLAG));
						}
					}
					trigger.setDescription(des);
					tmodel.setTrigger(trigger);
					int status = scheduler.getTriggerState(triggersInGroup[j], triggerGroups[i]);
					//如果发现执行job有错误,而且不是暂停状态,则设置为ERROR状态显示.
					if (SchedulerUtil.hasErrorTrigger(triggersInGroup[j]) && status != Trigger.STATE_PAUSED)
					{
						tmodel.setStatus(Trigger.STATE_ERROR);							
					}
					else
					{
						tmodel.setStatus(status);
					}					
					triggers.add(tmodel);
				}
				mv.addObject("groupTriggers"+i, triggers);
			}
		}
		catch (SchedulerException e)
		{
			logger.error("Get scheduler fail.",e);
		}
		return mv;
	}
	
	
	
	public class TriggerModel
	{
		private Trigger trigger = null;
		private int status = 0;
		
		public int getStatus()
		{
			return status;
		}
		public void setStatus(int status)
		{
			this.status = status;
		}
		public Trigger getTrigger()
		{
			return trigger;
		}
		public void setTrigger(Trigger trigger)
		{
			this.trigger = trigger;
		}
	}

	public void setScheduler(Scheduler avalue)
	{
		scheduler = avalue;
	}

	public String getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
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
