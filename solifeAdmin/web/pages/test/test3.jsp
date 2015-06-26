<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="java.util.List"%>
<%@page import="com.cartmatic.estore.catalog.service.ProductDescriptionManager"%>
<%@page import="com.cartmatic.estore.common.model.catalog.ProductDescription"%>
<%@page import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
	</head>
	<body>
	测试anthorize
	<br/>
	<%
		ProductDescriptionManager productDescriptionManager=(ProductDescriptionManager)ContextUtil.getSpringBeanById("productDescriptionManager");
		List<ProductDescription> productDescriptionList=productDescriptionManager.getAll();
		for(ProductDescription productDescription:productDescriptionList){
			Document document=Jsoup.parse(productDescription.getFullDescription());
			Elements a_elements=document.select("a");
			if(a_elements.size()>0){
				System.out.println(productDescription.getFullDescription());
				for (Element a_element : a_elements){
						a_element.before(a_element.html());
						a_element.remove();
				}
				System.out.println(document.body().html());
				productDescription.setFullDescription(document.body().html());
				productDescriptionManager.save(productDescription);
				System.out.println();
				//break;
			}
		}
	%>
	</body>
</html>
