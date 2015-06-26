<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/app"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${systemLanguage.languageNameKey}" entityHeadingKey="systemLanguageDetail.heading" />
<content tag="buttons">
<c:if test="${empty systemLanguage.isDefault or systemLanguage.isDefault == 0}">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'languageNameKey_value');" />
	<c:if test="${systemLanguage.systemLanguageId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'languageNameKey_value');" />
	</c:if>
</c:if>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="${entityClassName}.*" />

			<form:form cssClass="mainForm" method="post" id="${entityClassName}" commandName="${entityClassName}"
				action="${ctxPath}/system/systemLanguage.html" onsubmit="return validateSystemLanguage(this);">
				<form:hidden path="version" />
				<input type="hidden" name="systemLanguageId" value="${systemLanguage.systemLanguageId}" />
				<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
					<tr>
						<th width="10%">
							<StoreAdmin:label key="systemLanguage.languageNameKey" />
						</th>
						<td>
							<spring:bind path="systemLanguage.languageNameKey">
								<input class="Field400" type="text"
									name="<c:out value="${status.expression}"/>"
									id="<c:out value="${status.expression}"/>"
									value="<c:out value="${status.value}"/>" />
								<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
							</spring:bind>
						</td>
					</tr>
					<app:input property="localeCode" />
					<app:input property="sortOrder" />
					<app:checkbox property="status" value="1" />
				</table>
			</form:form>
		
<v:javascript formName="${entityClassName}" staticJavascript="false" />
<script type="text/javascript">
    //document.forms["systemLanguage"].elements["languageNameKey"].focus();
</script>