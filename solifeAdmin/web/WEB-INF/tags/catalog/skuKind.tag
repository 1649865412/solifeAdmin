<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="name"%>
<%@ attribute name="value"%>
<%@ attribute name="style"%>
<%@ attribute name="onclick"%>
<c:choose>
	<c:when test="${not empty name}">
		<c:set var="skuKinds" value="${fn:split('1,2,3,4',',')}"></c:set>
		<select class="Field200" name="${name}" id="${name}" <c:if test="${not empty onclick}">onclick="${onclick}"</c:if>>
			<c:forEach items="${skuKinds}" var="skuKind">
				<option value="${skuKind}" <c:if test="${skuKind==value}">selected="selected"</c:if>>
					<fmt:message key="productSku.skuKind_${skuKind}" />
				</option>
			</c:forEach>
		</select>
	</c:when>
	<c:otherwise>
		<fmt:message key="productSku.skuKind_${value}" />
	</c:otherwise>
</c:choose>