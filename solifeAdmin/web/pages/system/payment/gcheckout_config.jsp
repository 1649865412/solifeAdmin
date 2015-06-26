<%
	//Description: Google Checkout configuration page
	//CreateTime: 2008-03-05
	//Author: chenshangxuan
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<TITLE>Google Checkout Payment Gateway Configuration</TITLE>
</head>
<body>

	<table class="table-content" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td class="FieldLabel">Google Checkout Payment Configuration</td>
	    <td width="71%" align="right">
			<IMG alt="Google Checkout" src="<c:url value="/pages/system/${paymentMethod.paymentMethodIcon}"/>"/>
		</td>
	  </tr>
	  <tr>
	    <td align="left" colspan="2">
			<span class="fB">Note: </span>
			<ul style="padding-left:25px">
				<li>Please Regiseter a merchant account from <a href="http://checkout.google.com" target="_blank" style="color:red;text-decoration: underline">Google Checkout</a></li>
				<li>Fill the information below</li>
			</ul>
		</td>
	  </tr>
	</table>

				<form class="mainForm" name="configForm" method="post" action="paymentMethod.html">
					<table class="table-content" cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr> 
							<td class="FieldLabel">Merchant ID:</td>
							<td>
								<input type="text" name="merId" id="merId" value="${paymentMethod.configData.merId}" class="Field400" />
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">Merchant Key:</td>
							<td>
								<input type="text" name="merKey" id="merKey" class="Field400" value="${paymentMethod.configData.merKey}" />
							</td>
						</tr>
						<tr> 
							<td class="FieldLabel">Test/Live Mode:</td>
							<td>
								<select name="testModel" class="Field400">
									<option value="Y" <c:if test="${paymentMethod.configData.testModel=='Y'}">selected</c:if>>Test</option>
									<option value="N" <c:if test="${paymentMethod.configData.testModel=='N'}">selected</c:if>>Live</option>
								</select>
							</td>
						</tr>
						<tr>
						   <td>&nbsp;</td>
						  <td >
						  		<input type="hidden" name="paymentMethodId" value="${paymentMethod.paymentMethodId}"/>
								<input type="hidden" name="doAction" value="saveConfigAction"/>
						  		<input class="btn-back" type="submit" value="<fmt:message key="button.save"/>"/>
						  		&nbsp;
								<input class="btn-back" type="button" value="<fmt:message  key="button.cancel"/>" onclick="location.href='<c:url value="/system/paymentMethod.html"/>';">
						  </td>
					  	</tr> 
					</table>
				</form>
			
</body>
</html>
