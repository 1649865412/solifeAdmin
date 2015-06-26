<%@ page pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="menu.viewMyOrderPickShipping" />
<fmt:message key="order.shipment.action.complete" var="completeShippingTitle" />
<content tag="buttons">
	<c:if test="${not empty orderPickList}">
		<cartmatic:cartmaticBtn btnType="common" onclick="fnPrintPickList()" commonBtnValueKey="orderPickList.action.print.tip"/>
	</c:if>
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/inventory/orderPickList.html">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
			    	<li><a href="#pickPack"><fmt:message key="orderPickList.tab.title"/></a></li>
				</ul>
				<div class="content" id="pickPack">
					<div class="title"><fmt:message key="orderPickList.awaitingPicking"/></div>
					<span style="padding:15px"></span><span id="availableAmountDiv">${availableAmount}</span>
					<br>
					<div class="title"><fmt:message key="orderPickList.createdPickList"/></div>
					<div id="myOrderPickListDiv">
						<c:forEach var="item" items="${myOrderPickList}">
							<span style="padding:15px"></span><a href="${ctxPath}/inventory/orderPickList.html?orderPickListId=${item.orderPickListId}"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
							<br/>
						</c:forEach>
					</div>
					<br/><br/><br/><br/>
					<div>
						<cartmatic:cartmaticBtn btnType="common" onclick="fnCreatePickList()" commonBtnValueKey="orderPickList.action.createPickList"/>
					</div>
					<br/><br/>
				</div>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<fmt:message key="shipment.status.shipped" var="MSG_SHIPPED"/>
<form class="mainForm" id="orderPickListForm" name="orderPickListForm" method="post" action="${ctxPath}/inventory/orderPickList.html">
	<!--listing box start-->
	
		<div><c:if test="${not empty orderPickList}"><fmt:message key="orderPickList.startTime"/>:<fmt:formatDate value="${orderPickList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br/><br/></c:if></div>
		<display:table name="${orderPickList.orderShipments}" cellspacing="0" cellpadding="0" uid="orderShipment"
			class="table-list" export="false" requestURI="">
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.shipmentNo">
				<a onclick="fnNewWindow('${ctxPath}/order/window.html?doAction=edit&salesOrderId=${orderShipment.salesOrder.salesOrderId}&showShipmentId=${orderShipment.orderShipmentId}&tabIndex=1',650,1300);" href="javascript:void(false);" title="<fmt:message key="orderShipment.view"/>">${orderShipment.shipmentNo}</a>
				<c:if test="${not empty orderShipment.trackingNo}"><br><fmt:message key="orderShipment.trackingNo"/>:${orderShipment.trackingNo}</c:if>
			</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.carrierName">
        		${orderShipment.carrierName}<br/>
        		${orderShipment.orderAddress.address1}${emptySpace}${orderShipment.orderAddress.address2}<br/>
        		${orderShipment.orderAddress.country },${orderShipment.orderAddress.state },${orderShipment.orderAddress.city },${orderShipment.orderAddress.section }, &nbsp;${orderShipment.orderAddress.postalcode}<br/>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.customerName">
        		${orderShipment.orderAddress.firstname}${emptySpace}${orderShipment.orderAddress.lastname}
        		<br/>${orderShipment.orderAddress.phoneNumber }
        	</display:column>
        	 <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderShipment.status">
        		<span id="status${orderShipment.shipmentNo}">
        		<c:choose>
					<c:when test="${salesOrder.isOnHold eq 1}"><fmt:message key="order.status.onHold"/></c:when>
					<c:when test="${orderShipment.status eq 10}"><fmt:message key="shipment.status.awaiting.inventory"/></c:when>
					<c:when test="${orderShipment.status eq 20}"><fmt:message key="shipment.status.inventory.assigned"/></c:when>
					<c:when test="${orderShipment.status eq 30}"><fmt:message key="shipment.status.awaiting.payment"/></c:when>
					<c:when test="${orderShipment.status eq 40}"><fmt:message key="shipment.status.picking.available"/></c:when>
					<c:when test="${orderShipment.status eq 50}"><fmt:message key="shipment.status.picking"/></c:when>
					<c:when test="${orderShipment.status eq 60}">${MSG_SHIPPED}</c:when>
					<c:when test="${orderShipment.status eq 80}"><fmt:message key="order.status.80"/></c:when>
				</c:choose>
				</span>
        	</display:column>
        		
		   <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.updateTime">
        		<fmt:formatDate value="${orderShipment.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        	</display:column>
        		
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        		<span style="padding:4px"></span>
        		<a href="javascript:fnPrintPackingSlip('${orderShipment.orderShipmentId}');"><fmt:message key="orderPickList.action.printPickList" /></a><br/>
        		<span style="padding:4px"></span>
        		<a href="javascript:fnPrintReceipt('${orderShipment.orderShipmentId}');"><fmt:message key="orderPickList.action.printShipmentSlip" /></a><br/>
        		<span style="padding:4px"></span>
        		<a href="javascript:fnPrintPickEms('${orderShipment.orderShipmentId}');"><fmt:message key="orderPickList.action.printEMS"/></a><br/>
        		<span style="padding:4px"></span>
        		<c:if test="${orderShipment.status<60}">
        		<a href="javascript:fnCompleteShipping('${orderShipment.shipmentNo}');"><fmt:message key="order.shipment.action.complete"/></a><br/>
        		</c:if>
        		<c:if test="${orderShipment.status eq 50}">
	        		<span id="removeAction${orderShipment.shipmentNo}">
	        		<span style="padding:4px"></span>
	        		<a href="${ctxPath}/inventory/orderPickList.html?doAction=removeFromPickList&orderPickListId=${orderPickList.orderPickListId}&orderShipmentId=${orderShipment.orderShipmentId}"><fmt:message key="orderPickList.action.removeFromPickList" /></a>
        			</span>
        		</c:if>
        	</display:column>
		</display:table>
	
