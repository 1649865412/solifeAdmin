<%@ include file="/common/taglibs.jsp"%>
<link href="<c:url value='/styles/global.css'/>" rel="stylesheet" type="text/css" />
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="catalog" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table width="3000" border="0" cellpadding="0" cellspacing="0" class="listtable">
	<c:forEach items="${previewCsvData}" var="rowData" varStatus="varStatus">
		<tr>
			<c:forEach items="${rowData}" var="columnData" >
			<c:choose>
				<c:when test="${varStatus.first}">
					<th>${columnData}</th>
				</c:when>
				<c:otherwise>
					<td>${columnData}</td>
				</c:otherwise>
			</c:choose>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
