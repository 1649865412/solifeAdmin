<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${orderMessage.orderMessageName}" entityHeadingKey="orderMessageDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${orderMessage.orderMessageId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="orderMessage.*" />
	<form:form method="post" cssClass="mainForm" id="orderMessage" commandName="orderMessage"
			action="${ctxPath}/order/orderMessage.html" onsubmit="return validateOrderMessage(this);">
		<input type="hidden" name="orderMessageId" value="${orderMessage.orderMessageId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="subject" />
 		<app:input property="message" />
 		<app:input property="customerId" />
 		<app:input property="salesOrderId" />
 		<app:input property="status" />
 		<app:formText label="common.message.createTime" value="${orderMessage.createTime}" />
  		<app:userName2 label="common.message.createBy" userId="${orderMessage.createBy}" />
   	</table>
</form:form>
<v:javascript formName="orderMessage" staticJavascript="false" />
<script type="text/javascript">
    document.forms["orderMessage"].elements["subject"].focus();
</script>
