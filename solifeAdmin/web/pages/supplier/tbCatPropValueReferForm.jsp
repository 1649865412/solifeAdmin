<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${tbCatPropValueRefer.tbCatPropValueReferName}" entityHeadingKey="tbCatPropValueReferDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="saveAndNext" onclick="return fnDoSaveAndNext(this);" />
	--%>
    <c:if test="${tbCatPropValueRefer.tbCatPropValueReferId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="tbCatPropValueRefer.*" />
	<form:form method="post" cssClass="mainForm" id="tbCatPropValueRefer" commandName="tbCatPropValueRefer"
			action="${ctxPath}/supplier/tbCatPropValueRefer.html" onsubmit="return validateTbCatPropValueRefer(this);">
		<input type="hidden" name="tbCatPropValueReferId" value="${tbCatPropValueRefer.tbCatPropValueReferId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="accessoryId" />
 		<app:input property="tbCatPropReferId" />
 		<app:input property="tbCatPropId" />
 		<app:input property="tbCatPropValueId" />
 		<app:input property="tbCatPropValueName" />
  	</table>
</form:form>

<v:javascript formName="tbCatPropValueRefer" staticJavascript="false" />
<script type="text/javascript">
    document.forms["tbCatPropValueRefer"].elements["accessoryId"].focus();
</script>
