<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="salesOrderList.heading" />
<app:ui_leftMenu>
	    <div class="sidebar-left">
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#orderView"><fmt:message key="salesOrderList.tab.view"/></a></li>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div id="orderView" class="content">
					<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center">
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true});" href="javascript:void(false);"><fmt:message key="search.filter.all"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'ask4Cancel'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.ask4Cancel"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'awaitingPicking'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.awaitingPicking"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'picking'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.picking"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'partiallyShipped'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.partiallyShipped"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'finished'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.finished"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'partiallyPaid'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.partiallyPaid"/></a><td></tr>
						<%-- <tr height="30px"><td><a href="${ctxPath}/order/salesOrder.html?search=confirmedByRobot&btnSearch=true"><fmt:message key="salesOrder.view.confirmedByRobot"/></a><td></tr>--%>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'newMessages'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.newMessages"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'hasProblem'});" href="javascript:void(false);"><fmt:message key="salesOrder.view.hasProblem"/></a><td></tr>
						<tr><td><hr/><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:1});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_1"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:2});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_2"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:3});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_3"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:7});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_7"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:9});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_9"/></a><td></tr>
						<tr height="30px"><td><a onclick="fnSearchSalesOrder({btnSearch:true,search:'payment',paymentType:10});" href="javascript:void(false);"><fmt:message key="salesOrder.view.paymentType_10"/></a><td></tr>
					</table>
					<br/><br/><br/><br/>
				</div>
			<form method="post" action="${ctxPath}/order/salesOrder.html" onsubmit="return false;">
			<div id="glSearchBar">
				<search:input attrNameKey="salesOrder.orderNo" attrPath="so.orderNo" datatype="String" operator="LIKE"/>
				<search:input attrNameKey="salesOrder.customerEmail" attrPath="so.customerEmail" datatype="String" operator="LIKE"/>
				<search:input attrNameKey="salesOrder.customerFirstname" attrPath="so.customerFirstname" datatype="String" operator="LIKE"/>
				<search:input attrNameKey="salesOrder.customerLastname" attrPath="so.customerLastname" datatype="String" operator="LIKE"/>
				<div class="title"><fmt:message key="salesOrder.store"/></div>
				<div>
					<select name="COL@so.store.storeId@Integer@EQ" id="storeId" style="width:150px">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}">${storeItem.value.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="title"><fmt:message key="order.status.onHold"/></div>
				<div>
					<select name="COL@so.isOnHold@Short@EQ" id="isOnHold" style="width:150px">
						<option value=""></option>
						<option value="1">Yes</option>
						<option value="0">No</option>
					</select>
				</div>
				<div class="title"><fmt:message key="salesOrder.orderStatus"/></div>
				<div>
					<select name="COL@so.orderStatus@Short@EQ" id="orderStatus" style="width:150px" onchange="fnChangeOrderStatus()">
						<option value=""></option>
						<option value="10"><fmt:message key="order.status.10"/></option>
						<option value="20"><fmt:message key="order.status.20"/></option>
						<option value="30"><fmt:message key="order.status.30"/></option>
						<option value="40"><fmt:message key="order.status.40"/></option>
						<option value="80"><fmt:message key="order.status.80"/></option>
					</select>
					<div id="finishTime" style="display:none;">
						&nbsp;完成时间
						<div style="height:28px">
						<fmt:message key="common.message.from"/>
						<input type="text" id="updateStartTime" name="COL@so.updateTime@Date_Begin@GTE" readonly="true" value=""  style="width:100px"">
						<app:ui_datePicker outPut="updateStartTime" />
						</div>
						<div style="height:28px">
						<fmt:message key="common.message.to"/>
						<input type="text" id="updateEndTime" name="COL@so.updateTime@Date_End@LTE" readonly="true" style="width:100px">
						<app:ui_datePicker outPut="updateEndTime" />
						</div>
					</div>
				</div>
				<div class="title"><fmt:message key="salesOrder.paymentStatus"/></div>
				<div>
					<select name="COL@so.paymentStatus@Short@EQ" id="paymentStatus" style="width:150px">
						<option value=""></option>
						<option value="10"><fmt:message key="payment.status.10"/></option>
						<option value="20"><fmt:message key="payment.status.20"/></option>
						<option value="30"><fmt:message key="payment.status.30"/></option>
					</select>
				</div>
				<div class="title">支付方式</div>
				<div>
					<select name="COL@so.paymentMethodId@Integer@EQ" id="paymentMethodId" style="width:150px">
						<option value=""></option>
						<c:forEach items="${paymentMethodList}" var="paymentMethod">
						<option value="${paymentMethod.paymentMethodId}">${paymentMethod.paymentMethodDetail}</option>
						</c:forEach>
					</select>
				</div>
				<search:input attrNameKey="salesOrder.ipAddress" attrPath="so.ipAddress" datatype="String" operator="RLIKE"/>
				<div class="title"><fmt:message key="salesOrder.placeOrderTime"/></div>
				<div>
					<div class="date">
                        <fmt:message key="common.message.from"/>&nbsp;
                        <input type="text" id="startTime" name="COL@so.createTime@Date_Begin@GTE" readonly="true" style="width:100px"">
						<app:ui_datePicker outPut="startTime" />
					</div>
					<div class="date">
						<fmt:message key="common.message.to"/>&nbsp;
						<input type="text" id="endTime" name="COL@so.createTime@Date_End@LTE" readonly="true" style="width:100px">
						<app:ui_datePicker outPut="endTime" />
					</div>
				</div>
				<div class="blank10"/></div>
				<div>
					<input type="hidden" id="btnSearch" name="btnSearch" value="Search" />
					<input type="submit" id="SearchButton" name="SearchButton" onclick="fnSearchSalesOrder();" value="<fmt:message key="button.search"/>" class="btn-search"/>
					<input type="RESET" id="SearchReset" name="SearchReset" value="<fmt:message key="button.clear"/>" class="btn-search"/>
				</div>
                <div class="blank24"></div>
			</div>
			</form>
		</div>
