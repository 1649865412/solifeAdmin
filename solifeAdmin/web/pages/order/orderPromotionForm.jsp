<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${orderPromotion.orderPromotionName}" entityHeadingKey="orderPromotionDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${orderPromotion.orderPromotionId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="orderPromotion.*" />
	<form:form method="post" cssClass="mainForm" id="orderPromotion" commandName="orderPromotion"
			action="${ctxPath}/order/orderPromotion.html" onsubmit="return validateOrderPromotion(this);">
		<input type="hidden" name="orderPromotionId" value="${orderPromotion.orderPromotionId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="salesOrderId" />
 		<app:input property="promoRuleId" />
 		<app:input property="couponNo" />
 		<app:input property="promotionName" />
  	</table>
</form:form>

<v:javascript formName="orderPromotion" staticJavascript="false" />
<script type="text/javascript">
    document.forms["orderPromotion"].elements["salesOrderId"].focus();
</script>
