<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<c:set var="itemCount" value="${fn:length(regionItemList)}"></c:set>
<c:set var="endNum" value="${itemCount+(itemCount%5==0?0:(5-(itemCount%5)))-1}"></c:set>
<table id="divRegionItems" style="border: 0pt none;">
	<c:if test="${itemCount>0}">
	<c:forEach begin="0" end="${endNum}" var="i">
		<c:if test="${i%5==0}"><tr></c:if>
			<td style="padding-right: 8px; border: 0pt none;">
				<c:choose>
					<c:when test="${i<itemCount}">
						<c:set var="regionItem" value="${regionItemList[i]}"></c:set>
						<a href="javascript:;" onclick="fnDelItem(${regionItem.regionItemId},${regionItem.item.regionId});"><img src="${ctxPath}/images/plus.gif" border="0"> </a>${regionItem.item.regionName}
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		<c:if test="${i%5==4}"></tr></c:if>
	</c:forEach>
	</c:if>
</table>