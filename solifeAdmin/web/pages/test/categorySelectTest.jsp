<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<script type="text/javascript">
	function fnSelectCategoryTest(category){
		alert("fnSelectCateTest____selector1_id:"+category.id+"___name:"+category.name+"___path:"+category.categoryPath);
	}
	function fnSelectCategoryTest2(category,paramData){
		alert("fnSelectCateTest2_selector1_id:"+category.id+"___name:"+category.name+"\nparamData:"+paramData);
	}
	function fnSelectCategoryTest3(category){
		alert("fnSelectCateTest2_selector2_id:"+category.id+"___name:"+category.name);
	}
	function fnSelectCategoryTest5(category){
		alert("fnSelectCateTest2_selector2_id:"+category.id+"___name:"+category.name);
	}
</script>
	<input id="b2" type="button" class="admin-btn" value="选择目录1"/>
	<input id="b3" type="button" class="admin-btn" value="选择目录(带参数)" onclick="Selector1_show({type:'catalog'});"/>
	<input id="b4" type="button" class="admin-btn" value="选择目录2"/>
	<input id="b5" type="button" class="admin-btn" value="选择目录(实体)"/>
	<input id="b6" type="button" class="admin-btn" value="选择目录(Store 1)"/>
	<product:categorySelector id="Selector1" showSelectorBtnId="b2" ondblclick="fnSelectCategoryTest2"></product:categorySelector>
	<product:categorySelector id="Selector2" showSelectorBtnId="b4" ondblclick="fnSelectCategoryTest3" autoClose="false"></product:categorySelector>
	<product:categorySelector id="Selector1" showSelectorBtnId="b5" ondblclick="fnSelectCategoryTest5" virtual="0"></product:categorySelector>
	<product:categorySelector id="Selector1" showSelectorBtnId="b6" ondblclick="fnSelectCategoryTest5" catalogId="1"></product:categorySelector>
		
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>