<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<link media="all" href="${ctxPath}/styles/global.css" type="text/css" rel="stylesheet">
		<title>测试简单弹出窗口</title>
	</head>
	<body>
		<form class="mainForm" action="${ctxPath}/test/test2.html" method="POST" id="shoppingcart" name="shoppingcart">
			<input type="button" value="Popup" onclick="fnOpenPopup();">
			<input type="button" value="Popup2" onclick="fnOpenPopup2();">
			<br/>
			<br/>
			<select name="select">
				<option>
					111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
				</option>
				<option>
					2222222222222222222222222222222222222222222222222222222
				</option>
			</select>
			<textarea id="tmpText" rows="8" cols="80">ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt<Br></textarea>
		</form>
		<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/dynamic.jsp"></script>
		<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/framework/core.js"></script>
		<script type="text/javascript">

		function fnOpenPopup(){
			$importSimpleDialog(function(){
					var tmpDlg=fnCreateSimpleDialog("test","Test Title:","Loading......Just a test",{
						cacheEnabled:true,
						dialogWidth:"480px",
						dialogHeight:"240px"
					});
					tmpDlg.showDialog(__ctxPath+"/test/test2.html");
			});
		}
		function fnOpenPopup2(){
			$importSimpleDialog(function(){
				var tmpDlg2=fnCreateSimpleDialog("test22","Test Title:","Just another test<span onclick='fnOpenPopup();'> Open popup1 </span>",{
					cacheEnabled:true,
					top:"10px",
					left:"200px",
					dialogWidth:"320px",
					dialogHeight:"280px"
				});
				//tmpDlg2_show();
				//知道dialogId的话，可以用另一种用法
				showSimpleDialog("test22");
			});
		}
		function fnDoAction1(){
			alert("Action1");
		}
</script>
	</body>
</html>