</form>
<div class="blank24" style="height:100px"></div>
<div class="box-list" style="float:right;width:28%;">
	<display:table name="${myHistoryOrderPickList}" cellspacing="0" cellpadding="0" uid="item"
		class="table-list" export="false" requestURI="">
	   <display:column sortable="false" headerClass="data-table-title"
       		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.myHistoryOrderPickList">
			<a href="${ctxPath}/inventory/orderPickList.html?orderPickListId=${item.orderPickListId}"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
       	</display:column>
	</display:table>
	
</div>
<fmt:message key="orderPickList.action.createPickList" var="createPickListTitle"/>
<fmt:message key="common.message.cancel" var="buttonCancel"/>
<app:ui_dialog id="CreatePickListDlg" title="${createPickListTitle}" width="560" height="600"
	url="${ctxPath}/inventory/picklist/dialog.html?doAction=createPickList" buttons="'${createPickListTitle}':fnCompleteCreatePickList, '${buttonCancel}':fnCloseDlg">
</app:ui_dialog>
<fmt:message key="common.message.complete" var="buttonComplete"/>
<fmt:message key="common.message.cancel" var="buttonCancel"/>
<app:ui_dialog id="CompleteShippingDlg" title="${completeShippingTitle}" width="550" height="400" buttons="'${buttonComplete}':fnSaveCompleteShipping, '${buttonCancel}':dlgCompleteShippingDlg_close">
		<br/>
		<div id="notFoundDiv" style="display:none">
			<span style="padding:15px"></span><span style="color:red"><fmt:message key="order.shipment.action.validate.tip"></fmt:message></span>
			<br/><br/>
		</div>
		<div><span style="padding:15px"></span><fmt:message key="orderPickList.tip"/></div>
		<form method="post" id="completeShippingForm">
		<table cellSpacing="3" cellPadding="0" width="100%" border="0">
	 		<tr height="40px">
				<td width="20%" align="right">
					<StoreAdmin:label key="orderShipment.shipmentNo"/>
				</td>
				<td>
					<input type="text" id="shipmentNo" name="shipmentNo" value="" class="Field150" /><span style="padding:6px"></span>
					<cartmatic:iconBtn icon="check" textKey="order.shipment.action.validate" onclick="fnValidateShipment();" />
				</td>
			</tr>
		</table>
		<div id="detailDiv" style="display:none">
		<table cellSpacing="2" cellPadding="0" width="100%" border="0">
	 		<tr>
				<td width="20%" align="right"><fmt:message key="salesOrder.customerName" /></td>
				<td id="customerName"></td>
			</tr>
			<tr>
				<td width="20%" align="right"><fmt:message key="orderShipment.shippingAddress"/></td>
				<td id="shippingAddress"></td>
			</tr>
			<tr>
				<td width="20%" align="right"><fmt:message key="orderShipment.carrierName"/></td>
				<td>
					<select id="carrierName" name="carrierName" class="Field150" >
						<option value=""></option>
						<option value="EMS">EMS邮政</option>
						<option value="AirMail">顺丰</option>
						<option value="UPS">圆通</option>
						<option value="DHL">中通</option>
						<option value="TNT">申通</option>
						<option value="FedEx">FedEx</option>
						<option value="Aramex">宅急送</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%" align="right"><fmt:message key="orderShipment.trackingNo"/></td>
				<td><input type="text" id="trackingNo" name="trackingNo" value="" maxlength="64" class="Field150"/></td>
			</tr>
			<tr >
				<td width="20%" align="right"><fmt:message key="orderShipment.shippingCostPaid"/></td>
				<td><input type="text" id="shippingCostPaid" name="shippingCostPaid" value="" maxlength="64" class="Field150"/></td>
			</tr>
		</table>
		<table border="1" width="100%">
			<tbody id="shipment_skuItems"></tbody>
		</table>
		</div>
		</form>
