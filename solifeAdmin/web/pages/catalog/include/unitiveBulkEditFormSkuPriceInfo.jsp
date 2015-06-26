<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="3">
			<fmt:message key="product.sku.tab.priceInfo" />
		</th>
	</tr>
	<%--==================售价=================--%>
	<tr>
		<td>
			<input type="checkbox" id="priceCheck" name="priceCheck" value="1" />
		</td>
		<td>
			<fmt:message key="productSku.price" />
		</td>
		<td>
			<input type="radio" id="price_method_1" name="price_method" value="1" checked="checked">
			<fmt:message key="unitiveBulkEdit.method.direct" />&nbsp;&nbsp;&nbsp;
			<span><input id="price" name="price" />
			</span>
			<br />
			<input type="radio" id="price_method_4" name="price_method" value="4">
			<fmt:message key="unitiveBulkEdit.method.expression" />
			&nbsp;&nbsp;
			<fmt:message key="unitiveBulkEdit.method.expression.oldValue" />
			<select id="price_method_operator" name="price_method_operator">
				<option value="1">
					&#43;
				</option>
				<option value="2">
					&#45;
				</option>
				<option value="3">
					x
				</option>
			</select>
			<span><input id="price_numeric" name="price_numeric" />
			</span>
		</td>
	</tr>
	
	<%--==================特价=================--%>
	<tr>
		<td>
			<input type="checkbox" id="salePriceCheck" name="salePriceCheck"
				value="1" />
		</td>
		<td>
			<fmt:message key="productSku.salePrice" />
		</td>
		<td>
			<input type="radio" id="salePrice_method_1" name="salePrice_method"
				value="1" checked>
			<fmt:message key="unitiveBulkEdit.method.direct" />&nbsp;&nbsp;&nbsp;
			<span><input id="salePrice" name="salePrice" />
			</span>
			<br />
			<input type="radio" id="salePrice_method_4" name="salePrice_method"
				value="4">
			<fmt:message key="unitiveBulkEdit.method.expression" />
			&nbsp;&nbsp;
			<fmt:message key="unitiveBulkEdit.method.expression.oldValue" />
			<select id="salePrice_method_operator"
				name="salePrice_method_operator">
				<option value="1">
					&#43;
				</option>
				<option value="2">
					&#45;
				</option>
				<option value="3">
					x
				</option>
			</select>
			<span><input id="salePrice_numeric" name="salePrice_numeric" />
			</span>
		</td>
	</tr>
	
	<%--==================市场价=================--%>
	<tr>
		<td>
			<input type="checkbox" id="listPriceCheck" name="listPriceCheck"
				value="1" />
		</td>
		<td>
			<fmt:message key="productSku.listPrice" />
		</td>
		<td>
			<input type="radio" id="listPrice_method_1" name="listPrice_method"
				value="1" checked>
			<fmt:message key="unitiveBulkEdit.method.direct" />&nbsp;&nbsp;&nbsp;
			<span><input id="listPrice" name="listPrice" />
			</span>
			<br />
			<input type="radio" id="listPrice_method_4" name="listPrice_method"
				value="4">
			<fmt:message key="unitiveBulkEdit.method.expression" />
			&nbsp;&nbsp;
			<fmt:message key="unitiveBulkEdit.method.expression.oldValue" />
			<select id="listPrice_method_operator"
				name="listPrice_method_operator">
				<option value="1">
					&#43;
				</option>
				<option value="2">
					&#45;
				</option>
				<option value="3">
					x
				</option>
			</select>
			<span><input id="listPrice_numeric" name="listPrice_numeric" />
			</span>
		</td>
	</tr>
	
	<%--==================批发价=================--%>
	<tr>
		<td width="20">
			<input type="checkbox" id="wholeSalePriceCheck" name="wholeSalePriceCheck" value="1" />
		</td>
		<td width="80">
			<fmt:message key="productSku.wholeSalePrice" />
		</td>
		<td>
			<table cellspacing="0" cellpadding="0" id="productSkuWholesalePricesForm">
				<tr>
					<th width="50"><fmt:message key="wholesalePrice.minQuantity" /></th>
					<th width="100"><fmt:message key="wholesalePrice.price" /></th>
					<th><cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="fnAddWholesalePrice();" /></th>
				</tr>
			</table>
		</td>
	</tr>
</table>