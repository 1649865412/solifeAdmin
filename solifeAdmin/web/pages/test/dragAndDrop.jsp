<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<%@ include file="/decorators/include/styles.jspf"%>
	<%@ include file="/decorators/include/javascripts.jspf"%>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.draggable.js"></script>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.droppable.js"></script>

<style type="text/css">
.wrap{ width:1000px; margin:0px auto; background-color:#000; height:1000px;}
.big-wrap{ width:400px;height:720px; background-color:#f00; float:left;}
.small-wrap{ width:350px;height:350px;background-color:#ccc; float:right; margin-bottom:20px;}
.abc{background-color:#EF00C5; width:200px; height:50px; }
.cd{background-color:#00f; width:200px; height:50px; }
.droppable-active {opacity: 1.0;}
.droppable-hover {outline: 1px dotted black;}
</style>
<script type="text/javascript">
var $=$j;
var wrap=null;
$(document).ready(function(){
	$(".cd").draggable({helper:'clone',opacity:0.55,
	stop:function(ev, ui){
		$(ev.target).remove();
		wrap.append($('#'+ev.target.id));
		//wrap.append(ev.target);
		//log(wrap);log(ev.target);
		//wrap.appendChild(ev.target);
	}});
	$(".big-wrap,.small-wrap").droppable({
	accept: ".cd",
	activeClass: 'droppable-active',
	hoverClass: 'droppable-hover',
	drop: function(ev, ui) {
		wrap=$(this);
		}
	});
});
</script>
</head>

<body>
<div class="wrap" id="1">
    <div class="big-wrap">
		<div class="cd" id="c1">1</div>
		<div class="cd" id="c2">2</div>
		<div class="cd" id="c3">3</div>
		<div class="cd" id="c4">4</div>
		<div class="cd" id="c5">5</div>
		<div class="cd" id="c6">6</div>
		<div class="cd" id="c7">7</div>
		<div class="cd" id="c8">8</div>
		<div class="cd" id="c9">9</div>
		<div class="cd" id="c10">10</div>
	</div>
	<div class="small-wrap" id="2">
	</div>
	<div class="small-wrap" id="3">
	</div>
</div>
</body>
</html>
