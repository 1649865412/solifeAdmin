<%@ tag import="com.cartmatic.estore.common.model.catalog.Category" %>
<%@ tag import="java.util.Stack" %>
<%@tag import="com.cartmatic.estore.catalog.service.CategoryManager"%>
<%@tag import="com.cartmatic.estore.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="categoryId"%>
<%

	CategoryManager categoryManager= 
		(CategoryManager)org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(
			request.getSession().getServletContext()).getBean("categoryManager");

	StringBuilder pathGuide = new StringBuilder();
    Category category=categoryManager.getById(new Integer(categoryId));
    if(!categoryId.equals(Constants.ROOT_CATEGORY.toString())){
    	String contextPath=(String)application.getAttribute("ctxPath");
    	pathGuide.append(">><a href=\""+contextPath+"/content/contentCategories.html?_parentId="+category.getCategoryId()+"\">"+category.getCategoryName()+"</a>");
    
	    category = category.getCategory();
	    while(category.getCategoryId().intValue()!=Constants.ROOT_CATEGORY.intValue()){
	       pathGuide.insert(0,">><a href=\""+contextPath+"/content/contentCategories.html?_parentId="+category.getCategoryId()+"\">"+category.getCategoryName()+"</a>");
	       category = category.getCategory();
	    }
    }
	out.print(pathGuide);
%>


