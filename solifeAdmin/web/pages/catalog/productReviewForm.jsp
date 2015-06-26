<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${productReview.productReviewName}" entityHeadingKey="productReviewDetail.heading" />
<content tag="buttons">
<c:if test="${productReview.productReviewId!=null}">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'productReviewName');" />
</c:if>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="productReview.*" />
<form id="productReview" class="mainForm" action="${ctxPath}/catalog/productReview.html" method="post" onsubmit="return validateProductReview(this);">
<input type="hidden" name="productReviewId" value="${productReview.productReviewId}" />
</form>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="productReviewDetail.heading" />
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.sotreId" />
		</td>
		<td>
			<c:out value="${productReview.store.name}"/>
		</td>
    </tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.subject" />
		</td>
		<td>
			<c:out value="${productReview.subject}"/><input type="hidden" id="productReviewName" value="${productReview.productReviewName}">
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.productId" />
		</td>
		<td>
			${productReview.product.productName}
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.message" ignoreValidation="true"/>
		</td>
		<td>
			<spring:bind path="productReview.message">
				<textarea rows="5" class="Field600" readonly="readonly"><c:out value="${status.value}"/></textarea>
				<span class="fieldError">${status.errorMessage}</span>
			</spring:bind>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			评分:
		</td>
		<td>
			<fmt:message key="productReview.rate" />:${productReview.rate}<fmt:message key="productReview.star" />
			<c:forEach items="${productReview.productRateValues}" var="productRateValue">
				<br />${productRateValue.productRateItem.rateName}:${productRateValue.rateValue}<fmt:message key="productReview.star" />
			</c:forEach>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.givenPoint" />
		</td>
		<td>
			<c:choose>
				<c:when test="${empty productReview.givenTime}">
					<fmt:message key="productReview.message.confirmGivenPoint" var="confirmGivenPointMsg" />
					<form class="mainForm" action="${ctxPath}/catalog/productReview.html" method="post" >
					<select name="givenPoint" id="givenPoint" class="form-inputbox">
						<c:forEach items="${productReviewGivenPoints}" var="productReviewGivenPoint">
							<option value="${productReviewGivenPoint}">${productReviewGivenPoint}</option>
						</c:forEach>
					</select>&nbsp;&nbsp;
					<cartmatic:iconBtn icon="check" textKey="productReview.confirmGivenPoint" onclick="fnDoAction(this, 'save', '${confirmGivenPointMsg}'.replace('{0}',$j('#givenPoint').val()));" />
					<input type="hidden" name="productReviewId" value="${productReview.productReviewId}" />
					</form>
				</c:when>
				<c:otherwise>
					${productReview.givenPoint}
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
    <c:if test="${not empty productReview.grantor.username}">
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.grantor" />
		</td>
		<td>
			${productReview.grantor.username}
		</td>
    </tr>
    </c:if>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.status" ignoreValidation="${productReview.status==1}"/>
		</td>
		<td>
			<fmt:message key="productReview.message.confirmActive" var="confirmActiveMsg" ><fmt:param>${productReview.productReviewName}</fmt:param></fmt:message>
			<c:choose>
				<c:when test="${productReview.status!=1}">
					<form class="mainForm" action="${ctxPath}/catalog/productReview.html" method="post" >
					<fmt:message key="productReview.status_${productReview.status}" />
					&nbsp;&nbsp;<cartmatic:iconBtn icon="check" text="激活当前评论" onclick="fnDoAction(this, 'save', '${confirmActiveMsg}');" />
					<input type="hidden" name="productReviewId" value="${productReview.productReviewId}" />
					<input type="hidden" name="status" value="1" />
					</form>
				</c:when>
				<c:otherwise>
					<fmt:message key="productReview.status_${productReview.status}" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.customerName" />
		</td>
		<td>
			<c:choose>
				<c:when test="${productReview.reviewUser.appuserId==-2}">
					<c:out value="${productReview.customerName}" />(<fmt:message key="productReview.reviewUser.anonymous" />)
				</c:when>
				<c:otherwise>
					${productReview.reviewUser.username}
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.remoteIp" ignoreValidation="true"/>
		</td>
		<td>
			${productReview.remoteIp}
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.createTime" />
		</td>
		<td>
			<fmt:formatDate value="${productReview.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="productReview.updateTime" />
		</td>
		<td>
			<fmt:formatDate value="${productReview.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
		</td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			回复列表
		</th>
	</tr>
	<tr>
		<td id="div_reviewReplyList">
			
		</td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content" id="reviewReplyForm">
	<tr>
		<td class="FieldLabel">
			<fmt:message key="productReview.reply.message" />:
		</td>
		<td>
			<textarea rows="5" name="message" id="message" class="Field400"></textarea>
			<input type="hidden" name="productId" value="${productReview.productId}" />
			<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="fnSaveReply();" />
		</td>
    </tr>
</table>
<v:javascript formName="productReview" staticJavascript="false" />
<script type="text/javascript">
function fnGetReviewReplyList(){
	$j("#div_reviewReplyList").load(__ctxPath+"/catalog/productReview/dialog.html?doAction=listReply&productReviewId=${productReview.productReviewId}");
}
function fnSaveReply(){
    if(validateForm($j("#reviewReplyForm").get(0))){
    	var paramData=$j("#reviewReplyForm :input").serializeArray();
		$j.post(__ctxPath+"/catalog/productReview/dialog.html?doAction=saveReply&reviewId=${productReview.productReviewId}",paramData,function(){fnGetReviewReplyList();$j("#message").val("");});
    }
}

function fnDeleteReply(productReviewId){
    if (!confirm('<fmt:message key="catalog.review.delete" />'))
    {
        return;
    }
    $j.post(__ctxPath+"/catalog/productReview/dialog.html?doAction=deleteReply&productReviewId="+productReviewId,fnGetReviewReplyList);
}

$j(document).ready(function(){
	fnGetReviewReplyList();
});
</script>
