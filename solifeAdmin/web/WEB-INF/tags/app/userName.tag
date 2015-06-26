<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.system.service.AppUserManager"%><%@tag import="com.cartmatic.estore.common.model.system.AppUser"%><%@ 
   taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
   attribute name="userId" required="true"%><%@ 
   attribute name="noFormat" required="false" rtexprvalue="false" type="java.lang.String" description="any value will disable using formating"%><%@ 
   attribute name="target" required="false"  type="java.lang.String" description="_blank,_parent"%>
<%
AppUserManager appUserManager=(AppUserManager)ContextUtil.getSpringBeanById("appUserManager");
//输出用户名链接
String ctxPath = (String)application.getAttribute("ctxPath");
String userName="";
if (userId!=null&&!userId.equals("")) {
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
			String targetStr="";
			if(StringUtils.isNotBlank(target)){
				targetStr=" target=\""+target+"\"";
			}
			int userType = user.getUserType().intValue();
			if (noFormat==null) {
				if (userType==0) {//customer
					userName="<span class=\"userName\"><a href=\""+ctxPath+"/customer/customer.html?doAction=edit&customerId="+user.getAppuserId()+"\""+targetStr+">"+user.getFullName()+"</a></span>";
				} else if (userType==1) {//admin
					if(userId.equals(com.cartmatic.estore.webapp.util.RequestContext.getCurrentUserId().toString()))
						userName="<span class=\"userName\"><a href=\""+ctxPath+"/editProfile.html\""+targetStr+">"+user.getFullName()+"</a></span>";
					else
						userName="<span class=\"userName\"><a href=\""+ctxPath+"/system/appAdmin.html?doAction=edit&from=list&appAdminId="+user.getAppuserId()+"\""+targetStr+">"+user.getFullName()+"</a></span>";
				}
			} else {
				userName=user.getFullName();
			}
		}
	}
}
out.write(userName);
%>