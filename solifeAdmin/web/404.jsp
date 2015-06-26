<%@page import="com.cartmatic.estore.webapp.util.RequestUtil"%>
<%@page import="com.cartmatic.estore.common.helper.ConfigUtil;"%>
<%@page language="java" isErrorPage="true" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/I18n.tld" prefix="i18n"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- Tomcat 5.x 对错误页面处理有问题，永远用ISO-8859-1处理（设置也不生效），会出乱码。但用fmt测试过是可以正确显示中文的，fmt可能中间进行了转换或escape。 --%>

<c:set var="ctxPath" value="${applicationScope.ctxPath}" scope="page" />

<%!private final static transient org.apache.commons.logging.Log	logger	= org.apache.commons.logging.LogFactory
																					.getLog("404_jsp");%>
<%
	String errorUrl = RequestUtil.getErrorUrl(request);
	boolean isContent = (errorUrl.endsWith(".html") || errorUrl
			.endsWith(".jsp"));
	logger.warn("Requested url not found: " + errorUrl + " Referrer: "
			+ request.getHeader("REFERER"));
	if (!isContent) {
		response.setStatus(404);
		return;
	}
	String adminEmail = ConfigUtil.getInstance().getBugReportEmail();
	request.setAttribute("adminEmail", adminEmail);
%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<HTML xmlns="http://www.w3.org/1999/xhtml">
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title><i18n:msg key="404.title" /></title>
			<style type="text/css">
.button {
	border: solid #BABABA 1px;
	font-size: 12px;
	color: #000;
	height:20px;
	line-height:20px;
	text-align: center;
	cursor:pointer;
	background:url(${ctxPath}/images/404_button.gif) repeat-x ;
}

.page {
	width: 693px;
	height: 223px;
	margin-top: 20px;
	margin-left: auto;
	margin-right: auto;
	background: center no-repeat url(${ctxPath}/images/error.gif);
}

.content {
	width: 360px;
	margin-top: 60px;
	margin-left: 310px;
	float: left;
	color: #000;
	font-size: 12px;
	letter-spacing: 1px;
	display:inline;
}

.mail {
	color: #2084dc
}

H1 {
	color: #000;
	font-size: 16px;
	text-align: center;
}
.blank24{ font-size:1px; width:100%; height:24px; clear:both;}
.left {
	float: left;
}


.right {
	margin-left: 50px;
	float: left;
}
</style>
		</head>
		<body>
			<div class="page">
				<div class="content">
					<h1>
						<font color="red"> <i18n:msg key="404.title" /> </font>
					</h1>
					<p></p>
					<i18n:msg expr="404.message" arg1="${ctxPath}" arg2="${adminEmail}"/>
                    <div class="blank24"></div>
                    <div class="left">
                        <input type="button" class="button"
                            value="<i18n:msg key="button.return"/>"
                            onclick="history.back(-1);" />
                    </div>
                    <div class="right">
                        <input type="button" class="button"
                            value="<i18n:msg key="button.logout"/>"
                            onclick="document.location.href='${ctxPath}/j_acegi_logout';" />
                    </div>
                    </div>
				</div>
			</div>
		</body>
	</HTML>
