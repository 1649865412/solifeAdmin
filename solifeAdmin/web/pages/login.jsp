<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ page import="com.cartmatic.estore.common.model.system.LoginEntry,com.cartmatic.estore.core.util.CacheUtil,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%
		boolean isCodeEnabled=com.cartmatic.estore.common.helper.ConfigUtil.getInstance().getIsValidationCodeEnabled();
		if(isCodeEnabled){		
			LoginEntry entry=(LoginEntry)CacheUtil.getInstance().getLoginErrorCache(request.getRemoteAddr());
			String error=request.getParameter("error");
			String errorCode=request.getParameter("errorCode");
		
			if(entry==null&&(errorCode!=null||error!=null)){
				entry=new LoginEntry();
				entry.setVisitedTimes(1);
				entry.setIp(request.getRemoteAddr());
				entry.setVisitedDateTime(new Date());
				CacheUtil.getInstance().setLoginErrorCache(request.getRemoteAddr(),entry);
			}else if(errorCode!=null||error!=null){
				entry.setVisitedTimes(entry.getVisitedTimes()+1);
			}
			request.setAttribute("entry",entry);
		}
		request.setAttribute("isCodeEnabled",new Boolean(isCodeEnabled));
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
	<link href="<c:url value='/styles/global.css'/>" rel="stylesheet" type="text/css" />
	<title><fmt:message key="webapp.name"/> | <fmt:message key="login.title"/></title>
	<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/framework/core.js"></script>
	<script type="text/javascript">
    function __validateForm(form) {
    	var errMsg = '';
    	if (form.j_username.value == '')
    	{
    		errMsg = "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.username"/></fmt:param></fmt:message>\r\n";
    	}
    	if (form.j_password.value == '')
    	{
    		errMsg += "<fmt:message key="errors.required"><fmt:param><fmt:message key="label.password"/></fmt:param></fmt:message>\r\n";
    	}
		<c:if test="${isCodeEnabled=='true'}">
    	if (form.validationCode.value == '')
    	{
    		errMsg += "<fmt:message key="errors.required"><fmt:param><fmt:message key="login.input.validationCode"/></fmt:param></fmt:message>";
    	}
    	</c:if>
    	if (errMsg != '')
    	{
    		alert(errMsg);
    		return false;
    	}
    	else
    	{
        	return true;
        } 
    }

	function refreshImage(){
		var image=document.getElementById("imgValidationCode");
		var url="${ctxPath}/jCaptcha.html";
		var xmlHttpReq=null;
		if(window.ActiveXObject){
			xmlHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
		}else if(window.XMLHttpRequest){
			xmlHttpReq = new XMLHttpRequest();
		}
		xmlHttpReq.open("Post", url, true);
		xmlHttpReq.send(null); 
		
		image.src="${ctxPath}/jCaptcha.html?rand="+parseInt(1000*Math.random());
		return false;
	}
	

	function resetForm(){
		var form=$("loginForm");
		if (getCurrentUserName() != null){
			form.j_username.value="";
			form.j_password.value="";
			form.j_username.focus();
		}else{
			form.j_username.value="";
			form.j_password.value="";
			form.j_username.focus();
		}
	}
</script> 
</head>
<body>
<c:set var="maxFailTimes" value="${applicationScope.appConfig.maxLoginFailTimes}" scope="page"/>
<form class="loginForm" method="post" name="loginForm" id="loginForm" action="${ctxPath}/j_security_check"
	onsubmit="return __validateForm(this)">
<div class="login-wrap">
    <div class="login-header"></div>
        <div class="login-content">
            	<p>
                        <input type="text" id="j_username" name="j_username" maxlength="80" class="login-input" tabindex="1" placeholder="帐号"/>
                    </p>
                    <p>
                        <input type="password" name="j_password" id="j_password" maxlength="30" class="login-input"  tabindex="2" placeholder="密码"/>            
                    </p>
                    <div class="clear"></div>
                    <c:if test="${isCodeEnabled=='true'}">
                        <div style="float:left; padding-top:20px;">
                        	<span><fmt:message key="login.input.validationCode"/>:</span>
                            <input type="text" autocomplete="off" name="validationCode" style="width:80px;" maxlength="30" class="login-input" tabindex="3" />
                         </div>
                         <div class="login-img">
                            <a href="javascript:;" onClick="return refreshImage()">
                                <img id="imgValidationCode" src="${ctxPath}/jCaptcha.html" title="<fmt:message key="login.image.changeTips"/>" />
                            </a>
                        </div>
                    </c:if>
                    <div class="clear"></div>
                   
                    <c:if test="${(empty param.errorCode&&param.error != null)||(param.errorCode=='true')||(not empty entry && entry.visitedTimes > maxFailTimes)}">
    				<img src="<c:url value="/images/iconWarning.gif"/>" alt="<fmt:message key="icon.warning"/>" class="icon" />
                </c:if>
                <c:choose>
                    <c:when test="${param.errorCode=='true'}">
                        <fmt:message key="login.error.validationcode"/>
                        <c:remove var="errorFlag" scope="session"/>
                    </c:when>
                    <c:when test="${empty param.errorCode&&param.error != null}">
                        <fmt:message key="errors.password.mismatch" />
                    </c:when>
                    <c:when test="${not empty entry && entry.visitedTimes > maxFailTimes}">
                        <fmt:message key="login.error.loginTimes"/>
                    </c:when>
                </c:choose>
				 <div class="clear"></div>
               <p> <c:if test="${applicationScope.appConfig.isRememberMeEnabled}">
                            <input type="checkbox" name="rememberMe" id="rememberMe" tabindex="4" />
                            <label for="rememberMe"><fmt:message key="login.rememberMe" /></label>		
                     </c:if></p>
                     <p >
                        <input src="${ctxPath}/images/btn_login.gif" type="image">
                     </p>
    </div>
</div>
<div class="login-footer">
	<%@ include file="../decorators/include/footer.jspf"%>
</div>
<script type="text/javascript" defer>
		<%--
		If has dailog timeout then reLogin.
		只要判断是否引用了dynamic.jsp中的__ctxPath变量,就知道是否dailog超时了.
		因为dailog超时会自动转向login.jsp
		--%>
		if (${param.inDialog=='true'} ||(typeof __ctxPath != "undefined"))
		{
			alert("<fmt:message key="system.timeout"/>");
			window.location=__ctxPath+"/";
		}

		resetForm();
	</script>
</form>
<center><a href="http://192.168.1.191">名度OA管理系统</a></center>

</body>
</html>