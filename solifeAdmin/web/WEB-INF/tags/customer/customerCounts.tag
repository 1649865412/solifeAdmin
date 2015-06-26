
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.customer.service.CustomerManager"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="membershipId" required="true"%>
<%
 	CustomerManager customerManager=(CustomerManager)ContextUtil.getSpringContext().getBean("customerManager");
 	Long count=customerManager.getCustomerCountsByMembershipId(new Integer(membershipId));
	request.setAttribute("customer_counts",count);
	out.print(count.longValue());
%>