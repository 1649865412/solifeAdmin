<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<script type="text/javascript" src="${ctxPath}/scripts/jquery/jquery-1.2.6.js"></script>
		<%-- <link rel="stylesheet" href="${ctxPath}/scripts/jquery/themes/flora/flora.all.css" type="text/css" media="screen" title="Flora (Default)" />--%>
		
	</head>
	<body>
	测试anthorize
	<br/>
	<anthorize:authorizeResource resourceString="/sales/test2.html">
	这是内容
	</anthorize:authorizeResource>
	<br/>
	<br/>
	
	<div id="example">
            <ul>
                <li><a href="#fragment-1"><span>One</span></a></li>
                <li><a href="#fragment-2"><span>Two</span></a></li>
                <li><a href="#fragment-3"><span>Three</span></a></li>
            </ul>
            <div id="fragment-1">
                <p>First tab is active by default:</p>
                <pre><code>$('#example > ul').tabs();</code></pre>
            </div>
            <div id="fragment-2">
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
            </div>
            <div id="fragment-3">
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
                Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.
            </div>
    </div>
	
	
		Just a very simple page for test, this is from another page!
		<input type="text" name="testInput" id="testDate">

		<div id="dialog">Hello</div>
<script type="text/javascript">
$(document).ready(function(){	
    $("#example > ul").tabs();
	//$("#dialog").dialog({ modal: true, overlay: { opacity: 0.5, background: "black" } });
});
</script>
	<%-- <link rel="stylesheet" href="${ctxPath}/scripts/jquery/themes/cartmatic/ui.all.css" type="text/css"/>--%>
	<link rel="stylesheet" href="${ctxPath}/scripts/jquery/themes/cartmatic/ui.tabs.css" type="text/css"/>
	
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.core.js"></script>
	
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.tabs.js"></script>
	</body>
</html>
