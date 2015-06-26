<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			<fmt:message key="promoRule.promoType.catalogPromotion"></fmt:message>
		</th>
	</tr>
	<tr>
		<td>
			<display:table name="${catalogPromotionResults}" cellspacing="0" cellpadding="0" uid="item" export="false" requestURI="">
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode" style="width:40%">
					${item.productSkuCode}
					<product:skuOptionNameAndValue productSkuId="${item.productSkuId}" var="skuOptionNameAndValues"></product:skuOptionNameAndValue>
					<c:forEach items="${skuOptionNameAndValues}" var="skuOptionNameAndValue" varStatus="varStatus">
						${skuOptionNameAndValue.key.skuOptionName}:${skuOptionNameAndValue.value.skuOptionValueName}<c:if test="${!varStatus.last}">,</c:if>
					</c:forEach>
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.price" style="width:15%">
					${item.price} 
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.discountPrice" style="width:15%">
					${item.discountPrice} 
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.promotion" style="width:40%">
					${item.prule.ruleName}
				</display:column>
			</display:table>
		</td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			<fmt:message key="promoRule.promoType.shoppingcartPromotion"></fmt:message>
		</th>
	</tr>
	<tr>
		<td>
			<display:table name="${shoppingcartPromotionResults}" cellspacing="0" cellpadding="0" uid="item" export="false" requestURI="">
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode" style="width:40%">
					${item.productSkuCode} 
					<product:skuOptionNameAndValue productSkuId="${item.productSkuId}" var="skuOptionNameAndValues"></product:skuOptionNameAndValue>
					<c:forEach items="${skuOptionNameAndValues}" var="skuOptionNameAndValue" varStatus="varStatus">
						${skuOptionNameAndValue.key.skuOptionName}:${skuOptionNameAndValue.value.skuOptionValueName}<c:if test="${!varStatus.last}">,</c:if>
					</c:forEach>
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.price" style="width:15%">
					${item.price} 
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.discountPrice" style="width:15%">
					${item.discountPrice} 
				</display:column>
				<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.promotion" style="width:40%">
					${item.prule.ruleName}
				</display:column>
			</display:table>
			
		</td>
    </tr>
</table>