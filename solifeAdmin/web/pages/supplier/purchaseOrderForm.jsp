<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%><head>
</head>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="common" onclick="return fnDoAction(this,'confirmOrder','确认采购?');" commonBtnValue="确认采购"
	 isDisabled="${purchaseOrder.poStatus != 0}"/>
	<cartmatic:cartmaticBtn btnType="common" onclick="return dlgFinishPoDlg_show(null);" commonBtnValue="完成采购"
	 isDisabled="${purchaseOrder.poStatus == 3 || purchaseOrder.poStatus == 4}"/>
	<cartmatic:cartmaticBtn btnType="common" onclick="return fnDoAction(this,'cancelOrder','确认取消采购?');" commonBtnValue="取消采购"
	 isDisabled="${purchaseOrder.poStatus == 3 || purchaseOrder.poStatus == 4}"/>
	<cartmatic:cartmaticBtn btnType="common" onclick="return dlgAppendPoCommentsDlg_show(null);" commonBtnValue="添加备注" />
	<cartmatic:cartmaticBtn btnType="common" onclick="return fnPrint();" commonBtnValue="打印" />	
</content>
<app:showBindErrors bindPath="purchaseOrder.*" />

<app:pageHeading entityName="${purchaseOrder.purchaseOrderName}" entityHeadingKey="purchaseOrderDetail.heading" />
<div id="printArea">
<form:form method="post" cssClass="mainForm" id="purchaseOrder" commandName="purchaseOrder"
			action="${ctxPath}/supplier/purchaseOrder.html" onsubmit="return validatePurchaseOrder(this);">
		<form:hidden path="version" />
		<form:hidden path="poResult" />
		<form:hidden path="supplierId" />
		<form:hidden path="supplierName" />
		<input type="hidden" id="purchaseOrderId" name="purchaseOrderId" value="${purchaseOrder.purchaseOrderId}"/>
	 <table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
	 	<tr>
			<th colspan="2">
			采购单:${purchaseOrder.purchaseOrderNo}
			</th>
		</tr>
		<tr>
 		<td class="FieldLabel"><StoreAdmin:label key="purchaseOrder.poStatus" /></td>
 		<td><fmt:message key="purchaseOrder.poStatus${purchaseOrder.poStatus}"/></td>
 		</tr>
 		<tr>
 		<td class="FieldLabel"><StoreAdmin:label key="purchaseOrder.trackingNo" /></td>
 		<td>${purchaseOrder.trackingNo}</td>
 		</tr>
 		<tr>
 		<td class="FieldLabel"><StoreAdmin:label key="purchaseOrder.poResult" /></td>
 		<td><fmt:message key="purchaseOrder.poResult${purchaseOrder.poResult}"/></td>
 		</tr>
 		<app:userName2 label="common.message.createBy" userId="${purchaseOrder.createBy}" />
  		<app:formText label="common.message.createTime" value="${purchaseOrder.createTime}" />
  		<tr>
  		<td class="FieldLabel"><StoreAdmin:label key="purchaseOrder.comments" /></td>
  		<td id="comments">
  			${purchaseOrder.comments}<c:if test="${empty purchaseOrder.comments}">N/A</c:if>
  		</td>
  		</tr>
  	 </table>	 
	
	 <table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
	 	<tr>
			<th colspan="2">供应商信息</th>
		</tr>
		<tr>
			<td class="FieldLabel">供应商名</td>
			<td>${purchaseOrder.supplier.supplierName}</td>
		</tr>
		<tr>
			<td class="FieldLabel">供应商代码</td>
			<td>${purchaseOrder.supplier.supplierCode}</td>
		</tr>
		<tr>
			<td class="FieldLabel"><fmt:message key="supplier.email" /></td>
			<td>${purchaseOrder.supplier.email}</td>
		</tr>
		<tr>
			<td class="FieldLabel"><fmt:message key="supplier.webSite" /></td>
			<td>${purchaseOrder.supplier.webSite}</td>
		</tr>
		<tr>
			<td class="FieldLabel"><fmt:message key="supplier.address" /></td>
			<td>${purchaseOrder.supplier.address}</td>
		</tr>
  	</table>

	
