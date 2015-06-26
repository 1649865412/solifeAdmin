<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order"%>
<fmt:message key="orderReturnSku.reasonType.faulty" var="MSG_FUALTY"/>
<fmt:message key="orderReturnSku.reasonType.unwantedGift" var="MSG_UNWANTED_GIFT"/>
<fmt:message key="orderReturnSku.reasonType.incorrectItem" var="MSG_INCORRECT_ITEM"/>
<fmt:message key="orderReturnSku.reasonType.others" var="MSG_OTHERS"/>

<fmt:message key="return.received.status.perfect" var="MSG_PERFECT"/>
<fmt:message key="return.received.status.defective" var="MSG_DEFECTIVE"/>
<div id="editReturnMsgDiv">
</div>
<br/>
<form class="mainForm" id="editReturnForm" name="editReturnForm" method="post" action="${ctxPath}/order/orderReturn.html">
	<input id="orderReturnId" name="orderReturnId" type="hidden" value="${orderReturn.orderReturnId}"/>
	<input id="version" name="version" type="hidden" value="${orderReturn.version}"/>
	<input id="orderShipmentId" name="orderShipmentId" type="hidden" value="${orderShipmentId}"/>
	<c:if test="${completeReturn}">
		<input id="completeReturn" name="completeReturn" type="hidden" value="true"/>
	</c:if>
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
						<c:choose>
							<c:when test="${completeReturn}">
								<th width="10%"><fmt:message key="orderReturnSku.quantity" /></th>
								<th width="15%"><fmt:message key="orderReturnSku.reasonType" /></th>
								<th width="10%"><fmt:message key="orderReturnSku.receivedQuantity" /></th>
								<th><fmt:message key="orderReturnSku.receivedStatus" /></th>
							</c:when>
							<c:otherwise>
								<th width="13%"><fmt:message key="orderReturnSku.returnableQuantity" /></th>
								<th width="13%"><fmt:message key="orderReturnSku.quantity" /></th>
								<th><fmt:message key="orderReturnSku.reasonType" /></th>
							</c:otherwise>
						</c:choose>
					</tr>
					<c:forEach var="orderReturnSku" items="${orderReturn.orderReturnSkus}">
					<c:set var="orderReturnSkuId" value="${orderReturnSku.orderReturnSkuId}"/>
					<tr>
						<td style="text-align:center"><order:productLeadingInfo orderSku="${orderReturnSku.orderSku}"/></td>
						<c:choose>
							<c:when test="${completeReturn}">
								<td>${orderReturnSku.quantity}</td>
								<td>
									<c:choose>
										<c:when test="${orderReturnSku.reasonType eq 10}">${MSG_FUALTY}</c:when>
										<c:when test="${orderReturnSku.reasonType eq 20}">${MSG_UNWANTED_GIFT}</c:when>
										<c:when test="${orderReturnSku.reasonType eq 30}">${MSG_INCORRECT_ITEM}</c:when>
										<c:when test="${orderReturnSku.reasonType eq 40}">${MSG_OTHERS}</c:when>
									</c:choose>
								</td>
								<td>
									${orderReturnSku.receivedQuantity}
								</td>
								<td>
									<c:choose>
										<c:when test="${orderReturnSku.receivedStatus eq 0}">${MSG_PERFECT}</c:when>
										<c:when test="${orderReturnSku.receivedStatus eq 1}">${MSG_DEFECTIVE}</c:when>
									</c:choose>
								</td>
							</c:when>
							<c:otherwise>
								<td>${orderReturnSku.returnableQuantity}</td>
								<td><input onchange="fnCalculateReturnTotal();" id="returnQuantity${orderReturnSku.orderSku.orderSkuId}" name="returnQuantity${orderReturnSku.orderSku.orderSkuId}" type="text" class="input-form" value="${orderReturnSku.quantity}" size="3" validConf="required,integer,minValue=1,maxValue=${orderReturnSku.returnableQuantity}" onblur="validateEventHandler();"/></td>
								<td>
									<select id="reasonType${orderReturnSku.orderSku.orderSkuId}" name="reasonType${orderReturnSku.orderSku.orderSkuId}">
										<option value="10" <c:if test="${orderReturnSku.reasonType eq 10}">selected="true"</c:if>>${MSG_FUALTY}</option>
										<option value="20" <c:if test="${orderReturnSku.reasonType eq 20}">selected="true"</c:if>>${MSG_UNWANTED_GIFT}</option>
										<option value="30" <c:if test="${orderReturnSku.reasonType eq 30}">selected="true"</c:if>>${MSG_INCORRECT_ITEM}</option>
										<option value="40" <c:if test="${orderReturnSku.reasonType eq 40}">selected="true"</c:if>>${MSG_OTHERS}</option>
									</select>
								</td>
							</c:otherwise>
						</c:choose>
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
				<td width="13%" id="_itemSubTotal">${orderReturn.itemSubTotal}</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.shippingCost" /></td>
				<td width="13%" id="_shippingCost">${orderReturn.shippingCost}</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="salesOrder.hyphen.full.shape" /><fmt:message key="orderShipment.discountAmount" /></td>
				<td width="13%" id="_discountAmount">${orderReturn.discountAmount}</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr height="2px">
			  	<td colspan="2"><hr style="color: #ccc; height: 1px; border: 0; background:#ccc;"></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.totalBeforeTax" /></td>
				<td width="13%" id="_totalBeforeTax">
					<c:set var="totalBeforeTax" value="${orderReturn.itemSubTotal+orderReturn.shippingCost-orderReturn.discountAmount}"></c:set>
					${totalBeforeTax}
				</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderShipment.itemTax" /></td>
				<td width="13%" id="_itemTax">${orderReturn.itemTax}</td>
				<td>&nbsp;</td>
			  </tr>
			  <c:if test="${orderReturn.shippingTax gt 0}">
				  <tr>
					<td width="18%" align="right"><fmt:message key="orderShipment.shippingTax" /></td>
					<td width="13%" id="_shippingTax">${orderReturn.shippingTax}</td>
					<td>&nbsp;</td>
				  </tr>
			  </c:if>
			  <tr>
				<td width="18%" align="right"><fmt:message key="salesOrder.hyphen.full.shape" /><fmt:message key="orderReturn.lessRestockAmount" /></td>
				<td width="13%"><input onchange="fnCalculateReturnTotal();" id="lessRestockAmount" name="lessRestockAmount" type="text" class="input-form"  value="${orderReturn.lessRestockAmount}" size="8" validConf="money" onblur="validateEventHandler();"/></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr height="2px">
			  	<td colspan="2"><hr style="color: #ccc; height: 1px; border: 0; background:#ccc;"></td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td width="18%" align="right"><fmt:message key="orderReturn.returnTotalAmount" /></td>
				<td width="13%" id="_returnTotalAmount">${totalBeforeTax+orderReturn.itemTax+orderReturn.shippingTax-orderReturn.lessRestockAmount}</td>
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
				<textarea id="note" name="note" rows="5" cols="68">${orderReturn.note}</textarea>
			</td>
			<td width="1%">&nbsp;</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function fnCalculateReturnTotal(){
		if(!validateForm( document.getElementById("editReturnForm") )){
			return;
		}
		
		var parameters = $j("#editReturnForm").serialize();
		
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
