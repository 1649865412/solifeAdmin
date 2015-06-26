<%@ include file="/common/taglibs.jsp"%>
<div class="list">
	<h2><fmt:message key="dashboard.salesPromotion.title" /></h2>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="normal">
	<tr>
		<th width="80%">
			<fmt:message key="promoRule.name"></fmt:message>
		</th>
		<th width="20%">
			<fmt:message key="promoRule.endTime"></fmt:message>
		</th>
	</tr>
	<c:choose>
		<c:when test="${not empty promoRuleList}">
	<c:set var="editURLPath"
		value="/sales/promoRule.html?doAction=edit&from=list&promoRuleId=" scope="page" />
	
	<c:forEach items="${promoRuleList}" var="promoRule">
		<tr>
			<td>
				<a href="${ctxPath}${editURLPath}${promoRule.promoRuleId}">${promoRule.name}</a>
			</td>
			<td>
				<c:choose>
					<c:when test="${promoRule.endTime == null}">
						<fmt:message key="promoRule.endTime.notset" />
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${promoRule.endTime}" pattern="yyyy-MM-dd"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:forEach>
		</c:when>
		<c:otherwise>
			<tr  style="text-align:center;">
				<td colspan="2">
					<fmt:message key="promoRule.none"></fmt:message>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
</table>
</div>