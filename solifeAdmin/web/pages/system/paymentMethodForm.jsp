<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${paymentMethod.paymentMethodName}" entityHeadingKey="paymentMethodDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${paymentMethod.paymentMethodId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="paymentMethod.*" />
	<form:form method="post" cssClass="mainForm" id="paymentMethod" commandName="paymentMethod"
			action="${ctxPath}/system/paymentMethod.html" onsubmit="return validatePaymentMethod(this);">
		<form:hidden path="version" />
		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="paymentMethodName" />
 		<app:input property="paymentMethodCode" />
 		<app:input property="paymentMethodDetail" />
 		<app:input property="paymentMethodType" />
 		<app:input property="paymentMethodIcon" />
 		<app:input property="protocol" />
 		<app:input property="processorFile" />
 		<app:input property="configFile" />
 		<app:input property="configData" />
 		<app:input property="testModel" />
 		<app:input property="isCod" />
 		<app:input property="sortOrder" />
 		<app:input property="status" />
  	</table>
</form:form>

<v:javascript formName="paymentMethod" staticJavascript="false" />
<script type="text/javascript">
    document.forms["paymentMethod"].elements["paymentMethodName"].focus();
</script>
