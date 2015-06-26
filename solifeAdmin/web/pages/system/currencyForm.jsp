<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${currency.currencyName}" entityHeadingKey="currencyDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'currencyName');" />
    <c:if test="${currency.currencyId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'currencyName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="currency.*" />
	<form:form method="post" cssClass="mainForm" id="currency" commandName="currency"
			action="${ctxPath}/system/currency.html" onsubmit="return validateCurrency(this);">
		<form:hidden path="version" />
		<input type="hidden" name="currencyId" value="${currency.currencyId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="currencyCode" />
 		<app:input property="currencyName" /> 		
 		<app:checkbox property="isDefault" value="1"/>
 		<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="currency.status" />
		</td>
		<td>
			<app:statusCheckbox name="status" value="${currency.status}"/>
		</td>
		</tr>
 		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="currency.exchangeRate" />
			</td>
			<td>
				<input type="text" value="${currency.exchangeRate}" class="Field400" name="exchangeRate" id="exchangeRate" gtbfieldid="28">
				<form:errors path="${property}" cssClass="fieldError" />
			</td>
		</tr>
 		<app:input property="currencySymbol" />
 		<app:input property="sortOrder" />
  	</table>
</form:form>

<v:javascript formName="currency" staticJavascript="false" />
<script type="text/javascript">
    document.forms["currency"].elements["currencyCode"].focus();
</script>
