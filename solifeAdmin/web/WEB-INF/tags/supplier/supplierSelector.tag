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
<div id="supplierSelector_divDlg${id}">
</div>
<c:if test="${multiSelect}">
<%--加载列表数据后的回调--%>
<c:set var="getDataCallBack" value=",fnSelectedBoxChecked${id}"></c:set>
</c:if>
<script type="text/javascript" defer>
var supplierSelector_dialog_${id};
var supplierSelector_ondblclick_callback_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){${id}_show();});
	</c:if>
	if (supplierSelector_dialog_${id} == undefined){
		supplierSelector_dialog_${id} = $j("#supplierSelector_divDlg${id}"); 
	}
	supplierSelector_ondblclick_callback_${id}=eval(${ondblclick});
});
function getSupplierSelectorData_${id}(){
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
var supplierSelector_paramData_${id};
var supplierSelector_title_${id}="供应商选择器";
<c:if test="${not empty title}">
supplierSelector_title_${id}="${title}";
</c:if>
function ${id}_show(paramData)
{
	supplierSelector_paramData_${id}=paramData;
	if(!(supplierSelector_dialog_${id}.dialog("isOpen")==true||supplierSelector_dialog_${id}.dialog("isOpen")==false)){
		supplierSelector_dialog_${id}.css("visibility","visible");
		fillDivWithPage("supplierSelector_divDlg${id}",__ctxPath+"/selector/supplierSelector.html",getSupplierSelectorData_${id}(),"post");
		supplierSelector_dialog_${id}.dialog({ title:supplierSelector_title_${id}, modal: true, height:400, width:900,buttons: {${buttons}}});
	}
	supplierSelector_dialog_${id}.dialog("open");
}

function ${id}_close()
{
	supplierSelector_dialog_${id}.dialog("close");
}
function fn${id}GetData(){
	var paraDate=$j("#supplierSelectorSearch_${id} :input").serializeArray();
	fillDivWithPage("supplierSelectorDataList_${id}","${ctxPath}/selector/supplierSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function fnOnGoToPage${id}(){
	var paraDate=$j("#supplierSelectorDataList_${id} :input").serializeArray();
	fillDivWithPage("supplierSelectorDataList_${id}","${ctxPath}/selector/supplierSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function __fnGetObjJsonData_${id}(pid){
	var tempJsonTxt=$j("#jsonDataList_${id}_"+pid).text();
	var objData=eval("["+tempJsonTxt+"]");
	return objData[0];
}
<c:choose>
	<c:when test="${not empty multiSelect&&multiSelect}">
		var selectedSupplierList${id} =new Array();
		function fuSelectSupplier${id}(pid,val){
			if(val){
				if(val.checked){
					var supplier=__fnGetObjJsonData_${id}(pid);
					fnAddSelectedSupplier${id}(supplier);
				}else{
					fnRemoveSelectedSupplier${id}(pid);
				}
			}else{
				var supplier=__fnGetObjJsonData_${id}(pid);
				fnAddSelectedSupplier${id}(supplier);
				$j("#sel_ch_${id}_"+supplier.supplierId).attr("checked",true);
			}
			
		}
		function fnAddSelectedSupplier${id}(supplier){
			var exist=false;
			for(var i=0;i<selectedSupplierList${id}.length;i++){
				if(selectedSupplierList${id}[i].supplierId==supplier.supplierId)
					exist=true;
			}
			if(!exist){
				selectedSupplierList${id}.push(supplier);
				$j("#selectedSupplier_${id}").append("<span id='sel_${id}_"+supplier.supplierId+"' ondblclick='fnRemoveSelectedSupplier${id}("+supplier.supplierId+")' title='双击移除'>"+supplier.supplierName+",</span>");
			}
		}
		function fnRemoveSelectedSupplier${id}(id){
			for(var i=0;i<selectedSupplierList${id}.length;i++){
				if(selectedSupplierList${id}[i].supplierId==id){
					selectedSupplierList${id}=selectedSupplierList${id}.slice(0,i).concat(selectedSupplierList${id}.slice(i+1,selectedSupplierList${id}.length));
					$j("#sel_${id}_"+id).remove();
					$j("#sel_ch_${id}_"+id).attr("checked",false);
				}
			}
		}
		function fnRemoveAll${id}(){
			selectedSupplierList${id}=new Array();
			$j("#selectedSupplier_${id}").empty();
			$j("input[type='checkbox'][id^='sel_ch_']",$j("#supplierSelectorDataList_${id}")).attr("checked",false);
		}
		function fnConfirmSelectedSupplier${id}(){
			if(selectedSupplierList${id}.length<1)
				alert("请选择供应商");
			if(supplierSelector_ondblclick_callback_${id})
			      supplierSelector_ondblclick_callback_${id}.call(this,selectedSupplierList${id},supplierSelector_paramData_${id});
			fnRemoveAll${id}();
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
		function fnSelectedBoxChecked${id}(){
			for(var i=0;i<selectedSupplierList${id}.length;i++){
				var id=selectedSupplierList${id}[i].supplierId;
				$j("#sel_ch_${id}_"+id).attr("checked",true);
			}
		}
	</c:when>
	<c:otherwise>
		function fuSelectSupplier${id}(pid){
			if(supplierSelector_ondblclick_callback_${id}){
				var supplier=__fnGetObjJsonData_${id}(pid);
				supplierSelector_ondblclick_callback_${id}.call(this,supplier,supplierSelector_paramData_${id});
			}
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	</c:otherwise>
</c:choose>
</script>