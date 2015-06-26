<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<title>
<fmt:message key="menu.importData" />
</title>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/import.html'" />
	<cartmatic:cartmaticBtn btnType="next" onclick="if(validateForm($j('form.mainForm').get(0)))$j('form.mainForm').get(0).submit();"/>
</content>
<form class="mainForm" action="<c:url value="/system/import.html?doAction=importTarget"/>" method="post" onsubmit="return validateForm(this)">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="import.select.importType" />
		</th>
	</tr>
	<tr>
		<td colspan="2">
			<c:forEach items="${importTypes}" var="importType" varStatus="varStatus">
				&nbsp;&nbsp;
				<input type="radio" name="importType" id="importType" value="${importType}" <c:if test="${(empty param.importType&&varStatus.count==1)||param.importType==importType}">checked</c:if>/><fmt:message key="import.importType.${importType}"/>
			</c:forEach>
			<input type="hidden" id="importFile" name="importFile" value="${param.importFile}">
			<input type="hidden" id="originalFileName" name="originalFileName" value="${param.originalFileName}">
		</td>
    </tr>
</table>
</form>
<script type="text/javascript" defer>	
	applyValidate($("importType"),"required");
</script>