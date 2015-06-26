<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${orderReturn.orderReturnName}" entityHeadingKey="orderReturnDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${orderReturn.orderReturnId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="orderReturn.*" />
	<form:form method="post" cssClass="mainForm" id="orderReturn" commandName="orderReturn"
			action="${ctxPath}/order/orderReturn.html" onsubmit="return validateOrderReturn(this);">
		<form:hidden path="version" />
		<input type="hidden" name="orderReturnId" value="${orderReturn.orderReturnId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="rmaNo" />
 		<app:input property="salesOrderId" />
 		<app:input property="exchangeOrderId" />
 		<app:input property="receivedBy" />
 		<app:input property="returnType" />
 		<app:input property="status" />
 		<app:input property="isPhysicalReturn" />
 		<app:input property="lessRestockAmount" />
 		<app:input property="note" />
 		<app:userName2 label="common.message.createBy" userId="${orderReturn.createBy}" />
   	</table>
</form:form>
<v:javascript formName="orderReturn" staticJavascript="false" />
<script type="text/javascript">
    document.forms["orderReturn"].elements["rmaNo"].focus();
</script>