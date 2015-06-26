<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ attribute name="bindPath" required="true" rtexprvalue="true" type="java.lang.String"%>
<spring:bind path="${bindPath}">
	<c:if test="${not empty status.errorMessages}">
		<script type="text/javaScript">
			<c:forEach var="error" items="${status.errorMessages}">
				sysMsg("<c:out value="${error}" escapeXml="false" />",true);
			</c:forEach>
		</script>
	</c:if>
</spring:bind>

