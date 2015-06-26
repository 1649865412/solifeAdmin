<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="提示：可以用dlg加上这个Id来引用这个窗口，所以showDialogBtnId可以不需要"%> 
<%@attribute name="width" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="height" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="showSelectorBtnId" required="false" rtexprvalue="true" type="java.lang.String" description="打开本窗口的按钮的ID；留空并且自己设置该按钮的onclick事件也可"%>
<%@attribute name="onclick" required="false" rtexprvalue="true" type="java.lang.String" description="单击目录树回调函数,返回参数为tree的json对象,{id:'',name:'',items:{....}"%>
<%@attribute name="ondblclick" required="false" rtexprvalue="true" type="java.lang.String" description="双击目录树回调函数,返回参数为tree的json对象,{id,name,items}"%>
<%@attribute name="autoClose" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="当设置了onclick或ondblclick事件时，是否自动关闭选择器，默认true"%>
<%@attribute name="buttons" required="false" rtexprvalue="true" type="java.lang.String" description="需要的按钮；请按name1,action1;name2,action2格式输入；action方法返回true会关闭窗口"%>

<%@attribute name="regionId" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="regionName" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="regionType" required="false" rtexprvalue="true" type="java.lang.String" description="地区类型:all-->所有地区（默认），system-->系统内置，custom-->客户订制的，默认是all"%>
<c:set var="ui_tree" value="true" scope="request"/>
<fmt:message key="regionSelector.dialogTitle" var="regionSelectorTitle"/>
<div id="divRetionSelector${id}"></div>
<script type="text/javascript" defer>
var retionSelector_dialog_${id};
var retionSelector_tree_${id};
var retionSelector_onclick_callback_${id};
var retionSelector_ondblclick_callback_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){retionSelector${id}_show();});
	</c:if>
	retionSelector_dialog_${id} = $j("#divRetionSelector${id}"); 
	//log(retionSelector_dialog_${id});
	retionSelector_onclick_callback_${id}=eval(${onclick}); 
	retionSelector_ondblclick_callback_${id}=eval(${ondblclick});
	$j("#divRetionSelector${id}").show();
});

function init_tree_${id}(){
	retionSelector_tree_${id} = $j('#retionSelector_tree_${id} > ul').simpleTree({
		drag:false,
		<c:if test="${not empty autoclose}">autoclose: ${autoclose},</c:if>		
		afterClick:function(node){
			if(retionSelector_onclick_callback_${id})
	      		retionSelector_onclick_callback_${id}.call(this,fnCreateTreeJSON${id}(node));
		},
		afterDblClick:function(node){
			if(retionSelector_ondblclick_callback_${id})
	      		retionSelector_ondblclick_callback_${id}.call(this,fnCreateTreeJSON${id}(node));
	      	else{
	      		fnCreateTreeJSON${id}(node);
	      	}
		},
		afterMove:function(destination, source, pos){
			//alert("destination-"+destination.attr('id')+" source-"+source.attr('id')+" pos-"+pos);
		},
		afterAjax:function(){
			//alert('Loaded');
		},
		animate:true
		//,docToFolderConvert:true
	});
}

function fnCreateTreeJSON${id}(node){
	var name=$j.trim($j($j("span",$j(node))[0]).text());
	var id=$j(node)[0].id;
	var treeItem={id:id,name:name};
	if(${empty autoClose ? true : autoClose}){
		retionSelector${id}_close();
	}
	<c:if test="${not empty regionId}">
		$('${regionId}').value=id;
	</c:if>
	<c:if test="${not empty regionName}">
		$('${regionName}').value=name;
	</c:if>
	return treeItem;
}

function retionSelector${id}_show(){
	retionSelector_dialog_${id}.css("visibility","visible");
	fillDivWithPage("divRetionSelector${id}","${ctxPath}/selector/regionSelector.html?regionId=0&id=${id}&width=${empty width ? 200 : width}&regionType=${empty regionType ? all : regionType}",null,init_tree_${id},"post",false);
	retionSelector_dialog_${id}.dialog({ title:"${regionSelectorTitle}", modal: true, height:${empty height ? 450 : height}, width:${empty width ? 200 : width}, buttons: {${buttons}}});
	retionSelector_dialog_${id}.dialog("open");
}

function retionSelector${id}_close(){
	retionSelector_dialog_${id}.dialog("close");
}
</script>