<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="viewType" %>
<%@ attribute name="defaultValue" %>
<%@ attribute name="areaName"%>
<%
 java.util.ArrayList das=new java.util.ArrayList();
 java.util.Hashtable t1=new java.util.Hashtable();
 t1.put("k","1");
 t1.put("v","adPositionType.Sequential");
 das.add(t1);
 java.util.Hashtable t2=new java.util.Hashtable();
 t2.put("k","2");
 t2.put("v","adPositionType.Random");
 das.add(t2);
 request.setAttribute("das",das);
%>
<c:choose>
<c:when test="${viewType==1}"> 
 <select name="${areaName}" class="Field400">
 <c:forEach var="item" items="${das}">  
   <option value="${item.k}" <c:if test="${item.k==defaultValue}"> selected="selected" </c:if>><fmt:message key="${item.v}"/></option>    
 </c:forEach>
 </select>
</c:when>
<c:otherwise>
 <c:forEach var="item" items="${das}">
  <c:if test="${item.k==defaultValue}">
  <fmt:message key="${item.v}"/>
  </c:if>
 </c:forEach> 
</c:otherwise>
</c:choose>