<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="${ctxPath}/scripts/jquery/themes/cartmatic/ui.datepicker.css" type="text/css" media="screen" title="core css file" charset="utf-8" />
<script language="javascript" src="${ctxPath}/scripts/jquery/jquery-1.2.6.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.core.js"></script>
<script language="javascript" src="${ctxPath}/scripts/jquery/ui/ui.datepicker.js"></script>
<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/i18n/ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" defer>
$(document).ready(function(){
	$('#dateinput').datepicker({showOn: 'button', showOtherMonths: true, 
		showWeeks: true, firstDay: 1, changeFirstDay: false,closeAtTop:false,
		buttonImageOnly: true, buttonImage: '${ctxPath}/scripts/jquery/themes/cartmatic/images/calendar.gif'});
});
</script>
</head>
<body>
<input id="dateinput" type="text" readonly="readonly"/>
</body>
</html>
