<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<title><fmt:message key="feedbackList.replyTitle" />
</title>
<content tag="heading">
<fmt:message key="feedbackList.replyTitle" />
</content>
<content tag="description">
<fmt:message key="feedback.replys.header" />
</content>
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/customer/feedback.html?btnSearch=true'" />
</content>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" style="margin-left:10">
	<tr>
		<td class="title">
			<fmt:message key="feedback.threadDetail" />
		</td>
	</tr>
	<tr>
		<th style="text-align:left;padding-left:35px;">
			<strong><fmt:message key="feedback.subject" />:<c:out value="${feedback.subject}" /></strong>
		</th>
	</tr>
	<tr>
		<td style="padding-left:35px"><c:out value="${feedback.content}" />
		</td>
	</tr>
	<tr>
		<td style="padding-left:35px"><fmt:message key="feedback.username" />:<c:out value="${feedback.appUser.username}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${feedback.createTime}" pattern="yyyy-MM-dd hh:mm"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="feedback.status" />:<fmt:message key="feedback.status${feedback.status}" />
		</td>
	</tr>
</table>
<form class="mainForm" method="post" id="frmFeedback"
	action="${ctxPath}/customer/feedback.html">
	<input type="hidden" name="feedbackId" value="${feedback.feedbackId}" />
	<input type="hidden" name="doAction" value="replysAction" />
		<c:choose>
			<c:when test="${feedback.givenShopPointAction==0 or empty feedback.givenShopPointAction}">
				<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="feedback.donateShopPoint" 
				onclick="location.href='${ctxPath}/customer/editShopPointHistory.html?customerId=${feedback.appuserId}&feedbackId=${feedback.feedbackId }'"/>
				&nbsp;
				<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="feedback.notDonateShopPoint" 
				onclick="$('frmFeedback').doAction.value='closeDonateShopPointAction';$('frmFeedback').submit();"/>
				&nbsp;
			</c:when>
		</c:choose>

		<c:choose>
			<c:when test="${feedback.status==0}">
				<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="feedback.reply" 
				onclick="location.href='${ctxPath}/customer/replyFeedback.html?oriFbId=${feedback.feedbackId}&curFbId=${feedback.feedbackId}'"/>
				&nbsp;
				<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="feedback.close" 
				onclick="location.href='${ctxPath}/customer/feedbackReplys.html?doAction=closeFeedbackAction&feedbackId=${feedback.feedbackId}'"/>
				&nbsp;
			</c:when>
			<c:when test="${feedback.status==1}">
				<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="feedback.reopen" 
				onclick="location.href='${ctxPath}/customer/feedbackReplys.html?doAction=reOpenFeedbackAction&feedbackId=${feedback.feedbackId}'"/>
			</c:when>
		</c:choose>
		
<div class="blank8">&nbsp;</div>	
<table class="table-content" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="title">
			<StoreAdmin:label key="feedback.replyList" />
		</td>
	</tr>
	<c:if test="${fn:length(replyList)==0}">
		<tr>
			<td><fmt:message key="feedback.noreply" /></td>
		</tr>
	</c:if>
	<c:forEach var="reply" items="${replyList}">
		<tr>
			<th style="text-align:left;padding-left:35px;">
				<strong><c:out value="${reply.subject}" escapeXml="true"/></strong>
			</th>
		</tr>
		<tr>
			<td style="padding:0 35px">
				<c:out value="${reply.content}"  escapeXml="false" />
			</td>
		</tr>
		<tr>
			<td style="padding-left:35px">
				<fmt:message key="feedback.username" />:<c:out value="${reply.appUser.username}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${reply.createTime}" pattern="yyyy-MM-dd hh:mm"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<cartmatic:cartmaticBtn btnType="common" commonBtnValueKey="button.small.delete" 
				onclick="deleteFeedback('${reply.feedbackId}','${feedback.feedbackId}');"/>
			</td>
		</tr>
	</c:forEach>
	</table>
	<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript" defer>
    function deleteFeedback(feedbackId1,feedbackId2){
    	if(confirm('<fmt:message key="feedback.item.delete.confirm"/>')){
    		location.href="feedbackReplys.html?doAction=delFeedbackAction&feedbackId="+feedbackId1+"&threadId="+feedbackId2;
    	}
    }
</script>