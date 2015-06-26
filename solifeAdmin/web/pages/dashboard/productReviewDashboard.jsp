<%@ include file="/common/taglibs.jsp"%>
 <div class="list">
                <h2><fmt:message key="dashboard.last.productReview.title" /></h2>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.productReview.subject" />
		</th>
		<th width="80">
			<fmt:message key="dashboard.catalog.productReview.createTime" />
		</th>
		<th width="40">
			<fmt:message key="dashboard.catalog.productReview.status" />
		</th>
	</tr>
	<c:forEach items="${latestProductReviewList}" var="productReviewItem" varStatus="varStatus">
		<tr>
			<td>
				<A href="${ctxPath}/catalog/productReview.html?doAction=edit&productReviewId=${productReviewItem.productReviewId}" target="_blank"><c:out value="${productReviewItem.subject}" /></A>
			</td>
			<td>
				<fmt:formatDate value="${productReviewItem.createTime}" pattern="yyyy-MM-dd"/>
			</td>
			<td>
				<fmt:message key="productReview.status_${productReviewItem.status}" />
			</td>
		</tr>
	</c:forEach>
</table>
</div>