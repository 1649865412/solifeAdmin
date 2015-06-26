<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		<%@ include file="/decorators/include/styles.jspf"%>
		<script type="text/javascript" src="http://jquery-ui.googlecode.com/svn/tags/1.6rc2/ui/ui.core.js"></script>
		
		<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.spinner.js" ></script>
		 <script>
  $j(document).ready(function(){
    $j("#s2").spinner({currency: '$'});
  });
  </script>
	</head>
	<body>
	<c:forEach begin="0" end="23" var="">
	
	</c:forEach>
  <style type="text/css">
/* UI Spinner */
body
{
	background: #fff;
	font-family: Arial;
}

label {
	float: left;
	margin-right: .5em;
	padding: .15em 0;
	font-weight: bold;
}

.ui-spinner {
	width: 15em;
	display: block;
	position: relative;
	overflow: hidden;
	border: 1px solid #999;
	background: #FEFEFE url(../images/spinner-bg.gif) repeat-x left bottom;
	padding: 0 5px;
}

.ui-spinner-disabled {
	background: #F4F4F4;
	color: #CCC;
}

.ui-spinner-box {
	width: 90%;
	height: 100%;
	float: left;
	font-size: 125%;
	border: none;
	background: none;
	padding: 0;
}

.ui-spinner-up,
.ui-spinner-down {
	width: 10%;
	height: 50%;
	font-size: 0.5em;
	padding: 0;
	margin: 0;
	z-index: 100;
	text-align: center;
	vertical-align: middle;
	position: absolute;
	right: 0;
	cursor: default;
	border: 1px solid #999;
	border-right: none;
	border-top: none;
}

.ui-spinner-down {
	bottom: 0;
	border-bottom: 0;
}

.ui-spinner-pressed {
	background: #FEFEFE;
}

.ui-spinner-list,
.ui-spinner-listitem {
	margin: 0;
	padding: 0;
}
</style>

<p><label for="s2">Currency: </label>
<input type="text" id="s2" /></p>
</body>
</html>
