<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${orderSettlement.orderSettlementName}" entityHeadingKey="orderSettlementDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${orderSettlement.orderSettlementId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="orderSettlement.*" />
	<form:form method="post" cssClass="mainForm" id="orderSettlement" commandName="orderSettlement"
			action="${ctxPath}/order/orderSettlement.html" onsubmit="return validateOrderSettlement(this);">
		<input type="hidden" name="orderSettlementId" value="${orderSettlement.orderSettlementId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="shipmentNo" />
 		<app:input property="orderId" />
 		<app:input property="orderNo" />
 		<app:input property="carrierName" />
 		<app:input property="trackingNo" />
 		<app:input property="originalTotal" />
 		<app:input property="settlementAmount" />
 		<app:input property="isComplete" />
 		<app:input property="addedBy" />
  	</table>
</form:form>
<v:javascript formName="orderSettlement" staticJavascript="false" />
<script type="text/javascript">
    document.forms["orderSettlement"].elements["shipmentNo"].focus();
</script>