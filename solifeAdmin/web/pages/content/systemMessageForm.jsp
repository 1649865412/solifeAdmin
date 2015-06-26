<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${systemMessage.systemMessageName}" entityHeadingKey="systemMessageDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${systemMessage.systemMessageId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="systemMessage.*" />
	<form:form method="post" cssClass="mainForm" id="systemMessage" commandName="systemMessage"
			action="${ctxPath}/content/systemMessage.html" onsubmit="return validateSystemMessage(this);">
		<input type="hidden" name="systemMessageId" value="${systemMessage.systemMessageId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="messageHtml" />
 		<app:input property="messageType" />
 		<app:input property="status" />
 		<app:input property="appuserId" />
 		<app:formText label="common.message.createTime" value="${systemMessage.createTime}" />
   	</table>
</form:form>

<v:javascript formName="systemMessage" staticJavascript="false" />
<script type="text/javascript">
    document.forms["systemMessage"].elements["messageHtml"].focus();
</script>
