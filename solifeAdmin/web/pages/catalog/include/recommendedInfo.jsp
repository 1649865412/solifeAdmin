<div id="recommendedInfo" style="display:none;min-height: 520px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				<fmt:message key="category.recommendProduct.manage" />
			</th>
		</tr>
		<tr>
			<td>
				<jsp:include flush="true" page="/sales/recommendedTypeForCatalog.html">
		            <jsp:param name="sourceId" value="${product.productId}" />
		            <jsp:param name="sourceType" value="PRODUCT" />
		            <jsp:param name="doAction" value="search" />
		        </jsp:include>
			</td>
		</tr>
	</table>
</div>