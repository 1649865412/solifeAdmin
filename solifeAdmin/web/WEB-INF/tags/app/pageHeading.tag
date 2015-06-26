<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/I18n.tld" prefix="i18n"%>
<%@ attribute name="pageHeadingKey" required="false" rtexprvalue="false" type="java.lang.String"
	description="listing page heading i18n message key, like: systemLanguageList.heading"%>
<%@ attribute name="entityHeadingKey" required="false" rtexprvalue="true" type="java.lang.String"
	description="entity heading i18n message key, like: systemLanguageDetail.heading"%>
<%@ attribute name="entityName" required="false" rtexprvalue="true" type="java.lang.String"
	description="i18n key for this entity instance editing; null indicates this is a new instance. e.g. systemLanguage.languageNameKey"%>
<title>
<c:choose>
	<c:when test="${not empty pageHeadingKey}">
		<fmt:message key="${pageHeadingKey}"/>
	</c:when>
	<c:when test="${not empty entityName}">
		<c:set var="entityHeading"><fmt:message key="${entityHeadingKey}"/></c:set>
		<fmt:message key="common.message.editing"><fmt:param value="${entityHeading}"/><fmt:param value="${entityName}"/></fmt:message>
	</c:when>
	<c:otherwise>
		<c:set var="entityHeading"><fmt:message key="${entityHeadingKey}"/></c:set>
		<fmt:message key="common.message.addNew"><fmt:param value="${entityHeading}"/></fmt:message>
	</c:otherwise>
</c:choose>
</title>
