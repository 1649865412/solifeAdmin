<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
	<script type="text/javascript">
	function fnTestUserSupplier(user){
		log(user);
	}
	function fnTestMultiSelectUser(userList){
		log(userList);
	}
</script>
	<input id="b1" type="button" class="admin-btn" value="选择用户1" onclick="userSelector_show('kkk_DIV')"/>
	<system:userSelector id="userSelector" ondblclick="fnTestUserSupplier" autoClose="false"></system:userSelector>
	
	<input id="b2" type="button" class="admin-btn" value="选择用户2(多选)" onclick="multiUserSelector_show('kkk_DIV')"/>
	<system:userSelector id="multiUserSelector" ondblclick="fnTestMultiSelectUser" autoClose="false" multiSelect="true"></system:userSelector>
	
	<input id="b3" type="button" class="admin-btn" value="选择管理员1" onclick="userAdminSelector_show('kkk_DIV')"/>
	<system:userSelector id="userAdminSelector" ondblclick="fnTestUserSupplier" autoClose="false" userType="1"></system:userSelector>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>