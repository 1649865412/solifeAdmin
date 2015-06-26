<%@ include file="/common/taglibs.jsp"%>

<app:pageHeading entityName="${tax.taxName}" entityHeadingKey="taxDetail.title" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'taxName');" />
<c:if test="${tax.taxId!=null}">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'taxName');" />
</c:if>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="tax.*" />
<form class="mainForm" method="post" action="${ctxPath}/system/tax.html" id="taxForm" onsubmit="return validateTax(this);">
	<spring:bind path="tax.version">
		<input type="hidden" name="version" value="<c:out value="${status.value}"/>" />
	</spring:bind>
	<input type="hidden" name="taxId" value="${tax.taxId}" />
	<TABLE class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="tax.taxName" />
			</td>
			<td>
				<spring:bind path="tax.taxName">
					<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" class="Field400" title="${status.value}" />
					<span class="fieldError">${status.errorMessage}</span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="tax.taxRegisterNo" />
			</td>
			<td>
				<spring:bind path="tax.taxRegisterNo">
					<input class="Field400" type="text" name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="tax" staticJavascript="false" />
<script type="text/javascript">
	document.forms["taxForm"].elements["taxName"].focus();
</script>