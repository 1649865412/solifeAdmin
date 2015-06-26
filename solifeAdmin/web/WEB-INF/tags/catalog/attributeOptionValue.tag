<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="attributeOptionValue" %>
<c:choose>
	<c:when test="${fn:indexOf(attributeOptionValue,'__image__')==0}">
		<c:set var="attributeOptionValue" scope="request">${fn:substringAfter(attributeOptionValue,"__image__")}</c:set>
		<c:set var="attributeOptionValueType" scope="request">2</c:set>
	</c:when>
	<c:when test="${fn:indexOf(attributeOptionValue,'__color__')==0}">
		<c:set var="attributeOptionValue" scope="request">${fn:substringAfter(attributeOptionValue,"__color__")}</c:set>
		<c:set var="attributeOptionValueType" scope="request">1</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="attributeOptionValue" scope="request">${attributeOptionValue}</c:set>
		<c:set var="attributeOptionValueType" scope="request">0</c:set>
	</c:otherwise>
</c:choose>

