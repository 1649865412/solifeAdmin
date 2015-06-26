<%@page import="com.cartmatic.estore.common.model.catalog.Category"%>
<%@page import="com.cartmatic.estore.common.model.catalog.CategoryTreeItem"%>
<%@page import="java.util.List"%>
<%@page import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@page import="com.cartmatic.estore.catalog.service.CategoryManager"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
	<%
		CategoryManager categoryManager=(CategoryManager)ContextUtil.getSpringBeanById("categoryManager");
		List<CategoryTreeItem>categoryTreeItemList=categoryManager.getCatalogTreeItemList4Front(1);
		for (CategoryTreeItem categoryTreeItem : categoryTreeItemList){
			if(categoryTreeItem.getLevel()>1&&categoryTreeItem.isHasChildren()){
				out.println(categoryTreeItem.getCategoryCode()+"\t"+categoryTreeItem.getLevel()+"\t"+categoryTreeItem.isHasChildren());
				Category category=categoryManager.getById(categoryTreeItem.getId());
				category.setTemplatePath("catalog/categoryTemplateCatalogs");
				categoryManager.save(category);
			}
		}
	%>
	</body>
</html>