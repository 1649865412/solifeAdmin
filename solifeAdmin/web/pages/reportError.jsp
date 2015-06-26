<%@ page language="java" isErrorPage="true" import="com.cartmatic.estore.webapp.util.RequestUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/I18n.tld" prefix="i18n"%>
<%!private final static transient org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog("reportError_jsp");%>
<%
	//TODO, a bug report form, and attach the stack trace in a hidden field with the bug report, and remove these code
	String errorUrl = RequestUtil.getErrorUrl(request);
	//acegi will throw NullPointerException when user click logout but session is already timeout.
	if (errorUrl.indexOf("j_acegi_logout") != -1) {
		response.sendRedirect(request.getContextPath());
	}
	logger.error(RequestUtil.getErrorInfoFromRequest(request, logger.isInfoEnabled()));
%>
<title><i18n:msg expr="errorPage.title" />
</title>
<h1>
	<i18n:msg expr="errorPage.heading" key="errorPage.heading" />
</h1>
<hr />
<P>
	<i18n:msg expr="errorPage.message" arg1="${appConfig.bugReportEmail}" />
</P>
<a href="<c:url value="/"/>"><i18n:msg key="menu.home" />&#171;&#171;&#171; </a>
<a href="#" onclick="history.back();return false"><i18n:msg key="common.back" />&#171;&#171;&#171; </a>
 