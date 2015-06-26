<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="text" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="title" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="textKey" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="titleKey" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="showIf" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="noLinkIf" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="href" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="onclick" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="seperator" required="false" rtexprvalue="true" type="java.lang.String"%>
<c:if test="${empty showIf or showIf==true}">
	<c:choose>
		<c:when test="${empty noLinkIf or noLinkIf eq false}">
			<a href="${href}" <c:if test="${not empty onclick}">onclick="${onclick}"</c:if>	<c:if test="${not empty titleKey}"><fmt:message key="${titleKey}" /></c:if>	<c:if test="${not empty title}">${title}</c:if>	>
				<c:if test="${not empty textKey}"><fmt:message key="${textKey}" /></c:if><c:if test="${not empty text}">${text}</c:if>
			</a>${empty seperator ? '&nbsp;' : seperator}
		</c:when>
		<c:otherwise>
			<c:if test="${not empty textKey}"><fmt:message key="${textKey}" /></c:if><c:if test="${not empty text}">${text}</c:if>${empty seperator ? '&nbsp;' : seperator}
		</c:otherwise>
	</c:choose>
</c:if>
