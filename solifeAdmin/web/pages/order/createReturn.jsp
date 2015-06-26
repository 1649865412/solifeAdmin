<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order"%>
<fmt:message key="orderReturnSku.reasonType.faulty" var="MSG_FUALTY"/>
<fmt:message key="orderReturnSku.reasonType.unwantedGift" var="MSG_UNWANTED_GIFT"/>
<fmt:message key="orderReturnSku.reasonType.incorrectItem" var="MSG_INCORRECT_ITEM"/>
<fmt:message key="orderReturnSku.reasonType.others" var="MSG_OTHERS"/>
<div id="returnMsgDiv">
</div>
<br/>
<form class="mainForm" id="createReturnForm" name="createReturnForm" method="post" action="${ctxPath}/order/orderReturn.html">
	<input id="orderShipmentId" name="orderShipmentId" type="hidden" value="${orderShipment.orderShipmentId}"/>
	<table width="94%" cellspacing="0" cellpadding="0" border="0" align="center">
		<tr>
			<td colspan="3">
				<div class="top-title">
					<fmt:message key="order.detailInfo.items" />
				</div>
			</td>
		</tr>
		<tr>
			<td width="1%">&nbsp;</td>
			<td>
				<table width="98%" cellspacing="0" cellpadding="0" border="0" class="table-list" align="center">
					<tr>
						<th width="48%"><fmt:message key="orderSku.product" /></th>
						<th width="13%"><fmt:message key="orderReturnSku.returnableQuantity" /></th>
						<th width="13%"><fmt:message key="orderReturnSku.quantity" /></th>
						<th><fmt:message key="orderReturnSku.reasonType" /></th>
					</tr>
					<c:forEach var="orderSku" items="${orderSkus}">
					<tr>
						<td style="text-align:center"><order:productLeadingInfo orderSku="${orderSku}"/></td>
						<td>${orderSku.returnableQuantity}</td>
						<td><input onchange="fnCalculateReturnTotal();" id="returnQuantity${orderSku.orderSkuId}" name="returnQuantity${orderSku.orderSkuId}" type="text" class="input-form" value="0" size="3" validConf="required,integer,minValue=0,maxValue=${orderSku.returnableQuantity}" onblur="validateEventHandler();"/></td>
						<td>
							<select id="reasonType${orderSku.orderSkuId}" name="reasonType${orderSku.orderSkuId}">
								<option value="10">${MSG_FUALTY}</option>
								<option value="20">${MSG_UNWANTED_GIFT}</option>
								<option value="30">${MSG_INCORRECT_ITEM}</option>
								<option value="40">${MSG_OTHERS}</option>
							</select>
						</td>
					</tr>
					</c:forEach>					
				</table>
			</td>
			<td width="1%">&nbsp;</td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
		<td width="1%">&nbsp;</td>
		<td>
			<table width="98%" cellspacing="3" cellpadding="0" border="0" class="no-border">
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.itemSubTotal" /></td>
				<td width="13%" id="_itemSubTotal">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.shippingCost" /></td>
				<td width="13%" id="_shippingCost">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="salesOrder.hyphen.full.shape" /><fmt:message key="orderShipment.discountAmount" /></td>
				<td width="13%" id="_discountAmount">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr height="2px">
			  	<td colspan="2"><hr style="color: #ccc; height: 1px; border: 0; background:#ccc;"></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.totalBeforeTax" /></td>
				<td width="13%" id="_totalBeforeTax">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.itemTax" /></td>
				<td width="13%" id="_itemTax">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			  <c:if test="${false}">
				  <tr>
					<td width="18%" align="right"><fmt:message key="orderShipment.shippingTax" /></td>
					<td width="13%" id="_shippingTax">0.00</td>
					<td>&nbsp;</td>
				  </tr>
			  </c:if>
			  <tr>
				<td width="18%" align="right"><fmt:message key="salesOrder.hyphen.full.shape" /><fmt:message key="orderReturn.lessRestockAmount" /></td>
				<td width="13%"><input onchange="fnCalculateReturnTotal();" id="lessRestockAmount" name="lessRestockAmount" type="text" class="input-form"  value="0.00" size="8" validConf="money" onblur="validateEventHandler();"/></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr height="2px">
			  	<td colspan="2"><hr style="color: #ccc; height: 1px; border: 0; background:#ccc;"></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderReturn.returnTotalAmount" /></td>
				<td width="13%" id="_returnTotalAmount">0.00</td>
				<td>&nbsp;</td>
			  </tr>
			</table>
		</td>
		<td width="1%">&nbsp;</td>
		</tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td colspan="3">
				<div class="top-title">
					<fmt:message key="orderReturn.note" />
				</div>
			</td>
		</tr>
		<tr>
			<td width="1%">&nbsp;</td>
			<td>
				<textarea id="note" name="note" rows="5" cols="68"></textarea>
			</td>
			<td width="1%">&nbsp;</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function fnCalculateReturnTotal(){
		if(!validateForm( document.getElementById("createReturnForm") )){
			alert(__vaMsg.notPass);
			return;
		}
		
		var parameters = $j("#createReturnForm").serialize();
		
		$j.post(__ctxPath+'/order/orderReturn.html?doAction=calculateReturnTotal', parameters, fnFillReturnTotal, "json")
	}
	
	function fnFillReturnTotal(result){
		if(result.status==1){
			var data=result.data;
			$j("#_itemSubTotal").html(data.itemSubTotal);
			$j("#_shippingCost").html(data.shippingCost);
			$j("#_discountAmount").html(data.discountAmount);
			$j("#_totalBeforeTax").html(data.totalBeforeTax);
			
			$j("#_itemTax").html(data.itemTax);
			if($j("#_shippingTax"))
				$j("#_shippingTax").html(data.shippingTax);
			
			$j("#_returnTotalAmount").html(data.returnTotalAmount);
		}else{
			alert(result.msg);
		}
	}
	
</script>
