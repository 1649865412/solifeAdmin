<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name"%>
<%@ attribute name="selectedVal"%>
<%
	//======================================================================
	//
	//					此标签已无引用，考虑删除！ Noted by pengzhirong.
	//
	//=======================================================================
	if(request.getAttribute("contentCategoryList")==null){
		java.util.List contentCategoryList=null;
		com.cartmatic.estore.catalog.service.CategoryManager categoryManager=
		(com.cartmatic.estore.catalog.service.CategoryManager)
		org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(
		request.getSession().getServletContext()).getBean("categoryManager");
		
		contentCategoryList=categoryManager.getSubContentCategoriesById(com.cartmatic.estore.Constants.ROOT_CATEGORY);
		request.setAttribute("contentCategoryList",contentCategoryList);
	}

%>
<select name="${name}" class="form-inputbox">
	<option value="0"></option>
	<c:forEach items="${contentCategoryList}" var="item">
		<option value="${item.categoryId}" <c:if test="${item.categoryId==selectedVal}">selected</c:if> >${item.categoryName}</option>
	</c:forEach>
</select>