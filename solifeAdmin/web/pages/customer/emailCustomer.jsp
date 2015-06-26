<%@ include file="/common/taglibs.jsp"%>
<content tag="heading"><title><fmt:message key="customer.email.emailTitle" /></title></content>
	<spring:bind path="customerEmail.*">
		<c:if test="${not empty status.errorMessages}">
			<div class="error"><c:forEach var="error"
				items="${status.errorMessages}">
				<img src="<c:url value="/images/iconWarning.gif"/>"
					alt="<fmt:message key="icon.warning"/>" class="icon" />
				<c:out value="${error}" escapeXml="false" />
				<br />
			</c:forEach></div>
		</c:if>
	</spring:bind>
<form class="mainForm" id="customerEmail" method="post" action="emailCustomer.html" onsubmit="return validateCustomerEmail(this);">
<content tag="buttons">
		<cartmatic:cartmaticBtn btnType="sendEmail" commonBtnValueKey="customer.email.send"  onclick="send()"/>
        <cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);"/>
</content>
			<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                	<th colspan="2"><fmt:message key="customer.email"/></th>
                </tr>
                <tr>
					<td class="FieldLabel"><fmt:message key="customer.email.title" /></td>
					<td>
						<spring:bind path="customerEmail.title">
							<INPUT type="text" id="${status.expression}" class="Field400" name="${status.expression}"
								value="${status.value}" size="45" width="50%">
							<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<td class="FieldLabel"><fmt:message key="customer.email.receivers" /></td>
					<TD><spring:bind path="customerEmail.receivers">
						<input type="hidden" name="appuserId" value="${param.appuserId}"/>
						<INPUT type="text" class="Field400" name="${status.expression}" value="${status.value}" size="45">
						<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
					</spring:bind> </TD>
				</tr>
				<tr>
					<td class="FieldLabel"><fmt:message key="customer.email.content"/></td>
					<TD>
						<textarea id="content" name="content"></textarea>
					</TD>
				</tr>
			</table>		
</form>
<app:ui_htmlEditor textareaIds="content"/>
<v:javascript formName="customerEmail" staticJavascript="false" />
<script type="text/javascript">
<!--
function send(){
	confirmMsg="<i18n:msg key="customer.email.sendConfirm"/>"
	if(confirm(confirmMsg)){
		bCancel=false;
		oForm = $j('form.mainForm').get(0);
		fnDoAction(oForm, "send");		
	}
}
//-->
</script>
