<%@ include file="/common/taglibs.jsp"%>
<c:set var="buttonCreatePayment"><fmt:message key="orderPayment.action.createPayment"></fmt:message></c:set>
<app:pageHeading pageHeadingKey="orderPayment.action.createPayment" />
<c:remove var="navigationList"/>
<c:set var="IS_FORM_ACTION" value="true"></c:set>
<content tag="heading">${buttonCreatePayment}</content>
<content tag="buttons">
	<cartmatic:cartmaticBtn inputName="createPayment" btnType="common" onclick="fnCreatePayment()" commonBtnValue="${buttonCreatePayment}" />
</content>
<form method="post" id="createPaymentForm" action="${ctxPath}/order/salesOrder.html">
	<input name="doAction" value="createPayment" type="hidden"/>
	<input type="hidden"  value="" id="paymentGatewayName" name="paymentGatewayName" />
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="orderPayment.viewPaymentDetail" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="salesOrder.orderNo"/><span class="req">(*)</span>:
			</td>
			<td>
				<input type="text" id="orderNo" name="orderNo" value="${orderNo}" class="Field200"/>
				<span style="padding:15px"></span>
				<cartmatic:iconBtn icon="book" textKey="orderPayment.viewPaymentDetail" onclick="p_viewPaymentDetail();" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="orderPayment.transactionType"/><span class="req">(*)</span>:
			</td>
			<td>
				<select id="transactionType" name="transactionType" class="Field200" onchange="fnChangeTransactionType(this)">
					<option value=""></option>
					<option value="1"><fmt:message key="salesOrder.paymentType_1"/></option>
					<option value="2"><fmt:message key="salesOrder.paymentType_2"/></option>
					<option value="3"><fmt:message key="salesOrder.paymentType_3"/></option>
					<option value="7"><fmt:message key="salesOrder.paymentType_7"/></option>
					<option value="9"><fmt:message key="salesOrder.paymentType_9"/></option>
					<option value="10"><fmt:message key="salesOrder.paymentType_10"/></option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="orderPayment.createPayment.amount"/><span class="req">(*)</span>:
			</td>
			<td>
				<input type="text" id="paymentAmount" name="paymentAmount" value="${salesOrder.totalAmount-salesOrder.paidAmount}" class="Field200"/>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="salesOrder.paymentStatus" />:
			</td>
			<td>
				<c:if test="${not empty salesOrder.paymentStatus}">
				<fmt:message key="payment.status.${salesOrder.paymentStatus}" /></c:if>&nbsp;
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="salesOrder.totalAmount" />:
			</td>
			<td id="totalAmount">
				${salesOrder.totalAmount}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="salesOrder.paidAmount" />:
			</td>
			<td id="paidAmount">
				${salesOrder.paidAmount}&nbsp;
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="salesOrder.dueAmount" />:
			</td>
			<td id="dueAmount">
				${salesOrder.totalAmount-salesOrder.paidAmount}&nbsp;
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content" id="westerUnion_form" style="display: none;">
		<tr>
			<th colspan="2">
				<fmt:message key="paymentType.wuTitle"/>
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_0"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuFirstName" class="Field200" maxlength="30" name="wuFirstName" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_1"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuLastName" class="Field200" maxlength="30" name="wuLastName" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_2"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuCountry" class="Field200" maxlength="30" name="wuCountry" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_3"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuCity" class="Field200" maxlength="30" name="wuCity" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_4"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuMtcn" class="Field200" maxlength="10" name="wuMtcn" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_5"/>:
			</td>
			<td id="dueAmount">
				<input type="text" value="" id="wuAmount" class="Field200" maxlength="10" name="wuAmount" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="paymentType.wuInfo_6"/>:
			</td>
			<td id="dueAmount">
				<input type="text" maxlength="10" value="" class="Field200" id="wuZip" name="wuZip" />
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th>
				支付历史
			</th>
		</tr>
		<tr>
			<td>
				<table cellspacing="0" cellpadding="0">
					<tr>
						<th width="20%"><fmt:message key="common.message.createTime"/></th>
						<th width="18%"><fmt:message key="orderPayment.transactionType"/></th>
						<th width="12%"><fmt:message key="orderPayment.paymentAmount"/></th>
						<th width="12%"><fmt:message key="orderPayment.balance"/></th>
						<th width="18%"><fmt:message key="orderPayment.giftCertificateNo"/>/<fmt:message key="orderPayment.paymentGatewayName"/></th>
						<th><fmt:message key="orderPayment.addedBy"/></th>
					</tr>
					<c:forEach var="orderPayment" items="${salesOrder.orderPayments}">
						<tr>
							<td><fmt:formatDate value="${orderPayment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:message key="salesOrder.paymentType_${orderPayment.transactionType}" /></td>
							<td><c:if test="${orderPayment.transactionType eq 6}">-</c:if>${orderPayment.paymentAmount}</td>
							<td>${orderPayment.balance}</td>
							<td>
								${orderPayment.giftCertificateNo}
								<c:if test="${orderPayment.transactionType eq 9}">
									<c:forTokens delims="," items="${orderPayment.paymentGatewayName}" varStatus="status" var="wuItem">
									<fmt:message key="paymentType.wuInfo_${status.index}"/>:&nbsp;${wuItem}<br/>
									</c:forTokens>
								</c:if>
							</td>
							<td>${orderPayment.addedBy}<br/>${orderPayment.ipAddress}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	applyValidate($("orderNo"),"required");
	applyValidate($("transactionType"),"required");
	applyValidate($("paymentAmount"),"required,money");
</script>

<script type="text/javascript">
	function fnChangeTransactionType($obj)
	{
		if($obj.value == 9)
		{
			$j("#westerUnion_form").show();
		}
		else
		{
			$j("#westerUnion_form").hide();
		}
	}

	function fnCreatePayment(){
		var formObj = document.getElementById("createPaymentForm");
		if(!validateForm( formObj )){
			alert(__vaMsg.notPass);
			return;
		}
		if ($("transactionType").value == 9)
		{
			$("paymentGatewayName").value = $("wuFirstName").value+","+ 
			                                $("wuLastName").value+","+
			                                $("wuCountry").value+","+
			                                $("wuCity").value+","+
			                                $("wuMtcn").value+","+
			                                $("wuAmount").value+","+
			                                $("wuZip").value;
		}
		formObj.submit();
	}
	
	function p_viewPaymentDetail(){
		var orderNo = $j("#orderNo").val();
		if(validateField($("orderNo"))!="") return;
		window.location.href = '${ctxPath}/order/salesOrder.html?doAction=getPaymentDetail&orderNo='+orderNo;
	}
</script>