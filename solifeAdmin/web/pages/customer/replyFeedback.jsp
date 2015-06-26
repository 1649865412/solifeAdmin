<%@ include file="/common/taglibs.jsp"%>
<v:javascript formName="feedback" staticJavascript="false"/>

<title><fmt:message key="feedbackList.replyTitle"/></title>
<br>
<spring:bind path="feedback.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">	
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

	<form class="mainForm" id="feedback" method="post" action="replyFeedback.html">
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="doSaveAfterValidate_old('Feedback')" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="history.back(-1)" />
</content>
	<input type="hidden" name="oriFbId" value=""/>
	<input type="hidden" name="curFbId" value="${param['curFbId']}"/>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" style="margin-left:10">
	<tr>
		<td class="title">
			<fmt:message key="feedback.threadDetail" />
		</td>
	</tr>
	<tr>
		<th style="text-align:left;padding-left:35px;">
			<strong><fmt:message key="feedback.subject" />:<c:out value="${feedback.subject}" escapeXml="true" /></strong>
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


		<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td class="title" colspan="2"><fmt:message key="feedback.reply.title"/>
				</td> 
			</tr>
			<tr>
		        <th width="15%">
		            <StoreAdmin:label key="feedback.subject"/>
		        </th>
		        <td width="85%">
		        	<spring:bind path="feedback.subject" htmlEscape="true">
		            	<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="Re:${status.value}" />
						<span class="fieldError"><c:out value="${status.errorMessage}"/></span>
		            </spring:bind>
					
					<input type="hidden" name="threadId" value="${feedback.feedbackId}"/>
					
		        </td>
		   
			</tr>
			<tr>
		        <th valign="top">
		            <StoreAdmin:label key="feedback.content"/>
		        </th>
		        <td>
		        	<spring:bind path="feedback.content">
						<textarea id="${status.expression}" name="${status.expression}"></textarea>
		            	<span class="fieldError"><c:out value="${status.errorMessage}"/></span>
		            </spring:bind>
		        </td>
				
		    </tr>
		</table>

</form>
<app:ui_htmlEditor textareaIds="content"/>
<script type="text/javascript">
    //document.forms["feedback"].elements["subject"].focus();
    //tinyMCE.get('content').focus();
</script>