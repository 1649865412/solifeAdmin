<%@tag import="com.cartmatic.estore.common.model.system.PaymentMethod"%>
<%@tag import="com.cartmatic.estore.system.service.PaymentMethodManager"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="paymentMethodId" type="java.lang.Integer" required="true" description="paymentMethodId"%>
<%@ attribute name="var" type="java.lang.String" required="true"%>
<%
if(paymentMethodId!=null&&paymentMethodId>0){
	 PaymentMethodManager paymentMethodManager=(PaymentMethodManager)ContextUtil.getSpringBeanById("paymentMethodManager");
	 PaymentMethod paymentMethod=paymentMethodManager.getById(new Integer(paymentMethodId));
	 request.setAttribute(var,paymentMethod);
}else{
	 request.removeAttribute(var);
}
%>

