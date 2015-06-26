
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="java.util.List"%>
<%@tag import="com.cartmatic.estore.customer.service.MembershipManager"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name"%>
<%@ attribute name="membershipId" %>
<%@ attribute name="isAddAllOption"%>
<%
	if(request.getAttribute("membershipList")==null){
		MembershipManager membershipManager=(MembershipManager)ContextUtil.getSpringBeanById("membershipManager");
		List membershipList=membershipManager.getAllActiveMemberships();
		request.setAttribute("membershipList",membershipList);
	}
%>
<c:if test="${empty membershipId}">
	<c:set var="membershipId" value="0"/>
</c:if>
<c:choose>
	<c:when test="${not empty name}">
		<c:set var="m_name" value="${name}" scope="page"/>
	</c:when>
	<c:otherwise>
		<c:set var="m_name" value="membershipId" scope="page"/>
	</c:otherwise>
</c:choose>
<select id="membershipId" name="${m_name}" class="Field400">
	<c:if test="${not empty isAddAllOption}">
		<option value=""></option>
	</c:if>
	<c:forEach var="membership" items="${membershipList}">
		<option value="${membership.membershipId}" <c:if test="${membership.membershipId==membershipId}">selected="selected"</c:if>>${membership.membershipName}</option>
	</c:forEach>
</select>