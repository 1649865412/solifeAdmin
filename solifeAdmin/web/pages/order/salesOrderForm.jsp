<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${salesOrder.salesOrderName}" entityHeadingKey="salesOrderDetail.heading" />
<c:set var="RESEND_BTN_HIDDEN" value="${salesOrder.orderStatus eq 30}"></c:set>
<div id="btn-space" style="display: none;">
	<c:if test="${salesOrder.lockedByMyself or orderShipment.status le 20}">
		<cartmatic:cartmaticBtn btnType="save" onclick="ps('tabIframeId_${param.tid}','fnSaveOrder();');"/>
	</c:if>
	<c:if test="${salesOrder.lockOrderPermission}">
		<cartmatic:cartmaticBtn inputName="lock" btnType="common" onclick="ps('tabIframeId_${param.tid}','fnLock();');"  isDisabled="${!salesOrder.lockOrderPermission}" commonBtnValueKey="salesOrder.lock" />
	</c:if>
	<c:if test="${salesOrder.unlockOrderPermission}">
		<cartmatic:cartmaticBtn inputName="unlock" btnType="common" onclick="ps('tabIframeId_${param.tid}','fnUnlock();');" isDisabled="${!salesOrder.unlockOrderPermission}" commonBtnValueKey="salesOrder.unlock" />
	</c:if>
	<c:if test="${(salesOrder.lockedByMyself or orderShipment.status le 20)||salesOrder.lockOrderPermission||salesOrder.unlockOrderPermission}">
	</c:if>
		<cartmatic:cartmaticBtn btnType="common" onclick="ps('tabIframeId_${param.tid}','fnCancelOrder();');" isDisabled="${not salesOrder.cancelOrderPermission}" commonBtnValueKey="order.action.cancel" />
		<cartmatic:cartmaticBtn btnType="common" onclick="ps('tabIframeId_${param.tid}','fnHoldOrder();');" isDisabled="${not salesOrder.holdOrderPermission}" commonBtnValueKey="order.action.place.on.hold" />
		<cartmatic:cartmaticBtn btnType="common" onclick="ps('tabIframeId_${param.tid}','fnResumeOrder();');" isDisabled="${not salesOrder.resumeOrderPermission}" commonBtnValueKey="order.action.resume" />
	<cartmatic:cartmaticBtn inputName="resendConfirmEmail" btnType="common" onclick="ps('tabIframeId_${param.tid}','fnResendConfirmEmail(this);');" isDisabled="${RESEND_BTN_HIDDEN||salesOrder.orderStatus==80}" commonBtnValueKey="salesOrder.resendConfirmEmail" />
	<c:if test="${salesOrder.paymentStatus eq 10}"><%-- 未支付之前都可以赠送礼券 --%>
		<cartmatic:cartmaticBtn inputName="resendConfirmEmail" btnType="common" onclick="ps('tabIframeId_${param.tid}','fnCreateGc();');"  isDisabled="${RESEND_BTN_HIDDEN||salesOrder.orderStatus==80}" commonBtnValueKey="salesOrder.sendGcEmail" />
	</c:if>
</div>
<div class="top-showMsg-close" id="showMsg_id" style="display: none;"></div>
<div class="blank10"></div>
<div class="tab" id="salesOrderTab">
    <ul class="sub-tab">
	    <li><a href="#basicInfo"><fmt:message key="order.tab.basis" /></a></li>
	    <li><a href="#detailInfo"><fmt:message key="order.tab.detail" /></a></li>
		<li><a href="#paymentInfo"><fmt:message key="order.tab.payment" /></a></li>
		<li><a href="#returnInfo"><fmt:message key="order.tab.return" /></a></li>
		<li><a href="#message"><fmt:message key="order.tab.message" /><c:if test="${salesOrder.sumAdminNewMsgs > 0}"><b>(${salesOrder.sumAdminNewMsgs})</b></c:if></a></li>
		<li><a href="#geoInfo" onclick="setTimeout(loadGoogleMapScript, 100);"><fmt:message key="order.tab.geoinfo" /></a></li>
	</ul>
	<div class="blank10"><input type="hidden" id="tid" value="${param.tid}" /></div>
	<div id="basicInfo">
		<%@ include file="include/basicInfo.jspf"%>
	</div>
	<div id="detailInfo">
		<form:form method="post" cssClass="mainForm" id="salesOrder" commandName="salesOrder"
				action="${ctxPath}/order/window.html">
			<form:hidden path="version" />
			<input type="hidden" id="formSubmit" name="formSubmit" value="1"/>
			<input type="hidden" name="isEdit" value="${param.isEdit}"/>
			<%@ include file="include/detailInfo.jspf"%>
		</form:form>
	</div>
	<div id="paymentInfo">
		<%@ include file="include/paymentInfo.jspf"%>
	</div>
	<div id="returnInfo">
		<%@ include file="include/returnInfo.jspf"%>
	</div>			
	<div id="message" style="min-height: 520px;">
		<%@ include file="include/messageInfo.jspf"%>
	</div>
	<div id="geoInfo">
		<%@ include file="include/geoInfo.jspf"%>
	</div>
