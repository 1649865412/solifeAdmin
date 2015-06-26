<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<app:pageHeading entityName="${recommendedType.recommendTitle}"
	entityHeadingKey="recommendedTypeDetail.heading" />

<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave();" />
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="recommendedType.*" />

<form:form method="post" cssClass="mainForm" id="recommendedTypeForm"
	name="recommendedTypeForm" commandName="recommendedType" action="${ctxPath}/sales/recommendedType.html">
	<input type="hidden" name="recommendedTypeId" id="recommendedTypeId" value="${recommendedType.recommendedTypeId}" />
	<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="recommendedType.recommendTitle" />
			</td>
			<td>
				<span><input type="text" id="recommendTitle" name="recommendTitle" value="${recommendedType.recommendTitle}" class="Field400" /></span>
				<cartmatic:ui_tip id="recommendTitleDesc">
					<fmt:message key="recommendedType.recommendTitle.tip" />
				</cartmatic:ui_tip>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="checkbox_status">
					<fmt:message key="status.active" />
				</label>
				<input type="checkbox" name="checkbox_status" id="checkbox_status" onclick="ChangeValueOfStatus(this,'status')" <c:if test="${recommendedType.status == 1}">checked</c:if> <c:if test="${recommendedType.recommendedTypeId == null}">checked</c:if> />
				<c:choose>
					<c:when test="${recommendedType.recommendedTypeId == null}">
						<input type="hidden" name="status" id="status" value="1" />
					</c:when>
					<c:otherwise>
						<form:hidden path="status" />
					</c:otherwise>
				</c:choose>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="checkbox_autoEval">
					<fmt:message key="recommendedType.autoEval" />
				</label>
				<c:choose>
					<c:when test="${recommendedType.autoEval != 2}">
						<input type="checkbox" name="checkbox_autoEval" id="checkbox_autoEval" onclick="ChangeValueOfAutoEval(this,'autoEval')" <c:if test="${recommendedType.autoEval == 1}">checked</c:if> <c:if test="${recommendedType.autoEval == null}">checked</c:if> />
					</c:when>
					<c:otherwise>
						:<fmt:message key="recommendedType.autoEval.s2" />
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${recommendedType.recommendedTypeId == null}">
						<input type="hidden" name="autoEval" id="autoEval" value="1" />
					</c:when>
					<c:otherwise>
						<form:hidden path="autoEval" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="recommendedType.typeName" />
			</td>
			<td>
				<span><input type="text" id="typeName" name="typeName" value="${recommendedType.typeName}" readonly="true" class="Field400" /></span>
				<cartmatic:ui_tip id="typeNameDesc">
					<fmt:message key="recommendedType.typeName.tip" />
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="recommendedType.ruleCode" />
			</th>
			<td>
				<fmt:message key="recommendedType.ruleCode${recommendedType.ruleCode}" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="recommendedType.templatePath" />
			</th>
			<td>
				<app:templateSelect templateList="${appConfig.saleTemplates}" name="templatePath" classes="Field400" value="${recommendedType.templatePath}"></app:templateSelect>
			</td>
		</tr>
	</table>
			
</form:form>
<v:javascript formName="recommendedType" staticJavascript="false" />

<script type="text/javascript" defer="true">
$j(document).ready(function () {
	
});	

function ChangeValueOfStatus(checkbox,persistInput)
{
    if (checkbox.checked==true)
    {
      	$(persistInput).value = 1;
    }
    else
    {
      	$(persistInput).value = 0;
    }
}

function ChangeValueOfAutoEval(checkbox,persistInput)
{
    if (checkbox.checked==true)
    {
      	$(persistInput).value = 1;
    }
    else
    {
      	$(persistInput).value = 0;
    }
}

function validateAction(){
	 if(!validateRecommendedType()){
    	return false;
   	}
   	   
    return true;
}

function fnDoSave(){
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
	if(validateAction()){
    	if (!confirmMsg || confirm(confirmMsg)) {
	    	document.recommendedTypeForm.action += "?doAction=save";
			document.recommendedTypeForm.submit();
	    }
    }
}
</script>