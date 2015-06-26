<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${regionItem.regionItemName}" entityHeadingKey="regionItemDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${regionItem.regionItemId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="regionItem.*" />
	<form:form method="post" cssClass="mainForm" id="regionItem" commandName="regionItem"
			action="${ctxPath}/system/regionItem.html" onsubmit="return validateRegionItem(this);">
		<form:hidden path="version" />
		<input type="hidden" name="regionItemId" value="${regionItem.regionItemId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="regionId" />
 		<app:input property="itemId" />
  	</table>
</form:form>

<v:javascript formName="regionItem" staticJavascript="false" />
<script type="text/javascript">
    document.forms["regionItem"].elements["regionId"].focus();
</script>