</app:ui_leftMenu>
<div class="box-list">
	<div id="tab_container">
		<ul>
			<li><a href="#salesOrderListContent">订单列表</a></li>
		</ul>
		<div id="salesOrderListContent">
			<div class="blank10"></div>
			<div id="btn-space" style="display: none;">
				<cartmatic:cartmaticBtn btnType="common" onclick="fnCreatePickList()" commonBtnValueKey="orderPickList.action.createPickList"/>
				<cartmatic:cartmaticBtn btnType="refurbishList" inputType="button" onclick="fnRefurbishSalesOrderList();" inputName="btn_refurbishList"/>
			</div>
			<div id="salesOrderListContentBody">
				<%@ include file="include/salesOrderListContentBody.jsp"%>
			</div>
		</div>
	</div>
	
	<img src="${ctxPath}/images/icon/loading.gif" style="display: none">
	<form id="salesOrderListForm2" method="post" action="${ctxPath}/order/salesOrder.html"></form>
</div>
<%--增加创建备货单dlg--%>
<fmt:message key="orderPickList.action.createPickList" var="createPickListTitle"/>
<fmt:message key="common.message.cancel" var="buttonCancel"/>
<app:ui_dialog id="CreatePickListDlg" title="${createPickListTitle}" width="560" height="600"
	url="${ctxPath}/inventory/picklist/dialog.html?doAction=createPickList" buttons="'${createPickListTitle}':fnCompleteCreatePickList, '${buttonCancel}':fnCloseDlg">
</app:ui_dialog>
<script type="text/javascript">
highlightTableRows("salesOrderItem");
$j(document).ready(function(){
    	var $tabs = $j("#left_menu_tabs").appTabs();
    	<c:if test="${sc.param['SRH@filter'] == 'default'}">$tabs.appTabs("select", 1);</c:if>
		btnToP();
});

function fnSearchSalesOrder(paraDate){
	if(!paraDate){
		var paraDate=$j("#glSearchBar :input").serializeArray();
	}
	fnRefurbishSalesOrderList(paraDate);
	tab_container.appTabs( "select",0);
	//$j("#selectCategoryTitle").remove();
	//$j("#productListBtns").show();
}
function fnRefurbishSalesOrderList(paraDate){
	if(!paraDate){
		paraDate=$j("#salesOrderListContentBody :input").serializeArray();
	}
	fillDivWithPage($j("#salesOrderListContentBody"),"${ctxPath}/order/dialog.html?doAction=search",paraDate);
}

function fnOnGoToPage(){
	fnRefurbishSalesOrderList();
}

function fnOpenSalesOrderForm(id,code,params){
	if(params){
		params="&"+params;
	}else{
		params="";
	}
	fnCreateTabWindow(id,'#'+code,'order/window.html','doAction=edit&salesOrderId='+id+""+params);
}

var formObj = this.document.getElementById("salesOrderListForm2");
function fnUnlock(salesOrderId){
	formObj.action="${ctxPath}/order/dialog.html?doAction=unlock&salesOrderId="+salesOrderId+"&from=list";
	formObj.submit();
}
function fnChangeOrderStatus()
{
	if ($j("#orderStatus").val()=="30")
	{
		$j("#finishTime").show();
	}
	else
	{
		$j("#finishTime").hide();
		$("updateStartTime").value="";
		$("updateEndTime").value="";
	}
}

function fnCreatePickList(){
		dlgCreatePickListDlg_show(null);
}

function fnCompleteCreatePickList(){
	var parameters = $j("#createPickListForm").serialize();
	$j.post(__ctxPath+'/inventory/orderPickList.html?doAction=completeCreatePickList', parameters, fnCompleteCreatePickListCallbackHandler,"json");
}

function fnCompleteCreatePickListCallbackHandler(result){
	if(result.status==1){
    	fnCloseDlg();
    	window.location.href=__ctxPath+'/inventory/orderPickList.html';
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