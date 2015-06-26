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
<%@ attribute name="catalogId" type="java.lang.Integer" description="只显示指定目录下的产品"%>
<%@ attribute name="virtual" type="java.lang.Integer" description="0表示区分所有显示,1只显示虚拟的,2只显示实体的.默认为0"%>
<div id="productSelector_divDlg${id}">
</div>
<c:if test="${multiSelect}">
<%--加载列表数据后的回调--%>
<c:set var="getDataCallBack" value=",fnSelectedBoxChecked${id}"></c:set>
</c:if>
<script type="text/javascript" defer>
var productSelector_dialog_${id};
var productSelector_ondblclick_callback_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){${id}_show();});
	</c:if>
	if (productSelector_dialog_${id} == undefined){
		productSelector_dialog_${id} = $j("#productSelector_divDlg${id}"); 
	}
	productSelector_ondblclick_callback_${id}=eval(${ondblclick});
});
function getProductSelectorData_${id}(){
	var obj=new Object();
	obj.id="${id}";
	<c:if test="${not empty showProductKinds}">
	obj.productKind="${showProductKinds}";
	</c:if>
	<c:if test="${not empty multiSelect&&multiSelect}">
	obj.multiSelect=true;
	</c:if>
	<c:if test="${not empty catalogId}">
	obj.catalogId=${catalogId};
	</c:if>
	<c:if test="${not empty virtual}">
	obj.virtual=${virtual};
	</c:if>
	return obj;
}
var productSelector_paramData_${id};
var productSelector_title_${id}=__FMT.productSelector_title;
<c:if test="${not empty title}">
productSelector_title_${id}="${title}";
</c:if>
function ${id}_show(paramData)
{
	productSelector_paramData_${id}=paramData;
	if(!(productSelector_dialog_${id}.dialog("isOpen")==true||productSelector_dialog_${id}.dialog("isOpen")==false)){
		productSelector_dialog_${id}.css("visibility","visible");
		productSelector_dialog_${id}.dialog({title:productSelector_title_${id},modal: true,height:400, width:900,buttons: {${buttons}}});
		var tempproductSelector_divDlg=$j("[id='productSelector_divDlg${id}']");
		if(tempproductSelector_divDlg.length>1){
			fillDivWithPage($j(tempproductSelector_divDlg[tempproductSelector_divDlg.length-1]),__ctxPath+"/selector/productSelector.html",getProductSelectorData_${id}(),"post");
		}else{
			fillDivWithPage("productSelector_divDlg${id}",__ctxPath+"/selector/productSelector.html",getProductSelectorData_${id}());
		}
	}
	productSelector_dialog_${id}.dialog("open");
}

function ${id}_close()
{
	productSelector_dialog_${id}.dialog("close");
}
function fn${id}GetData(){
	var paraDate=$j("#productSelectorSearch_${id} :input").serializeArray();
	fillDivWithPage("productSelectorDataList_${id}","${ctxPath}/selector/productSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function fnOnGoToPage${id}(){
	var paraDate=$j("#productSelectorDataList_${id} :input").serializeArray();
	fillDivWithPage("productSelectorDataList_${id}","${ctxPath}/selector/productSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function __fnGetObjJsonData_${id}(pid){
	var tempJsonTxt=$j("#jsonDataList_${id}_"+pid).text();
	var objData=eval("["+tempJsonTxt+"]");
	return objData[0];
}
<c:choose>
	<c:when test="${not empty multiSelect&&multiSelect}">
		var selectedProductList${id} =new Array();
		function fuSelectProduct${id}(pid,val){
			if(val){
				if(val.checked){
					var product=__fnGetObjJsonData_${id}(pid);
					fnAddSelectedProduct${id}(product);
				}else{
					fnRemoveSelectedProduct${id}(pid);
				}
			}else{
				var product=__fnGetObjJsonData_${id}(pid);
				fnAddSelectedProduct${id}(product);
				$j("#sel_ch_${id}_"+product.productId).attr("checked",true);
			}
			
		}
		function fnAddSelectedProduct${id}(product){
			var exist=false;
			for(var i=0;i<selectedProductList${id}.length;i++){
				if(selectedProductList${id}[i].productId==product.productId)
					exist=true;
			}
			if(!exist){
				selectedProductList${id}.push(product);
				$j("#selectedProduct_${id}").append("<span id='sel_${id}_"+product.productId+"' ondblclick='fnRemoveSelectedProduct${id}("+product.productId+")' title='双击移除'>"+product.productName+",</span>");
			}
		}
		function fnRemoveSelectedProduct${id}(id){
			for(var i=0;i<selectedProductList${id}.length;i++){
				if(selectedProductList${id}[i].productId==id){
					selectedProductList${id}=selectedProductList${id}.slice(0,i).concat(selectedProductList${id}.slice(i+1,selectedProductList${id}.length));
					$j("#sel_${id}_"+id).remove();
					$j("#sel_ch_${id}_"+id).attr("checked",false);
				}
			}
		}
		function fnRemoveAll${id}(){
			selectedProductList${id}=new Array();
			$j("#selectedProduct_${id}").empty();
			$j("input[type='checkbox'][id^='sel_ch_']",$j("#productSelectorDataList_${id}")).attr("checked",false);
		}
		function fnConfirmSelectedProduct${id}(){
			if(selectedProductList${id}.length<1)
				alert("请选择产品");
			if(productSelector_ondblclick_callback_${id})
			      productSelector_ondblclick_callback_${id}.call(this,selectedProductList${id},productSelector_paramData_${id});
			fnRemoveAll${id}();
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
		function fnSelectedBoxChecked${id}(){
			for(var i=0;i<selectedProductList${id}.length;i++){
				var id=selectedProductList${id}[i].productId;
				$j("#sel_ch_${id}_"+id).attr("checked",true);
			}
		}
	</c:when>
	<c:otherwise>
		function fuSelectProduct${id}(pid){
			if(productSelector_ondblclick_callback_${id}){
				var product=__fnGetObjJsonData_${id}(pid);
				productSelector_ondblclick_callback_${id}.call(this,product,productSelector_paramData_${id});
			}
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	</c:otherwise>
</c:choose>
</script>
