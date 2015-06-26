<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
		<input name="fileName" id="fileName" type="text" size="30"/>
		<div id="uploadBtnPlaceholder"></div>
		<cartmatic:swf_upload fileInputId="fileName" btnPlaceHolderId="uploadBtnPlaceholder" onComplete="uploadHandler" isMultiFiles="true"></cartmatic:swf_upload>
		
		<input name="fileName" id="fileName" type="button" onclick="createUpload();" value="创建上传"/>
		<div id="uploadBtnPlaceholder2"></div>
		<input name="fileName2" id="fileName2" type="text" size="30"/>
		<div id="uploadBtnPlaceholder3"></div>
		<script type="text/javascript" defer>
			function uploadHandler(data){
				log(data);
			}
			function uploadHandler(data){
				log(data);
			}
			function createUpload(){
				initSwfUploadInstance({btnPlaceHolderId:"uploadBtnPlaceholder2",fileInputId:"fileName2"});
				initSwfUploadInstance({btnPlaceHolderId:"uploadBtnPlaceholder3"});
			}
		</script>
	    <%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>