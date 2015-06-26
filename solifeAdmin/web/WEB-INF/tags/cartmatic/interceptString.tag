<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="str" type="java.lang.String" required="true" description="需截取的字符串"%>
<%@ attribute name="length" type="java.lang.Integer" required="true" description="保留字符串长度"%>
<%
if(str!=null){
	if(str.length()-length>0){
		str=str.substring(0,length);
		str+="...";
	}
	request.setAttribute("changeStr",str);
	%><c:out value="${changeStr}"/><%
}%>