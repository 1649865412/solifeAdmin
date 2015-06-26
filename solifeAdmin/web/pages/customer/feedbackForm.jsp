<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${feedback.feedbackName}" entityHeadingKey="feedbackDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
    <c:if test="${feedback.feedbackId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="feedback.*" />
	<form:form method="post" cssClass="mainForm" id="feedback" commandName="feedback"
			action="${ctxPath}/customer/feedback.html" onsubmit="return validateFeedback(this);">
		<form:hidden path="version" />
		<input type="hidden" name="feedbackId" value="${feedback.feedbackId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="productId" />
 		<app:input property="appuserId" />
 		<app:input property="title" />
 		<app:input property="firstName" />
 		<app:input property="lastName" />
 		<app:input property="subject" />
 		<app:input property="content" />
 		<app:input property="status" />
 		<app:input property="replyType" />
 		<app:input property="feedbackType" />
 		<app:input property="priority" />
 		<app:input property="email" />
 		<app:input property="telephone" />
 		<app:input property="fax" />
 		<app:input property="threadId" />
 		<app:formText label="common.message.updateTime" value="${feedback.updateTime}" />
  		<app:formText label="common.message.createTime" value="${feedback.createTime}" />
  		<app:input property="givenShopPointAction" />
  	</table>
</form:form>
<v:javascript formName="feedback" staticJavascript="false" />
<script type="text/javascript">
    document.forms["feedback"].elements["productId"].focus();
</script>