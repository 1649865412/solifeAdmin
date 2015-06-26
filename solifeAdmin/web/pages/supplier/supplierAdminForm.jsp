<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<td colspan="4" class="title">
			超级管理员信息
		</td>
	</tr>
	<tr>
		<th width="15%">
			<StoreAdmin:label key="customer.username" />
		</th>
		<td width="35%">
			<spring:bind path="supplier.supplierAdmin.username">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
		<th width="15%">
			<StoreAdmin:label key="customer.title" />
		</th>
		<td width="35%">
			<spring:bind path="supplier.supplierAdmin.title">
				<customer:userTitle id="${status.expression}" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="customer.firstname"/>(*):
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.firstname">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
		<th>
			<StoreAdmin:label key="customer.password" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.password">
				<input type="password" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="customer.lastname"/>(*):
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.lastname">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
		<th>
			<StoreAdmin:label key="customer.confirmPassword" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.confirmPassword">
				<input type="password" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<th>
			<StoreAdmin:label key="customer.email" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.email">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
		<th>
			<StoreAdmin:label key="customer.telephone" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.telephone">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<th>
			<StoreAdmin:label key="customer.fax" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.fax">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
		<th>
			<StoreAdmin:label key="customer.zip" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.zip">
				<input type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<th>
			<StoreAdmin:label key="customer.note" />
		</th>
		<td>
			<spring:bind path="supplier.supplierAdmin.note">
				<textarea name="${status.expression}" id="${status.expression}" cols="50" rows="10">${status.value}</textarea>
			</spring:bind>
		</td>
		<th>
			&nbsp;
		</th>
		<td>
			&nbsp;
		</td>
	</tr>
</table>