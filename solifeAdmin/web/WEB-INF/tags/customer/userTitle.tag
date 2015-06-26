<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name"%>
<%@ attribute name="id"%>
<%@ attribute name="value"%>
<select name="${name}" id="${id}" class="Field400">
	<c:forEach var="i" begin="1" end="2" step="1">
		<fmt:message var="temp" scope="page" key="user.title${i}"/>
		<option value="<fmt:message key="user.title${i}"/>" <c:if test="${value==temp}">selected</c:if>><fmt:message key="user.title${i}"/></option>
	</c:forEach>
</select>