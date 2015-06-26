<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="product" required="true" rtexprvalue="true" type="com.cartmatic.estore.common.model.catalog.Product"%>
<%@ attribute name="outType" rtexprvalue="true" type="java.lang.Short" description="2为文字，非2为图片"%>
<fmt:message key="product.productKind_${product.productKind}" var="sampleProductKind"/>
<c:choose>
	<c:when test="${outType==2}">
		${sampleProductKind}
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${product.productKind==1}">
				<img src="${ctxPath}/images/product_common.gif" title="${sampleProductKind}"/>
			</c:when>
			<c:when test="${product.productKind==2}">
				<img src="${ctxPath}/images/product_variation.gif" title="${sampleProductKind}"/>
			</c:when>
			<c:when test="${product.productKind==3}">
				<img src="${ctxPath}/images/product_package.gif" title="${sampleProductKind}"/>
			</c:when>
		</c:choose>
	</c:otherwise>
</c:choose>
