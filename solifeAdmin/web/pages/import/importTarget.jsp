<%@ include file="/common/taglibs.jsp"%>
<title>
<fmt:message key="import.title1" >
	<fmt:param><fmt:message key="import.importType.${param.importType}"/></fmt:param>
</fmt:message>
</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/import.html'" />
	<cartmatic:cartmaticBtn btnType="prev" onclick="location.href='${ctxPath}/system/import.html?doAction=importType&importType=${param.importType}&importFile=${param.importFile}&originalFileName=${param.originalFileName}';return false;"/>
	<cartmatic:cartmaticBtn btnType="next" onclick="if(validateForm($j('form.mainForm').get(0)))$j('form.mainForm').get(0).submit();"/>
</content>
<form class="mainForm" action="<c:url value="/system/import.html"/>" method="post" onsubmit="return validateForm(this);">
<input type="hidden" id="doAction" name="doAction" value="importUpload">
<input type="hidden" id="importType" name="importType" value="${param.importType}">
<input type="hidden" id="originalFileName" name="originalFileName" value="${param.originalFileName}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="import.target" />:
		</th>
	</tr>
	<c:if test="${not empty catalogList}">
	<tr>
		<td class="FieldLabel">
			<fmt:message key="product.import.target.catalog"/>
		</td>
		<td>
			<select name="catalogId">
				<c:forEach items="${catalogList}" var="catalog">
					<option value="${catalog.catalogId}" <c:if test="${param.catalogId==catalog.catalogId}">selected</c:if>>
						${catalog.name}
					</option>
				</c:forEach>
			</select>
		</td>
    </tr>
	</c:if>
	<c:if test="${not empty storeList}">
	<tr>
		<td class="FieldLabel">
			<fmt:message key="product.import.target.store"/>
		</td>
		<td>
			<select name="storeId">
				<c:forEach items="${storeList}" var="store">
					<option value="${store.storeId}" <c:if test="${param.storeId==store.storeId}">selected</c:if>>
						${store.name}
					</option>
				</c:forEach>
			</select>
		</td>
    </tr>
	</c:if>
</table>
</form>
<script type="text/javascript">	
	applyValidate($("storeId"),"required");
	applyValidate($("catalogId"),"required");
</script>
