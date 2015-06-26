<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ attribute name="skuOptionValue" type="com.cartmatic.estore.common.model.catalog.SkuOptionValue"%>
<%@ attribute name="mediaPath"%>
<c:choose>
	<c:when test="${skuOptionValue.skuOptionValueType==2}">
		<span title="${skuOptionValue.skuOptionValue}" style="background-color:${skuOptionValue.skuOptionValue};display:block;width:25px;height:25px;margin-top:3px;">&nbsp;</span>
	</c:when>
	<c:when test="${skuOptionValue.skuOptionValueType==3}">
		<cartmatic:img url="${skuOptionValue.skuOptionValue}" mediaType="skuOptionValue" width="30" height="30"></cartmatic:img>
	</c:when>
	<c:otherwise>${skuOptionValue.skuOptionValue}</c:otherwise>
</c:choose>
