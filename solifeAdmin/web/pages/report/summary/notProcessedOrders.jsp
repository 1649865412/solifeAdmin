<%@ include file="/common/taglibs.jsp"%>
<div class="box-content">
	<div class="top">
		<div class="content-title">
			<fmt:message key="report.notProcessedOrders" />
		</div>
	</div>
	<div class="content">
		<display:table name="${salesOrderList}" cellspacing="0"
			cellpadding="0" uid="items" class="table-list" export="false"
			requestURI="" length="5">
			<display:column style="width: 25%" titleKey="salesOrder.orderNo"
				media="html">
				&nbsp;<a
					href="${ctxPath}/order/salesOrderView.html?doAction=viewSalesOrder&orderNo=${items[0]}">${items[0]}</a>
			</display:column>
			<display:column style="width: 30%" titleKey="salesOrder.createTime"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
				&nbsp;<fmt:formatDate value="${items[1]}" type="both" timeStyle="short"
					dateStyle="short" />
			</display:column>
			<display:column style="width: 20%" titleKey="report.itemsCount"
				media="html">
		&nbsp;${items[2]}
	</display:column>
			<display:column style="width: 25%" titleKey="salesOrder.total"
				media="html">
				&nbsp;<system:CurrencyForRate value="${items[3]}" />
			</display:column>
		</display:table>
	</div>
</div>
