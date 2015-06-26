<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
	<script type="text/javascript">
	function fnTestSelectSupplier(supplier){
		log(supplier);
	}
	function fnTestMultiSelectSupplier(supplierList){
		log(supplierList);
	}
</script>
	<input id="b1" type="button" class="admin-btn" value="选择供应商1" onclick="supplierSelector_show('kkk_DIV')"/>
	<supplier:supplierSelector id="supplierSelector" ondblclick="fnTestSelectSupplier" autoClose="false"></supplier:supplierSelector>
	
	<input id="b1" type="button" class="admin-btn" value="选择供应商2(多选)" onclick="multiSupplierSelector_show('kkk_DIV')"/>
	<supplier:supplierSelector id="multiSupplierSelector" ondblclick="fnTestMultiSelectSupplier" autoClose="false" multiSelect="true"></supplier:supplierSelector>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>