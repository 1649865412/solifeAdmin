<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<title><fmt:message key="appAdminDetail.title" />
</title>
<spring:bind path="appAdmin.*">
	<c:if test="${not empty status.errorMessages}">
		<div class="error">
			<c:forEach var="error" items="${status.errorMessages}">
				<img src="<c:url value="/images/iconWarning.gif"/>"
					alt="<fmt:message key="icon.warning"/>" class="icon" />
				<c:out value="${error}" escapeXml="false" />
				<br />
			</c:forEach>
		</div>
	</c:if>
</spring:bind>
<form class="mainForm" method="post" action="${ctxPath}/editProfile.html" id="appAdmin" onsubmit="return validateAppAdmin(this)">
<input type="hidden" name="doAction"/>
<spring:bind path="appAdmin.version">
	<input type="hidden" name="version" value="<c:out value="${status.value}"/>" />
</spring:bind>
<spring:bind path="appAdmin.appuserId">
	<input type="hidden" name="appAdminId" value="<c:out value="${status.value}"/>" />
</spring:bind>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="fnDoSave()" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="fnDoCancelForm()" />
	<cartmatic:cartmaticBtn btnType="myLog" onclick="javascript:location.href='${ctxPath}/system/appAudit.html?COL@s.procUserId@Integer@EQ=${appAdmin.appuserId}';"/>	
</content>
	<TABLE class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.username" />
			</td>
			<td>
				<spring:bind path="appAdmin.username">
					<c:if test="${empty appAdmin.appuserId}">
						<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
						<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
					</c:if>
					<c:if test="${not empty appAdmin.appuserId}">
						<c:out value="${status.value}" />
					</c:if>
				</spring:bind>
				<input class="Field400" maxlength="64" type="hidden" name="password" id="password" value="${appAdmin.password}" />
				<input type="hidden" name="confirmPassword" value="${appAdmin.password}">
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.title" />
			</td>
			<td>
				<spring:bind path="appAdmin.title">
					<customer:userTitle id="title" name="title" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.firstname" />
			</td>
			<td>
				<spring:bind path="appAdmin.firstname">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.lastname" />
			</td>
			<td>
				<spring:bind path="appAdmin.lastname">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>

		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.email" />
			</td>
			<td>
				<spring:bind path="appAdmin.email">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.telephone" />
			</td>
			<td>
				<spring:bind path="appAdmin.telephone">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.fax" />
			</td>
			<td>
				<spring:bind path="appAdmin.fax">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="adminInfo.department" />
			</td>
			<td>
				<spring:bind path="appAdmin.adminInfo.department">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.userPosition" />
			</td>
			<td>
				<spring:bind path="appAdmin.userPosition">
					<input class="Field400" maxlength="64" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="appAdmin" staticJavascript="false" />
<script type="text/javascript">	
	//本页面新增时验证密码
	<c:if test="${empty appAdmin.appuserId}">
		applyValidate($("password"),"required,minlength=6,maxlength=16");
		applyValidate($("confirmPassword"),"required,minlength=6,maxlength=16");
	</c:if>
</script>