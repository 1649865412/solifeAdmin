<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${favorite.favoriteName}" entityHeadingKey="favoriteDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${favorite.favoriteId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="favorite.*" />
	<form:form method="post" cssClass="mainForm" id="favorite" commandName="favorite"
			action="${ctxPath}/customer/favorite.html" onsubmit="return validateFavorite(this);">
		<form:hidden path="version" />
		<input type="hidden" name="favoriteId" value="${favorite.favoriteId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="customerId" />
 		<app:input property="favoriteTitle" />
 		<app:input property="url" />
 		<app:formText label="common.message.createTime" value="${favorite.createTime}" />
   	</table>
</form:form>

<v:javascript formName="favorite" staticJavascript="false" />
<script type="text/javascript">
    document.forms["favorite"].elements["customerId"].focus();
</script>
