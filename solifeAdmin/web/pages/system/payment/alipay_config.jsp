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
		<td class="FieldLabel"><fmt:message key="system.pay.xxx"/></td>
	    <td >
			<IMG alt="<fmt:message key="system.pay.kkk"/>" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	
		</td>
	  </tr>
	  <tr>
	    <td colspan="2" align="left" style="padding-left:30px">
	    	<fmt:message key="system.pay.shuo"/><br/>
			<fmt:message key="system.pay.syzb"/><a target="_blank" href="http://www.alipay.com" style="text-decoration: underline;color:red;"><fmt:message key="system.pay.kkk"/></a><fmt:message key="system.pay.out"/>
				<div style="text-indent:40px">
					<ul>	
						<li><fmt:message key="system.pay.notice"/>
						<li><fmt:message key="system.pay.liuc"/>
					</ul>
				<div>
		</td>
	  </tr>
	</table>

	<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.email"/></th>
							<td>
								<INPUT class="Field400" style="WIDTH: 50%" name="merchant" value="${paymentMethod.configData.merchant}"/>
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">
								<fmt:message key="system.pay.hzid"/>
							</td>
							<td>
								<INPUT class="Field400" style="WIDTH: 50%" name="partnerId" value="${paymentMethod.configData.partnerId}"/>
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
