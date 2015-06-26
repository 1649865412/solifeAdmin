<%@ include file="/common/taglibs.jsp"%>
<form class="mainForm" id="salesOrderListForm" name="salesOrderListForm" method="post" action="${ctxPath}/order/dialog.html">
	<input type="hidden" name="search" value="${search}" />
	<input type="hidden" name="paymentType" value="${param.paymentType}" />
	<c:set var="editURLPath" value="/order/salesOrder.html?doAction=edit&from=list" scope="page" />
	<c:set var="checkAll">
		<input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" />
	</c:set>
	<display:table name="${salesOrderList}" cellspacing="0" cellpadding="0" uid="salesOrderItem" class="table-list" style="table-layout:fixed;" export="false" requestURI="">
		<display:column media="html" headerClass="w30" class="w30" title="${checkAll}">
			<input type="checkbox" name="multiIds" value="${salesOrderItem.salesOrderId}" class="checkbox" title="${salesOrderItem.salesOrderName}" />
		</display:column>
		<display:column media="html" sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.orderNo">
			<c:choose>
				<c:when test="${salesOrderItem.orderStatus eq 80}">
					<a href="javascript:void%20${salesOrderItem.salesOrderId}" onclick="fnOpenSalesOrderForm(${salesOrderItem.salesOrderId},'${salesOrderItem.orderNo}');" title="<fmt:message key="order.status.80"/>"><span style="color: red; text-decoration: line-through;">${salesOrderItem.orderNo}</span>
					</a>
				</c:when>
				<c:when test="${salesOrderItem.isCod eq 1}">
					<a href="javascript:void%20${salesOrderItem.salesOrderId}" onclick="fnOpenSalesOrderForm(${salesOrderItem.salesOrderId},'${salesOrderItem.orderNo}');">${salesOrderItem.orderNo}</a>
				</c:when>
				<c:when test="${salesOrderItem.isCod eq 2}">
					<a href="javascript:void%20${salesOrderItem.salesOrderId}" onclick="fnOpenSalesOrderForm(${salesOrderItem.salesOrderId},'${salesOrderItem.orderNo}');" title="<fmt:message key="salesOrder.payType_transfer"/>"><span style="color: green">${salesOrderItem.orderNo}</span>
					</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void%20${salesOrderItem.salesOrderId}" onclick="fnOpenSalesOrderForm(${salesOrderItem.salesOrderId},'${salesOrderItem.orderNo}');" title="<fmt:message key="salesOrder.payFirst"/>"><span style="color: blue">${salesOrderItem.orderNo}</span>
					</a>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column sortable="false" headerClass="w150" class="w150" titleKey="salesOrder.store" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			${salesOrderItem.store.name}
		</display:column>
		<display:column media="html" sortable="false" headerClass="wauto" class="wauto" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.customerName">
        	${salesOrderItem.customerFirstname}<c:if test="${not empty salesOrderItem.customerLastname}">&nbsp;${salesOrderItem.customerLastname}</c:if>
		</display:column>
		<display:column media="html" sortable="false" headerClass="wauto" class="wauto" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.payType">
			<c:choose>
				<c:when test="${salesOrderItem.isCod eq 1}">
					<fmt:message key="salesOrder.payType_cod"/>
				</c:when>
				<c:when test="${salesOrderItem.isCod eq 2}">
					<fmt:message key="salesOrder.payType_transfer"/>
				</c:when>
				<c:otherwise>
					<fmt:message key="salesOrder.payType_online"/>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" property="totalAmount" sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.totalAmount" />
		<display:column media="html" sortable="false" headerClass="w200" class="w200" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.createTime">${salesOrderItem.createTime}
        	</display:column>
		<display:column media="html" sortable="false" headerClass="w150" class="w150" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.orderStatus">
			<c:if test="${salesOrderItem.isOnHold eq 1}">
				<span style="color: red">(<fmt:message key="order.status.onHold" />)</span>
			</c:if>
			<c:if test="${salesOrderItem.hasProblem eq 1}">
				<span style="color: red">(<fmt:message key="salesOrder.hasProblem" />)</span>
			</c:if>
			<span class="${salesOrderItem.orderStatus==30 ? 'green' : (salesOrderItem.orderStatus==10 || salesOrderItem.orderStatus==20 ? 'yellow' : '')}"><fmt:message key="order.status.${salesOrderItem.orderStatus}" /></span>
        	|
        	<span class="${salesOrderItem.paymentStatus==30?'green':(salesOrderItem.paymentStatus==20?'yellow':'red')}"><fmt:message key="payment.status.${salesOrderItem.paymentStatus}" /></span>
		</display:column>
		<display:column media="html" sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="order.tab.message">
			<a href="javascript:void%20${salesOrderItem.salesOrderId}" onclick="fnOpenSalesOrderForm(${salesOrderItem.salesOrderId},'${salesOrderItem.orderNo}','tabIndex=4');" title="View messages for this order">${salesOrderItem.sumOfAllMsgs}|New(${salesOrderItem.sumAdminNewMsgs})</a>
		</display:column>
		<%-- Export only as following.--%>
		<display:column sortable="false" headerClass="data-table-title" title="完成时间" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<c:if test="${salesOrderItem.orderStatus == 30}">${salesOrderItem.updateTime}</c:if>
		</display:column>
		<display:column property="orderNo" sortable="false" headerClass="data-table-title" titleKey="salesOrder.orderNo" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" />
		<display:column property="totalAmount" sortable="false" headerClass="data-table-title" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.totalAmount" />
		<display:column property="totalProductCost" sortable="false" headerClass="data-table-title" title="产品成本" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" />
		<display:column property="totalShippingCost" sortable="false" headerClass="data-table-title" title="运费" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" />
		<display:column sortable="false" headerClass="data-table-title" titleKey="orderShipment.trackingNo" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<c:forEach var="shipment" items="${salesOrderItem.orderShipments}" varStatus="i">
					${shipment.trackingNo}<c:if test="${not i.last}">,</c:if>
			</c:forEach>
		</display:column>
		<display:column sortable="false" headerClass="data-table-title" titleKey="salesOrder.customerName" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
		   		${salesOrderItem.customerFirstname}${emptySpace}${salesOrderItem.customerLastname}
		</display:column>
		<display:column property="customerEmail" sortable="false" headerClass="data-table-title" titleKey="salesOrder.customerEmail" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" />
		<display:column sortable="false" headerClass="data-table-title" title="特殊信息" media="csv xml excel pdf" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<c:forEach var="orderAudit" items="${salesOrderItem.orderAudits}">
				<fmt:formatDate value="${orderAudit.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />${orderAudit.addedBy}${emptySpace}${orderAudit.detail}
			</c:forEach>
		</display:column>
		<display:column sortable="false" headerClass="data-table-title" media="csv xml excel pdf" titleKey="salesOrder.paymentStatus" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<fmt:message key="payment.status.${salesOrderItem.paymentStatus}" />
		</display:column>
		<display:column decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" sortable="false" headerClass="data-table-title" media="csv xml excel pdf" titleKey="salesOrder.orderStatus">
			<fmt:message key="order.status.${salesOrderItem.orderStatus}" />
		</display:column>
	</display:table>
	<c:if test="${not empty salesOrderList}">
		<%@include file="/common/pagingNew.jsp"%>
	</c:if>
</form>