</form:form>
<table width="100%" cellspacing="0" cellpadding="0" border="0" class="table-content">
	<tr>
		<th>
			采购明细
		</th>
	</tr>
	<tr>
		<td>
		<display:table name="${purchaseOrder.purchaseOrderItems}" cellspacing="0" cellpadding="0" uid="purchaseOrderItemItem"
			 export="false" requestURI="">
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" title="图片">
        		<cartmatic:img url="${purchaseOrderItemItem.orderSku.productSku.image}" id="productImage" mediaType="product" size="c" width="130" height="130"></cartmatic:img>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrderItem.productName">
        		${purchaseOrderItemItem.productName} &nbsp;
        		<product:orderSkuOption displaySkuOptions="${purchaseOrderItemItem.skuDisplay}"/>
				<product:showAccessories accessories="${purchaseOrderItemItem.accessories}"/>
				<br/>
				ItemNo:${purchaseOrderItemItem.orderSku.productSkuCode}
				<br/>
				订单号:<a onclick="fnNewWindow('${ctxPath}/order/window.html?doAction=edit&salesOrderId=${purchaseOrderItemItem.orderSku.orderShipment.salesOrder.salesOrderId}',650,1300);" href="javascript:void%20${purchaseOrderItemItem.orderSku.orderShipment.salesOrder.salesOrderId}">
        		${purchaseOrderItemItem.orderSku.orderShipment.salesOrder.orderNo}</a>
        		<br>
        		<table cellspacing="0" cellpadding="0" class="table-list" id="audit_${purchaseOrderItemItem.orderSku.orderSkuId}">
				<tr>
				<td width="15%"><fmt:message key="common.message.createTime"/></td>
				<td width="10%"><fmt:message key="common.message.createBy"/></td>
				<td width="70%"><fmt:message key="orderAudit.detail"/></td>				
				</tr>
				<c:set var="countAudit" value="0"/>
				<c:forEach var="orderAudit" items="${purchaseOrderItemItem.orderSku.orderShipment.salesOrder.orderAudits}">
				<c:if test="${orderAudit.transactionType!='SYSTEM'}">
				<c:set var="countAudit" value="${countAudit + 1}"/>
				<tr>
				<td><fmt:formatDate value="${orderAudit.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><span id="addedByTd${orderAudit.orderAuditId}">${orderAudit.addedBy}</span></td>
				<td><span id="detailTd${orderAudit.orderAuditId}">${orderAudit.detail}</span></td>
				</tr>
				</c:if>
				</c:forEach>
				</table>
				<c:if test="${countAudit == 0}"><script type="text/javascript">$j("#audit_${purchaseOrderItemItem.orderSku.orderSkuId}").hide();</script></c:if>
        	</display:column>
        	 <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrderItem.purchaseQuantity">
        		${purchaseOrderItemItem.purchaseQuantity}/
        		<input type="text" id="${purchaseOrderItemItem.purchaseOrderItemId}_passedQuantity" size="2" 
        		value="${0 == purchaseOrderItemItem.passedQuantity ? purchaseOrderItemItem.purchaseQuantity : purchaseOrderItemItem.passedQuantity}"/>
        	</display:column>		    
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrderItem.listPrices">
        		<system:CurrencyForRate value="${purchaseOrderItemItem.orderSku.discountPrice}"/>/
        		<input type="text" id="${purchaseOrderItemItem.purchaseOrderItemId}_purchasePrice" size="4" value="${purchaseOrderItemItem.purchasePrice}"/>
        	</display:column>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        		<c:if test="${purchaseOrderItemItem.status == 2}">完成</c:if>
        		<c:if test="${purchaseOrderItemItem.status !=2}">
        		<span id="${purchaseOrderItemItem.purchaseOrderItemId}_action">
        		<a href="javascript:void(0);" onclick="return fnPoSave('${purchaseOrderItemItem.purchaseOrderItemId}')">修改</a>|
        		<a href="javascript:void(0);" onclick="return fnPoComplete('${purchaseOrderItemItem.purchaseOrderItemId}')">确认收货</a>|
        		<a href="javascript:void(0);" onclick="return fnDoRemovePoItem('${purchaseOrderItemItem.purchaseOrderItemId}')">删除</a>
        		</span>
        		</c:if>
        	</display:column>
		</display:table>
	</td>
	</tr>
