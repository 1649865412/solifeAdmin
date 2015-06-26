<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${textMessage.textMessageName}" entityHeadingKey="textMessageDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="saveAndNext" onclick="return fnDoSaveAndNext(this);" />
	--%>
    <c:if test="${textMessage.textMessageId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="textMessage.*" />
	<form:form method="post" cssClass="mainForm" id="textMessage" commandName="textMessage"
			action="${ctxPath}/customer/textMessage.html" onsubmit="return validateTextMessage(this);">
		<input type="hidden" name="textMessageId" value="${textMessage.textMessageId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="phone" />
 		<app:input property="email" />
  	</table>
</form:form>

<v:javascript formName="textMessage" staticJavascript="false" />
<script type="text/javascript">
    document.forms["textMessage"].elements["phone"].focus();
</script>
