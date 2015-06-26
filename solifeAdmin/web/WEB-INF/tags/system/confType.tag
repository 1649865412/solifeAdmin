<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="currentType" required="false"%>
<%
 java.util.ArrayList types=new java.util.ArrayList();
 java.util.Hashtable type1=new java.util.Hashtable();
 type1.put("key","1");
 type1.put("value","text");
 types.add(type1);
 
 java.util.Hashtable type2=new java.util.Hashtable();
 type2.put("key","2");
 type2.put("value","select");
 types.add(type2);
 
 java.util.Hashtable type3=new java.util.Hashtable();
 type3.put("key","3");
 type3.put("value","checkbox");
 types.add(type3);
 request.setAttribute("types",types);
%>
<c:forEach var="item" items="${types}">
 <c:choose>
 <c:when test="${currentType==item.key}">
  <option value="${item.key}" selected><fmt:message key="conf.confType.${item.value}"/></option>
 </c:when>
 <c:otherwise>
  <option value="${item.key}"><fmt:message key="conf.confType.${item.value}"/></option>
 </c:otherwise>
 </c:choose>
</c:forEach>