</app:ui_dialog>
<c:set var="ui_tabs" value="true" scope="request"/>
<script type="text/javascript">
   function fnCreatePickList(){
   		dlgCreatePickListDlg_show(null);
   }
   
   function fnCompleteCreatePickList(){
   		var parameters = $j("#createPickListForm").serialize();
    	$j.post(__ctxPath+'/inventory/orderPickList.html?doAction=completeCreatePickList', parameters, fnCompleteCreatePickListCallbackHandler,"json");
   }
    
   function fnCompleteCreatePickListCallbackHandler(result){
    	if(result.status==1){
        	var data=result.data;
	    	fnCloseDlg();
	    	var appendString = "<span style='padding:15px'></span><a href='${ctxPath}/inventory/orderPickList.html?orderPickListId="+data.orderPickListId+"'>"+data.createTime+"</a><br/>"
	    	<%--å¢å æçå¤è´§å--%>
	    	var myOrderPickListDiv = $j("#myOrderPickListDiv");
	    	myOrderPickListDiv.html(myOrderPickListDiv.html()+appendString);
	    	$j("#availableAmountDiv").text(data.availableAmount);
	    }else{
	    	alert('<fmt:message key="orderPickList.tip.dataDirty"/>');
    		fnCloseDlg();
    		dlgCreatePickListDlg_show(null);
	    }
   }
   
   function fnCloseDlg(){
   		dlgCreatePickListDlg_close();
   }
</script>
<!-- script type="text/javascript" src="${ctxPath}/scripts/jquery/ui/ui.autocomplete.js" / -->
<script type="text/javascript">
//applyValidate($("shipmentNo"),"required");
var hasChangeSkuitemQty = false;
function fnCompleteShipping(_shipmentNo){
	$j("#notFoundDiv").css("display", "none");
	$j("#detailDiv").css("display", "none");
	$j("#carrierName").val("");
	$j("#trackingNo").val("");	
	$j("#shippingCostPaid").val("");
	dlgCompleteShippingDlg_show(null);
	if(_shipmentNo)
		$j("#shipmentNo").val(_shipmentNo);
	dlgCompleteShippingDlg_disableBtn('${buttonComplete}');
	//$j("#shipmentNo").autocomplete(__ctxPath+'/order/salesOrder.html?doAction=autoCompleteShipmentNo', {});
}

function fnValidateShipment(){
	if(!validateForm( document.getElementById("completeShippingForm") )){
		alert(__vaMsg.notPass);
		return;
	}
	
	var parameters = {shipmentNo:$j("#shipmentNo").val()};
	$j.post(__ctxPath+'/inventory/orderPickList.html?doAction=validateShipment', parameters, fnValidateShipmentCallbackHandler, "json");
}

function fnValidateShipmentCallbackHandler(result){
	if(result.status==1){
		var data=result.data;
		$j("#customerName").text(data.firstName+" "+data.lastName);
		$j("#shippingAddress").text(data.shippingAddress);
		//$j("#carrierName").text(data.carrierName);
		$j("#notFoundDiv").css("display", "none");
		$j("#detailDiv").css("display", "block");
		dlgCompleteShippingDlg_enableBtn('${buttonComplete}');
		$j("#shipment_skuItems").empty();
		$j.each(data.skuItems,function(){
			var content="<tr>";
			content+="<td>"+this.productName + "<br/>Sku:"+ this.skuCode+"<br/>"+this.accessories+"</td>"
			content+="<td><input type='text' size='3' name='"+ this.id +"_qty' onChange='hasChangeSkuitemQty=true' value='"+this.quantity+"'/></td>"
			content+="</tr>";
			$j("#shipment_skuItems").append(content);
		});
	}else{
		$j("#detailDiv").css("display", "none");
		$j("#notFoundDiv").css("display", "block");
	}
}

