<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="item" required="true"  type="com.cartmatic.estore.common.model.attribute.Attribute"%>
<c:choose>
  <c:when test="${item.attributeDataType == 1}"><fmt:message key="attribute.attributeDataType.multicheckboxes"/></c:when>
  <c:when test="${item.attributeDataType == 2}"><fmt:message key="attribute.attributeDataType.url"/></c:when>
  <c:when test="${item.attributeDataType == 3}"><fmt:message key="attribute.attributeDataType.image"/></c:when>
  <c:when test="${item.attributeDataType == 4}"><fmt:message key="attribute.attributeDataType.int"/></c:when>
  <c:when test="${item.attributeDataType == 5}"><fmt:message key="attribute.attributeDataType.float"/></c:when>
  <c:when test="${item.attributeDataType == 6}"><fmt:message key="attribute.attributeDataType.boolean"/></c:when>
  <c:when test="${item.attributeDataType == 7}"><fmt:message key="attribute.attributeDataType.date"/></c:when>
  <c:when test="${item.attributeDataType == 8}"><fmt:message key="attribute.attributeDataType.selectlist"/></c:when>
  <c:when test="${item.attributeDataType == 9}"><fmt:message key="attribute.attributeDataType.radiobuttons"/></c:when>
  <c:when test="${item.attributeDataType == 10}"><fmt:message key="attribute.attributeDataType.shorttext"/></c:when>
  <c:otherwise><fmt:message key="attribute.attributeDataType.longtext"/></c:otherwise>
</c:choose>



  
