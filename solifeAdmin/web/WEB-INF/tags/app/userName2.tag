
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.system.service.AppUserManager"%><%@tag import="com.cartmatic.estore.common.model.system.AppUser"%><%@ 
   taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
   taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin" %><%@ 
   attribute name="userId" %><%@ 
   attribute name="label" required="true" rtexprvalue="true" type="java.lang.String" %>
<%
//输出表格行
AppUserManager appUserManager=(AppUserManager)ContextUtil.getSpringBeanById("appUserManager");
String ctxPath = (String)application.getAttribute("ctxPath");
String userName="";
if (StringUtils.isNotBlank(userId)) {
	if (userId.equals("-2")) {
		userName="ANONYMOUS_USER";
	} else if (userId.equals("-1")) {
		userName="system";
	} else {
		AppUser user = null;
		try {
			user = appUserManager.getById(Integer.valueOf(userId));
		} catch (java.lang.Throwable e) {
			System.err.println("error looking up user with id: "+userId);
		}
		if (user!=null) {
			int userType = user.getUserType().intValue();
			if (userType==0) {//customer
				userName="<span class=\"userName\"><a href=\""+ctxPath+"/customer/customer.html?doAction=edit&customerId="+user.getAppuserId()+"\">"+user.getFullName()+"</a></span>";
			} else if (userType==1) {//admin
				if(userId.equals(com.cartmatic.estore.webapp.util.RequestContext.getCurrentUserId().toString()))
					userName="<span class=\"userName\"><a href=\""+ctxPath+"/editProfile.html\">"+user.getFullName()+"</a></span>";
				else
					userName="<span class=\"userName\"><a href=\""+ctxPath+"/system/editAppAdmin.html?ReturnView=previous&appAdminId="+user.getAppuserId()+"\">"+user.getFullName()+"</a></span>";
			}
		}
	}
}
request.setAttribute("tempUserName",userName);
%>
<tr>
	<td class="FieldLabel">
		<StoreAdmin:label key="${label}" />
	</td>
	<td>
		${requestScope.tempUserName}
	</td>
</tr>

