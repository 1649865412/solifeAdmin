<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name"%>
<%@ attribute name="id"%>
<%@ attribute name="value"%>

<select name="${name}" id="${id}" style="width:240px" class="form-inputbox">
	<option value="-1">  </option>
	<c:forEach var="i" begin="0" end="3" step="1">
		<option value="${i}" <c:if test="${value==i}">selected</c:if> ><fmt:message key="address.type${i}"/></option>
	</c:forEach>
</select>