<%
   /**
	 +----------------------------------------------------------------------+
	 |																		|
	 | The Config File for AliPay PaymentGateway	 						|
	 |																		|		
	 +----------------------------------------------------------------------+
	*/
 %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE><fmt:message key="system.pay.zfb"/></TITLE>
</head>
<body id="mine">

	<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
	  <tr>
		<td class="FieldLabel"><br/>民生银行支付网关设置</td>
	    <td >
			<br/><IMG alt="<fmt:message key="system.pay.kkk"/>" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	
		</td>
	  </tr>
	  <tr>
	    <td colspan="2" align="left" style="padding-left:30px">
	    	<br/>
		</td>
	  </tr>
	</table>

	<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.email"/></th>
							<td>
								<INPUT class="Field400" style="WIDTH: 50%" name="seller_email" value="${paymentMethod.configData.seller_email}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">
								<fmt:message key="system.pay.hzid"/>
							</td>
							<td>
								<INPUT class="Field400" style="WIDTH: 50%" name="partner_id" value="${paymentMethod.configData.partner_id}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.jym"/></th>
							<td>
								<input class="Field400" style="WIDTH: 50%" name="key" value="${paymentMethod.configData.key}"/>
							</td>
						</tr>
						<tr>
							<td><fmt:message key="system.pay.zbz"/></td>
							<td><fmt:message key="system.pay.ren"/></td>
						</tr>
						<tr>
						  <td>&nbsp;</td>
						  <td align="left">
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit"  value="<fmt:message key="button.save"/>"/>
						  		&nbsp;
								<input class="btn-back" type="button"  value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';"/>
						  </td>
					 	</tr>
					  
				</table>
	</form>
			
</body>
