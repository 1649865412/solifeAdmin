
<%@tag import="com.cartmatic.estore.webapp.util.RequestContext"%>
<%@tag import="com.cartmatic.estore.common.model.system.AppUser"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%
	AppUser currentUser=(AppUser)RequestContext.getCurrentUser();	
	boolean isNotAdmin= currentUser ==null || (!currentUser.isContainRole("admin"));
	if(isNotAdmin){
%>
		<jsp:doBody />
<%
	}
%>