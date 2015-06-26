
package com.cartmatic.estore.system.scheduler.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.core.util.SchedulerUtil;
import com.cartmatic.estore.core.view.MailEngine;

public class JobListenerImpl implements JobListener {
	private final static Log logger	= LogFactory.getLog(JobListenerImpl.class);
	private final static String EMAIL_TEMPLATE = "system/bugReportEmail.vm";
	private String				name	= null;

	/**
	 * <p>
	 * Get the name of the <code>JobListener</code>.
	 * </p>
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> when a
	 * <code>{@link org.quartz.JobDetail}</code> was about to be executed (an
	 * associated <code>{@link Trigger}</code> has occured), but a
	 * <code>{@link TriggerListener}</code> vetoed it's execution.
	 * </p>
	 * 
	 * @see #jobToBeExecuted(JobExecutionContext)
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> when a
	 * <code>{@link org.quartz.JobDetail}</code> is about to be executed (an
	 * associated <code>{@link Trigger}</code> has occured).
	 * </p>
	 * 
	 * <p>
	 * This method will not be invoked if the execution of the Job was vetoed by
	 * a <code>{@link TriggerListener}</code>.
	 * </p>
	 * 
	 * @see #jobExecutionVetoed(JobExecutionContext)
	 */
	public void jobToBeExecuted(JobExecutionContext context) {
	}

	/**
	 * <p>
	 * Called by the <code>{@link Scheduler}</code> after a
	 * <code>{@link org.quartz.JobDetail}</code> has been executed, and be for
	 * the associated <code>Trigger</code>'s <code>triggered(xx)</code>
	 * method has been called.
	 * </p>
	 */
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		if (jobException != null) {
			// 记录错误
			SchedulerUtil.addErrorJob(context.getTrigger().getName(),
					jobException);
			logger.error(jobException);
			reportEmail(context, jobException);
		} else {
			SchedulerUtil.removeErrorJob(context.getTrigger().getName());
		}
	}

	/**
	 * 发送email，报告错误
	 * 
	 * @param context
	 * @param jobEx
	 */
	private void reportEmail(JobExecutionContext context,
			JobExecutionException jobEx) {
		ConfigUtil configUtil = ConfigUtil.getInstance();
		MailEngine mailEngine = (MailEngine) ContextUtil.getSpringBeanById("mailEngine");
		if (mailEngine == null) {
			return;
		}
		String error = new StringBuilder("Trigger[").append(context.getTrigger().getName()).
				append("]<br/> Time[").append(new Date()).
				append("]<br/><br/>StoreURL:").
				append(ConfigUtil.getInstance().getStore().getSiteUrl()).
				append("<br/><br/>").
				append(jobEx.toString()).toString();
		Map model = new HashMap();
		model.put("errorMsg", error);
		try {
			mailEngine.sendSimpleTemplateMail(EMAIL_TEMPLATE, 
			                model, null, null, configUtil.getBugReportEmail());
		} catch (Exception e) {
			logger.error(e);

		}

	}

	public void setName(String avalue) {
		name = avalue;
	}
}
