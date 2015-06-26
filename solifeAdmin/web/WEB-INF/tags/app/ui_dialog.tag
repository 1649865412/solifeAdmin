<%@ tag body-content="scriptless"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@ 
	taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><%@ 
	attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="提示：可以用dlg加上这个Id来引用这个窗口，所以showDialogBtnId可以不需要"%><%@ 
	attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%><%@ 
	attribute name="width" required="true" rtexprvalue="true" type="java.lang.String"%><%@ 
	attribute name="height" required="true" rtexprvalue="true" type="java.lang.String"%><%@ 
	attribute name="buttons" required="false" rtexprvalue="true" type="java.lang.String" description="需要的按钮；请按name1,action1;name2,action2格式输入；action方法返回true会关闭窗口"%><%@ 
	attribute name="url" required="false" rtexprvalue="true" type="java.lang.String" description="窗口的内容从URL读取；否则使用TAG中间的内容"%><%@ 
	attribute name="showDialogBtnId" required="false" rtexprvalue="true" type="java.lang.String" description="打开本窗口的按钮的ID；留空并且自己设置该按钮的onclick事件也可"%><%@ 
	attribute name="callback" required="false"  rtexprvalue="true" type="java.lang.String" description="载入成功时回调函数"%>
<div id="divDlg${id}" style="display:none;">
	<c:if test="${empty url}">
		<jsp:doBody />
	</c:if>
</div>
<script type="text/javascript" defer>
var ui_dialog_${id};
$j(document).ready(function(){
	<c:if test="${not empty showDialogBtnId}">
	$j("#${showDialogBtnId}").bind("click", function(){dlg${id}_show();});
	</c:if>
	ui_dialog_${id} = $j("#divDlg${id}"); 
}); 
/**
 * 显示dialog
 * @param {Object} param (可选) 发送至服务器的 key/value 数据;JSON格式
 * @param {Object} methodType(可选) 请求方式 ("POST" 或 "GET")， 默认为 "GET"
 */
function dlg${id}_show(param,methodType)
{
	ui_dialog_${id}.css("visibility","visible");
	<c:if test="${not empty url}">
	$j("#divDlg${id}").empty();
	if(!param)param={}; 
	fillDivWithPage("divDlg${id}","${url}",param${empty callback ? '' : ','}${callback},methodType);
	</c:if>
	ui_dialog_${id}.dialog({height:${height},width:${width},modal: true, title:"${title}",buttons: {${buttons}}});
	ui_dialog_${id}.dialog("open");
}

function dlg${id}_fillUrl(url)
{
	fillDivWithPage("divDlg${id}","url");
}

function dlg${id}_close()
{
	ui_dialog_${id}.dialog("close");
}

/**
 * 禁用dialog buttons项中的某一个button
 * @param btnValue  button content value, e.g. <button>btnValue</button>
 */
function dlg${id}_disableBtn(btnValue){
	$j("#divDlg${id}").filter( function(){ $j(":button:contains("+btnValue+")").attr("disabled","true").attr("style", "background:#E6E6E6;border:1px solid #ccc;color:#bbb;"); } );
}

function dlg${id}_enableBtn(btnValue){
	$j("#divDlg${id}").filter( function(){ $j(":button:contains("+btnValue+")").removeAttr("disabled").removeAttr("style"); } );
}
</script>