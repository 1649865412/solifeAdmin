<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>

<%@ attribute name="item" description="the item must extend from the AttributeValue object or the tag will throw a exception" required="true" type="com.cartmatic.estore.common.model.attribute.BaseAttributeValue"%>
<%@ attribute name="cssName" %>

<c:set var="type" value="${item.attributeDataType}" />
<c:catch var="exception">
<c:choose>
<c:when test="${type == 2}">
<a <c:if test="${not empty cssName }">class="${cssName }"</c:if> href="${item.attributeValue }"><c:out value="${item.attributeValue }"></c:out></a>
</c:when>

<c:when test="${type == 3}">
<img <c:if test="${not empty cssName }">class="${cssName }"</c:if> src="${ctxPath }/media/<c:out value="${item.attributeValue }"></c:out>"/>
</c:when>

<c:when test="${type == 6}">
  <c:choose>
    <c:when test="${item.attributeValue==1}"><span <c:if test="${not empty cssName }">class="${cssName }"</c:if> ><fmt:message key="common.message.yes"/></span></c:when>
    <c:otherwise><span <c:if test="${not empty cssName }">class="${cssName }"</c:if>  ><fmt:message key="common.message.no" /></span></c:otherwise>
  </c:choose>
</c:when>

<c:otherwise>
<span <c:if test="${not empty cssName }">class="${cssName }"</c:if> ><c:out value="${item.attributeValue}"></c:out></span><span>${item.attribute.attributeUnit }</span>
</c:otherwise>
</c:choose>
</c:catch>
<c:if test="${not empty exception}">${exception}</c:if>
  




