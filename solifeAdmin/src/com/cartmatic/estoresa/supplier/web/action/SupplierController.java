package com.cartmatic.estoresa.supplier.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.view.AjaxView;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.supplier.service.SupplierManager;
import com.cartmatic.estore.system.service.AppUserManager;
import com.opensymphony.oscache.util.StringUtil;

public class SupplierController extends GenericController<Supplier> {
    private SupplierManager supplierManager = null;
    
    private AppUserManager appUserManager=null;
    
    private CustomerManager customerManager=null;
    
    public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	public void setSupplierManager(SupplierManager inMgr) {
        this.supplierManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Supplier entity) {
		return entity.getSupplierName();
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
		mgr = supplierManager;
	}

	
	@Override
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	@Override
	public ModelAndView multiDelete(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	
	@Override
	protected void onSave(HttpServletRequest request, Supplier entity, BindException errors) 
	{
		//如果编码为空的,就自动用最后一个的id+1代替
		if (StringUtil.isEmpty(entity.getSupplierCode()))
		{
			Supplier lastOne = supplierManager.getLast();
			if (lastOne != null)
				entity.setSupplierCode(""+(lastOne.getSupplierId()+1));
			else 
				entity.setSupplierCode("0");
		}
		
		Supplier tempSupplier=supplierManager.getSupplierByCode(entity.getSupplierCode());
		if(tempSupplier!=null&&(entity.getSupplierId()==null||tempSupplier.getSupplierId().intValue()!=entity.getSupplierId().intValue())){
			String msgKey = "supplier.supplierCode.repeated";
			errors.rejectValue("supplierCode", msgKey);
		}
		//20130314供应商超级管理员可以为空
		Integer adminId=ServletRequestUtils.getIntParameter(request,"adminId", 0);
		if(adminId>0){
			AppUser appUser=appUserManager.getById(adminId);
			if(appUser==null||appUser.getUserType().intValue()!=AppUser.USER_TYPE_CUSTOMER.intValue()){
				String msgKey = "supplier.adminId.required";
				errors.rejectValue("adminId", msgKey);
			}
		}
		
		//默认供应商不能修改rejectValue("supplierCode",x)暂随便指定
		if(entity.getSupplierId()!=null&&entity.getSupplierId().intValue()==-1){
			String msgKey = "supplier.conNotModifyDefaultSupplier";
			errors.rejectValue("supplierCode", msgKey);
		}
		
		String prodDescConvertJson=getProdDescConvertJson(request);
		entity.setProdDescConvert(prodDescConvertJson);
	}

	private String getProdDescConvertJson(HttpServletRequest request) {
		JSONArray prodDescConvertList=new JSONArray();
		String prodDescConvert_regexs[]=request.getParameterValues("prodDescConvert_regex");
		String prodDescConvert_replacements[]=request.getParameterValues("prodDescConvert_replacement");
		if(prodDescConvert_regexs!=null){
			for (int i = 0; i < prodDescConvert_regexs.length; i++) {
				String prodDescConvert_regex=prodDescConvert_regexs[i];
				if(StringUtils.isNotBlank(prodDescConvert_regex)){
					JSONObject prodDescConvert=new JSONObject();
					prodDescConvert.put(prodDescConvert_regex,prodDescConvert_replacements[i]);
					prodDescConvertList.add(prodDescConvert);
				}
			}
		}
		
		//排序
		Collections.sort(prodDescConvertList,new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				JSONObject j1=(JSONObject)o1;
				JSONObject j2=(JSONObject)o2;
				String regex1=(String)j1.keys().next();
				String regex2=(String)j2.keys().next();
				int c=(regex1.length()-regex2.length())*-1;
				if(c==0){
					String replacement1=(String)j1.get(regex1);
					String replacement2=(String)j2.get(regex2);
					return (replacement1.length()-replacement2.length())*-1;
				}
				return c;
			}
		});
		for (Object object : prodDescConvertList) {
			JSONObject obj=(JSONObject)object;
			String regex=(String)obj.keys().next();
			String replacement=(String)obj.get(regex);
			System.out.println(regex+"-->"+replacement);
		}
		
		return prodDescConvertList.toString();
	}

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Supplier supplier=(Supplier) mv.getModel().get(formModelName);
		if(supplier.getSupplierId()!=null){
			Set<Supplier> supplierUserList=supplier.getCustomers();
			request.setAttribute("supplierUserList",supplierUserList);
		}
		JSONArray prodDescConvertList=JSONArray.fromObject(supplier.getProdDescConvert());
		request.setAttribute("prodDescConvertList",prodDescConvertList);
	}
	
	public ModelAndView addSupplierUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		AjaxView ajaxView=new AjaxView(response);
		try {
			String supplierUserId=request.getParameter("supplierUserId");
			String supplierId=request.getParameter("supplierId");
			if(StringUtils.isNotBlank(supplierUserId)&&StringUtils.isNotBlank(supplierId)){
				Supplier supplier=supplierManager.getById(new Integer(supplierId));
				if(supplier.getCustomers()!=null&&supplier.getCustomers().size()>=5){
					
				}else{
					Customer supplierUser=customerManager.getById(new Integer(supplierUserId));
					supplierUser.setSupplier(supplier);
					customerManager.save(supplierUser);
				}
			}
		} catch (Exception e) {
			ajaxView.setStatus(new Short("-500"));
			ajaxView.setMsg(getMessage("system.error.msg"));
			e.printStackTrace();
		}
		return ajaxView;
	}
	
	
	public ModelAndView removeUser(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String supplierUserId=request.getParameter("supplierUserId");
		String supplierId=request.getParameter("supplierId");
		Customer supplierUser=customerManager.getById(new Integer(supplierUserId));
		if(supplierUser!=null){
			//20130314供应商超级管理员可以为空
			if(supplierUser.getSupplier().getAdminId()!=null&&supplierUser.getSupplier().getAdminId().intValue()==supplierUser.getCustomerId()){
				saveMessage(Message.error("supplier.error.can.not.remove.admin", supplierUser.getUsername()));
			}else{
				supplierUser.setSupplier(null);
				customerManager.save(supplierUser);
				saveMessage(Message.info("supplier.removed.manager", supplierUser.getUsername()));
			}
		}
		return getRedirectView("/supplier/supplier.html?doAction=edit&supplierId="+supplierId);
	}
}
