<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${salesOrderGeoip.salesOrderGeoipName}" entityHeadingKey="salesOrderGeoipDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${salesOrderGeoip.salesOrderGeoipId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="salesOrderGeoip.*" />
	<form:form method="post" cssClass="mainForm" id="salesOrderGeoip" commandName="salesOrderGeoip"
			action="${ctxPath}/order/salesOrderGeoip.html" onsubmit="return validateSalesOrderGeoip(this);">
		<input type="hidden" name="salesOrderGeoipId" value="${salesOrderGeoip.salesOrderGeoipId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="orderNo" />
 		<app:input property="customerIp" />
 		<app:input property="lon" />
 		<app:input property="lat" />
 		<app:input property="actionType" />
 		<app:formText label="common.message.createTime" value="${salesOrderGeoip.createTime}" />
   	</table>
</form:form>
<v:javascript formName="salesOrderGeoip" staticJavascript="false" />
<script type="text/javascript">
    document.forms["salesOrderGeoip"].elements["orderNo"].focus();
</script>