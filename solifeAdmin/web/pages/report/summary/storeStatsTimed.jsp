<%@ include file="/common/taglibs.jsp"%>
<div class="box-content">
	<div class="top">
		<div class="content-title">
			<fmt:message key="report.summaryreportTimed" />
		</div>
	</div>
	<div class="content">
		<table class="table-list" cellspacing="0" cellpadding="0">
			<tr>
				<th width="20%">
					<fmt:message key="report.title.stattype" />
				</th>
				<th>
					<fmt:message key="report.range.today"/>
				</th>
				<th>
					<fmt:message key="report.range.thisweek"/>
				</th>
				<th>
					<fmt:message key="report.range.thismonth"/>
				</th>
				<th>
					<fmt:message key="report.range.thisquarter"/>
				</th>
				<th>
					<fmt:message key="report.range.thisfy"/>
				</th>
				<th>
					<fmt:message key="report.range.alltime"/>
				</th>
			</tr>
			<tr>
				<td>
					<fmt:message key="report.salordercount" />
				</td>
				<td>
					&nbsp;${soCount1}
				</td>
				<td>
					&nbsp;${soCount2}
				</td>
				<td>
					&nbsp;${soCount3}
				</td>
				<td>
					&nbsp;${soCount4}
				</td>
				<td>
					&nbsp;${soCount5}
				</td>
				<td>
					&nbsp;${soCount6}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="report.completeSalesOrderCount" />
				</td>
				<td>
					&nbsp;${soCompleteCount1}
				</td>
				<td>
					&nbsp;${soCompleteCount2}
				</td>
				<td>
					&nbsp;${soCompleteCount3}
				</td>
				<td>
					&nbsp;${soCompleteCount4}
				</td>
				<td>
					&nbsp;${soCompleteCount5}
				</td>
				<td>
					&nbsp;${soCompleteCount6}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="report.registerCustomerCount" />
				</td>
				<td>
					&nbsp;${customerCount1}
				</td>
				<td>
					&nbsp;${customerCount2}
				</td>
				<td>
					&nbsp;${customerCount3}
				</td>
				<td>
					&nbsp;${customerCount4}
				</td>
				<td>
					&nbsp;${customerCount5}
				</td>
				<td>
					&nbsp;${customerCount6}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="report.productcount" />
				</td>
				<td>
					&nbsp;${productCount1}
				</td>
				<td>
					&nbsp;${productCount2}
				</td>
				<td>
					&nbsp;${productCount3}
				</td>
				<td>
					&nbsp;${productCount4}
				</td>
				<td>
					&nbsp;${productCount5}
				</td>
				<td>
					&nbsp;${productCount6}
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="report.categorycount" />
				</td>
				<td>
					&nbsp;${categoryCount1}
				</td>
				<td>
					&nbsp;${categoryCount2}
				</td>
				<td>
					&nbsp;${categoryCount3}
				</td>
				<td>
					&nbsp;${categoryCount4}
				</td>
				<td>
					&nbsp;${categoryCount5}
				</td>
				<td>
					&nbsp;${categoryCount6}
				</td>
			</tr>
		</table>
	</div>
</div>
