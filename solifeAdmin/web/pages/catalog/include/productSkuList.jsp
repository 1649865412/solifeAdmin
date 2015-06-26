<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			<fmt:message key="productDetail.skuList" />
			<input type="hidden" id="old_defaultProductSkuId" value="${product.defaultProductSkuId}" />
		</th>
	</tr>
	<tr>
		<td>
			<table cellspacing="0" cellpadding="0">
				<tr>
					<th><fmt:message key="productSku.productSkuCode" /></th>
					<th><fmt:message key="productSku.productSkuOptionValues" /></th>
					<th><fmt:message key="productSku.price" /></th>
					<th><fmt:message key="productSku.isDefault" /></th>
					<th><fmt:message key="productSku.status" /></th>
					<th><cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="dlgProductSkuEditor_show({productId:${product.productId}});" /></th>
				</tr>
				<c:forEach items="${product.productSkus}" var="productSku">
					<tr>
						<td>
							${productSku.productSkuCode}
						</td>
						<td>
							<product:skuOptionNameAndValue productSkuId="${productSku.productSkuId}" var="skuOptionNameAndValues"></product:skuOptionNameAndValue>
							<c:forEach items="${skuOptionNameAndValues}" var="skuOptionNameAndValue" varStatus="varStatus">
								${skuOptionNameAndValue.key.skuOptionName}:${skuOptionNameAndValue.value.skuOptionValueName}<c:if test="${!varStatus.last}">,</c:if>
							</c:forEach>
						</td>
						<td>
							${productSku.price}
							<c:if test="${not empty productSku.salePrice}">(${productSku.salePrice})</c:if>
						</td>
						<td>
							<input type="radio" value="${productSku.productSkuId}" name="defaultProductSkuId" <c:if test="${productSku.productSkuId==product.defaultProductSkuId}">checked="checked"</c:if> onchange="fnSetProductDefaultSku(this.value)" />
						</td>
						<td>
							<fmt:message key="product.status_${productSku.status}" />
						</td>
						<td>
							<cartmatic:iconBtn icon="plus" textKey="common.link.edit" onclick="dlgProductSkuEditor_show({productId:${product.productId},productSkuId:${productSku.productSkuId}});" />
							<%--条码
							<c:if test="${product.status!=0}"><cartmatic:iconBtn icon="tag" textKey="common.link.printBarCode"  onclick="fnPrintBarCode('${productSku.id}');" /></c:if>
							--%>
						</td>
					</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>