</div>
<c:set var="ui_QuickTip" value="true" scope="request"/>
<script type="text/javascript">
    autoApplyValidate(document.getElementById("salesOrder"));
    var _salesOrderId = "${salesOrder.salesOrderId}";
    var $tabs=null;
    $j(document).ready(function(){
    	$tabs = $j("#salesOrderTab").appTabs({type:2});
    	$tabs.appTabs("select", ${(param.tabIndex>0 || param.tabIndex<5) ? param.tabIndex : 0} );
		btnToP();
    });
    
    function fnLock(){
    	window.location.href="${ctxPath}/order/window.html?doAction=lock&salesOrderId="+_salesOrderId+"&tabIndex="+fnGetSelectedTabIndex()+"&tid=${param.tid}";
    }
    
    function fnUnlock(){
    	window.location.href="${ctxPath}/order/window.html?doAction=unlock&salesOrderId="+_salesOrderId+"&tabIndex="+fnGetSelectedTabIndex()+"&tid=${param.tid}";
    }
    function fnGetSelectedTabIndex(){
    	var selected = $tabs.appTabs( "option", "selected" );
		return selected;
    }
    
    function fnSaveOrder(){
    	var formObj = document.getElementById("salesOrder");
    	if(!validateForm(formObj)){
			alert(__vaMsg.notPass);
			return;
		}
		if(confirm("确认要保存订单所有信息?")){
			formObj.action=__ctxPath+"/order/window.html?doAction=save&salesOrderId="+_salesOrderId+"&orderNo=${salesOrder.orderNo}&tid=${param.tid}";
			formObj.submit();
		}
    }
    <%-- Cancel Order --%>
    function fnCancelOrder(){
    	if(confirm("确认要取消订单?")){
    		$j.post(__ctxPath+"/order/salesOrder.html?doAction=cancelOrder&salesOrderId="+_salesOrderId+"&orderNo=${salesOrder.orderNo}&tabIndex=0&tid=${param.tid}", null, fnCancelOrderCallbackHandler, "json");
    	}
	}
	function fnCancelOrderCallbackHandler(result){
		sysMsg4p(result.msg,result.status!=1);
		if(result.status==1){
			view(0);
		}
    }
	<%-- Hold Order --%>
    function fnHoldOrder(){
		$j.post(__ctxPath+"/order/salesOrder.html?doAction=holdOrder&salesOrderId="+_salesOrderId+"&tabIndex=0&tid=${param.tid}", null, fnHoldOrderCallbackHandler, "json");
	}
	function fnHoldOrderCallbackHandler(result){
		sysMsg4p(result.msg,result.status!=1);
		if(result.status==1){
			view(0);
		}
    }
	<%-- Resume Order --%>
	 function fnResumeOrder(){
		$j.post(__ctxPath+"/order/salesOrder.html?doAction=resumeOrder&salesOrderId="+_salesOrderId+"&tabIndex=0&tid=${param.tid}", null, fnResumeOrderCallbackHandler, "json");
	}
	function fnResumeOrderCallbackHandler(result){
		sysMsg4p(result.msg,result.status!=1);
		if(result.status==1){
			view(0);
		}
    }
    <%--common area--%>    
    function view(tabIndex){
    	window.location.href="${ctxPath}/order/window.html?doAction=edit&salesOrderId="+_salesOrderId+"&tabIndex=" + tabIndex+"&tid=${param.tid}";
    }
    
	function fnShowDiv(divId){
		var jId = "#"+divId;
		if($j(jId).css("display")=="block")
			$j(jId).css("display", "none");
		else
			$j(jId).css("display", "block");
	}
     <%--end common area--%>     
    function fnResendConfirmEmail(btnObj){
    	btnObj.disabled=true;
    	$j.post(__ctxPath+"/order/salesOrder.html?doAction=sendReConfirmEmail&salesOrderId="+_salesOrderId, null, fnSendReConfirmEmailCallbackHandler, "json");
    }
    
    function fnSendReConfirmEmailCallbackHandler(result){
    	if(result.status==1)
    		sysMsg4p('<fmt:message key="salesOrder.resendConfirmEmail.successfully"><fmt:param value="${salesOrder.orderNo}"/></fmt:message>');
    	else
    		sysMsg4p('<fmt:message key="salesOrder.resendConfirmEmail.failed"><fmt:param value="${salesOrder.orderNo}"/></fmt:message>',true);
    }    
    <%-- 赠送礼券 --%>
    function fnCreateGc(){
    	dlgcreateGC_show();
    }
	function fnDoCreateGc(){
		var gcAmt = $j("#gcAmt").val();
		if (gcAmt == '')
		{
			alert("Input a Amount for Gift Certificate.");
			return false;
		}
		$j.post(__ctxPath+"/order/salesOrder.html?doAction=sendGcForSalesOrder&salesOrderId="+_salesOrderId+"&gcAmt="+gcAmt, null, fnCreateGcCallbackHandler, "json");
		dlgcreateGC_close();
    }
	function fnCreateGcCallbackHandler(result){
		sysMsg4p(result.msg,result.status!=1);
    }    
	<%-- 赠送礼券 end --%>
    <%-- Override preItem returnToSearch nextItem--%>
    function fnDoPrevItem(obj){
    	$j("#salesOrder").attr("action", "${ctxPath}/order/window.html?doAction=prevItem&isEdit=${param.isEdit}&salesOrderId="+_salesOrderId+"&tid=${param.tid}");
    	$j("#salesOrder").submit();
    }
    
    function fnDoNextItem(obj){
    	$j("#salesOrder").attr("action", "${ctxPath}/order/window.html?doAction=nextItem&isEdit=${param.isEdit}&salesOrderId="+_salesOrderId+"&tid=${param.tid}");
    	$j("#salesOrder").submit();
    }
</script>
<app:ui_dialog id="createGC" title="${sendGcTitle}" width="400" height="150"
		buttons="'save':fnDoCreateGc">
	<table>
		<tr>
			<th><fmt:message key="giftCertificateList.giftCertAmt"/></th>
			<td>
				<input type="text" id="gcAmt" name="gcAmt" value="" size="8" />
			</td>
		</tr>
		<tr>
			<td colspan="2">赠送的礼券将会发送到<app:maskEmail email="${salesOrder.customerEmail}"/></td>
		</tr>
	</table>
</app:ui_dialog>