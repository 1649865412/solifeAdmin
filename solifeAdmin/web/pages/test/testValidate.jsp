<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/dynamic.jsp"></script>
		<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/framework/core.js"></script>
		<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/framework/validation.js"></script>
	</head>
	<body>
		<div id="divTest"></div>
		
		<form class="mainForm" action="testValidate.html" onsubmit="return validateForm(this);">
		<input type="submit" value="Submit" >
		<input type="button" value="DyLoadJS" onclick="$import('/scripts/cartmatic/system/appMessageList.js');return false;">
		
			<div>
				<input id="testInput1" value="testInput1" type="text" title="测试属性"
					validConf="required,integer,maxValue=100" />
			</div>
			<br />
			<div>
				<input id="testInput2" name="testInput2" value="OOOOOO" type="text" />
			</div>
			<br />
			<div>
				<select id="testSlt1" name="testSlt1" validConf="required">
					<option value="">
						Select...
					</option>
					<option value="opt1">
						opt1
					</option>
					<option value="opt2">
						opt2
					</option>
					<option value="opt3">
						opt3
					</option>
				</select>
			</div>
			<br />
			<div>
				<input id="ckTest1" type="checkbox" name="checkboxTest" value="ddd">
				ddd
				<input type="checkbox" name="checkboxTest" value="eee" />
				eee
				<input type="checkbox" name="checkboxTest" value="fff" />
				fff
			</div>
			<div>
				<input id="radioTest1" type="radio" name="radioTest" value="aaa">
				aaa
				<input type="radio" name="radioTest" value="bbb" />
				bbb
				<input type="radio" name="radioTest" value="ccc" />
				ccc
			</div>

			<br />
			
		</form>
		<script type="text/javascript" defer>
			autoApplyValidate();
			applyValidate($("testInput2"),"required,integer,maxValue=200");
			applyValidate($("ckTest1"),"required");
			applyValidate($("radioTest1"),"required");
		</script>
	</body>
</html>
