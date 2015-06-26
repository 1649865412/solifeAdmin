
<%@tag import="com.cartmatic.estore.catalog.service.ProductMainManager"%>
<%@tag import="java.util.Map"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.SkuOption"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.SkuOptionValue"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.Category"%>
<%@ attribute name="productSkuId" required="true" rtexprvalue="true" type="java.lang.Integer"%>
<%@ attribute name="var" required="true" rtexprvalue="true" type="java.lang.String"%>
<%
ProductMainManager productMainManager=(ProductMainManager)ContextUtil.getSpringBeanById("productMainManager");
Map<SkuOption,SkuOptionValue> result=productMainManager.findSkuOptionAndValuesByProductSku(productSkuId);
request.setAttribute(var,result);
%>