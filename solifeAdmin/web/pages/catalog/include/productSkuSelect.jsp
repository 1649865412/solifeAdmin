<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>

	<select name="productMedia_productSkus">
		<c:forEach items="${product.productSkus}" var="productSku">
			<option value="${productSku.id}" <c:if test="${productMoreImage.productSku.id==productSku.id }">selected</c:if>>
			${productSku.productSkuCode}
			<product:skuOptionNameAndValue productSkuId="${productSku.productSkuId}" var="skuOptionNameAndValues"></product:skuOptionNameAndValue>
			<c:forEach items="${skuOptionNameAndValues}" var="skuOptionNameAndValue" varStatus="varStatus">
				${skuOptionNameAndValue.key.skuOptionName}:${skuOptionNameAndValue.value.skuOptionValueName}<c:if test="${!varStatus.last}">,</c:if>
			</c:forEach>
			</option>
		</c:forEach>
	</select>
