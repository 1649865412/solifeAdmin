<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="categoryPath"%>

<c:if test="${not empty categoryPath}">
    <c:forTokens items="${categoryPath}" delims="." varStatus="status">
      <c:if test="${status.index>0}">
           <c:choose>
              <c:when test="${status.last}">
                  <img src="<c:url value="/images/icon_cat_folder.gif"/>"/>
              </c:when>
              <c:otherwise>
                  <img src="<c:url value="/images/categoryBlank.png"/>"/>                
              </c:otherwise>
           </c:choose>
      </c:if>
    </c:forTokens>
</c:if>

