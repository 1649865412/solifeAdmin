<%@ tag body-content="scriptless"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="选择器Id，要求唯一，可以看成选择器的引用"%>
<%@ attribute name="showSelectorBtnId" required="false" rtexprvalue="true" type="java.lang.String" description="打开本窗口的按钮的ID；留空并且自己设置该按钮的onclick事件也可,通过指定事件时打开时，本属性为空"%>
<%@ attribute name="ondblclick" required="false"  rtexprvalue="true" type="java.lang.String" description="双击时的回调函数，参数为选定的product对象(选择后的回调，单选时返回所选定的Sku，多选时返回所选择的Sku数组)"%>
<%@ attribute name="autoClose" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="ondblclick事件时，是否自动关闭选择器，默认true"%>
<%@ attribute name="showProductKinds" required="false"  rtexprvalue="true" type="java.lang.String" description="显示的ProductKind,默认显示所有，1表示普通产品,2表示变种，3，产品包；多个用逗号分隔"%>
<%@ attribute name="title" required="false"  rtexprvalue="true" type="java.lang.String" description="选择器标题,默认为产品选择器(主要是订单那边需指定为商品选择器)"%>
<%@ attribute name="multiSelect" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="是否多选，默认false,表示否，"%>
<%@ attribute name="userType" required="false"  rtexprvalue="true" type="java.lang.Short" description="用户类型，0为客户，1为管理员。默认为0"%>
<div id="userSelector_divDlg${id}">
</div>
<c:if test="${multiSelect}">
<%--加载列表数据后的回调--%>
<c:set var="getDataCallBack" value=",fnSelectedBoxChecked${id}"></c:set>
</c:if>
<script type="text/javascript" defer>
var userSelector_dialog_${id};
var userSelector_ondblclick_callback_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){${id}_show();});
	</c:if>
	if (userSelector_dialog_${id} == undefined){
		userSelector_dialog_${id} = $j("#userSelector_divDlg${id}"); 
	}
	userSelector_ondblclick_callback_${id}=eval(${ondblclick});
});
function getUserSelectorData_${id}(){
	var obj=new Object();
	obj.id="${id}";
	<c:if test="${not empty showProductKinds}">
	obj.productKind="${showProductKinds}";
	</c:if>
	<c:if test="${not empty multiSelect&&multiSelect}">
	obj.multiSelect=true;
	</c:if>
	return obj;
}
var userSelector_paramData_${id};
var userSelector_title_${id}="用户选择器";
<c:if test="${not empty title}">
userSelector_title_${id}="${title}";
</c:if>
function ${id}_show(paramData)
{
	userSelector_paramData_${id}=paramData;
	if(!(userSelector_dialog_${id}.dialog("isOpen")==true||userSelector_dialog_${id}.dialog("isOpen")==false)){
		userSelector_dialog_${id}.css("visibility","visible");
		fillDivWithPage("userSelector_divDlg${id}",__ctxPath+"/selector/userSelector.html",getUserSelectorData_${id}());
		userSelector_dialog_${id}.dialog({ title:userSelector_title_${id}, modal: true, height:400, width:900,buttons: {${buttons}}});
	}
	userSelector_dialog_${id}.dialog("open");
}

function ${id}_close()
{
	userSelector_dialog_${id}.dialog("close");
}
function fn${id}GetData(){
	var paraDate=$j("#userSelectorSearch_${id} :input").serializeArray();
	fillDivWithPage("userSelectorDataList_${id}","${ctxPath}/selector/userSelectorDataList.html?pagingId=${id}&userType=${userType}",paraDate${getDataCallBack});
}
function fnOnGoToPage${id}(){
	var paraDate=$j("#userSelectorDataList_${id} :input").serializeArray();
	fillDivWithPage("userSelectorDataList_${id}","${ctxPath}/selector/userSelectorDataList.html?pagingId=${id}&userType=${userType}",paraDate${getDataCallBack});
}
function __fnGetObjJsonData_${id}(pid){
	var tempJsonTxt=$j("#jsonDataList_${id}_"+pid).text();
	var objData=eval("["+tempJsonTxt+"]");
	return objData[0];
}
<c:choose>
	<c:when test="${not empty multiSelect&&multiSelect}">
		var selectedUserList${id} =new Array();
		function fuSelectUser${id}(pid,val){
			if(val){
				if(val.checked){
					var user=__fnGetObjJsonData_${id}(pid);
					fnAddSelectedUser${id}(user);
				}else{
					fnRemoveSelectedUser${id}(pid);
				}
			}else{
				var user=__fnGetObjJsonData_${id}(pid);
				fnAddSelectedUser${id}(user);
				$j("#sel_ch_${id}_"+user.appuserId).attr("checked",true);
			}
			
		}
		function fnAddSelectedUser${id}(user){
			var exist=false;
			for(var i=0;i<selectedUserList${id}.length;i++){
				if(selectedUserList${id}[i].appuserId==user.appuserId)
					exist=true;
			}
			if(!exist){
				selectedUserList${id}.push(user);
				$j("#selectedUser_${id}").append("<span id='sel_${id}_"+user.appuserId+"' ondblclick='fnRemoveSelectedUser${id}("+user.appuserId+")' title='双击移除'>"+user.username+",</span>");
			}
		}
		function fnRemoveSelectedUser${id}(id){
			for(var i=0;i<selectedUserList${id}.length;i++){
				if(selectedUserList${id}[i].appuserId==id){
					selectedUserList${id}=selectedUserList${id}.slice(0,i).concat(selectedUserList${id}.slice(i+1,selectedUserList${id}.length));
					$j("#sel_${id}_"+id).remove();
					$j("#sel_ch_${id}_"+id).attr("checked",false);
				}
			}
		}
		function fnRemoveAll${id}(){
			selectedUserList${id}=new Array();
			$j("#selectedUser_${id}").empty();
			$j("input[type='checkbox'][id^='sel_ch_']",$j("#userSelectorDataList_${id}")).attr("checked",false);
		}
		function fnConfirmSelectedUser${id}(){
			if(selectedUserList${id}.length<1)
				alert("请选择用户");
			if(userSelector_ondblclick_callback_${id})
			      userSelector_ondblclick_callback_${id}.call(this,selectedUserList${id},userSelector_paramData_${id});
			fnRemoveAll${id}();
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
		function fnSelectedBoxChecked${id}(){
			for(var i=0;i<selectedUserList${id}.length;i++){
				var id=selectedUserList${id}[i].appuserId;
				$j("#sel_ch_${id}_"+id).attr("checked",true);
			}
		}
	</c:when>
	<c:otherwise>
		function fuSelectUser${id}(pid){
			if(userSelector_ondblclick_callback_${id}){
				var user=__fnGetObjJsonData_${id}(pid);
				userSelector_ondblclick_callback_${id}.call(this,user,userSelector_paramData_${id});
			}
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	</c:otherwise>
</c:choose>
</script>