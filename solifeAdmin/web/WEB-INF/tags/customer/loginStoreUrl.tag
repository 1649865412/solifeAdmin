<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.customer.service.CustomerManager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="customerId" required="true" type="java.lang.Integer"%>
<%@ attribute name="storeId" required="true" type="java.lang.Integer"%>
<%
String url="";
if(customerId!=null&&customerId>0&&storeId!=null&&storeId>0){
	CustomerManager customerManager=(CustomerManager)ContextUtil.getSpringBeanById("customerManager");
	url=customerManager.getVirtualLoginUrl(customerId,storeId);
}
out.print(url);
%>