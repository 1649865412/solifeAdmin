<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="selectEntity" %>
<%@ attribute name="orderBy" required="false"%>
<!-- Select is no more necessary. -->
<c:if test="${not empty selectEntity}">
			<input type="hidden" name="SRH@selectEntity" value="${selectEntity}"/>
</c:if>
<!-- TODO, enhancement -->
<c:if test="${not empty orderBy}">
			<input type="hidden" name="SRH@orderBy" value="${orderBy}"/>
</c:if>