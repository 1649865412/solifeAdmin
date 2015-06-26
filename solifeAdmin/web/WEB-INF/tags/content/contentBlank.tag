<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="level"%>

<c:forEach var="i" begin="1" end="${level}">
     <c:choose>
              <c:when test="${i<level}">
                  <img src="<c:url value="/images/categoryBlank.png"/>"/>
              </c:when>
              <c:otherwise>
                   <img src="<c:url value="/images/icon_cat_folder.gif"/>"/>               
              </c:otherwise>
     </c:choose>
</c:forEach>