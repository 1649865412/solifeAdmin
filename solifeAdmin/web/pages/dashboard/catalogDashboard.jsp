<%@ include file="/common/taglibs.jsp"%>
<div class="list">
			<h2><fmt:message key="dashboard.catalog.title" /></h2>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="normal">
	<tr>
		<th>
			&nbsp;
		</th>
		<th>
			<fmt:message key="dashboard.catalog.sumTotal" />
		</th>
		<th>
			<fmt:message key="dashboard.catalog.activeTotal" />
		</th>
		<th>
			<fmt:message key="dashboard.catalog.inActiveTotal" />
		</th>
		<th>
			<fmt:message key="dashboard.catalog.draftTotal" />
		</th>
		<th>
			<fmt:message key="dashboard.catalog.awaitingDeleteTotal" />
		</th>
	</tr>
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.product" />:
		</th>
		<td>
			<span class="num">${catalogDashBoardData.productSumTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.activeProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.inActiveProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.draftProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.awaitingDeleteProductTotal}</span>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.commonProduct" />:
		</th>
		<td>
			<span class="num">${catalogDashBoardData.commonProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.activeCommonProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.inActiveCommonProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.draftCommonProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.awaitingCommonDeleteProductTotal}</span>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.variationProduct" />:
		</th>
		<td>
			<span class="num">${catalogDashBoardData.variationProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.activeVariationProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.inActiveVariationProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.draftVariationProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.awaitingVariationDeleteProductTotal}</span>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.packageProduct" />:
		</th>
		<td>
			<span class="num">${catalogDashBoardData.packageProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.activePackageProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.inActivePackageProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.draftPackageProductTotal}</span>
		</td>
		<td>
			<span class="num">${catalogDashBoardData.awaitingPackageDeleteProductTotal}</span>
		</td>
	</tr>
	<tr>
		<th>
			<fmt:message key="dashboard.catalog.product.availabilityRule" />:
		</th>
		<td colspan="5">
			<fmt:message key="dashboard.catalog.product.availabilityRule_1" />:<span class="num">${catalogDashBoardData.inStockProductTotal}</span>
			<fmt:message key="dashboard.catalog.product.availabilityRule_2" />:<span class="num">${catalogDashBoardData.preOrderProductTotal}</span>
			<fmt:message key="dashboard.catalog.product.availabilityRule_3" />:<span class="num">${catalogDashBoardData.backOrderProductTotal}</span>
			<fmt:message key="dashboard.catalog.product.availabilityRule_4" />:<span class="num">${catalogDashBoardData.alwaysSellProductTotal}</span>
			<fmt:message key="dashboard.catalog.product.availabilityRule_5" />:<span class="num">${catalogDashBoardData.notInStockSellProductTotal}</span>
		</td>
	</tr>
</table>
<%--
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="box-module-wrap">
	<tr>
		<td colspan="5">
			产品
		</td>
	</tr>
	<tr>
		<td>
			总数量： ${catalogDashBoardData.productSumTotal}
		</td>
		<td>
			激活数量： ${catalogDashBoardData.activeProductTotal}
		</td>
		<td>
			非激活数量： ${catalogDashBoardData.inActiveProductTotal}
		</td>
		<td>
			草稿状态数量： ${catalogDashBoardData.draftProductTotal}
		</td>
		<td>
			待删除数量： ${catalogDashBoardData.awaitingDeleteProductTotal}
		</td>
	</tr>

	<tr>
		<td colspan="5">
			普通产品
		</td>
	</tr>
	<tr>
		<td>
			总数量： ${catalogDashBoardData.commonProductTotal}
		</td>
		<td>
			激活数量： ${catalogDashBoardData.activeCommonProductTotal}
		</td>
		<td>
			非激活数量： ${catalogDashBoardData.inActiveCommonProductTotal}
		</td>
		<td>
			草稿状态数量： ${catalogDashBoardData.draftCommonProductTotal}
		</td>
		<td>
			待删除数量： ${catalogDashBoardData.awaitingCommonDeleteProductTotal}
		</td>
	</tr>


	<tr>
		<td colspan="5">
			变种产品
		</td>
	</tr>
	<tr>
		<td>
			总数量： ${catalogDashBoardData.variationProductTotal}
		</td>
		<td>
			激活数量： ${catalogDashBoardData.activeVariationProductTotal}
		</td>
		<td>
			非激活数量： ${catalogDashBoardData.inActiveVariationProductTotal}
		</td>
		<td>
			草稿状态数量： ${catalogDashBoardData.draftVariationProductTotal}
		</td>
		<td>
			待删除数量： ${catalogDashBoardData.awaitingVariationDeleteProductTotal}
		</td>
	</tr>

	<tr>
		<td colspan="5">
			产品包产品
		</td>
	</tr>
	<tr>
		<td>
			总数量： ${catalogDashBoardData.packageProductTotal}
		</td>
		<td>
			激活数量： ${catalogDashBoardData.activePackageProductTotal}
		</td>
		<td>
			非激活数量： ${catalogDashBoardData.inActivePackageProductTotal}
		</td>
		<td>
			草稿状态数量： ${catalogDashBoardData.draftPackageProductTotal}
		</td>
		<td>
			待删除数量： ${catalogDashBoardData.awaitingPackageDeleteProductTotal}
		</td>
	</tr>


	<tr>
		<td colspan="5">
			销售规则
		</td>
	</tr>
	<tr>
		<td>
			有库存才可以购买： ${catalogDashBoardData.inStockProductTotal}
		</td>
		<td>
			预订： ${catalogDashBoardData.preOrderProductTotal}
		</td>
		<td>
			缺货销售： ${catalogDashBoardData.backOrderProductTotal}
		</td>
		<td>
			永远可售： ${catalogDashBoardData.alwaysSellProductTotal}
		</td>
		<td>
			&nbsp;
		</td>
	</tr>

	<tr>
		<td colspan="5">
			缺少主图产品数量：${catalogDashBoardData.settingProductImgTotal}
		</td>
	</tr>

	<tr>
		<td colspan="5">
			未处理的产品评论数：${catalogDashBoardData.confirmProductReviewTotal}
		</td>
	</tr>

	<tr>
		<td colspan="5">
			最近10条评论
		</td>
	</tr>
	<c:forEach items="${latestProductReviewList}" var="productReviewItem" varStatus="varStatus">
		<tr>
			<td>
				${varStatus.count}
			</td>
			<td colspan="4">
				<c:out value="${productReviewItem.subject}" />
			</td>
		</tr>
	</c:forEach>
</table>--%>
</div>