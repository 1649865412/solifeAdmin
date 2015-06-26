<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
	<%@ include file="/decorators/include/styles.jspf"%>
	<%@ include file="/decorators/include/javascripts.jspf"%>
	<script type="text/javascript" src="${ctxPath}/scripts/jquery/plugins/form/jquery.form.js"></script>

<script type="text/javascript">
var $=$j;
$(document).ready(function(){
	$("#b1").click(fnB1);
	$("#b2").click(fnB2);
	$("#b3").click(u1);
	$("#b4").click(d1);
	$("#b5").click(addGroup);
});
function fnB1(){
	fnMove('s1','s2');
}

function fnB2(){
fnMove('s2','s1');
/*
	var options=$("#s2 option");
	var options1=document.getElementById("s1").options;
	var selectedValue=$("#s2").val();
	for(var i=0;i<=options.length;i++){
		if(options[i].value==selectedValue){
			var option=new Option(options[i].text,options[i].value);
			options1[options1.length]=option;
			$(options[i]).remove();
			break;
		}
	}*/
}

function u1(){
fnMoveOptionUp('s2');
}

function d1(){
moveOptionDown('s2');
}
function fnMove(sourceId,destId){
	var selectOption=$("#"+sourceId+" option[selected]").get(0);
	if(selectOption&&selectOption.id==""){
		var index=selectOption.index;
		var options=document.getElementById(destId).options;
		var option=new Option(selectOption.text,selectOption.value);
		option.setAttribute("id",selectOption.id);
		options[options.length]=option;
		$(selectOption).remove();
		var sourceOptions=document.getElementById(sourceId).options;
		if(index>=sourceOptions.length)
			index--;
		if(sourceOptions.length>0)		
			sourceOptions[index].selected="selected";
	}
}

function fnMoveOptionUp(sId){
	var selectOption=$("#"+sId+" option[selected]");
	if(selectOption.get(0)&&selectOption.get(0).index!=0){
		selectOption.insertBefore(selectOption.prev());
	}
}

function moveOptionDown(sId){
	var selectOption=$("#"+sId+" option[selected]");
	var options=$("#"+sId+" option");
	if(selectOption.get(0)&&selectOption.get(0).index!=options.length-1){
		selectOption.insertAfter(selectOption.next());
	}
}



function addGroup(){
var	groupName=$("#groupName").val();
var selectOption=$("#s2 option[value="+groupName+"]");
if(selectOption.length>0)
log(selectOption);
	var options=document.getElementById("s2").options;
	var option=new Option($("#groupName").val(),$("#groupName").val());
	option.setAttribute("id","group_0");
	options[options.length]=option;
}
</script>
</head>

<body>
<select id="s1" class="sss" multiple="multiple" style="height:300px;width:200px;">
<option value="x1">x1</option>
<option value="x2">x2</option>
<option value="x3">x3</option>
<option value="x4">x4</option>
<option value="x5">x5</option>
<option value="x6">x6</option>
</select>
<input id="b1" type="button" value="<<" />
<input id="b2" type="button" value=">>"/>

<select id="s2" class="sss" multiple="multiple" style="height:300px;width:200px;">
<option value="x7">x7</option>
<option value="x8">x8</option>
<option value="x9">x9</option>
<option value="x10">x10</option>
</select>
<input id="b3" type="button" value="up"/>
<input id="b4" type="button" value="down"/>
<input type="text" id="groupName" name="groupName"/><input id="b5" type="button" value="addGroup"/>
</body>
</html>
