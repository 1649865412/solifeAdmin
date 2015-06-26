<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>RegionTest</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
	<input id="regionId" type="text"/>
	<input id="regionName" type="text"/>
	<input id="b1" type="button" class="admin-btn" value="RegionSelect"/>
	<input id="b2" type="button" class="admin-btn" value="RegionSelect"/>
	<input id="b3" type="button" class="admin-btn" value="RegionSelect"/>
	
	<app:region id="myRegion1" showSelectorBtnId="b1" regionId="regionId" regionName="regionName"/>
	<app:region id="myRegion2" showSelectorBtnId="b2" regionId="regionId" regionName="regionName"  regionType="custom" ondblclick="afterDblclick1" autoClose="false"/>
	<app:region id="myRegion3" showSelectorBtnId="b3" regionId="regionId" regionName="regionName" regionType="system" onclick="afterClick1" ondblclick="afterDblclick1" autoClose="false" buttons=""/>
	
	<script type="text/javascript">
		function afterClick1(treeNode){
		//alert("afterClick1_"+treeNode.id+"___"+treeNode.name);
	}
	function afterDblclick1(treeNode){
		//alert("afterDblclick1_"+treeNode.id+"___"+treeNode.name);
	}
	</script>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>
