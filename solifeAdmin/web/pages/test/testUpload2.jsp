<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/ocupload/jquery.ocupload-1.1.2.js"></script>
	</head>
	<body>
		<div id="uploadDiv" style="display:none">
		test:<input name="upload_btn" value="upload" id="upload_btn" type="button"/>
		<input name="fileName" value="upload" id="fileName" type="text"/>
		</div>
		<input type="button" value="Show Upload" onclick="showUpload();">
	<%@ include file="/decorators/include/uiResource.jspf"%>
	<script>
	var myUpload = $j($("upload_btn")).upload({
        name: 'file',
        action:  __ctxPath+'/dialog/upload.html',
        enctype: 'multipart/form-data',
        params: {},
        onSubmit: function() {},
        onComplete: function() {},
        onSelect: function() {$j("#fileName").attr("value",this.filename());},
        autoSubmit: true
	});
	
		function showUpload(){
			$j("#uploadDiv").show();
		}
	function aa()
	{
		
	}
	</script>
	</body>
</html>