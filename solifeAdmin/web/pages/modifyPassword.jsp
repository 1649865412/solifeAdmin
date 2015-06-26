<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><fmt:message key="appUser.password.modify.title"/></title>
</head>
<body>
	<spring:bind path="modifyPasswordModel.*">
	    <c:if test="${not empty status.errorMessages}">
	    <div class="error">	
	        <c:forEach var="error" items="${status.errorMessages}">
	            <img src="<c:url value="/images/iconWarning.gif"/>"
	                alt="<fmt:message key="icon.warning"/>" class="icon" />
	            <c:out value="${error}" escapeXml="false"/><br />
	        </c:forEach>
	    </div>
	    </c:if>
	</spring:bind>
	<form class="mainForm" id="modifyPasswordModel" action="modifyPassword.html" onsubmit="return validateModifyPasswordModel(this);" method="post">
		<content tag="buttons">
			<cartmatic:cartmaticBtn btnType="save" onclick="fnDoSave()" />
			<cartmatic:cartmaticBtn btnType="cancel" onclick="fnDoCancelForm()" />
		</content>	
		<table cellpadding="0" cellspacing="0" align="center" width="100%" class="table-content">
			</tr>
			  <tr>
			    <td class="FieldLabel"><fmt:message key="appUser.oldPassword"/></td>
			    <td >
					<input type="hidden" name="appuserId" value="${modifyPasswordModel.appuserId}"/>
					<input name="oldPassword" id="oldPassword" type="password" size="33" class="Field400"/>
				</td>
			  </tr>
			  <tr>
			    <td class="FieldLabel"><fmt:message key="appUser.newPassword"/></td>
			    <td><input name="newPassword" id="newPassword" type="password" size="33" class="Field400" /><span><fmt:message key="appUser.password.rule"/></span></td>
			  </tr>
			  <tr>
			    <td class="FieldLabel"><fmt:message key="appUser.rePassword"/></td>
			    <td ><input name="confirmPassword" id="confirmPassword" type="password" size="33" class="Field400" /></td>
			  </tr>
		</table>
	</form>
	<v:javascript formName="modifyPasswordModel" staticJavascript="false"/>
</body>
</html>