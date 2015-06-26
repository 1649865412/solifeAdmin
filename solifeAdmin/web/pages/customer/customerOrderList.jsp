<%@ include file="/common/taglibs.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/customer" prefix="customer"%>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<th>
			<fmt:message key="customer.orders.headers">
				<fmt:param value="${customer.username}" />
			</fmt:message>
		</th> 
	</tr>
    <tr>
		<td>
			<display:table name="${orderList}" cellspacing="0" cellpadding="0" uid="salesOrderItem" export="false" requestURI="">
				<display:column style="width:12%" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.orderNo">
	        		<c:choose>
		        		<c:when test="${salesOrderItem.orderStatus eq 80}">
		        			<span style="color: red; text-decoration: line-through;">${salesOrderItem.orderNo}</span>[<fmt:message key="order.status.80"/>]
		        		</c:when>
		        		<c:when test="${salesOrderItem.isCod eq 0}">
		        			<span style="color:blue">${salesOrderItem.orderNo}</span>[<fmt:message key="salesOrder.payFirst"/>]
		        		</c:when>
		        		<c:otherwise>
		        			${salesOrderItem.orderNo}
		        		</c:otherwise>
	        		</c:choose>
        		</display:column>
        		<display:column style="width:12%" sortable="false" headerClass="data-table-title" titleKey="salesOrder.store" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
					${salesOrderItem.store.name}
				</display:column>
			    <display:column style="width:12%" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.customerName">
	        		${salesOrderItem.customerFirstname}<c:if test="${not empty salesOrderItem.customerLastname}">&nbsp;${salesOrderItem.customerLastname}</c:if>
	        	</display:column>
			    <display:column style="width:10%" property="totalAmount" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.totalAmount"/>
	        	<display:column style="width:12%" property="createTime" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.createTime"/>
			    <display:column style="width:12%" sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.orderStatus">
	        		<c:if test="${salesOrderItem.isOnHold eq 1}"><span style="color:red">(<fmt:message key="order.status.onHold"/>)</span></c:if>
	     			<fmt:message key="order.status.${salesOrderItem.orderStatus}"/>
	        	</display:column>
			</display:table>
		</td>
	</tr>
	<c:if test="${not empty customer.appuserId and fn:length(orderList)==20}">
	<tr>
		<td>
			<table width="100%"><tr><td style="text-align:right;"><a href="javascript:;" onclick="fnNewWindow('${ctxPath}/order/salesOrder.html?customerId=${customer.appuserId}')">more...</a></td></tr></table>
		</td>
	</tr>
	</c:if>
</table>
<%@include file="/common/pagingOnlyNew.jsp"%>