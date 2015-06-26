<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${carrier.carrierName}" entityHeadingKey="carrierDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'carrierName');" />
    <c:if test="${carrier.carrierId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'carrierName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="carrier.*" />
	<form:form method="post" cssClass="mainForm" id="carrier" commandName="carrier"
			action="${ctxPath}/system/carrier.html" onsubmit="return validateCarrier(this);">
		<form:hidden path="version" />
		<input type="hidden" name="carrierId" value="${carrier.carrierId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="carrierName" />
 		<app:input property="linkman" />
 		<app:input property="telephone" />
 		<app:input property="fax" />
 		<app:input property="email" />
 		<app:input property="zip" />
		<tr>
			<td class="FieldLabel">
				<fmt:message key="carrier.logo" />
				:
			</td>
			<td>
				<spring:bind path="carrier.logo">
					<div style="float: left;">
						<cartmatic:img url="${status.value}" mediaType="other" id="image" height="120" width="120"></cartmatic:img>
					</div>
					<div style="float: left; margin: 20px;">
						<span id="logo_btnPlaceHolderId"></span>
						<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}">
						<br />
						<fmt:message key="carrier.logo.desc" />
					</div>
				</spring:bind>
			</td>
		</tr>
		<app:input property="carrierAddress" />
 		<app:input property="carrierAddress2" />
 		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="carrier.status" />
			</td>
			<td>
				<spring:bind path="carrier.status">
					<select name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" class="Field400">
						<option value="1"<c:if test="${1==status.value}"> selected="selected"</c:if>>
							<fmt:message key="status.active" />
						</option>
						<option value="2"<c:if test="${2==status.value}"> selected="selected"</c:if>>
							<fmt:message key="status.inactive" />
						</option>
					</select>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
  	</table>
</form:form>
<v:javascript formName="carrier" staticJavascript="false" />
<script type="text/javascript">
    document.forms["carrier"].elements["carrierName"].focus();
</script>
<cartmatic:swf_upload btnPlaceHolderId="logo_btnPlaceHolderId" uploadFileTypes="*.jpg" fileInputId="logo" previewImg="image"></cartmatic:swf_upload>