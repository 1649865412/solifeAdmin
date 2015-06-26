<%@ include file="/common/taglibs.jsp"%>
<div class="list">
	<h2><fmt:message key="dashboard.order.title" /></h2>
	&nbsp;&nbsp;&nbsp;最近7日的订单走势:
	<div id="last7DayOrderCreatedChart"></div>
	<div class="clear"></div>
	&nbsp;&nbsp;&nbsp;各订单状态:
	<fmt:message key="common.message.colon" var="MSG_COLON"/>
	<ul>
		<li>
			<fmt:message key="order.status.10"/>${MSG_COLON}
			<span class="num">${map.countInProgress}</span>
		</li>
		<li>
			<fmt:message key="order.status.20"/>${MSG_COLON}
			<span class="num">${map.countPartiallyShipped}</span>
		</li>
		<li>
			<fmt:message key="order.status.30"/>${MSG_COLON}
			<span class="num">${map.countComplete}</span>
		</li>
		<li>
			<fmt:message key="order.status.40"/>${MSG_COLON}
			<span class="num">${map.countAwaitingReturn}</span>
		</li>
		<li>
			<fmt:message key="order.status.onHold"/>${MSG_COLON}
			<span class="num">${map.countOnHold}</span>
		</li>
		<li>
			<fmt:message key="order.status.80"/>${MSG_COLON}
			<span class="num">${map.countCancelled}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.awaiting.inventory"/>${MSG_COLON}
			<span class="num">${map.countAwaitingInventory}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.inventory.assigned"/>${MSG_COLON}
			<span class="num">${map.countInventoryAssigned}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.awaiting.payment"/>${MSG_COLON}
			<span class="num">${map.countAwaitingPayment}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.picking.available"/>${MSG_COLON}
			<span class="num">${map.countPickingAvailable}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.picking"/>${MSG_COLON}
			<span class="num">${map.countPicking}</span>
		</li>
		<li>
			<fmt:message key="shipment.status.shipped"/>${MSG_COLON}
			<span class="num">${map.countShipped}</span>
		</li>
		<li>
			<fmt:message key="order.status.80"/>${MSG_COLON}
			<span class="num">${map.countShipmentCancelled}</span>
		</li>
	</ul>
</div>