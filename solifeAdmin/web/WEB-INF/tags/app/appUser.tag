<%@tag import="com.cartmatic.estore.common.model.system.AppAdmin"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.system.service.AppAdminManager"%>
<%@tag import="org.springframework.security.core.context.SecurityContext,
				org.springframework.security.core.context.SecurityContextHolder,
				org.springframework.security.core.Authentication,
				com.cartmatic.estore.common.model.system.AppUser"%><%@ 
   taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
   taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin" %><%@ 
   attribute name="var" description="获得当前appuser变量"%>
<%
SecurityContext securityContext = SecurityContextHolder.getContext();
if (securityContext != null) {
	Authentication auth = securityContext.getAuthentication();
	if (auth != null) {
		Object principal = auth.getPrincipal();
		if (principal instanceof AppUser) {
			AppUser currentUser=(AppUser) principal;
			AppAdminManager appAdminManager=(AppAdminManager)ContextUtil.getSpringBeanById("appAdminManager");
			AppAdmin appAdmin=appAdminManager.getById(currentUser.getAppuserId());
			request.setAttribute(var,appAdmin);
		}
	} 
}
%>