function fnSaveCompleteShipping(){
	if (hasChangeSkuitemQty)
		if (!confirm("Are you sure to split this order shipment?"))
		{
			return;
		}
	if ($("carrierName").value == "")
		if (!confirm("Are you sure to complete this order shipment without input Carrier?"))
		{
			return;
		}
	if ($("trackingNo").value == "")
		if (!confirm("Are you sure to complete this order shipment without input trackingNo?"))
		{
			return;
		}
	if ($("shippingCostPaid").value == "")
		if (!confirm("Are you sure to complete this order shipment without input shipping cost?"))
		{
			return;
		}
	//var parameters = {shipmentNo:$j("#shipmentNo").val().trim(), 
	//		trackingNo:$j("#trackingNo").val(), 
	//		shippingCostPaid:$j("#shippingCostPaid").val()};
	var parameters = $j("#completeShippingForm").serialize();
	$j.post(__ctxPath+'/inventory/orderPickList.html?doAction=completeShipping', parameters, fnCompleteShippingCallbackHandler,"json");
}

function fnCompleteShippingCallbackHandler(result){
	if(result.status==1){
		dlgCompleteShippingDlg_close();
		$j("#status"+$j("#shipmentNo").val().trim()).html("${MSG_SHIPPED}");
		$j("#removeAction"+$j("#shipmentNo").val().trim()).remove();
		fnClear();
	}
	sysMsg(result.msg,result.status!=1);
}

function fnClear(){
	$j("#shipmentNo").val("");
	$j("#carrierName").val("");
	$j("#trackingNo").val("");
	$j("#customerName").text("");
	$j("#shippingAddress").text("");
	//$j("#carrierName").text("");
	$j("#notFoundDiv").css("display", "none");
	$j("#detailDiv").css("display", "none");
}

function fnPrintPickList(){
	var formObj = document.getElementById("orderPickListForm");
	formObj.target="_blank";
	if(document.getElementById("doAction"))
		$j("#doAction").val("printPickList");
	else
		fnAddHiddenField("doAction","printPickList",formObj);
	fnAddHiddenField("orderPickListId", '${orderPickList.orderPickListId}', formObj);
	formObj.submit();
}

function fnPrintPickEms(orderShipmentId){
	var formObj = document.getElementById("orderPickListForm");
	formObj.target="_blank";
	if(document.getElementById("doAction"))
		$j("#doAction").val("printEMS");
	else
		fnAddHiddenField("doAction","printEMS",formObj);
	fnAddHiddenField("printable","true",formObj)
    fnAddHiddenField("orderShipmentId", orderShipmentId, formObj);
	formObj.submit();
}

function  fnPrintPackingSlip(orderShipmentId){
	var formObj = document.getElementById("orderPickListForm");
	formObj.target="_blank";
	if(document.getElementById("doAction"))
		$j("#doAction").val("printPackingSlip");
	else
		fnAddHiddenField("doAction","printPackingSlip",formObj);
	fnAddHiddenField("orderPickListId", '${orderPickList.orderPickListId}', formObj);
	
	if(document.getElementById("orderShipmentId"))
		document.getElementById("orderShipmentId").value="";
	if(orderShipmentId)
		fnAddHiddenField("orderShipmentId", orderShipmentId, formObj);
	formObj.submit();
}

function fnPrintReceipt(orderShipmentId){
	var formObj = document.getElementById("orderPickListForm");
	formObj.target="_blank";
	if(document.getElementById("doAction"))
		$j("#doAction").val("printReceipt");
	else
		fnAddHiddenField("doAction","printReceipt",formObj);
	fnAddHiddenField("orderPickListId", '${orderPickList.orderPickListId}', formObj);
	
	if(document.getElementById("orderShipmentId"))
		document.getElementById("orderShipmentId").value="";
	if(orderShipmentId)
		fnAddHiddenField("orderShipmentId", orderShipmentId, formObj);
	formObj.submit();
}
</script>