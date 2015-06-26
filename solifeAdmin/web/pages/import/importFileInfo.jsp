<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><head>
<title>
<c:if test="${not empty param.catalogId}">
<fmt:message key="import.title3.catalog" >
	<fmt:param><fmt:message key="import.importType.${param.importType}"/></fmt:param>
	<fmt:param>${catalog.name}</fmt:param>
	<fmt:param>${param.encoding}</fmt:param>
	<fmt:param>${param.importFile}</fmt:param>
</fmt:message>
</c:if>
<c:if test="${not empty param.storeId}">
<fmt:message key="import.title3.store" >
	<fmt:param><fmt:message key="import.importType.${param.importType}"/></fmt:param>
	<fmt:param>${store.name}</fmt:param>
	<fmt:param>${param.encoding}</fmt:param>
	<fmt:param>${param.importFile}</fmt:param>
</fmt:message>
</c:if>
</title>
</head>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/import.html'" />
	<cartmatic:cartmaticBtn btnType="prev" inputType="button" onclick="location.href='${ctxPath}/system/import.html?doAction=importUpload&importType=${param.importType}&catalogId=${param.catalogId}&storeId=${param.storeId}&importFile=${param.importFile}&originalFileName=${param.originalFileName}';return false;"/>
	<cartmatic:cartmaticBtn btnType="next" onclick="if(validateForm($j('form.mainForm').get(0)))$j('form.mainForm').get(0).submit();"/>
</content>
<form class="mainForm" action="<c:url value="/system/import.html"/>" method="post">
<input type="hidden" name="doAction" value="doImport" />
<input type="hidden" id="importType" name="importType" value="${param.importType}">
<input type="hidden" id="importFile" name="importFile" value="${param.importFile}">
<input type="hidden" id="encoding" name="encoding" value="${param.encoding}">
<input type="hidden" id="originalFileName" name="originalFileName" value="${param.originalFileName}">
<input type="hidden" id="catalogId" name="catalogId" value="${param.catalogId}">
<input type="hidden" id="storeId" name="storeId" value="${param.storeId}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="import.preview" />
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<fmt:message key="import.import.file.rowCount" />
		</td>
		<td>
			${rowCount}
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<fmt:message key="import.product.preview" />:
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
</table>
</form>
<iframe width="100%" height="400"  src="<c:url value="/system/import.html?decorator=selecter&&doAction=previewCsv&importType=${param.importType}&importFile=${param.importFile}&encoding=${param.encoding}&originalFileName=${param.originalFileName}"/>"></iframe>
