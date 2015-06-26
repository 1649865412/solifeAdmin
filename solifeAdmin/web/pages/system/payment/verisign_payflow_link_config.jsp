<%
	/***********************************************************************************
	+ Desc: verisign payflow link configuration
	+
	+
	+
	***********************************************************************************/
%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
	<TITLE>Verisign Payflow Link Configuration</TITLE>
</head>
<body id="mine">

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel">Verisign Payflow Link Configuration</td>
	    <td width="71%" align="right">&nbsp;
			<IMG alt="verisign" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>	</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
				Please get a new account(VeriSign Payflow Link) from <a href="http://www.verisign.com/" target="_blank" style="color:red;text-decoration: underline">Verisign</a>
				In order to track your VeriSign Payflow Link orders by the shopping cart software you have to proceed these steps:
				Log in to your VeriSign account
				Go to the 'Account Info/Payflow Link Info' menu
				<ul style="padding-left:25px;">
					<li>Set the option 'Return URL Method' to 'POST'
					<li>Set the 'Return URL' to:
						${appConfig.store.siteUrl}/system/payment/finishPayment.html 
					<li>Put the tick into the 'Silent POST URL' checkbox and set text field to:
						${appConfig.store.siteUrl}/system/payment/verisign_payflow_link_response.html  
				</ul>
		</td>
	  </tr>
	</table>

			<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
				<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr>
						<td class="FieldLabel">Login:</td>
						<td>
							<INPUT class="Field400" name="login" value="${paymentMethod.configData.login}"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Partner:</td>
						<td>
							<input class="Field400" name="partner" value="${paymentMethod.configData.partner}"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">Server being used:</td>
						<td>
							<select name="server" class="Field400">
								<option value="USA" <c:if test="${paymentMethod.configData.server=='USA'}">selected</c:if> >United States</option>
								<option value="AU" <c:if test="${paymentMethod.configData.server=='AU'}">selected</c:if>>Australia</option>
							</select>
						</td>
					</tr>
					<tr>
					  <td>&nbsp;</td>
					  <td height="32px">
					  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
							<input type="hidden" name="doAction" value="saveConfigAction"/>
					  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>"/>
					  		&nbsp;
							<input class="btn-back" type="button" value="<fmt:message key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';"/>
					  </td>
				  </tr>
				</table>
			</form>
</body>