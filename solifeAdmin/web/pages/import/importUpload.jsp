<%@ include file="/common/taglibs.jsp"%>
<title>
<c:if test="${not empty param.catalogId}">
<fmt:message key="import.title2.catalog" >
	<fmt:param><fmt:message key="import.importType.${param.importType}"/></fmt:param>
	<fmt:param>${catalog.name}</fmt:param>
</fmt:message>
</c:if>
<c:if test="${not empty param.storeId}">
<fmt:message key="import.title2.store" >
	<fmt:param><fmt:message key="import.importType.${param.importType}"/></fmt:param>
	<fmt:param>${store.name}</fmt:param>
</fmt:message>
</c:if>
</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/import.html'" />
	<cartmatic:cartmaticBtn btnType="prev" onclick="location.href='${ctxPath}/system/import.html?doAction=importTarget&importType=${param.importType}&catalogId=${param.catalogId}&storeId=${param.storeId}&importFile=${param.importFile}&originalFileName=${param.originalFileName}';return false;"/>
	<cartmatic:cartmaticBtn btnType="next" onclick="if(validateForm($j('form.mainForm').get(0)))$j('form.mainForm').get(0).submit();"/>
</content>
<form class="mainForm" action="<c:url value="/system/import.html"/>" method="post" onsubmit="return validateForm(this);">
<input type="hidden" id="doAction" name="doAction" value="checkFile">
<input type="hidden" id="importType" name="importType" value="${param.importType}">
<input type="hidden" id="originalFileName" name="originalFileName" value="${param.originalFileName}">
<input type="hidden" id="catalogId" name="catalogId" value="${param.catalogId}">
<input type="hidden" id="storeId" name="storeId" value="${param.storeId}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="import.upload.file" />:
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<fmt:message key="product.import.encoding"/>
		</td>
		<td>
			<select name="encoding">
				<c:forEach items="${encodings}" var="encoding">
					<option value="${encoding}" <c:if test="${param.encoding==encoding}">selected</c:if>>
						${encoding}
					</option>
				</c:forEach>
			</select>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<fmt:message key="product.import.upload.file"/>
		</td>
		<td>
			<div><input type="text" name="importFile" id="importFile" size="50" value="${param.importFile}" class="form-inputbox" readonly="readonly"/></div>
			<span id="importFile_importFile"></span>
			<div>(<fmt:message key="import.upload.file.decs"/>)</div>
		</td>
    </tr>
</table>
</form>
<script type="text/javascript">	
	applyValidate($("importFile"),"required");
	function checkFileName(file){
		var importFile=file.name;
		importFile=importFile.substring(0,importFile.lastIndexOf("."));
		if(importFile.lastIndexOf(" ")==importFile.length-1){
			alert('<fmt:message key="import.upload.file.error"/>');
			return false;
		}else{
			return true;
		}
	}
</script>
<cartmatic:swf_upload btnPlaceHolderId="importFile_importFile" fileInputId="importFile" uploadFileTypes="*.csv" uploadCategory="other" onSelect="checkFileName" fileSizeLimit="20MB"></cartmatic:swf_upload>