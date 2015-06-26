<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.ProductStat"%>
<%@tag import="com.cartmatic.estore.catalog.service.ProductStatManager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="var" required="true" type="java.lang.String"%>
<%@ attribute name="storeId" required="true" rtexprvalue="true" type="java.lang.Integer" %>
<%@ attribute name="productId" required="true" type="java.lang.Integer"%>
<%
ProductStat productStat=null;
if(storeId!=null&&storeId>0&&productId!=null&&productId>0){
	ProductStatManager productStatManager=(ProductStatManager)ContextUtil.getSpringBeanById("productStatManager");
	productStat=productStatManager.getProductStat(storeId,productId)
}
request.setAttribute(var,productStat);
%>