<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
		<input id="ClickMe" name="ClickMe" type="button" value="DialogFromTag" />
		<input id="ClickMe2" name="ClickMe2" type="button" value="DialogFromScript" onclick="showDlgTest2();"/>
		<app:ui_dialog id="tttt" width="400" height="300" title="haha" showDialogBtnId="ClickMe">
			<span>just a test</span>
			<input id="datePicker" name="datePicker" type="text"/>
			<input type="button" onclick="test();" />
		</app:ui_dialog>
		
		<script type="text/javascript">
			var dlgTest2=null;
			function showDlgTest2() {
				var ss=$j("#ui_dialog_ClickMe").dialog("isOpen");
				console.log(ss);
				/*
				if (!dlgTest2) {
					dlgTest2 = fnCreateDialog("Test2","Test Dialog Window",null,{
						dialogButtons:[{text:"Send",handler:fnDialogButtonHandler},{text:"关闭",handler:"closeHandler"}],
						cacheEnabled:true,
						dialogWidth:"800px",
						dialogHeight:"600px",
						closable:false
					});
				}
				dlgTest2.showDialog(__ctxPath+"/test/test2.html",fnDialogOnShowHandler);
				*/
			}
			function test(){
				var ss=$j("#divDlgtttt").dialog("isOpen");
				console.log(ss);
			}
			function fnDialogButtonHandler() {
				alert("fnDialogButtonHandler");
			}
			function fnDialogOnShowHandler() {
				alert("dialog 2 created and show up.");
			}
		</script>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>
