<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${appAudit.appAuditName}" entityHeadingKey="appAuditDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${appAudit.appAuditId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="appAudit.*" />
	<form:form method="post" cssClass="mainForm" id="appAudit" commandName="appAudit"
			action="${ctxPath}/system/appAudit.html" onsubmit="return validateAppAudit(this);">
		<input type="hidden" name="appAuditId" value="${appAudit.appAuditId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="procUserId" />
 		<app:input property="procObj" />
 		<app:input property="actionName" />
 		<app:input property="procResult" />
 		<app:input property="requestUrl" />
 		<app:input property="procTime" />
  	</table>
</form:form>

<v:javascript formName="appAudit" staticJavascript="false" />
<script type="text/javascript">
    document.forms["appAudit"].elements["procUserId"].focus();
</script>
