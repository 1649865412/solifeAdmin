
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ attribute name="mediaType" description="product,productMedia。默认为：product"%>
<%@ attribute name="product" type="com.cartmatic.estore.common.model.catalog.Product" description="显示产品默认Sku的图片"%>
<%@ attribute name="sku" type="com.cartmatic.estore.common.model.catalog.ProductSku" description="显示指定Sku的图片"%>
<%@ attribute name="width" %>
<%@ attribute name="height" %>
<%@ attribute name="id" %>
<%@ attribute name="alt" %>
<%@ attribute name="onLoadHandler" %> 
<%@ attribute name="onClickHandler" %>
<%@ attribute name="mouseOverHandler" %>
<%@ attribute name="size" description="规格目录" required="false"%>
<%@ attribute name="isUrlOnly" type="java.lang.Boolean" description="只打印图片Url，不包含path"%>
<%
	if(StringUtils.isBlank(mediaType)){
		mediaType="product";
	}
	if(sku!=null){
		product=sku.getProduct();
	}else if(product!=null){
		sku=product.getDefaultProductSku();
	}
	if(StringUtils.isBlank(alt)){
		alt=product.getProductName();
	}
%>
<cartmatic:img url="<%=sku.getImage()%>" mediaType="<%=mediaType%>" size="${size}" alt="<%=alt%>" height="${height}" width="${width}" isUrlOnly="${isUrlOnly}" id="${id}" mouseOverHandler="${mouseOverHandler}" onClickHandler="${onClickHandler}" onLoadHandler="${onLoadHandler}"></cartmatic:img>