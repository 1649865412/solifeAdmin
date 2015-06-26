<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${systemConfig.systemConfigName}" entityHeadingKey="systemConfigDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${systemConfig.systemConfigId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="systemConfig.*" />
	<form:form method="post" cssClass="mainForm" id="systemConfig" commandName="systemConfig"
			action="${ctxPath}/system/systemConfig.html" onsubmit="return validateSystemConfig(this);">
		<form:hidden path="version" />
		<input type="hidden" name="systemConfigId" value="${systemConfig.systemConfigId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="configKey" />
 		<app:input property="description" />
 		<app:input property="category" />
 		<app:input property="configType" />
 		<app:input property="configValue" />
 		<app:input property="options" />
 		<app:input property="dataType" />
 		<app:input property="isSystem" />
 		<app:userName2 label="common.message.createBy" userId="${systemConfig.createBy}" />
  		<app:userName2 label="common.message.updateBy" userId="${systemConfig.updateBy}" />
  		<app:formText label="common.message.createTime" value="${systemConfig.createTime}" />
  		<app:formText label="common.message.updateTime" value="${systemConfig.updateTime}" />
   	</table>
</form:form>

<v:javascript formName="systemConfig" staticJavascript="false" />
<script type="text/javascript">
    document.forms["systemConfig"].elements["configKey"].focus();
</script>
