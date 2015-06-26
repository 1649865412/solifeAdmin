<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${systemVersion.systemVersionName}" entityHeadingKey="systemVersionDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="systemVersion.*" />
	<form:form method="post" cssClass="mainForm" id="systemVersion" commandName="systemVersion"
			action="${ctxPath}/system/systemVersion.html" onsubmit="return validateSystemVersion(this);">
		<form:hidden path="version" />
		<input type="hidden" name="systemVersionId" value="${systemVersion.systemVersionId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="sysVersion" />
 		<app:input property="productCode" />
 		<app:input property="domainName" />
 		<app:input property="licenseKey" />
 		<app:formText label="common.message.createTime" value="${systemVersion.createTime}" />
  		<app:formText label="common.message.updateTime" value="${systemVersion.updateTime}" />
   	</table>
</form:form>

<v:javascript formName="systemVersion" staticJavascript="false" />
<script type="text/javascript">
    document.forms["systemVersion"].elements["sysVersion"].focus();
</script>
