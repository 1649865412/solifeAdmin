<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="defaultValue" required="true"%>
<%@ attribute name="currentValue" required="false"%>
<c:if test="${not empty defaultValue}">
<%
 String defv=(String)defaultValue;
 String[] items=defaultValue.split("[,]");
 for(int i=0;i<items.length;i++){
  if(currentValue!=null&&(currentValue.toString()).equals(items[i])){
   out.println("<option value=\""+items[i]+"\" selected>"+items[i]+"</option>");  
  }else{
   out.println("<option value=\""+items[i]+"\">"+items[i]+"</option>");
  }
 }
 
%>
</c:if>