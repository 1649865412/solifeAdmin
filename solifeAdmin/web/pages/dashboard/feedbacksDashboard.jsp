<%@ include file="/common/taglibs.jsp"%>
<div class="list">
    <h2><fmt:message key="dashboard.last.feedbacks.title" /></h2>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
	<tr><th>主题</th><th width="140">创建时间</th></tr>
	<c:forEach items="${lastestFeedbackList}" var="feedback">
	<tr>
		<td><a href="${ctxPath }/customer/feedbackReplys.html?feedbackId=${feedback.feedbackId }&doAction=replysAction&from=list"><c:out value="${feedback.subject}"></c:out></a></td>
		<td>
			<c:choose>
				<c:when test="${feedback.createTime == null}">
					--
				</c:when>
				<c:otherwise>
					<fmt:formatDate value="${feedback.createTime}" pattern="yyyy-MM-dd hh:mm"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</c:forEach>
</table>
</div>