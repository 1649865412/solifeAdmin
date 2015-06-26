<%
   /**
	 +----------------------------------------------------------------------+
	 |																		|
	 | The Config File for Paypal China PaymentGateway 						|
	 |																		|		
	 +----------------------------------------------------------------------+
	*/
 %>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"
	 import="com.cartmatic.estore.*"
	 contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<TITLE><fmt:message key="system.pay.body"/></TITLE>
<body id="mine">

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel"><fmt:message key="system.pay.body"/></td>
	    <td >
			<IMG alt="eWay" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/></td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB"><fmt:message key="system.pay.shuo"/></span>
			<ul style="padding-left:25px">
				<li><fmt:message key="system.pay.please"/><a href="http://www.paypal.com.cn" target="_blank" style="color:red;text-decoration: underline"><fmt:message key="system.pay.bao"/></a><fmt:message key="system.pay.kait"/></li>
				<li><fmt:message key="system.pay.guan"/></li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.email"/></td>
							<td>
								<INPUT class="Field400" name="business" value="${paymentMethod.configData.business}"/>
							</td>
						</tr>
						
						<tr>
							<td class="FieldLabel"><fmt:message key="system.pay.bizhong"/></td>
							<td>
								<select class="Field400" name="currency_code">
									<option value="CNY" <c:if test="${paymentMethod.configData.currency_code=='CNY'}">selected</c:if>><fmt:message key="system.pay.ren"/></option>
								 	<option value="USD" <c:if test="${paymentMethod.configData.currency_code=='USD'}">selected</c:if>><fmt:message key="system.pay.doller"/></option>
								</select>
							</td>
						</tr>
				
						<tr> 
							<td class="FieldLabel"><fmt:message key="system.pay.title3"/></td>
							<td>
								<select name="testModel" class="Field400">
									<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>><fmt:message key="system.pay.ceshi"/></option>
									<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>><fmt:message key="status.active"/></option>
								</select>
							</td>
						</tr> 
						<tr>
						  <td>&nbsp;</td>
						  <td  >
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>" />
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';"/>
						  </td>
					  </tr>
					</table>
				</form>
			
</body>