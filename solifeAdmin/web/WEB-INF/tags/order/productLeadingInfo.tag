<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ attribute name="orderSku" required="true" type="com.cartmatic.estore.common.model.order.OrderSku"%>
<c:choose>
	<c:when test="${not empty orderSku.productId}">
		<a href="javascript:fnNewWindow('${ctxPath}/catalog/product/window.html?doAction=edit&productId=${orderSku.productId}',650,1100);">${orderSku.productName}</a>
		<div><fmt:message key="orderSku.productSkuCode"/>:${orderSku.productSkuCode}</div>
		<div>
			<product:orderSkuOption displaySkuOptions="${orderSku.displaySkuOptions}"/>
			<product:showAccessories accessories="${orderSku.accessories}"/>
		</div>
	</c:when>
	<c:otherwise>
		<a href="${ctxPath}/sales/giftCertificate.html?doAction=edit&giftCertificateId=${orderSku.giftCertificate.giftCertificateId}" target="_blank"><fmt:message key="orderSku.giftCert"/></a>
		<div><fmt:message key="giftCertificate.recipient"/>: ${orderSku.giftCertificate.recipient}</div>
		<div><fmt:message key="giftCertificate.recipientEmail"/>: ${orderSku.giftCertificate.recipientEmail}</div>
	</c:otherwise>
</c:choose>