<%@ include file="/common/taglibs.jsp"%>
<TABLE class="table-list" id="items" cellSpacing="0"
	cellPadding="0">
	<THEAD>
		<TR>
			<TH class="data-table-title" width="20%">
				<fmt:message key="salesOrder.orderNo" />
			</TH>
			<TH class="data-table-title" width="30%">
				<fmt:message key="salesOrder.createTime" />
			</TH>
			<TH class="data-table-title" width="30%">
				<fmt:message key="salesOrder.paymentStatus" />
			</TH>
			<TH class="data-table-title" width="20%">
				<fmt:message key="salesOrder.total" />
			</TH>
		</TR>
	</THEAD>
	<TBODY>
		<c:forEach items="${salesOrderList}" var="salesOrder" varStatus="i">
			<TR <c:if test="${i.index%2==1}">class="odd"</c:if>>
				<TD>
					<a
						href="${ctxPath}/order/salesOrderView.html?doAction=viewSalesOrder&orderId=${salesOrder.orderId}">${salesOrder.orderNo}</a>
				</TD>
				<TD>
					<fmt:formatDate value="${salesOrder.createTime}" type="both"
						timeStyle="short" dateStyle="short" />
				</TD>
				<TD>
					<fmt:message
						key="salesOrder.paymentStatus_${salesOrder.paymentStatus}" />
				</TD>
				<TD>
					<system:CurrencyForRate value="${salesOrder.total}" />
				</TD>
			</TR>
		</c:forEach>
		<tr>
			<td colspan="4">
				<div style="text-align: right">
					<a
						href="${ctxPath}/order/salesOrders.html?doAction=getRecentUnHandleOrders"><fmt:message
							key="public.more" />...</a>
				</div>
			</td>
		</tr>
	</TBODY>
</TABLE>
