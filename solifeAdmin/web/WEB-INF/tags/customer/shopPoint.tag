
<%@tag import="com.cartmatic.estore.common.model.customer.ShopPoint"%>
<%@tag import="com.cartmatic.estore.customer.service.ShopPointManager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="customerId" required="true" type="java.lang.Integer"%>
<%@ attribute name="var" required="true" type="java.lang.String"%>
<%
		javax.servlet.ServletContext servletContext = session.getServletContext();
		org.springframework.context.ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ShopPointManager shopPointManager=(ShopPointManager)ctx.getBean("shopPointManager");
		ShopPoint shopPoint=shopPointManager.getByCustomerId(customerId);
		request.setAttribute(var,shopPoint);
%>