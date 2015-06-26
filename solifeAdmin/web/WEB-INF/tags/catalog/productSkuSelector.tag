<%@ tag body-content="scriptless"%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="选择器Id，要求唯一，可以看成选择器的引用"%>
<%@ attribute name="showSelectorBtnId" required="false" rtexprvalue="true" type="java.lang.String" description="打开本窗口的按钮的ID；留空并且自己设置该按钮的onclick事件也可"%>
<%@ attribute name="ondblclick" required="false"  rtexprvalue="true" type="java.lang.String" description="双击时的回调函数，参数为选定的productSku对象(选择后的回调，单选时返回所选定的Sku，多选时返回所选择的Sku数组)"%>
<%@ attribute name="autoClose" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="ondblclick事件时，是否自动关闭选择器，默认true"%>
<%@ attribute name="showProductKinds" required="false"  rtexprvalue="true" type="java.lang.String" description="显示的ProductKind,默认显示所有，1表示普通产品,2表示变种，3，产品包；多个用逗号分隔"%>
<%@ attribute name="title" required="false"  rtexprvalue="true" type="java.lang.String" description="选择器标题,默认为SKU选择器;(主要是订单那边需指定为商品选择器)"%>
<%@ attribute name="multiSelect" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="是否多选，默认false,表示否，"%>
<%@ attribute name="catalogId" type="java.lang.Integer" description="只显示指定目录下的产品"%>
<%@ attribute name="virtual" type="java.lang.Integer" description="0表示区分所有显示,1只显示虚拟的,2只显示实体的.默认为0"%>
<div id="productSkuSelector_divDlg${id}">
</div>
<c:if test="${multiSelect}">
<%--加载列表数据后的回调--%>
<c:set var="getDataCallBack" value=",fnSelectedBoxChecked${id}"></c:set>
</c:if>
<script type="text/javascript" defer>
var productSkuSelector_dialog_${id};
var productSkuSelector_ondblclick_callback_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){${id}_show();});
	</c:if>
	if (productSkuSelector_dialog_${id} == undefined){
		productSkuSelector_dialog_${id} = $j("#productSkuSelector_divDlg${id}"); 
	}
	productSkuSelector_ondblclick_callback_${id}=eval(${ondblclick});
}); 

function getProductSkuSelectorData_${id}(){
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

var productSkuSelector_paramData_${id};
var productSkuSelector_title_${id}=__FMT.productSkuSelector_title;
<c:if test="${not empty title}">
productSkuSelector_title_${id}="${title}";
</c:if>
function ${id}_show(paramData)
{
	productSkuSelector_paramData_${id}=paramData;
	if(!(productSkuSelector_dialog_${id}.dialog("isOpen")==true||productSkuSelector_dialog_${id}.dialog("isOpen")==false)){
		productSkuSelector_dialog_${id}.css("visibility","visible");
		fillDivWithPage("productSkuSelector_divDlg${id}",__ctxPath+"/selector/productSkuSelector.html",getProductSkuSelectorData_${id}(),"post");
		productSkuSelector_dialog_${id}.dialog({ title:productSkuSelector_title_${id}, modal: true, height:400, width:900,buttons: {${buttons}}});
	}
	productSkuSelector_dialog_${id}.dialog("open");
}

function ${id}_close()
{
	productSkuSelector_dialog_${id}.dialog("close");
}
function fn${id}GetData(){
	var paraDate=$j("#productSkuSelectorSearch_${id} :input").serializeArray();
	fillDivWithPage("productSkuSelectorDataList_${id}","${ctxPath}/selector/productSkuSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function fnOnGoToPage${id}(){
	var paraDate=$j("#productSkuSelectorDataList_${id} :input").serializeArray();
	fillDivWithPage("productSkuSelectorDataList_${id}","${ctxPath}/selector/productSkuSelectorDataList.html?pagingId=${id}",paraDate${getDataCallBack},"post");
}
function __fnGetObjJsonData_${id}(skuId){
	var tempJsonTxt=$j("#jsonDataList_${id}_"+skuId).text();
	var objData=eval("["+tempJsonTxt+"]");
	return objData[0];
}
<c:choose>
	<c:when test="${not empty multiSelect&&multiSelect}">
		var selectedProductSkuList${id} =new Array();
		function fuSelectProductSku${id}(skuId,val){
			if(val){
				if(val.checked){
					var productSku=__fnGetObjJsonData_${id}(skuId);
					fnAddSelectedProductSku${id}(productSku);
				}else{
					fnRemoveSelectedProductSku${id}(skuId);
				}
			}else{
				var productSku=__fnGetObjJsonData_${id}(skuId);
				fnAddSelectedProductSku${id}(productSku);
				$j("#sel_ch_${id}_"+productSku.productSkuId).attr("checked",true);
			}
		}
		function fnAddSelectedProductSku${id}(productSku){
			var exist=false;
			for(var i=0;i<selectedProductSkuList${id}.length;i++){
				if(selectedProductSkuList${id}[i].productSkuId==productSku.productSkuId)
					exist=true;
			}
			if(!exist){
				selectedProductSkuList${id}.push(productSku);
				$j("#selectedProductSku_${id}").append("<span id='sel_${id}_"+productSku.productSkuId+"' ondblclick='fnRemoveSelectedProductSku${id}("+productSku.productSkuId+")' title='双击移除'>"+productSku.productSkuCode+",</span>");
			}
		}
		function fnRemoveSelectedProductSku${id}(id){
			for(var i=0;i<selectedProductSkuList${id}.length;i++){
				if(selectedProductSkuList${id}[i].productSkuId==id){
					selectedProductSkuList${id}=selectedProductSkuList${id}.slice(0,i).concat(selectedProductSkuList${id}.slice(i+1,selectedProductSkuList${id}.length));
					$j("#sel_${id}_"+id).remove();
					$j("#sel_ch_${id}_"+id).attr("checked",false);
				}
			}
		}
		function fnRemoveAll${id}(){
			selectedProductSkuList${id}=new Array();
			$j("#selectedProductSku_${id}").empty();
			$j("input[type='checkbox'][id^='sel_ch_']",$j("#productSkuSelectorDataList_${id}")).attr("checked",false);
		}
		function fnConfirmSelectedProductSku${id}(){
			if(selectedProductSkuList${id}.length<1)
				alert("请选择Sku");
			if(productSkuSelector_ondblclick_callback_${id})
			      productSkuSelector_ondblclick_callback_${id}.call(this,selectedProductSkuList${id},productSkuSelector_paramData_${id});
			fnRemoveAll${id}();
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
		function fnSelectedBoxChecked${id}(){
			for(var i=0;i<selectedProductSkuList${id}.length;i++){
				var id=selectedProductSkuList${id}[i].productSkuId;
				$j("#sel_ch_${id}_"+id).attr("checked",true);
			}
		}
	</c:when>
	<c:otherwise>
		function fuSelectProductSku${id}(skuId){
			if(productSkuSelector_ondblclick_callback_${id})
				var productSku=__fnGetObjJsonData_${id}(skuId);
			    productSkuSelector_ondblclick_callback_${id}.call(this,productSku,productSkuSelector_paramData_${id});
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	</c:otherwise>
</c:choose>
</script>