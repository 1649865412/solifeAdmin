<%@ include file="/common/taglibs.jsp"%>
<c:set var="editURLPath" value="/inventory/inventoryAudit.html?doAction=edit&from=list" scope="page" />
<display:table name="${inventoryAuditList}" class="table-list" cellspacing="0" cellpadding="0" uid="inventoryAuditItem" export="false" requestURI="">
	<display:column url="${editURLPath}"
				paramId="inventoryAuditId" paramProperty="inventoryAuditId" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode">
		${inventoryAuditItem.productSku.productSkuCode}
	</display:column>
	<display:column sortable="false"  decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.eventType">
		<fmt:message key="inventoryAudit.eventType_${inventoryAuditItem.eventType}" />
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.quantity" >
		<c:if test="${inventoryAuditItem.eventType==1&&inventoryAuditItem.quantity>0}">+</c:if>${inventoryAuditItem.quantity}
	</display:column>
	<display:column  sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.quantityOnHand" >
		<span title="<fmt:message key="inventoryAudit.title.quantityOnHand" />">${inventoryAuditItem.quantityOnHand}</span>
	</display:column>
	<display:column  sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.allocatedQuantity" >
		<span title="<fmt:message key="inventoryAudit.title.allocatedQuantity" />">${inventoryAuditItem.allocatedQuantity}</span>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.reason">
		${inventoryAuditItem.reason}
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.orderId">
		${inventoryAuditItem.salesOrder.orderNo}
	</display:column>
	<display:column property="eventHandler" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.eventHandler" />
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="inventoryAudit.createTime">
		<fmt:formatDate value="${inventoryAuditItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
	</display:column>
</display:table>