</table>
</div>
<v:javascript formName="purchaseOrder" staticJavascript="false" />
<script type="text/javascript">
function fnDoRemovePoItem($id)
{
	var _form=$j("form.mainForm").get(0);
	if (confirm("确认取消?"))
	{
		fnAddHiddenField("purchaseOrderItemId", $id, _form);
		fnSubmitActionForm(_form, "removePoItem");
	}
}

function fnPoComplete($itemId)
{
	var _price = $($itemId+"_purchasePrice").value;
	var _passQty = $($itemId+"_passedQuantity").value;
	if (confirm("确认修改并收货?")){	
		$j.post(__ctxPath+"/supplier/purchaseOrder.html?doAction=savePoItem",{purchaseOrderItemId:$itemId,price:_price,passQty:_passQty},function(result){
			sysMsg(result.msg,result.status!=1);
			if(result.status==1){
				$j.post(__ctxPath+"/supplier/purchaseOrder.html?doAction=doCompleted",{purchaseOrderItemId:$itemId},function(result){
						$j("#"+$itemId+"_action").replaceWith("完成");
						sysMsg(result.msg,result.status!=1);
					},"json");
			}
		},"json");
	}
}


function fnPoSave($itemId)
{
	var _price = $($itemId+"_purchasePrice").value;
	var _passQty = $($itemId+"_passedQuantity").value;
	if (confirm("确认修改?")){	
		$j.post(__ctxPath+"/supplier/purchaseOrder.html?doAction=savePoItem",{purchaseOrderItemId:$itemId,price:_price,passQty:_passQty},function(result){
				sysMsg(result.msg,result.status!=1);
			},"json");
	}
}
function fnAppendPoComments()
{
	if (confirm("确认添加备注?")){
		$j.post(__ctxPath+"/supplier/purchaseOrder.html?doAction=doAppendPoComments",{purchaseOrderId:$("purchaseOrderId").value,comment:$j("#newComment").val()},function(result){
			sysMsg(result.msg,result.status!=1);
			if(result.status==1){
				$j("#comments").html(result.data);
				$j("#newComment").val("");
				dlgAppendPoCommentsDlg_close();
			}
		},"json");
	}
}
function fnFinishPo()
{
	if (confirm("确认完成采购?"))
	{
		$j("#poResult").val($j("#chooseResult").val());
		fnDoAction(null,"confirmCompleted");
	}
}
function fnPrint()
{
    
	var win=window.open("about:blank");//打开一个空页面
    win.document.write($("printArea").innerHTML);
    win.print();
    win.close();
}
</script>
<app:ui_dialog id="FinishPoDlg" title="完成采购" width="256" height="120" 
	buttons="'确认':fnFinishPo, '取消':dlgFinishPoDlg_close">
	采购结果:
	<select id="chooseResult">
	<option value="1" selected="selected"><fmt:message key="purchaseOrder.poResult1"/></option>
	<option value="2"><fmt:message key="purchaseOrder.poResult2"/></option>
	<option value="3"><fmt:message key="purchaseOrder.poResult3"/></option>
	<option value="4"><fmt:message key="purchaseOrder.poResult4"/></option>
	</select>
</app:ui_dialog>
<app:ui_dialog id="AppendPoCommentsDlg" title="添加备注" width="256" height="180" 
	buttons="'确认':fnAppendPoComments, '取消':dlgAppendPoCommentsDlg_close">
	备注<br/>
	<textarea id="newComment" rows="4" cols="26"></textarea>
</app:ui_dialog>