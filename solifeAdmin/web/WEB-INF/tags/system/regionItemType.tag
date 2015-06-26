<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="viewType" %>
<%@ attribute name="defaultValue" %>
<%@ attribute name="areaName"%>
<%
 java.util.ArrayList das=new java.util.ArrayList();
 java.util.Hashtable t1=new java.util.Hashtable();
 t1.put("k","1");
 t1.put("v","Address");
 das.add(t1);
 java.util.Hashtable t2=new java.util.Hashtable();
 t2.put("k","2");
 t2.put("v","Zip");
 das.add(t2);
 
 java.util.Hashtable t3=new java.util.Hashtable();
 t3.put("k","3");
 t3.put("v","Region");
 das.add(t3);
 
 request.setAttribute("das",das);
%>

<c:choose>
<c:when test="${viewType==1}">
 
 <select name="${areaName}">
 <c:forEach var="item" items="${das}">
  <c:if test="${item.k==defaultValue}">
   <option value="${item.k}" selected="selected">${item.v}</option>
  </c:if>
  <option value="${item.k}">${item.v}</option>
 </c:forEach>
 </select>
</c:when>
<c:otherwise>
 <c:forEach var="item" items="${das}">
  <c:if test="${item.k==defaultValue}">
   ${item.v}
  </c:if>
 </c:forEach>
 
</c:otherwise>
</c:choose>
