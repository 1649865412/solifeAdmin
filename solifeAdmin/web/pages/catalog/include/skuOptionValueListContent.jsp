<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="dlgSkuOptionValueEditor_show();" />
<table cellspacing="0" cellpadding="0">
	<tr>
		<th><fmt:message key="skuOptionValue.skuOptionValueName" /></th>
		<th><fmt:message key="skuOptionValue.skuOptionValueType" /></th>
		<th><fmt:message key="skuOptionValue.skuOptionValue" /></th>
		<th><StoreAdmin:label key="skuOptionValue.sortOrder" /></th>
		<th><fmt:message key="skuOptionValue.status" /></th>
		<th><fmt:message key="common.action"/></th>
	</tr>
	<c:forEach items="${skuOption.skuOptionValues}" var="skuOptionValue">
		<tr>
			<td>
				${skuOptionValue.skuOptionValueName}
			</td>
			<td>
				<fmt:message key="skuOptionValue.skuOptionValueType_${skuOptionValue.skuOptionValueType}" />
			</td>
			<td>
				<product:skuOptionValue skuOptionValue="${skuOptionValue}" mediaPath="${mediaPath}"></product:skuOptionValue>
			</td>
			<td>
				${skuOptionValue.sortOrder}
			</td>
			<td>
				<fmt:message key="skuOptionValue.status_${skuOptionValue.status}" />
			</td>
			<td>
				<cartmatic:iconBtn icon="pen" textKey="common.link.edit" onclick="dlgSkuOptionValueEditor_show({skuOptionValueId:${skuOptionValue.skuOptionValueId}});" />
			</td>
		</tr>
	</c:forEach>
</table>