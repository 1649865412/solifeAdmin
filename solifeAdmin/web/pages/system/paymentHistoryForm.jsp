<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${paymentHistory.paymentHistoryName}" entityHeadingKey="paymentHistoryDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${paymentHistory.paymentHistoryId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="paymentHistory.*" />
	<form:form method="post" cssClass="mainForm" id="paymentHistory" commandName="paymentHistory"
			action="${ctxPath}/system/paymentHistory.html" onsubmit="return validatePaymentHistory(this);">
		<form:hidden path="version" />
		<input type="hidden" name="paymentHistoryId" value="${paymentHistory.paymentHistoryId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="orderNo" />
 		<app:input property="flag" />
 		<app:input property="flowNo" />
 		<app:input property="amount" />
 		<app:input property="errorMessage" />
 		<app:input property="errorCode" />
 		<app:input property="remoteIp" />
 		<app:input property="receiveData" />
 		<app:input property="paymentMethodId" />
 		<app:input property="isBrowsed" />
 		<app:formText label="common.message.createTime" value="${paymentHistory.createTime}" />
   	</table>
</form:form>

<v:javascript formName="paymentHistory" staticJavascript="false" />
<script type="text/javascript">
    document.forms["paymentHistory"].elements["orderNo"].focus();
</script>
