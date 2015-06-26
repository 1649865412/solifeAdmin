package com.cartmatic.estoresa.customer.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.cartmatic.estore.common.helper.AttributeUtil;
import com.cartmatic.estore.common.model.attribute.AttributeValue;
import com.cartmatic.estore.common.model.customer.Address;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.order.SalesOrder;
import com.cartmatic.estore.common.service.OrderService;
import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.core.exception.ApplicationException;
import com.cartmatic.estore.core.model.BaseObject;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.customer.service.AddressManager;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.customer.service.MembershipManager;
import com.cartmatic.estore.order.service.SalesOrderManager;
import com.cartmatic.estore.order.vo.OrderFilter;
import com.cartmatic.estore.system.service.AppUserManager;

public class CustomerController extends GenericController<Customer> {
	private SalesOrderManager salesOrderManager=null;
    private CustomerManager customerManager = null;
    private MembershipManager membershipManager = null;
    private OrderService orderService;
    private AppUserManager appUserManager=null;
    private AddressManager addressManager = null;
    
    public void setSalesOrderManager(SalesOrderManager salesOrderManager) {
		this.salesOrderManager = salesOrderManager;
	}

    public void setMembershipManager(MembershipManager avalue)
	{
		this.membershipManager = avalue;
	}

	public void setAddressManager(AddressManager addressManager) {
		this.addressManager = addressManager;
	}

	public void setAppUserManager(AppUserManager appUserManager) {
		this.appUserManager = appUserManager;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(Customer entity) {
		return entity.getAppUserName();
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
		Map<Integer, Map<String, Object>> multiSaveModel = null;
		String custmoerIds[]=request.getParameterValues("custmoerIds");
		if(custmoerIds!=null&&custmoerIds.length>0){
			multiSaveModel = new HashMap<Integer, Map<String, Object>>();
			for (String custmoerId : custmoerIds) {
				String status = request.getParameter("status_" + custmoerId);
				if (status == null || "".equals(status)) {
					status = "0";
				}
				Map<String, Object> entityModel = new HashMap<String, Object>();
				entityModel.put("status", new Short(status));
				multiSaveModel.put(new Integer(custmoerId), entityModel);
			}
		}
		return multiSaveModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = customerManager;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, Customer customer, BindException errors) {
		request.getParameter("status");
		if(customer.getCustomerId()==null){
			if(appUserManager.isCustomerExist(customer.getUsername())){
				Object[] params={customer.getUsername()};
				errors.rejectValue("username","appUser.username.isExist",params,"the user name has exist!");
			}
		}
		if(appUserManager.isEmailExist(customer)){
			errors.rejectValue("email","appUser.email.isExist","the email has exist!");
		}
		if(isEntityNew(request)){
			if(StringUtils.isBlank(customer.getNewPassword())){
				errors.rejectValue("newPassword","customer.newPassword.required");
			}
		}
		//if(StringUtils.isNotBlank(customer.getNewPassword())&&!errors.hasErrors()){
		//	String algorithm = ConfigUtil.getInstance().getPasswordEncryptionAlgorithm();
    	//	String newPassword=StringUtil.encodePassword(customer.getNewPassword(),algorithm);
    	//	customer.setPassword(newPassword);
		//}
	}
	
	@Override
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 取得Form对应的Model
		Customer entity = formBackingObject(request);

		BindException errors = null;
		try {
			ServletRequestDataBinder binder = bindAndValidate(request, entity);
			errors = new BindException(binder.getBindingResult());
			// 传给子类的实现，后者可以继续验证和加入错误到errors
			onSave(request, entity, errors);
			if (!errors.hasErrors()) {// 里面如果出错应该抛出异常
				List<AttributeValue> customerAttributeValueList = AttributeUtil.getInstance().getAttributeFromRequest(request);
				customerManager.saveCustomer(entity, customerAttributeValueList);
				String msgKey = (isEntityNew(request)) ? "common.added": "common.updated";
				saveMessage(Message.info(msgKey, getEntityTypeMessage(), getEntityName(entity)));
			}
		} catch (ApplicationException e) {
			handleApplicationException(errors, e);
		}

		ModelAndView mav;
		if (errors.hasErrors()) {
			mav = showForm(request, errors);
		} else if (successView != null) {
			mav = getModelAndView(successView, errors.getModel());
		} else {
			mav = getRedirectToActionView("edit", ((BaseObject) entity).getId()
					.toString());
		}
		return mav;
	}
	
	
    public ModelAndView viewCustomerOrder(HttpServletRequest request,HttpServletResponse response){
    	SearchCriteria sc =salesOrderManager.getSearchCriteriaBuilder("ordreList4CustomerView").buildSearchCriteria(request, getPageSize());
    	Integer customerId=ServletRequestUtils.getIntParameter(request,"customerId",0);
		sc.addParamValue(customerId);
		sc.lockParamValues();
		List orderList = searchByCriteria(sc);
		Customer customer =customerManager.getById(customerId);
		ModelAndView mv=getModelAndView("customer/customerOrderList","orderList", orderList);
		mv.addObject("customer", customer);
		mv.addObject("sc", sc);
		return mv;
    }

	@Override
	protected void onShowForm(HttpServletRequest request, ModelAndView mv) {
		Customer customer = (Customer) mv.getModel().get(formModelName);
		if (customer.getCustomerId()!= null){
			OrderFilter orderFilter = new OrderFilter();
			orderFilter.setCustomerId(customer.getCustomerId());
			List<SalesOrder> orderList = orderService.getSalesOrderByUserId(customer.getCustomerId());
			mv.addObject("orderList", orderList);
			
			List<Address>addressList=addressManager.findByPropertyOrdered("appUser.appuserId", customer.getCustomerId(), "addressId", true);
			mv.addObject("addressList", addressList);
			
			List<Membership> membershipList = membershipManager.getAllActiveMemberships();
			mv.addObject("membershipList", membershipList);
			//List<Wishlist>wishlistList=wishlistManager.findByPropertyOrdered("customer.appuserId", customer.getCustomerId(), "wishListId", true);
			//mv.addObject("wishlistList", wishlistList);
		}
	}
    
}
