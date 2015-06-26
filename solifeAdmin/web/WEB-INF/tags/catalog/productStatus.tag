<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="name"%>
<%@ attribute name="value"%>
<%@ attribute name="style"%>
<%@ attribute name="onclick"%>
<c:choose>
	<c:when test="${not empty name}">
		<c:set var="productStatus" value="${fn:split('1,2',',')}"></c:set>
		<select class="Field400" name="${name}" id="${name}" <c:if test="${not empty onclick}">onclick="${onclick}"</c:if>>
			<c:forEach items="${productStatus}" var="status">
				<option value="${status}" <c:if test="${status==value}">selected="selected"</c:if>>
					<fmt:message key="status_${status}" />
				</option>
			</c:forEach>
		</select>
	</c:when>
	<c:otherwise>
		<fmt:message key="status_${value}" />
	</c:otherwise>
</c:choose>