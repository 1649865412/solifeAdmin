<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${taxRate.taxRateName}" entityHeadingKey="taxRateDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${taxRate.taxRateId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="taxRate.*" />
<form:form method="post" cssClass="mainForm" id="taxRate" commandName="taxRate" action="${ctxPath}/system/taxRate.html" onsubmit="return validateTaxRate(this);">
<spring:bind path="taxRate.version">
	<input type="hidden" name="version" value="<c:out value="${status.value}"/>" />
</spring:bind>
<spring:bind path="taxRate.taxRateId">
	<input type="hidden" name="taxRateId" value="<c:out value="${status.value}"/>" />
</spring:bind>
<input type="hidden" name="rateType" value="1" />
<TABLE class="table-content" cellSpacing="0" cellPadding="0"width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="taxRate.productTypeId" />
			</td>
			<td>
				<spring:bind path="taxRate.productTypeId">
					<select name="${status.expression}" id="${status.expression}"  class="Field400" <c:if test="${not empty taxRate.productTypeId}">disabled="true"</c:if> >
						<c:forEach var="productType" items="${productTypeList}">
							<option value="${productType.productTypeId}"
								<c:choose>
									<c:when test="${(empty taxRate.productTypeId) and (empty error)}">selected</c:when>
									<c:when test="${(not empty taxRate.productTypeId) and (productType.productTypeId eq status.value)}">selected</c:when>
									<c:when test="${(not empty error) and (productType.productTypeId eq param.productTypeId)}">selected</c:when>
								</c:choose>
								>
								${productType.productTypeName}
							</option>
						</c:forEach>
					</select>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="taxRate.taxId" />
			</td>
			<td>
				<spring:bind path="taxRate.taxId">
					<select class="Field400" name="taxId" id="taxId">
						<c:forEach var="tax" items="${taxs}">
							<option value="${tax.taxId}" <c:if test="${tax.taxId==status.value}"> selected="selected" </c:if>>
								${tax.taxName}
							</option>
						</c:forEach>
					</select>
					<span class="fieldError"><c:out
							value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="taxRate.regionId" />
			</td>
			<td>
				<spring:bind path="taxRate.regionId">
					<input class="Field400" type="hidden" id="${status.expression}" name="${status.expression}" value="${status.value}" />
					<input class="Field400" type="text" id="regionName" name="regionName" readonly="true" value="<c:if test="${not empty status.value}">${taxRate.region.regionName}</c:if>" />
					<cartmatic:cartmaticBtn btnType="common" inputName="btnSelectRegion" commonBtnValueKey="taxRate.region"/>
					<app:region id="regionSeclector" showSelectorBtnId="btnSelectRegion" regionId="${status.expression}" regionName="regionName"/>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="taxRate.rateValue" />
			</td>
			<td align="left">
				<spring:bind path="taxRate.rateValue">
					<input class="Field400" type="text" name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>%
			</td>
		</tr>
</table>
</form:form>
<v:javascript formName="taxRate" staticJavascript="false" />
<script type="text/javascript">
applyValidate($("productTypeId"), "required");
applyValidate($("taxId"), "required");
applyValidate($("regionName"), "required");
document.forms["taxRate"].elements["taxId"].focus();
</script>