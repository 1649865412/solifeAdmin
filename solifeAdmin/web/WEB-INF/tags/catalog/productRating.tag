<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="ratingValue" %>

<c:choose>
   <c:when test="${ratingValue eq 1}">
      <fmt:message key="productRating.poor"/>
  </c:when> 
<c:when test="${ratingValue eq 2}">
      <fmt:message key="productRating.fair"/>
  </c:when> 
<c:when test="${ratingValue eq 3}">
      <fmt:message key="productRating.good"/>
  </c:when> 
<c:when test="${ratingValue eq 4}">
      <fmt:message key="productRating.veryGood"/>
  </c:when> 
<c:when test="${ratingValue eq 5}">
      <fmt:message key="productRating.excellent"/>
  </c:when> 
 
</c:choose>
