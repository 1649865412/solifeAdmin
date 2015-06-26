<%@ include file="/common/taglibs.jsp"%>
<div class="box-content">
	<div class="top">
		<div class="content-title">
			<fmt:message key="report.summaryreport" />
		</div>
	</div>
	<div class="content">
		<table class="table-list" cellspacing="0" cellpadding="0">
			<tr>
				<th width="50%">
					<fmt:message key="report.title.stattype" />
				</th>
				<th width="50%">
					<fmt:message key="report.title.statresult" />
				</th>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.completeSalesOrderTotalMoney" />
				</td>
				<td>
					&nbsp;<system:CurrencyForRate value="${sumCompletedSoAmt}" />
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.salordercount" />
				</td>
				<td>
					&nbsp;${soCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					|----
					<fmt:message key="report.placedSalesOrderCount" />
				</td>
				<td>
					&nbsp;${placedSoCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					|----
					<fmt:message key="report.inProgressSalesOrderCount" />
				</td>
				<td>
					${processingSoCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					|----
					<fmt:message key="report.completeSalesOrderCount" />
				</td>
				<td>
					&nbsp;${completedSoCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					|----
					<fmt:message key="report.cancelSalesOrderCount" />
				</td>
				<td>
					&nbsp;${cancelledSoCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.registerCustomerCount" />
				</td>
				<td>
					&nbsp;${customerCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.adminCount" />
				</td>
				<td>
					&nbsp;${adminCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.productcount" />
				</td>
				<td>
					&nbsp;${productCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.categorycount" />
				</td>
				<td>
					&nbsp;${categoryCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.manufacturerCount" />
				</td>
				<td>
					&nbsp;${manufacturerCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.promotioncount" />
				</td>
				<td>
					&nbsp;${promotionCount}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.productStockCount" />
				</td>
				<td>
					&nbsp;${sumStockProductQty}
				</td>
			</tr>
			<tr>
				<td class="text-left">
					<fmt:message key="report.sumStockProductAmt" />
				</td>
				<td>
					&nbsp;<system:CurrencyForRate value="${sumStockProductAmt}" />
				</td>
			</tr>
		</table>
	</div>
</div>
