<%@tag import="com.cartmatic.estore.common.model.system.AppUser"%>
<%@tag import="com.cartmatic.estore.webapp.util.RequestContext"%>
<%@tag import="com.cartmatic.estore.common.model.system.AppRole"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ attribute name="onclick"%>
<%
	AppUser currentUser=(AppUser)RequestContext.getCurrentUser();
	if(currentUser!=null && (currentUser.isContainRole("admin") || currentUser.isContainRole(AppRole.ORDER_CANCEL_ROLE))){
%>
	<cartmatic:cartmaticBtn btnType="cancelOrder"  onclick="${onclick}"/>
<%
	}
%>