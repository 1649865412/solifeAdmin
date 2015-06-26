<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${membership.membershipName}" entityHeadingKey="membershipDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="membership.*" />
<form id="membership" class="mainForm" action="${ctxPath}/customer/membership.html" method="post" onsubmit="return validateMembership(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="membershipDetail.heading" /><input type="hidden" name="membershipId" value="${membership.membershipId}" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="membership.membershipName" />
			</td>
			<td>
				<spring:bind path="membership.membershipName">
					<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
				</spring:bind>
			</td>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="membership.membershipLevel" />
			</td>
			<td>
				<spring:bind path="membership.membershipLevel">
					<c:choose>
						<c:when test="${membership.membershipLevel<2}">
							${status.value}
						</c:when>
						<c:otherwise>
							<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
						</c:otherwise>
					</c:choose>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="membership.upgradeShopPoint" />
			</td>
			<td>
				<spring:bind path="membership.upgradeShopPoint">
					<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
				</spring:bind>
			</td>
       </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="membership.status" />
			</td>
			<td>
				<spring:bind path="membership.status">
					<select name="status" class="FieldLabel">
						<option value="1" <c:if test="${status.value==1}">selected</c:if>><fmt:message key="membership.status1"/></option>
						<option value="2" <c:if test="${status.value==2}">selected</c:if>><fmt:message key="membership.status2"/></option>
					</select>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="membership.membershipDetail" />
			</td>
			<td>
				<spring:bind path="membership.membershipDetail">
    		       	<textarea name="${status.expression}" id="${status.expression}" 
  	            			class="FieldLabel"  rows="10" cols="80">${status.value}</textarea>
        	    	<span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="membership" staticJavascript="false" />
<script type="text/javascript">
    document.forms["membership"].elements["membershipName"].focus();
</script>
