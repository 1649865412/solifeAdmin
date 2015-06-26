<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/content"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${adPositionType.adPositionTypeName}" entityHeadingKey="adPositionTypeDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'positionName');" />
    <c:if test="${adPositionType.adPositionTypeId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'positionName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="adPositionType.*" />
	<form:form method="post" cssClass="mainForm" id="adPositionType" commandName="adPositionType"
			action="${ctxPath}/content/adPositionType.html" onsubmit="return validateAdPositionType(this);">
		<form:hidden path="version" />
		<input type="hidden" name="adPositionTypeId" value="${adPositionType.adPositionTypeId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="positionName" />
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="adPositionType.store" />
			</td>
			<td>
				<spring:bind path="adPositionType.storeId">
					<select name="storeId" id="storeId" classes="Field400">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}" 
							<c:if test="${adPositionType.store.storeId == storeItem.value.storeId}">selected="selected" </c:if>>${storeItem.value.name}</option>
						</c:forEach>
					</select>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
 		<app:input property="height" />
 		<app:input property="width" />
 		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="adPositionType.displayType" />
			</td>
			<td>
				<spring:bind path="adPositionType.displayType">
					<content:adDisplayType viewType="1" defaultValue="${status.value}" areaName="${status.expression}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
 		<app:input property="description" />
 		<tr>
			<td class="FieldLabel" height="26">
				<StoreAdmin:label key="adPositionType.templatePath" />
			</td>
			<td>
				<app:templateSelect templateList="${appConfig.advertisementTemplates}" name="templatePath" classes="Field400" value="${adPositionType.templatePath}"></app:templateSelect>
			</td>
		</tr>
  	</table>
</form:form>

<v:javascript formName="adPositionType" staticJavascript="false" />
<script type="text/javascript">
    document.forms["adPositionType"].elements["positionName"].focus();
</script>
