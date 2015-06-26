package com.cartmatic.estoresa.system.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.common.model.system.SystemVersion;
import com.cartmatic.estore.other.service.PatchExecuteHistoryManager;
import com.cartmatic.estore.system.service.SystemVersionManager;

public class SystemVersionController extends GenericController<SystemVersion> {
    private SystemVersionManager systemVersionManager = null;
    
    private PatchExecuteHistoryManager patchExecuteHistoryManager=null;

    public void setSystemVersionManager(SystemVersionManager inMgr) {
        this.systemVersionManager = inMgr;
    }

	public void setPatchExecuteHistoryManager(
			PatchExecuteHistoryManager patchExecuteHistoryManager) {
		this.patchExecuteHistoryManager = patchExecuteHistoryManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(SystemVersion entity) {
		return entity.getSystemVersionName();
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
		mgr = systemVersionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, SystemVersion systemVersion, BindException errors) {
		License_Cartmatic_eStore li = new License_Cartmatic_eStore();
		String l_key = li.generate(systemVersion.getDomainName());
		if (!systemVersion.getLicenseKey().equals(l_key))
		{
			ModelAndView mv = new ModelAndView("system/systemVersionForm");
			errors.rejectValue("licenseKey", "license.illegal");
		}
	}
	
	
	
	@Override
	public ModelAndView defaultAction(HttpServletRequest request,HttpServletResponse response) {
		SystemVersion systemVersion=systemVersionManager.getSystemVersion();
		if(systemVersion==null){
			systemVersion=new SystemVersion();
		}
		request.setAttribute("patchHistorys",patchExecuteHistoryManager.getAllOrdered("patchExecuteHistoryId", true));
		return getModelAndView(listView,"systemVersion",systemVersion);
	}

	@Override
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//不需要
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//不需要
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public ModelAndView multiDelete(HttpServletRequest request,HttpServletResponse response) {
		//不需要
		throw new RuntimeException("Not implemented yet!");
	}




	private class License_Cartmatic_eStore{
	private static final String PATTERN = "Cartmatic-2007-";
	
	private static final String L = "-- Copyright©2007 Cartmatic Software Technology Co., Ltd.";
	
	public String generate(String domain)
	{
		String pCode = "Cartmatic eStore" + L;
			String need = domain.substring(0, 1) + PATTERN;
			String dx = need + pCode + domain;
			int suf = decode(dx);
			return change(need + suf);
		}
	
		private int decode(String s)
		{
			int i = 0;
			char ac[] = s.toCharArray();
			int j = 0;
			for (int k = ac.length; j < k; j++)
			{
				i = 31 * i + ac[j];
			}
			return Math.abs(i);
		}
	
		private String change(String s)
		{
			byte abyte0[] = s.getBytes();
			char ac[] = new char[s.length()];
			int i = 0;
			for (int k = abyte0.length; i < k; i++)
			{
				int j = abyte0[i];
				if (j >= 48 && j <= 57) j = ((j - 48) + 5) % 10 + 48;
				else if (j >= 65 && j <= 90) j = ((j - 65) + 13) % 26 + 65;
				else if (j >= 97 && j <= 122) j = ((j - 97) + 13) % 26 + 97;
				ac[i] = (char) j;
			}
			return String.valueOf(ac);
		}
	}

}
