<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${validationSession.validationSessionName}" entityHeadingKey="validationSessionDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${validationSession.validationSessionId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="validationSession.*" />
	<form:form method="post" cssClass="mainForm" id="validationSession" commandName="validationSession"
			action="${ctxPath}/customer/validationSession.html" onsubmit="return validateValidationSession(this);">
		<input type="hidden" name="validationSessionId" value="${validationSession.validationSessionId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="url" />
 		<app:input property="expiredDate" />
 		<app:input property="email" />
 		<app:input property="vsType" />
  	</table>
</form:form>
<v:javascript formName="validationSession" staticJavascript="false" />
<script type="text/javascript">
    document.forms["validationSession"].elements["url"].focus();
</script>
