<%@ include file="/common/taglibs.jsp"%>
<div class="list">
	<h2><fmt:message key="dashboard.inventory.title" /></h2>
<ul class="col">
	<li>
		<fmt:message key="dashboard.inventory.lack.stock.total" />：
		<span class="num">${inventoryDashboardData.lackStockProductSkuTotal}</span>
		<fmt:message key="dashboard.inventory.lack.stock.activeTotal" />：
		<span class="num">${inventoryDashboardData.lackStockActiveProductSkuTotal}</span>
	</li>
	<li>
		<fmt:message key="dashboard.inventory.low.stock.total" />：
		<span class="num">${inventoryDashboardData.lowStockProductSkuTotal}</span>
		<fmt:message key="dashboard.inventory.low.stock.activeTotal" />：
		<span class="num">${inventoryDashboardData.lowStockActiveProductSkuTotal}</span>
	</li>
	<li>
		<fmt:message key="dashboard.inventory.expectedRestock.total" />：
		<span class="num">${inventoryDashboardData.alreadyExpectedRestockDateInventoryTotal}</span>
	</li>
</ul>
</div>