<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<form id="customer" class="mainForm" action="${ctxPath}/customer/customer.html" method="post" onsubmit="return validateCustomer(this);">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="customerDetail.heading" /><input type="hidden" name="customerId" value="${customer.customerId}" />
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.username" />
		</td>
		<td>
			<spring:bind path="customer.username">
				<c:choose>
					<c:when test="${empty customer.customerId}">
						<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
						<cartmatic:ui_tip id="userNameRuleTip"><fmt:message key="customer.userName.rule"/></cartmatic:ui_tip>
					</c:when>
					<c:otherwise>
						${status.value}
					</c:otherwise>
				</c:choose>
			</spring:bind>
		</td>
    </tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.title" />
		</td>
		<td>
			<spring:bind path="customer.title">
				<customer:userTitle id="${status.expression}" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.firstname" />
		</td>
		<td>
			<spring:bind path="customer.firstname">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
     </tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.lastname" />
		</td>
		<td>
			<spring:bind path="customer.lastname">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.email" />
		</td>
		<td>
			<spring:bind path="customer.email">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
    </tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.telephone" />
		</td>
		<td>
			<spring:bind path="customer.telephone">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.fax" />
		</td>
		<td>
			<spring:bind path="customer.fax">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
     </tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.customerPosition" />
		</td>
		<td>
			<spring:bind path="customer.customerPosition">
				<input type="text" class="Field400" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
    </tr>
    <tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.membershipId" />
		</td>
		<td>
			<select name="membershipId" id="membershipId">
			<c:forEach items="${membershipList}" var="m">
			<option value="${m.membershipId}" <c:if test="${m.membershipId == customer.membershipId}">selected="selected"</c:if>>${m.membershipName}</option>
			</c:forEach>
			</select>
		</td>
    </tr>
	<c:if test="${not empty customer.store}">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="customer.store" />
			</td>
			<td>
				${customer.store.name}
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.registerTime" />
		</td>
		<td>
			<fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="appUser.lastLoginTime" />
		</td>
		<td>
			<fmt:formatDate value="${customer.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr>
		<td class="FieldLabel"><StoreAdmin:label key="appUser.status" /></td>
		<td>
			<app:statusCheckbox name="status" value="${customer.status}"></app:statusCheckbox>
		</td>
	</tr>
	<c:if test="${not empty customer.registerIpAddress}">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="customer.registerIpAddress" />
			</td>
			<td>
				<a href="http://whois.domaintools.com/${customer.registerIpAddress}" target="_blank">${customer.registerIpAddress}</a>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="FieldLabel">
			<StoreAdmin:label key="customer.note" />
		</td>
		<td>
			<spring:bind path="customer.note">
				<textarea name="${status.expression}" id="${status.expression}" cols="61" rows="10">${status.value}</textarea>
			</spring:bind>
		</td>
    </tr>
	<tr>
		<td class="FieldLabel">
			<c:if test="${customer.isSupplier}">
				<StoreAdmin:label key="customer.supplierId" />
			</c:if>
			&nbsp;
		</td>
		<td>
			<c:if test="${customer.isSupplier}">
				<a href="${ctxPath}/supplier/supplier.html?doAction=edit&supplierId=${customer.supplier.supplierId}">
				${customer.supplier.supplierName}
				</a>
			</c:if>
			&nbsp;
		</td>
	</tr>
</table>
<c:if test="${not empty customer.customerId}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="customer.loginEStore"/>
		</th>
	</tr>
	<c:forEach items="${appConfig.storeMap}" var="storeItem">
		<tr>
			<td class="FieldLabel">
				<a href="<customer:loginStoreUrl storeId="${storeItem.value.storeId}" customerId="${customer.customerId}"></customer:loginStoreUrl>" target="_blank">${storeItem.value.name}&nbsp;[${storeItem.value.code}]</a>
			</td>
			<td>
				&nbsp;
			</td>
           </tr>
	</c:forEach>
</table>
</c:if>
<c:if test="${not empty customerAttrList}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="customerDetail.attrubutes" />
		</th>
	</tr>
	<c:forEach items="${customerAttrList}" var="attribute" varStatus="varStatus">
		<tr>
			<td class="FieldLabel">
				${attribute.attributeName}：
			</td>
			<td>
				<attribute:input isdisplayHelp="true" attribute="${attribute}" entity="${customer}"></attribute:input>
			</td>
           </tr>
	</c:forEach>
</table>
</c:if>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="2">
			<fmt:message key="customerDetail.password" />
		</th>
	</tr>
	<tr>
		<td class="FieldLabel">
			<fmt:message key="customer.password" />：
		</td>
		<td>
			<input type="password" class="Field400" name="newPassword" id="newPassword" />
		</td>
	</tr>
	<tr>
		<td class="FieldLabel">
			<fmt:message key="customer.confirmPassword" />：
		</td>
		<td>
			<input type="password" class="Field400" name="confirmPassword" id="confirmPassword" vequalTo="与密码不一致,请重新输入"/>
		</td>
	</tr>
</table>
</form>
<v:javascript formName="customer" staticJavascript="false" />