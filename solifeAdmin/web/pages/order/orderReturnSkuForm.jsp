<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${orderReturnSku.orderReturnSkuName}" entityHeadingKey="orderReturnSkuDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${orderReturnSku.orderReturnSkuId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="orderReturnSku.*" />
	<form:form method="post" cssClass="mainForm" id="orderReturnSku" commandName="orderReturnSku"
			action="${ctxPath}/order/orderReturnSku.html" onsubmit="return validateOrderReturnSku(this);">
		<input type="hidden" name="orderReturnSkuId" value="${orderReturnSku.orderReturnSkuId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="orderReturnId" />
 		<app:input property="orderSkuId" />
 		<app:input property="quantity" />
 		<app:input property="returnAmount" />
 		<app:input property="receivedQuantity" />
 		<app:input property="reasonType" />
  	</table>
</form:form>
<v:javascript formName="orderReturnSku" staticJavascript="false" />
<script type="text/javascript">
    document.forms["orderReturnSku"].elements["orderReturnId"].focus();
</script>
