<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${coupon.couponName}" entityHeadingKey="couponDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${coupon.couponId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="coupon.*" />
	<form:form method="post" cssClass="mainForm" id="coupon" commandName="coupon"
			action="${ctxPath}/sales/coupon.html" onsubmit="return validateCoupon(this);">
		<form:hidden path="version" />
		<input type="hidden" name="couponId" value="${coupon.couponId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="promoRuleId" />
 		<app:input property="couponNo" />
 		<app:input property="isSent" />
 		<app:input property="remainedTimes" />
 		<app:input property="status" />
  	</table>
</form:form>
<v:javascript formName="coupon" staticJavascript="false" />
<script type="text/javascript">
    document.forms["coupon"].elements["promoRuleId"].focus();
</script>
