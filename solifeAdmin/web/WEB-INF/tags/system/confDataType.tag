<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="currentType" required="false"%>
<%
 java.util.ArrayList types = new java.util.ArrayList();
 
 String[][] dataTypes=new String[3][2];
 
 java.util.Hashtable item1 = new java.util.Hashtable();
 item1.put("key","1");
 item1.put("value","text");
 
 types.add(item1);
 

 java.util.Hashtable item2 = new java.util.Hashtable();
 item2.put("key","2");
 item2.put("value","boolean");
 
 types.add(item2);
 
 java.util.Hashtable item3 = new java.util.Hashtable();
 item3.put("key","3");
 item3.put("value","integer");
 
 types.add(item3);
 
 java.util.Hashtable item4 = new java.util.Hashtable();
 item4.put("key","4");
 item4.put("value","date");
 
 types.add(item4);
 
 java.util.Hashtable item5 = new java.util.Hashtable();
 item5.put("key","5");
 item5.put("value","media");
 
 types.add(item5);
 
 java.util.Hashtable item6 = new java.util.Hashtable();
 item6.put("key","6");
 item6.put("value","i18n");
 
 types.add(item6);
 request.setAttribute("systemConfigDateType",types);
 
%>

<c:forEach var="item" items="${systemConfigDateType}">
 <option value="${item.key}" ><fmt:message key="conf.dataType.${item.value}"/></option>
</c:forEach>
