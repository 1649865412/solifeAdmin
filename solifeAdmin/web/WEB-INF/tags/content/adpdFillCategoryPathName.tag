
<%@tag import="java.util.ArrayList"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.Category"%>
<%@tag import="java.util.List"%>
<%@tag import="com.cartmatic.estore.common.model.content.ProductAdvertisement"%>
<%@tag import="com.cartmatic.estore.common.helper.CatalogHelper"%>
<%@tag import="java.util.Set"%>
<%@tag import="com.cartmatic.estore.common.model.content.Advertisement"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="advertisement" type="com.cartmatic.estore.common.model.content.Advertisement" required="true"%>
<%
	List<Category> categoryList=new ArrayList();
	if(advertisement!=null){
          Set<ProductAdvertisement> productAdvertisements=advertisement.getProductAdvertisements();
          if(productAdvertisements!=null){
			CatalogHelper catalogHelper=CatalogHelper.getInstance();
         	Set<ProductAdvertisement>productAdvertisementList=advertisement.getProductAdvertisements();
			for (ProductAdvertisement productAdvertisement : productAdvertisementList) {
				Category categorytemp=catalogHelper.getCategoryById(productAdvertisement.getCategoryId());
				productAdvertisement.setCategoryPathName(categorytemp.getCategoryPathName());
			}
          }
	}
%>

