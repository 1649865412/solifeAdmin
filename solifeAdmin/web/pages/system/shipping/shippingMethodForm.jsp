<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="shippingMethodDetail.title" /></title>
<app:pageHeading entityName="${shippingMethod.shippingMethodName}" entityHeadingKey="shippingMethodDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'shippingMethodName');" />
	<c:if test="${shippingMethod.shippingMethodId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'shippingMethodName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="shippingMethod.*" />
<form class="mainForm" method="post" target="_parent" action="<c:url value="/system/shippingMethod.html"/>" id="shippingMethod" onsubmit="return validateShippingMethod(this);">
	<spring:bind path="shippingMethod.version">
		<input type="hidden" name="version" value="<c:out value="${status.value}"/>" />
	</spring:bind>
	<spring:bind path="shippingMethod.shippingMethodId">
		<input type="hidden" name="shippingMethodId" value="<c:out value="${status.value}"/>" />
	</spring:bind>
	<TABLE class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shippingMethod.shippingMethodName" />
			</td>
			<td>
				<spring:bind path="shippingMethod.shippingMethodName">
					<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" class="Field400" />
					<span class="fieldError">${status.errorMessage}</span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shippingMethod.carrierId" />
			</td>
			<td>
				<spring:bind path="shippingMethod.carrierId">
					<select name="<c:out value="${status.expression}"/>" class="Field400">
						<c:forEach var="carrier" items="${carrierList}">
							<option value="<c:out value="${carrier.carrierId}"/>"<c:if test="${carrier.carrierId==status.value}"> selected="selected"</c:if>>
								<c:out value="${carrier.carrierName}" />
							</option>
						</c:forEach>
					</select>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>

		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shippingMethod.deliveryTime" />
			</td>
			<td>
				<spring:bind path="shippingMethod.deliveryTime">
					<textarea name="${status.expression}" id="${status.expression}" class="Field400" rows="5" cols="80">${status.value}</textarea>
					<span class="fieldError">${status.errorMessage}</span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shippingMethod.status" />
			</td>
			<td>
				<spring:bind path="shippingMethod.status">
					<select name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" class="Field400">
						<option value="1"<c:if test="${1==status.value}"> selected="selected"</c:if>>
							<fmt:message key="status.active" />
						</option>
						<option value="2"<c:if test="${2==status.value}"> selected="selected"</c:if>>
							<fmt:message key="status.inactive" />
						</option>
					</select>
					<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="shippingMethod.shippingMethodDetail" />
			</td>
			<td>
				<spring:bind path="shippingMethod.shippingMethodDetail">
					<textarea name="${status.expression}" id="${status.expression}" class="Field400" rows="10" cols="80">${status.value}</textarea>
					<span class="fieldError">${status.errorMessage}</span>
				</spring:bind>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="shippingMethod" staticJavascript="false" />